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
		validationMessages.clear();
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
		request.setAttribute("page", "maintenance");

		parseMode(request);

		switch (m) {
		case "new":
			request.setAttribute("cars", dbm.getCars(user.getId()));
			request.setAttribute("paymentMethods", dbm.getPaymentMethods(user.getId()));
			request.getRequestDispatcher(MAINTENANCES_FORM).forward(request, response);
			break;

		case "update":
			parseId(request);

			MaintenanceModel mm = dbm.getMaintenance(id);
			if (mm != null) {
				request.setAttribute("maintenance", mm);
				request.setAttribute("cars", dbm.getCars(user.getId()));
				request.setAttribute("paymentMethods", dbm.getPaymentMethods(user.getId()));
				request.getRequestDispatcher(MAINTENANCES_FORM).forward(request, response);
			} else {
				request.setAttribute("status", -1);
				request.getRequestDispatcher(MAINTENANCES).forward(request, response);
			}
			break;
		case "":
		case "cancel":
		default:
			request.setAttribute("maintenances", dbm.getMaintenances(user.getId()));
			request.getRequestDispatcher(MAINTENANCES).forward(request, response);
			break;
		}
	}

	/**
	 * Method to manage HTTP POST method.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		request.setAttribute("page", "maintenance");

		parseMode(request);

		String[] mustElements = { "car", "odometer", "paymentMethod", "amount", "maintenanceDate" };
		for (String key : mustElements) {
			if (request.getParameter(key) == null || "".equalsIgnoreCase(request.getParameter(key))) {
				validationMessages.add(key);
			} else {
				validationMessages.remove(key);
			}
		}

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

				if (dbm.createUpdateMaintenance(mm)) {
					request.setAttribute("status", 0);
				} else {
					request.setAttribute("status", -1);
				}

				request.setAttribute("maintenances", dbm.getMaintenances(user.getId()));
				request.getRequestDispatcher(MAINTENANCES).forward(request, response);

				break;

			case "update":
				parseId(request);

				mm.setOperation(1);
				mm.setId(id);

				if (dbm.createUpdateMaintenance(mm)) {
					request.setAttribute("status", 1);
				} else {
					request.setAttribute("status", -1);
				}

				request.setAttribute("maintenances", dbm.getMaintenances(user.getId()));
				request.getRequestDispatcher(MAINTENANCES).forward(request, response);
				break;

			case "":
			default:
				break;
			}
		} else {
			request.setAttribute("status", -2);
			request.setAttribute("validationMessages", validationMessages);
			request.setAttribute("cars", dbm.getCars(user.getId()));
			request.setAttribute("paymentMethods", dbm.getPaymentMethods(user.getId()));
			request.getRequestDispatcher(MAINTENANCES_FORM).forward(request, response);
		}
	}

}
