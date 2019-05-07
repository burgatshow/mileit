package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.DynaCacheAdaptor;
import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.MaintenanceModel;
import hu.thom.mileit.models.UserModel;

/**
 * Servlet class to manage maintenance related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/maintenance")
public class MaintenanceController extends Controller {
	private static final long serialVersionUID = 2199011911238724358L;
	
	private String userMaintenanceKey = null;
	private String userCarsKey = null;
	private String userPaymentsKey = null;

	/**
	 * Constructor
	 */
	public MaintenanceController() {
		super();
		assignedObjects.put(UIKeys.PAGE, "maintenance");
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
			userMaintenanceKey = user.getUsername() + "_" + UIKeys.MAINTENANCES;
			userCarsKey = user.getUsername() + "_" + UIKeys.CARS;
			userPaymentsKey = user.getUsername() + "_" + UIKeys.PMS;
			
			parseMode(request);
			
			if (dc.get(userCarsKey) == null) {
				dc.put(userCarsKey, dbm.getCars(user.getId(), sm), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIKeys.CARS, dc.get(userCarsKey));
			
			if (dc.get(userPaymentsKey) == null) {
				dc.put(userPaymentsKey, dbm.getPaymentMethods(user.getId()), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIKeys.PMS, dc.get(userPaymentsKey));
			
			if (dc.get(userMaintenanceKey) == null) {
				dc.put(userMaintenanceKey, dbm.getMaintenances(user.getId()), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIKeys.MAINTENANCES, dc.get(userMaintenanceKey));

			switch (m) {
			case UIKeys.MODE_NEW:
				validationMessages.clear();
				assignedObjects.remove(UIKeys.MAINTENANCES);
				renderPage(MAINTENANCES_FORM, request, response);
				break;

			case UIKeys.MODE_UPDATE:
				parseId(request);

				MaintenanceModel mm = dbm.getMaintenance(id);
				if (mm != null) {
					assignedObjects.put(UIKeys.MAINTENANCES, mm);
					renderPage(MAINTENANCES_FORM, request, response);
				} else {
					assignedObjects.put(UIKeys.STATUS, -1);
					renderPage(MAINTENANCES, request, response);
				}
				break;
			case UIKeys.MODE_:
			case UIKeys.MODE_CANCEL:
			default:
				assignedObjects.remove(UIKeys.STATUS);
				renderPage(MAINTENANCES, request, response);
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
			userMaintenanceKey = user.getUsername() + "_" + UIKeys.MAINTENANCES;
			
			parseMode(request);

			checkValidationMessages(UIKeys.FORM_ME_MAINTENANCE, validationMessages, request);

			assignedObjects.put(UIKeys.CARS, dbm.getCars(user.getId(), sm));
			assignedObjects.put(UIKeys.PMS, dbm.getPaymentMethods(user.getId()));

			if (validationMessages.isEmpty()) {
				MaintenanceModel mm = new MaintenanceModel(request.getParameterMap(), user);

				switch (m) {
				case UIKeys.MODE_NEW:
					mm.setOperation(0);
					break;
				case UIKeys.MODE_UPDATE:
					parseId(request);
					mm.setOperation(1);
					mm.setId(id);
					break;
				case UIKeys.MODE_:
				case UIKeys.MODE_CANCEL:
				default:
					break;
				}

				assignedObjects.put(UIKeys.STATUS, dbm.createUpdateMaintenance(mm) ? 1 : -1);
				dc.put(userMaintenanceKey, dbm.getMaintenances(user.getId()), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIKeys.MAINTENANCES, dc.get(userMaintenanceKey));
				renderPage(MAINTENANCES, request, response);

			} else {
				assignedObjects.put(UIKeys.STATUS, -2);
				renderPage(MAINTENANCES_FORM, request, response);
			}
		}
	}

}
