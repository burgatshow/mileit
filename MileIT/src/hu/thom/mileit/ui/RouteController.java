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
import hu.thom.mileit.models.RouteModel;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

/**
 * Servlet class to manage route related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/routes")
public class RouteController extends Controller {
	private static final long serialVersionUID = -1721075590725598848L;

	private String userRoutesKey = null;
	private String userCarsKey = null;
	private String userPlacesKey = null;

	/**
	 * Constructor
	 */
	public RouteController() {
		super();
		assignedObjects.put(UIBindings.PAGE, "routes");
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
			userRoutesKey = user.getUsername() + "_" + UIBindings.ROUTES;
			userCarsKey = user.getUsername() + "_" + UIBindings.CARS;
			userPlacesKey = user.getUsername() + "_" + UIBindings.PLACES;

			parseMode(request);

			if (dc.get(userCarsKey) == null) {
				dc.put(userCarsKey, db.getCars(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.CARS, dc.get(userCarsKey));

			if (dc.get(userPlacesKey) == null) {
				dc.put(userPlacesKey, db.getPlaces(user.getId()), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.PLACES, dc.get(userPlacesKey));

			if (dc.get(userRoutesKey) == null) {
				dc.put(userRoutesKey, db.getRoutes(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.ROUTES, dc.get(userRoutesKey));

			switch (m) {
			case UIBindings.MODE_NEW:
				validationMessages.clear();
				assignedObjects.remove(UIBindings.ROUTES);
				renderPage(ROUTES_FORM, request, response);
				break;

			case UIBindings.MODE_DELETE:
				parseId(request);

				assignedObjects.put(UIBindings.STATUS, db.deleteRoute(id) ? 2 : -1);
				assignedObjects.put(UIBindings.ROUTES, db.getRoutes(user.getId(), em));
				renderPage(ROUTES, request, response);

				break;

			case UIBindings.MODE_UPDATE:
				parseId(request);

				RouteModel r = db.getRoute(id);
				if (r != null) {
					assignedObjects.put(UIBindings.ROUTES, r);
					renderPage(ROUTES_FORM, request, response);
				} else {
					assignedObjects.put(UIBindings.STATUS, -1);
					renderPage(ROUTES, request, response);
				}

				break;
			case UIBindings.MODE_:
			case UIBindings.MODE_CANCEL:
			default:
				assignedObjects.remove(UIBindings.STATUS);
				renderPage(ROUTES, request, response);
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
		user = (UserModel) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login");
		} else {
			userRoutesKey = user.getUsername() + "_" + UIBindings.ROUTES;

			parseMode(request);

			checkValidationMessages(UIBindings.FORM_ME_ROUTE, validationMessages, request);

			if (validationMessages.isEmpty()) {
				RouteModel r = new RouteModel(request.getParameterMap(), user);

				switch (m) {
				case UIBindings.MODE_NEW:
					System.out.println("Round-trip: " + request.getParameter("roundTrip") != null ? true : false);
					r.setRoundTrip(request.getParameter("roundTrip") != null ? true : false);
					r.setOperation(0);
					break;
				case UIBindings.MODE_UPDATE:
					parseId(request);
					r.setOperation(1);
					r.setId(id);
					break;
				case UIBindings.MODE_:
				case UIBindings.MODE_CANCEL:
				default:
					break;
				}

				assignedObjects.put(UIBindings.STATUS, db.createUpdateRoute(r) ? 0 : -1);
				dc.put(userRoutesKey, db.getRoutes(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIBindings.ROUTES, db.getRoutes(user.getId(), em));
				renderPage(ROUTES, request, response);
			} else {
				assignedObjects.put(UIBindings.STATUS, -2);
				assignedObjects.put(UIBindings.CARS, db.getCars(user.getId(), em));
				assignedObjects.put(UIBindings.PLACES, db.getPlaces(user.getId()));
				renderPage(ROUTES_FORM, request, response);
			}
		}
	}

}
