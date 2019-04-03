package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.models.CarModel;
import hu.thom.mileit.models.PlaceModel;
import hu.thom.mileit.models.PaymentMethodModel;
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
		assignedObjects.put("page", "refuels");
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

		assignedObjects.put("cars", dbm.getCars(user.getId()));
		assignedObjects.put("locations", dbm.getPlaces(user.getId()));
		assignedObjects.put("paymentMethods", dbm.getPaymentMethods(user.getId()));
		assignedObjects.put("refuels", dbm.getRefuels(user.getId()));

		switch (m) {
		case "new":
			renderPage(REFUELS_FORM, request, response);
			break;

		case "update":
			parseId(request);

			RefuelModel rf = dbm.getRefuel(id);
			if (rf != null) {
				assignedObjects.put("refuel", rf);
				renderPage(REFUELS_FORM, request, response);
			} else {
				assignedObjects.put("status", -1);
				renderPage(REFUELS, request, response);
			}
			break;
		case "":
		case "cancel":
		default:
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doPost(request, response);

		parseMode(request);

		String[] mustElements = { "car", "unitPrice", "amount" };
		for (String key : mustElements) {
			if (request.getParameter(key) == null || "".equalsIgnoreCase(request.getParameter(key))) {
				validationMessages.add(key);
			} else {
				validationMessages.remove(key);
			}
		}

		if (validationMessages.isEmpty()) {
			RefuelModel rf = new RefuelModel();
			rf.setCar(new CarModel(request.getParameter("car")));
			rf.setLocation(new PlaceModel(request.getParameter("location")));
			rf.setPayment(new PaymentMethodModel(request.getParameter("paymentMethod")));
			rf.setUser(user);

			rf.setRefuelTimestamp(request.getParameter("refuelTimestamp"));
			rf.setOdometer(request.getParameter("odometer"));
			rf.setUnitPrice(request.getParameter("unitPrice"));
			rf.setAmount(request.getParameter("amount"));

			switch (m) {
			case "new":
				rf.setOperation(0);
				break;
			case "update":
				parseId(request);
				rf.setOperation(1);
				rf.setId(id);
				break;
			case "":
			default:
				break;
			}

			assignedObjects.put("status", dbm.createUpdateRefuel(rf) ? 1 : -1);
			assignedObjects.put("refuels", dbm.getRefuels(user.getId()));
			renderPage(REFUELS, request, response);
		} else {
			assignedObjects.put("status", -2);
			assignedObjects.put("cars", dbm.getCars(user.getId()));
			assignedObjects.put("locations", dbm.getPlaces(user.getId()));
			assignedObjects.put("paymentMethods", dbm.getPaymentMethods(user.getId()));
			renderPage(REFUELS_FORM, request, response);
		}
	}
}