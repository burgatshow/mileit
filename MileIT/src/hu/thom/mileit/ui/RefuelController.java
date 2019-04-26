package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.RefuelModel;

/**
 * Servlet class to manage refuel related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/refuels")
public class RefuelController extends Controller {
	private static final long serialVersionUID = 5999291452665397698L;

	/**
	 * Constructor
	 */
	public RefuelController() {
		super();
		assignedObjects.put(UIKeys.PAGE, "refuels");
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

		assignedObjects.put(UIKeys.CARS, dbm.getCars(user.getId()));
		assignedObjects.put(UIKeys.PLACES, dbm.getPlaces(user.getId()));
		assignedObjects.put(UIKeys.PMS, dbm.getPaymentMethods(user.getId()));
		assignedObjects.put(UIKeys.REFUELS, dbm.getRefuels(user.getId()));

		switch (m) {
		case UIKeys.MODE_NEW:
			validationMessages.clear();
			assignedObjects.remove(UIKeys.REFUELS);
			renderPage(REFUELS_FORM, request, response);
			break;
			
		case UIKeys.MODE_DELETE:
			parseId(request);

			assignedObjects.put(UIKeys.STATUS, dbm.deleteRefuel(id) ? 2 : -1);
			assignedObjects.put(UIKeys.REFUELS, dbm.getRefuels(user.getId()));
			renderPage(REFUELS, request, response);

			break;

		case UIKeys.MODE_UPDATE:
			parseId(request);

			RefuelModel rf = dbm.getRefuel(id);
			if (rf != null) {
				assignedObjects.put(UIKeys.REFUELS, rf);
				renderPage(REFUELS_FORM, request, response);
			} else {
				assignedObjects.put(UIKeys.STATUS, -1);
				renderPage(REFUELS, request, response);
			}
			break;
		case UIKeys.MODE_:
		case UIKeys.MODE_CANCEL:
		default:
			assignedObjects.remove(UIKeys.STATUS);
			renderPage(REFUELS, request, response);
			break;
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

		parseMode(request);

		checkValidationMessages(UIKeys.FORM_ME_REFUEL, validationMessages, request);

		if (validationMessages.isEmpty()) {
			RefuelModel rf = new RefuelModel(request.getParameterMap(), user);

			switch (m) {
			case UIKeys.MODE_NEW:
				rf.setOperation(0);
				break;
			case UIKeys.MODE_UPDATE:
				parseId(request);
				rf.setOperation(1);
				rf.setId(id);
				break;
			case UIKeys.MODE_:
			case UIKeys.MODE_CANCEL:
			default:
				break;
			}
			
			request.getSession().removeAttribute("fuelStats");

			assignedObjects.put(UIKeys.STATUS, dbm.createUpdateRefuel(rf) ? 1 : -1);
			assignedObjects.put(UIKeys.REFUELS, dbm.getRefuels(user.getId()));
			renderPage(REFUELS, request, response);
		} else {
			assignedObjects.put(UIKeys.STATUS, -2);
			assignedObjects.put(UIKeys.CARS, dbm.getCars(user.getId()));
			assignedObjects.put(UIKeys.PLACES, dbm.getPlaces(user.getId()));
			assignedObjects.put(UIKeys.PMS, dbm.getPaymentMethods(user.getId()));
			renderPage(REFUELS_FORM, request, response);
		}
	}
}