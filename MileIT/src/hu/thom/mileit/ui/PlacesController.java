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
 * Servlet class to manage place related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/places")
public class PlacesController extends Controller {
	private static final long serialVersionUID = 2631517143636421486L;

	/**
	 * Constructor
	 */
	public PlacesController() {
		super();
		assignedObjects.put(UIKeys.PAGE, "locations");
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

		assignedObjects.put(UIKeys.PLACES, dbm.getPlaces(user.getId()));

		switch (m) {
		case UIKeys.MODE_NEW:
			validationMessages.clear();
			assignedObjects.remove(UIKeys.PLACES);
			renderPage(PLACES_FORM, request, response);
			break;

		case UIKeys.MODE_ARCHIVE:
			parseId(request);

			assignedObjects.put(UIKeys.STATUS, dbm.archivePlace(id) ? 1 : -1);
			assignedObjects.put(UIKeys.PLACES, dbm.getPlaces(user.getId()));
			renderPage(PLACES, request, response);

			break;

		case UIKeys.MODE_UPDATE:
			parseId(request);

			PlaceModel l = dbm.getPlace(id);
			if (l != null) {
				assignedObjects.put(UIKeys.LOAD_MAPS, 1);
				assignedObjects.put(UIKeys.PLACES, l);
				renderPage(PLACES_FORM, request, response);
			} else {
				assignedObjects.put(UIKeys.STATUS, -1);
				renderPage(PLACES, request, response);
			}
			break;
		case UIKeys.MODE_:
		case UIKeys.MODE_CANCEL:
		default:
			assignedObjects.remove(UIKeys.STATUS);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);

		parseMode(request);

		checkValidationMessages(UIKeys.FORM_ME_PLACE, validationMessages, request);

		if (validationMessages.isEmpty()) {
			PlaceModel l = new PlaceModel(request.getParameterMap(), user);

			switch (m) {
			case UIKeys.MODE_NEW:
				l.setOperation(0);
				break;
			case UIKeys.MODE_UPDATE:
				parseId(request);
				l.setOperation(1);
				l.setId(id);
				break;
			case UIKeys.MODE_:
			case UIKeys.MODE_CANCEL:
			default:
				break;
			}

			assignedObjects.put(UIKeys.STATUS, dbm.createUpdatePlace(l) ? 1 : -1);
			assignedObjects.put(UIKeys.PLACES, dbm.getPlaces(user.getId()));
			renderPage(PLACES, request, response);
		} else {
			assignedObjects.put(UIKeys.LOAD_MAPS, 1);
			assignedObjects.put(UIKeys.STATUS, -2);
			renderPage(PLACES_FORM, request, response);
		}
	}
}
