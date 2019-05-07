package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.DynaCacheAdaptor;
import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.RouteModel;
import hu.thom.mileit.models.UserModel;

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
		assignedObjects.put(UIKeys.PAGE, "routes");
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
			userRoutesKey = user.getUsername() + "_" + UIKeys.ROUTES;
			userCarsKey = user.getUsername() + "_" + UIKeys.CARS;
			userPlacesKey = user.getUsername() + "_" + UIKeys.PLACES;

			parseMode(request);

			if (dc.get(userCarsKey) == null) {
				dc.put(userCarsKey, dbm.getCars(user.getId(), sm), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIKeys.CARS, dc.get(userCarsKey));

			if (dc.get(userPlacesKey) == null) {
				dc.put(userPlacesKey, dbm.getPlaces(user.getId()), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIKeys.PLACES, dc.get(userPlacesKey));

			if (dc.get(userRoutesKey) == null) {
				dc.put(userRoutesKey, dbm.getRoutes(user.getId()), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIKeys.ROUTES, dc.get(userRoutesKey));

			switch (m) {
			case UIKeys.MODE_NEW:
				validationMessages.clear();
				assignedObjects.remove(UIKeys.ROUTES);
				renderPage(ROUTES_FORM, request, response);
				break;

			case UIKeys.MODE_DELETE:
				parseId(request);

				assignedObjects.put(UIKeys.STATUS, dbm.deleteRoute(id) ? 2 : -1);
				assignedObjects.put(UIKeys.ROUTES, dbm.getRoutes(user.getId()));
				renderPage(ROUTES, request, response);

				break;

			case UIKeys.MODE_UPDATE:
				parseId(request);

				RouteModel r = dbm.getRoute(id);
				if (r != null) {
					assignedObjects.put(UIKeys.ROUTES, r);
					renderPage(ROUTES_FORM, request, response);
				} else {
					assignedObjects.put(UIKeys.STATUS, -1);
					renderPage(ROUTES, request, response);
				}

				break;
			case UIKeys.MODE_:
			case UIKeys.MODE_CANCEL:
			default:
				assignedObjects.remove(UIKeys.STATUS);
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
			userRoutesKey = user.getUsername() + "_" + UIKeys.ROUTES;
			
			parseMode(request);

			checkValidationMessages(UIKeys.FORM_ME_ROUTE, validationMessages, request);

			if (validationMessages.isEmpty()) {
				RouteModel r = new RouteModel(request.getParameterMap(), user);

				switch (m) {
				case UIKeys.MODE_NEW:
					System.out.println("Round-trip: " + request.getParameter("roundTrip") != null ? true : false);
					r.setRoundTrip(request.getParameter("roundTrip") != null ? true : false);
					r.setOperation(0);
					break;
				case UIKeys.MODE_UPDATE:
					parseId(request);
					r.setOperation(1);
					r.setId(id);
					break;
				case UIKeys.MODE_:
				case UIKeys.MODE_CANCEL:
				default:
					break;
				}

				assignedObjects.put(UIKeys.STATUS, dbm.createUpdateRoute(r) ? 0 : -1);
				dc.put(userRoutesKey, dbm.getRoutes(user.getId()), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIKeys.ROUTES, dbm.getRoutes(user.getId()));
				renderPage(ROUTES, request, response);
			} else {
				assignedObjects.put(UIKeys.STATUS, -2);
				assignedObjects.put(UIKeys.CARS, dbm.getCars(user.getId(), sm));
				assignedObjects.put(UIKeys.PLACES, dbm.getPlaces(user.getId()));
				renderPage(ROUTES_FORM, request, response);
			}
		}
	}

}
