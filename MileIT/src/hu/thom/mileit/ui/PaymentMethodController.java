package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.models.PaymentMethodModel;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

/**
 * Servlet class to manage payment method related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/payment_methods")
public class PaymentMethodController extends Controller {
	private static final long serialVersionUID = 2631517143636421486L;
	
	private String userPaymentsKey = null;

	/**
	 * Constructor
	 */
	public PaymentMethodController() {
		super();
		assignedObjects.put(UIBindings.PAGE, "payment");
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
		user = (UserModel) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login");
		} else {
			userPaymentsKey = user.getUsername() + "_" + UIBindings.PMS;

			parseMode(request);

			if (dc.get(userPaymentsKey) == null) {
				dc.put(userPaymentsKey, db.getPaymentMethods(user.getId()), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.PMS, dc.get(userPaymentsKey));

			switch (m) {
			case UIBindings.MODE_NEW:
				validationMessages.clear();
				assignedObjects.remove(UIBindings.PMS);
				renderPage(PAYMENT_METHODS_FORM, request, response);
				break;

			case UIBindings.MODE_ARCHIVE:
				parseId(request);

				assignedObjects.put(UIBindings.STATUS, db.archivePaymentMethod(id) ? 1 : -1);
				assignedObjects.put(UIBindings.PMS, db.getPaymentMethods(user.getId()));
				renderPage(PAYMENT_METHODS, request, response);

				break;

			case UIBindings.MODE_UPDATE:
				parseId(request);

				PaymentMethodModel pm = db.getPaymentMethod(id);
				if (pm != null) {
					assignedObjects.put(UIBindings.PMS, pm);
					renderPage(PAYMENT_METHODS_FORM, request, response);
				} else {
					assignedObjects.put(UIBindings.STATUS, -1);
					renderPage(PAYMENT_METHODS, request, response);
				}
				break;
			case UIBindings.MODE_:
			case UIBindings.MODE_CANCEL:
			default:
				assignedObjects.remove(UIBindings.STATUS);
				renderPage(PAYMENT_METHODS, request, response);
				break;
			}
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
		user = (UserModel) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login");
		} else {
			userPaymentsKey = user.getUsername() + "_" + UIBindings.PMS;
			
			parseMode(request);

			checkValidationMessages(UIBindings.FORM_ME_PAYMENT_METHOD, validationMessages, request);

			if (validationMessages.isEmpty()) {
				PaymentMethodModel pm = new PaymentMethodModel(request.getParameterMap(), user);

				switch (m) {
				case UIBindings.MODE_NEW:
					pm.setOperation(0);
					break;
				case UIBindings.MODE_UPDATE:
					parseId(request);
					pm.setOperation(1);
					pm.setId(id);
					break;

				case UIBindings.MODE_:
				case UIBindings.MODE_CANCEL:
				default:
					break;
				}

				assignedObjects.put(UIBindings.STATUS, db.createUpdatePaymentMethod(pm) ? 1 : -1);
				dc.put(userPaymentsKey, db.getPaymentMethods(user.getId()), DynaCacheManager.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIBindings.PMS, dc.get(userPaymentsKey));
				renderPage(PAYMENT_METHODS, request, response);
			} else {
				assignedObjects.put(UIBindings.STATUS, -2);
				renderPage(PAYMENT_METHODS_FORM, request, response);
			}
		}
	}
}