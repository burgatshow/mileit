package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.models.CarModel;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

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
		assignedObjects.put(UIBindings.PAGE, "cars");
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
			userCarsKey = user.getUsername() + "_" + UIBindings.CARS;
			
			String userRefuelsKey = user.getUsername() + "_" + UIBindings.REFUELS;

			parseMode(request);

			if (dc.get(UIBindings.CAR_VENDORS) == null) {
				dc.put(UIBindings.CAR_VENDORS, db.getCarVendors(), DynaCacheManager.DC_TTL_FOREVER, user.getUsername());
			}

			assignedObjects.put(UIBindings.CAR_VENDORS, dc.get(UIBindings.CAR_VENDORS));

			if (dc.get(userCarsKey) == null) {
				dc.put(userCarsKey, db.getCars(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}

			assignedObjects.put(UIBindings.CARS, dc.get(userCarsKey));

			switch (m) {
			case UIBindings.MODE_NEW:
				validationMessages.clear();
				assignedObjects.remove(UIBindings.CARS);
				renderPage(CARS_FORM, request, response);
				break;

			case UIBindings.MODE_ARCHIVE:
			case UIBindings.MODE_ACTIVATE:
				parseId(request);

				assignedObjects.put(UIBindings.STATUS, db.archiveOrActivateCar(id, "archive".equals(m) ? 1 : 0) ? 2 : -1);

				dc.put(userCarsKey, db.getCars(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
				dc.invalidate(userRefuelsKey);
				assignedObjects.put(UIBindings.CARS, dc.get(userCarsKey));

				renderPage(CARS, request, response);

				break;

			case UIBindings.MODE_UPDATE:
				parseId(request);

				CarModel car = db.getCar(id, em);
				if (car != null) {
					assignedObjects.put(UIBindings.CARS, car);
					renderPage(CARS_FORM, request, response);
				} else {
					assignedObjects.put(UIBindings.STATUS, -1);
					renderPage(CARS, request, response);
				}

				break;
			case UIBindings.MODE_:
			case UIBindings.MODE_CANCEL:
			default:
				assignedObjects.remove(UIBindings.STATUS);
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
			userCarsKey = user.getUsername() + "_" + UIBindings.CARS;

			parseMode(request);

			checkValidationMessages(UIBindings.FORM_ME_CARS, validationMessages, request);

			if (validationMessages.isEmpty()) {
				CarModel car = new CarModel(request.getParameterMap(), user);

				int status = 0;
				switch (m) {
				case UIBindings.MODE_NEW:
					car.setOperation(0);
					break;
				case UIBindings.MODE_UPDATE:
					parseId(request);
					car.setId(id);
					car.setOperation(1);
					status = 1;
					break;

				case UIBindings.MODE_:
				case UIBindings.MODE_CANCEL:
				default:
					break;
				}
				
				// Encrypt sensitive data
				car.setPlateNumber(em.encrypt(car.getPlateNumber()));
				car.setVin(em.encrypt(car.getVin()));

				assignedObjects.put(UIBindings.STATUS, db.createUpdateCar(car) ? status : -1);
				dc.put(userCarsKey, db.getCars(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIBindings.CARS, dc.get(userCarsKey));
				renderPage(CARS, request, response);
			} else {
				assignedObjects.put(UIBindings.STATUS, -2);
				renderPage(CARS_FORM, request, response);
			}
		}
	}
}