package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.models.CarModel;
import hu.thom.mileit.models.PlaceModel;
import hu.thom.mileit.models.PaymentMethodModel;
import hu.thom.mileit.models.RefuelModel;

@WebServlet("/refuels")
public class RefuelController extends Controller {
	private static final long serialVersionUID = 5999291452665397698L;

	/**
	 * Constructor
	 */
	public RefuelController() {
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
			request.setAttribute("cars", dbm.getCars(user.getId()));
			request.setAttribute("locations", dbm.getLocations(user.getId()));
			request.setAttribute("paymentMethods", dbm.getPaymentMethods(user.getId()));
			request.getRequestDispatcher(REFUELS_FORM).forward(request, response);
			break;

		case "update":
			parseId(request);

			RefuelModel rf = dbm.getRefuel(id);
			if (rf != null) {
				request.setAttribute("refuel", rf);
				request.setAttribute("cars", dbm.getCars(user.getId()));
				request.setAttribute("locations", dbm.getLocations(user.getId()));
				request.setAttribute("paymentMethods", dbm.getPaymentMethods(user.getId()));
				request.getRequestDispatcher(REFUELS_FORM).forward(request, response);
			} else {
				request.setAttribute("status", -1);
				request.setAttribute("refuels", dbm.getRefuels(user.getId()));
				request.getRequestDispatcher(REFUELS).forward(request, response);
			}
			break;
		case "":
		case "cancel":
		default:
			request.setAttribute("refuels", dbm.getRefuels(user.getId()));
			request.getRequestDispatcher(REFUELS).forward(request, response);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		request.setAttribute("page", "refuels");

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

				if (dbm.createUpdateRefuel(rf)) {
					request.setAttribute("status", 0);
				} else {
					request.setAttribute("status", -1);
				}

				request.setAttribute("refuels", dbm.getRefuels(user.getId()));
				request.getRequestDispatcher(REFUELS).forward(request, response);

				break;

			case "update":
				parseId(request);

				rf.setOperation(1);
				rf.setId(id);

				if (dbm.createUpdateRefuel(rf)) {
					request.setAttribute("status", 1);
				} else {
					request.setAttribute("status", -1);
				}

				request.setAttribute("refuels", dbm.getRefuels(user.getId()));
				request.getRequestDispatcher(REFUELS).forward(request, response);
				break;

			case "":
			default:
				break;
			}
		} else {
			request.setAttribute("status", -2);
			request.setAttribute("validationMessages", validationMessages);
			request.setAttribute("cars", dbm.getCars(user.getId()));
			request.setAttribute("locations", dbm.getLocations(user.getId()));
			request.setAttribute("paymentMethods", dbm.getPaymentMethods(user.getId()));
			request.getRequestDispatcher(REFUELS_FORM).forward(request, response);
		}
	}
}
