package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.models.PaymentMethodModel;

/**
 * Servlet class to manage payment method related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/payment_methods")
public class PaymentMethodController extends Controller {
	private static final long serialVersionUID = 2631517143636421486L;

	/**
	 * Constructor
	 */
	public PaymentMethodController() {
		validationMessages.clear();
		assignedObjects.put("page", "payment");
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

		switch (m) {
		case "new":
			renderPage(PAYMENT_METHODS_FORM, request, response);
			break;

		case "update":
			parseId(request);

			PaymentMethodModel pm = dbm.getPaymentMethod(id);
			if (pm != null) {
				assignedObjects.put("pm", pm);
				renderPage(PAYMENT_METHODS_FORM, request, response);
			} else {
				assignedObjects.put("status", -1);
				renderPage(PAYMENT_METHODS, request, response);
			}
			break;
		case "":
		case "cancel":
		default:
			renderPage(PAYMENT_METHODS, request, response);
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

		String[] mustElements = { "name" };
		for (String key : mustElements) {
			if (request.getParameter(key) == null || "".equalsIgnoreCase(request.getParameter(key))) {
				validationMessages.add(key);
			} else {
				validationMessages.remove(key);
			}
		}

		if (validationMessages.isEmpty()) {
			PaymentMethodModel pm = new PaymentMethodModel();
			pm.setUser(user);
			pm.setName(request.getParameter("name"));
			pm.setDescription(request.getParameter("description"));

			switch (m) {
			case "new":
				pm.setOperation(0);
				break;
			case "update":
				parseId(request);
				pm.setOperation(1);
				pm.setId(id);
				break;

			case "":
			default:
				break;
			}

			assignedObjects.put("status", dbm.createUpdatePaymentMethod(pm) ? 1 : -1);
			assignedObjects.put("paymentMethods", dbm.getPaymentMethods(user.getId()));
			renderPage(PAYMENT_METHODS, request, response);
		} else {
			assignedObjects.put("status", -2);
			renderPage(PAYMENT_METHODS_FORM, request, response);
		}
	}
}