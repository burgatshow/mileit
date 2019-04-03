package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.models.PlaceModel;

/**
 * Servlet class to manage place related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/locations")
public class PlaceController extends Controller {
	private static final long serialVersionUID = 2631517143636421486L;

	/**
	 * Constructor
	 */
	public PlaceController() {
		super();
		assignedObjects.put("page", "locations");
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);

		parseMode(request);

		assignedObjects.put("locations", dbm.getPlaces(user.getId()));

		switch (m) {
		case "new":
			renderPage(PLACES_FORM, request, response);
			break;

		case "update":
			parseId(request);

			PlaceModel l = dbm.getPlace(id);
			if (l != null) {
				assignedObjects.put("location", l);
				renderPage(PLACES_FORM, request, response);
			} else {
				assignedObjects.put("status", -1);
				renderPage(PLACES, request, response);
			}
			break;
		case "":
		case "cancel":
		default:
			renderPage(PLACES, request, response);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
				break;
			case "update":
				parseId(request);
				l.setOperation(1);
				l.setId(id);
				break;
			case "":
			default:
				break;
			}
			
			assignedObjects.put("status", dbm.createUpdatePlace(l) ? 1 : -1);
			assignedObjects.put("locations", dbm.getPlaces(user.getId()));
			renderPage(PLACES, request, response);
		} else {
			assignedObjects.put("status", -2);
			renderPage(PLACES_FORM, request, response);
		}
	}
}
