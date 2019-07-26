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
import hu.thom.mileit.models.PlaceModel;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

/**
 * Servlet class to manage place related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/places")
public class PlacesController extends Controller {
	private static final long serialVersionUID = 2631517143636421486L;
	
	String userPlacesKey = null;

	/**
	 * Constructor
	 */
	public PlacesController() {
		super();
		assignedObjects.put(UIBindings.PAGE, "locations");
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
		user = (UserModel) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login");
		} else {
			userPlacesKey = user.getUsername() + "_" + UIBindings.PLACES;

			parseMode(request);

			if (dc.get(userPlacesKey) == null) {
				dc.put(userPlacesKey, db.getPlaces(user.getId()), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.PLACES, dc.get(userPlacesKey));

			switch (m) {
			case UIBindings.MODE_NEW:
				validationMessages.clear();
				assignedObjects.remove(UIBindings.PLACES);
				renderPage(PLACES_FORM, request, response);
				break;

			case UIBindings.MODE_ARCHIVE:
				parseId(request);

				assignedObjects.put(UIBindings.STATUS, db.archivePlace(id) ? 1 : -1);
				assignedObjects.put(UIBindings.PLACES, db.getPlaces(user.getId()));
				renderPage(PLACES, request, response);

				break;

			case UIBindings.MODE_UPDATE:
				parseId(request);

				PlaceModel l = db.getPlace(id);
				if (l != null) {
					assignedObjects.put(UIBindings.LOAD_MAPS, 1);
					assignedObjects.put(UIBindings.PLACES, l);
					renderPage(PLACES_FORM, request, response);
				} else {
					assignedObjects.put(UIBindings.STATUS, -1);
					renderPage(PLACES, request, response);
				}
				break;
			case UIBindings.MODE_:
			case UIBindings.MODE_CANCEL:
			default:
				assignedObjects.remove(UIBindings.STATUS);
				renderPage(PLACES, request, response);
				break;
			}
		}
	}

	/**
	 * Method to manage HTTP POST method.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		user = (UserModel) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login");
		} else {
			userPlacesKey = user.getUsername() + "_" + UIBindings.PLACES;
			
			parseMode(request);

			checkValidationMessages(UIBindings.FORM_ME_PLACE, validationMessages, request);

			if (validationMessages.isEmpty()) {
				PlaceModel l = new PlaceModel(request.getParameterMap(), user);

				switch (m) {
				case UIBindings.MODE_NEW:
					l.setOperation(0);
					break;
				case UIBindings.MODE_UPDATE:
					parseId(request);
					l.setOperation(1);
					l.setId(id);
					break;
				case UIBindings.MODE_:
				case UIBindings.MODE_CANCEL:
				default:
					break;
				}

				assignedObjects.put(UIBindings.STATUS, db.createUpdatePlace(l) ? 1 : -1);
				dc.put(userPlacesKey, db.getPlaces(user.getId()), DynaCacheManager.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIBindings.PLACES, dc.get(userPlacesKey));
				renderPage(PLACES, request, response);
			} else {
				assignedObjects.put(UIBindings.LOAD_MAPS, 1);
				assignedObjects.put(UIBindings.STATUS, -2);
				renderPage(PLACES_FORM, request, response);
			}
		}
	}
}
