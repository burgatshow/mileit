package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.PlaceModel;

/**
 * Servlet class to manage tyres related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/tyres")
public class TyreController extends Controller {
	private static final long serialVersionUID = 2631517143636421486L;

	/**
	 * Constructor
	 */
	public TyreController() {
		super();
		assignedObjects.put(UIKeys.PAGE, "tyres");
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
		

		parseMode(request);

		switch (m) {
		case "new":
			request.getRequestDispatcher(PLACES_FORM).forward(request, response);
			break;

		case "update":
			parseId(request);

			PlaceModel l = dbm.getPlace(id);
			if (l != null) {
				request.setAttribute("location", l);
				request.getRequestDispatcher(PLACES_FORM).forward(request, response);
			} else {
				request.setAttribute("status", -1);
				request.setAttribute("locations", dbm.getPlaces(user.getId()));
				request.getRequestDispatcher(PLACES).forward(request, response);
			}
			break;
		case "":
		case "cancel":
		default:
			request.setAttribute("locations", dbm.getPlaces(user.getId()));
			request.getRequestDispatcher(PLACES).forward(request, response);
			break;
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

		parseMode(request);

		String[] mustElements = { "name" };
		for (String key : mustElements) {
			if (request.getParameter(key) == null || "".equalsIgnoreCase(request.getParameter(key))) {
				validationMessages.add(key);
			} else {
				validationMessages.remove(key);
			}
		}

		if (validationMessages.isEmpty()) {
			PlaceModel l = new PlaceModel();
			l.setUser(user);

			l.setName(request.getParameter("name"));
			l.setAddress(request.getParameter("address"));
			l.setLatitude(request.getParameter("latitude"));
			l.setLongitude(request.getParameter("longitude"));

			switch (m) {
			case "new":
				l.setOperation(0);

				if (dbm.createUpdatePlace(l)) {
					request.setAttribute("status", 0);
				} else {
					request.setAttribute("status", -1);
				}

				request.setAttribute("locations", dbm.getPlaces(user.getId()));
				request.getRequestDispatcher(PLACES).forward(request, response);

				break;

			case "update":
				parseId(request);

				l.setOperation(1);
				l.setId(id);
				
//				assignedObjects.put(UIKeys.STATUS, dbm.create);
//				dbm.createUpdatePlace(l) ? 

				if (dbm.createUpdatePlace(l)) {
					request.setAttribute(UIKeys.STATUS, 1);
				} else {
					request.setAttribute(UIKeys.STATUS, -1);
				}

				request.setAttribute("locations", dbm.getPlaces(user.getId()));
				request.getRequestDispatcher(PLACES).forward(request, response);
				break;

			case "":
			default:
				break;
			}
		} else {
			request.setAttribute(UIKeys.STATUS, -2);
			request.getRequestDispatcher(PLACES_FORM).forward(request, response);
		}
	}
}
