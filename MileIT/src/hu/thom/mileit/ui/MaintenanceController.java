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
import hu.thom.mileit.models.MaintenanceModel;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

/**
 * Servlet class to manage maintenance related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/maintenance")
public class MaintenanceController extends Controller {
	private static final long serialVersionUID = 2199011911238724358L;
	
	private String userMaintenanceKey = null;
	private String userCarsKey = null;
	private String userPaymentsKey = null;

	/**
	 * Constructor
	 */
	public MaintenanceController() {
		super();
		assignedObjects.put(UIBindings.PAGE, "maintenance");
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
			response.sendRedirect("login");
		} else {
			userMaintenanceKey = user.getUsername() + "_" + UIBindings.MAINTENANCES;
			userCarsKey = user.getUsername() + "_" + UIBindings.CARS;
			userPaymentsKey = user.getUsername() + "_" + UIBindings.PMS;
			
			parseMode(request);
			
			if (dc.get(userCarsKey) == null) {
				dc.put(userCarsKey, db.getCars(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.CARS, dc.get(userCarsKey));
			
			if (dc.get(userPaymentsKey) == null) {
				dc.put(userPaymentsKey, db.getPaymentMethods(user.getId()), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.PMS, dc.get(userPaymentsKey));
			
			if (dc.get(userMaintenanceKey) == null) {
				dc.put(userMaintenanceKey, db.getMaintenances(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.MAINTENANCES, dc.get(userMaintenanceKey));

			switch (m) {
			case UIBindings.MODE_NEW:
				validationMessages.clear();
				assignedObjects.remove(UIBindings.MAINTENANCES);
				renderPage(MAINTENANCES_FORM, request, response);
				break;

			case UIBindings.MODE_UPDATE:
				parseId(request);

				MaintenanceModel mm = db.getMaintenance(id);
				if (mm != null) {
					assignedObjects.put(UIBindings.MAINTENANCES, mm);
					renderPage(MAINTENANCES_FORM, request, response);
				} else {
					assignedObjects.put(UIBindings.STATUS, -1);
					renderPage(MAINTENANCES, request, response);
				}
				break;
			case UIBindings.MODE_:
			case UIBindings.MODE_CANCEL:
			default:
				assignedObjects.remove(UIBindings.STATUS);
				renderPage(MAINTENANCES, request, response);
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
			response.sendRedirect("login");
		} else {
			userMaintenanceKey = user.getUsername() + "_" + UIBindings.MAINTENANCES;
			
			parseMode(request);

			checkValidationMessages(UIBindings.FORM_ME_MAINTENANCE, validationMessages, request);

			assignedObjects.put(UIBindings.CARS, db.getCars(user.getId(), em));
			assignedObjects.put(UIBindings.PMS, db.getPaymentMethods(user.getId()));

			if (validationMessages.isEmpty()) {
				MaintenanceModel mm = new MaintenanceModel(request.getParameterMap(), user);

				switch (m) {
				case UIBindings.MODE_NEW:
					mm.setOperation(0);
					break;
				case UIBindings.MODE_UPDATE:
					parseId(request);
					mm.setOperation(1);
					mm.setId(id);
					break;
				case UIBindings.MODE_:
				case UIBindings.MODE_CANCEL:
				default:
					break;
				}

				assignedObjects.put(UIBindings.STATUS, db.createUpdateMaintenance(mm) ? 1 : -1);
				dc.put(userMaintenanceKey, db.getMaintenances(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIBindings.MAINTENANCES, dc.get(userMaintenanceKey));
				renderPage(MAINTENANCES, request, response);

			} else {
				assignedObjects.put(UIBindings.STATUS, -2);
				renderPage(MAINTENANCES_FORM, request, response);
			}
		}
	}

}
