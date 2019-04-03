package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.models.CarModel;
import hu.thom.mileit.models.MaintenanceModel;
import hu.thom.mileit.models.PaymentMethodModel;

/**
 * Servlet class to manage maintenance related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/maintenance")
public class MaintenanceController extends Controller {
	private static final long serialVersionUID = 2199011911238724358L;

	/**
	 * Constructor
	 */
	public MaintenanceController() {
		super();
		assignedObjects.put("page", "maintenance");
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

		assignedObjects.put("paymentMethods", dbm.getPaymentMethods(user.getId()));
		assignedObjects.put("cars", dbm.getCars(user.getId()));
		assignedObjects.put("maintenances", dbm.getMaintenances(user.getId()));

		switch (m) {
		case "new":
			renderPage(MAINTENANCES_FORM, request, response);
			break;

		case "update":
			parseId(request);

			MaintenanceModel mm = dbm.getMaintenance(id);
			if (mm != null) {
				assignedObjects.put("maintenance", mm);
				renderPage(MAINTENANCES_FORM, request, response);
			} else {
				assignedObjects.put("status", -1);
				renderPage(MAINTENANCES, request, response);
			}
			break;
		case "":
		case "cancel":
		default:
			renderPage(MAINTENANCES, request, response);
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

		String[] mustElements = { "car", "odometer", "paymentMethod", "amount", "maintenanceDate" };
		for (String key : mustElements) {
			if (request.getParameter(key) == null || "".equalsIgnoreCase(request.getParameter(key))) {
				validationMessages.add(key);
			} else {
				validationMessages.remove(key);
			}
		}

		assignedObjects.put("cars", dbm.getCars(user.getId()));
		assignedObjects.put("paymentMethods", dbm.getPaymentMethods(user.getId()));

		if (validationMessages.isEmpty()) {
			MaintenanceModel mm = new MaintenanceModel();
			mm.setCar(new CarModel(request.getParameter("car")));
			mm.setPayment(new PaymentMethodModel(request.getParameter("paymentMethod")));
			mm.setUser(user);
			mm.setMaintenanceDate(request.getParameter("maintenanceDate"));
			mm.setOdometer(request.getParameter("odometer"));
			mm.setAmount(request.getParameter("amount"));
			mm.setDescription(request.getParameter("description"));

			switch (m) {
			case "new":
				mm.setOperation(0);
				break;
			case "update":
				parseId(request);
				mm.setOperation(1);
				mm.setId(id);
				break;
			case "":
			default:
				break;
			}

			assignedObjects.put("status", dbm.createUpdateMaintenance(mm) ? 1 : -1);
			assignedObjects.put("maintenances", dbm.getMaintenances(user.getId()));
			renderPage(MAINTENANCES, request, response);

		} else {
			assignedObjects.put("status", -2);
			renderPage(MAINTENANCES_FORM, request, response);
		}
	}

}
