package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.DynaCacheAdaptor;
import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.CarModel;
import hu.thom.mileit.models.UserModel;

/**
 * Servlet class to manage car related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/cars")
public class CarController extends Controller {
	private static final long serialVersionUID = -1404168270197334376L;

	private String userCarsKey = null;

	/**
	 * Constructor
	 */
	public CarController() {
		super();
		assignedObjects.put(UIKeys.PAGE, "cars");
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
			userCarsKey = user.getUsername() + "_" + UIKeys.CARS;

			parseMode(request);

			if (dc.get(UIKeys.CAR_VENDORS) == null) {
				dc.put(UIKeys.CAR_VENDORS, dbm.getCarVendors(), DynaCacheAdaptor.DC_TTL_FOREVER, user.getUsername());
			}

			assignedObjects.put(UIKeys.CAR_VENDORS, dc.get(UIKeys.CAR_VENDORS));

			if (dc.get(userCarsKey) == null) {
				dc.put(userCarsKey, dbm.getCars(user.getId(), sm), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
			}

			assignedObjects.put(UIKeys.CARS, dc.get(userCarsKey));

			switch (m) {
			case UIKeys.MODE_NEW:
				validationMessages.clear();
				assignedObjects.remove(UIKeys.CARS);
				renderPage(CARS_FORM, request, response);
				break;

			case UIKeys.MODE_ARCHIVE:
				parseId(request);

				assignedObjects.put(UIKeys.STATUS, dbm.archiveOrActivateCar(id, 1) ? 2 : -1);

				dc.put(userCarsKey, dbm.getCars(user.getId(), sm), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIKeys.CARS, dc.get(userCarsKey));

				renderPage(CARS, request, response);

				break;

			case UIKeys.MODE_ACTIVATE:
				parseId(request);

				assignedObjects.put(UIKeys.STATUS, dbm.archiveOrActivateCar(id, 0) ? 2 : -1);

				dc.put(userCarsKey, dbm.getCars(user.getId(), sm), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIKeys.CARS, dc.get(userCarsKey));

				renderPage(CARS, request, response);

				break;

			case UIKeys.MODE_UPDATE:
				parseId(request);

				CarModel car = dbm.getCar(id, sm);
				if (car != null) {
					assignedObjects.put(UIKeys.CARS, car);
					renderPage(CARS_FORM, request, response);
				} else {
					assignedObjects.put(UIKeys.STATUS, -1);
					renderPage(CARS, request, response);
				}

				break;
			case UIKeys.MODE_:
			case UIKeys.MODE_CANCEL:
			default:
				assignedObjects.remove(UIKeys.STATUS);
				renderPage(CARS, request, response);
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
			userCarsKey = user.getUsername() + "_" + UIKeys.CARS;

			parseMode(request);

			checkValidationMessages(UIKeys.FORM_ME_CARS, validationMessages, request);

			if (validationMessages.isEmpty()) {
				CarModel car = new CarModel(request.getParameterMap(), user);

				int status = 0;
				switch (m) {
				case UIKeys.MODE_NEW:
					car.setOperation(0);
					break;
				case UIKeys.MODE_UPDATE:
					parseId(request);
					car.setId(id);
					car.setOperation(1);
					status = 1;
					break;

				case UIKeys.MODE_:
				case UIKeys.MODE_CANCEL:
				default:
					break;
				}
				
				// Encrypt sensitive data
				car.setPlateNumber(sm.encrypt(car.getPlateNumber()));
				car.setVin(sm.encrypt(car.getVin()));

				assignedObjects.put(UIKeys.STATUS, dbm.createUpdateCar(car) ? status : -1);
				dc.put(userCarsKey, dbm.getCars(user.getId(), sm), DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIKeys.CARS, dc.get(userCarsKey));
				renderPage(CARS, request, response);
			} else {
				assignedObjects.put(UIKeys.STATUS, -2);
				renderPage(CARS_FORM, request, response);
			}
		}
	}
}