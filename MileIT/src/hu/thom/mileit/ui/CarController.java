package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.CarModel;

/**
 * Servlet class to manage car related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/cars")
public class CarController extends Controller {
	private static final long serialVersionUID = -1404168270197334376L;

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

		parseMode(request);

		assignedObjects.put(UIKeys.CAR_VENDORS, dbm.getCarVendors());
		assignedObjects.put(UIKeys.CARS, dbm.getCars(user.getId()));

		switch (m) {
		case UIKeys.MODE_NEW:
			assignedObjects.remove(UIKeys.CARS);
			renderPage(CARS_FORM, request, response);
			break;

		case UIKeys.MODE_ARCHIVE:
			parseId(request);

			assignedObjects.put(UIKeys.STATUS, dbm.archiveCar(id) ? 1 : -1);
			renderPage(CARS, request, response);

			break;

		case UIKeys.MODE_UPDATE:
			parseId(request);

			CarModel car = dbm.getCar(id);
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
			renderPage(CARS, request, response);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);

		parseMode(request);

		String[] mustElements = { "manufacturer", "fuel", "model", "vin", "plateNumber", "status" };
		for (String key : mustElements) {
			if (request.getParameter(key) == null || "".equalsIgnoreCase(request.getParameter(key))) {
				validationMessages.add(key);
			} else {
				validationMessages.remove(key);
			}
		}

		if (validationMessages.isEmpty()) {
			CarModel car = new CarModel();
			car.setManufacturer(request.getParameter("manufacturer"));
			car.setModel(request.getParameter("model"));
			car.setManufacturerDate(request.getParameter("manufactureDate"));
			car.setStartDate(request.getParameter("startDate"));
			car.setEndDate(request.getParameter("endDate"));
			car.setColor(request.getParameter("color"));
			car.setVin(request.getParameter("vin"));
			car.setPlateNumber(request.getParameter("plateNumber"));
			car.setFuelCapacity(request.getParameter("fuelCapacity"));
			car.setFuel(request.getParameter("fuel"));
			car.setDescription(request.getParameter("description"));
			car.setFriendlyName(request.getParameter("friendlyName"));
			car.setActive(request.getParameter("status"));
			car.setUser(user);

			switch (m) {
			case UIKeys.MODE_NEW:
				car.setOperation(0);
				break;
			case UIKeys.MODE_UPDATE:
				parseId(request);
				car.setId(id);
				car.setOperation(1);
				break;

			case UIKeys.MODE_:
			case UIKeys.MODE_CANCEL:
			default:
				break;
			}

			assignedObjects.put(UIKeys.STATUS, dbm.createUpdateCar(car) ? 1 : -1);
			assignedObjects.put(UIKeys.CARS, dbm.getCars(user.getId()));
			renderPage(CARS, request, response);
		} else {
			assignedObjects.put(UIKeys.STATUS, -2);
			renderPage(CARS_FORM, request, response);
		}
	}
}