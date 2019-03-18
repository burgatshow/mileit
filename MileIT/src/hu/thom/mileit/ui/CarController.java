package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		request.setAttribute("page", "cars");

		parseMode(request);

		switch (m) {
		case "new":
			request.setAttribute("carVendors", dbm.getCarVendors());
			request.getRequestDispatcher(CARS_FORM).forward(request, response);
			break;

		case "archive":
			parseId(request);

			if (dbm.archiveCar(id)) {
				request.setAttribute("status", 2);
			} else {
				request.setAttribute("status", -1);
			}

			request.setAttribute("cars", dbm.getCars(user.getId()));
			request.getRequestDispatcher(CARS).forward(request, response);

			break;

		case "update":
			request.setAttribute("carVendors", dbm.getCarVendors());
			parseId(request);

			CarModel car = dbm.getCar(id);
			if (car != null) {
				request.setAttribute("car", car);
				request.getRequestDispatcher(CARS_FORM).forward(request, response);
			} else {
				request.setAttribute("status", -1);
				request.setAttribute("cars", dbm.getCars(user.getId()));
				request.getRequestDispatcher(CARS).forward(request, response);
			}

			break;
		case "":
		case "cancel":
		default:
			request.setAttribute("cars", dbm.getCars(user.getId()));
			request.getRequestDispatcher(CARS).forward(request, response);
			break;
		}
	}

	/**
	 * Method to manage HTTP POST method.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		request.setAttribute("page", "cars");

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
			CarModel car = null;
			switch (m) {
			case "new":
				car = new CarModel();
				car.setOperation(0);
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

				if (dbm.createUpdateCar(car)) {
					request.setAttribute("status", 0);
				} else {
					request.setAttribute("status", -1);
				}

				request.setAttribute("cars", dbm.getCars(user.getId()));
				request.getRequestDispatcher(CARS).forward(request, response);

				break;

			case "update":
				parseId(request);

				car = new CarModel();
				car.setId(id);
				car.setOperation(1);
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

				if (dbm.createUpdateCar(car)) {
					request.setAttribute("status", 1);
				} else {
					request.setAttribute("status", -1);
				}

				request.setAttribute("cars", dbm.getCars(user.getId()));
				request.getRequestDispatcher(CARS).forward(request, response);
				break;

			case "":
			default:
				break;
			}
		} else {
			request.setAttribute("status", -2);
			request.setAttribute("validationMessages", validationMessages);
			request.getRequestDispatcher(CARS_FORM).forward(request, response);
		}
	}
}
