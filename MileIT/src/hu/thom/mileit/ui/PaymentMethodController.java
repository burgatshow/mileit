package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.models.PaymentMethodModel;

@WebServlet("/payment_methods")
public class PaymentMethodController extends Controller {
	private static final long serialVersionUID = 2631517143636421486L;

	/**
	 * Constructor
	 */
	public PaymentMethodController() {
		validationMessages.clear();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		request.setAttribute("page", "refuels");

		parseMode(request);

		switch (m) {
		case "new":
			request.getRequestDispatcher(PAYMENT_METHODS_FORM).forward(request, response);
			break;

		case "update":
			parseId(request);

			PaymentMethodModel pm = dbm.getPaymentMethod(id);
			if (pm != null) {
				request.setAttribute("pm", pm);
				request.getRequestDispatcher(PAYMENT_METHODS_FORM).forward(request, response);
			} else {
				request.setAttribute("status", -1);
				request.setAttribute("paymentMethods", dbm.getPaymentMethods(user.getId()));
				request.getRequestDispatcher(PAYMENT_METHODS).forward(request, response);
			}
			break;
		case "":
		case "cancel":
		default:
			request.setAttribute("paymentMethods", dbm.getPaymentMethods(user.getId()));
			request.getRequestDispatcher(PAYMENT_METHODS).forward(request, response);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		request.setAttribute("page", "refuels");

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

				if (dbm.createUpdatePaymentMethod(pm)) {
					request.setAttribute("status", 0);
				} else {
					request.setAttribute("status", -1);
				}

				request.setAttribute("paymentMethods", dbm.getPaymentMethods(user.getId()));
				request.getRequestDispatcher(PAYMENT_METHODS).forward(request, response);

				break;

			case "update":
				parseId(request);

				pm.setOperation(1);
				pm.setId(id);

				if (dbm.createUpdatePaymentMethod(pm)) {
					request.setAttribute("status", 1);
				} else {
					request.setAttribute("status", -1);
				}

				request.setAttribute("paymentMethods", dbm.getPaymentMethods(user.getId()));
				request.getRequestDispatcher(PAYMENT_METHODS).forward(request, response);
				break;

			case "":
			default:
				break;
			}
		} else {
			request.setAttribute("status", -2);
			request.setAttribute("validationMessages", validationMessages);
			request.getRequestDispatcher(PAYMENT_METHODS_FORM).forward(request, response);
		}
	}
}
