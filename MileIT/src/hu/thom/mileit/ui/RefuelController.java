/**
 * MIT License
 *
 * Copyright (c) 2019 Tamas BURES
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.models.RefuelModel;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

/**
 * Servlet class to manage refuel related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/refuels")
public class RefuelController extends Controller {
	private static final long serialVersionUID = 5999291452665397698L;

	String userPaymentsKey = null;
	String userCarsKey = null;
	String userPlacesKey = null;
	String userRefuelsKey = null;

	/**
	 * Constructor
	 */
	public RefuelController() {
		super();
		assignedObjects.put(UIBindings.PAGE, UIBindings.REFUELS);
	}

	/**
	 * Init method for this servlet
	 * 
	 * @see HttpServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
	}

	/**
	 * Method to manage HTTP GET method.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);

		user = (UserModel) request.getSession().getAttribute(UIBindings.USER);
		if (user == null) {
			response.sendRedirect(UIBindings.LOGIN);
		} else {
			userPaymentsKey = user.getUsername() + "_" + UIBindings.PMS;
			userCarsKey = user.getUsername() + "_" + UIBindings.CARS;
			userPlacesKey = user.getUsername() + "_" + UIBindings.PLACES;
			userRefuelsKey = user.getUsername() + "_" + UIBindings.REFUELS;

			parseMode(request);

			if (dc.get(userCarsKey) == null) {
				dc.put(userCarsKey, db.getCars(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.CARS, dc.get(userCarsKey));

			if (dc.get(userPlacesKey) == null) {
				dc.put(userPlacesKey, db.getPlaces(user.getId()), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.PLACES, dc.get(userPlacesKey));

			if (dc.get(userPaymentsKey) == null) {
				dc.put(userPaymentsKey, db.getPaymentMethods(user.getId()), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.PMS, dc.get(userPaymentsKey));

			if (dc.get(userRefuelsKey) == null) {
				dc.put(userRefuelsKey, db.getRefuels(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.REFUELS, dc.get(userRefuelsKey));

			switch (m) {
			case UIBindings.MODE_NEW:
				validationMessages.clear();
				assignedObjects.remove(UIBindings.REFUELS);
				renderPage(REFUELS_FORM, request, response);
				break;

			case UIBindings.MODE_DELETE:
				parseId(request);

				assignedObjects.put(UIBindings.STATUS, db.deleteRefuel(id) ? 2 : -1);
				assignedObjects.put(UIBindings.REFUELS, db.getRefuels(user.getId(), em));
				renderPage(REFUELS, request, response);

				break;

			case UIBindings.MODE_UPDATE:
				parseId(request);

				RefuelModel rf = db.getRefuel(id);
				if (rf != null) {
					assignedObjects.put(UIBindings.REFUELS, rf);
					renderPage(REFUELS_FORM, request, response);
				} else {
					assignedObjects.put(UIBindings.STATUS, -1);
					renderPage(REFUELS, request, response);
				}
				break;
			case UIBindings.MODE_:
			case UIBindings.MODE_CANCEL:
			default:
				assignedObjects.remove(UIBindings.STATUS);
				renderPage(REFUELS, request, response);
				break;
			}
		}
	}

	/**
	 * Method to manage HTTP POST method.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		user = (UserModel) request.getSession().getAttribute(UIBindings.USER);
		if (user == null) {
			response.sendRedirect(UIBindings.LOGIN);
		} else {
			userRefuelsKey = user.getUsername() + "_" + UIBindings.REFUELS;

			parseMode(request);

			checkValidationMessages(UIBindings.FORM_ME_REFUEL, validationMessages, request);

			if (validationMessages.isEmpty()) {
				RefuelModel rf = new RefuelModel(request.getParameterMap(), user);

				switch (m) {
				case UIBindings.MODE_NEW:
					rf.setOperation(0);
					break;
				case UIBindings.MODE_UPDATE:
					parseId(request);
					rf.setOperation(1);
					rf.setId(id);
					break;
				case UIBindings.MODE_:
				case UIBindings.MODE_CANCEL:
				default:
					break;
				}

				assignedObjects.put(UIBindings.STATUS, db.createUpdateRefuel(rf) ? 1 : -1);
				dc.put(userRefuelsKey, db.getRefuels(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIBindings.REFUELS, db.getRefuels(user.getId(), em));
				dc.invalidate(UIBindings.LAST_REFUEL);
				dc.invalidate(UIBindings.FUEL_STATS);
				renderPage(REFUELS, request, response);
			} else {
				assignedObjects.put(UIBindings.STATUS, -2);
				assignedObjects.put(UIBindings.CARS, db.getCars(user.getId(), em));
				assignedObjects.put(UIBindings.PLACES, db.getPlaces(user.getId()));
				assignedObjects.put(UIBindings.PMS, db.getPaymentMethods(user.getId()));
				renderPage(REFUELS_FORM, request, response);
			}
		}
	}
}