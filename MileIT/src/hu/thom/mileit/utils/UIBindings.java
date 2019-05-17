package hu.thom.mileit.utils;

import java.io.Serializable;

/**
 * Class to hold key names for UI assignments
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public final class UIBindings implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -1572929004551162917L;

	/**
	 * Form submission must elements
	 */
	public static final String[] FORM_LOGIN = { "username", "password" };
	public static final String[] FORM_ME_CARS = { "manufacturer", "fuel", "model", "vin", "plateNumber", "status" };
	public static final String[] FORM_ME_MAINTENANCE = { "car", "odometer", "paymentMethod", "amount", "maintenanceDate" };
	public static final String[] FORM_ME_TYRE = { "manufacturer", "model", "width", "height", "radius", "axis", "type" };
	public static final String[] FORM_ME_TYRE_EVENT = { "car", "odometer_start" };
	public static final String[] FORM_ME_REFUEL = { "car", "place", "paymentMethod", "unitPrice", "amount" };
	public static final String[] FORM_ME_PLACE = { "name", "fuelStation" };
	public static final String[] FORM_ME_PAYMENT_METHOD = { "name" };
	public static final String[] FORM_ME_ROUTE = { "car", "type", "startPlace", "endPlace", "routeDatetime" };

	public static final String[] VALID_PATTERNS = { "yyyy. MM. dd.", "yyyy-MM-dd", "yyyy/MM/dd", "yyyy. MMMM dd.", "yyyy. MMM. dd.", "dd. MM. yyyy.",
			"dd-MM-yyyy", "dd/MM/yyyy", "dd. MMMM yyyy.", "dd. MMM. yyyy.", "HH:mm", "h:mm a" };

	/**
	 * General controller keys
	 */
	public static final String VERSION = "v";
	public static final String PAGE = "page";
	public static final String USER = "user";
	public static final String VALIDATION_MSGS = "validationMessages";
	public static final String STATUS = "status";
	public static final String MODE_NEW = "new";
	public static final String MODE_ = "";
	public static final String MODE_MAP = "map";
	public static final String MODE_ARCHIVE = "archive";
	public static final String MODE_ACTIVATE = "activate";
	public static final String MODE_UPDATE = "update";
	public static final String MODE_CANCEL = "cancel";
	public static final String MODE_DELETE = "delete";
	public static final String MODE_AJAX_FUELSTAT = "afs";
	public static final String MODE_AJAX_AMOUNTPAID = "aap";
	public static final String LOAD_MAPS = "loadMap";
	public static final String LOAD_CHARTS = "loadCharts";
	public static final String PARAM_MODE = "m";
	public static final String PARAM_ID = "id";

	/**
	 * IndexController
	 */
	public static final String LAST_REFUEL = "lr";
	public static final String FUEL_STATS = "fs";

	/**
	 * CarController
	 */
	public static final String CAR_VENDORS = "carVendors";
	public static final String CARS = "cars";

	/**
	 * PaymentMethodController
	 */
	public static final String PMS = "pms";

	/**
	 * RefuelController
	 */
	public static final String REFUELS = "refuels";

	/**
	 * PlaceController
	 */
	public static final String PLACES = "places";

	/**
	 * MaintenanceController
	 */
	public static final String MAINTENANCES = "maintenances";

	/**
	 * TyreController
	 */
	public static final String TYRES = "tyres";
	public static final String TYRE_VENDORS = "tyreVendors";
	public static final String TYRE_EVENTS = "tes";

	/**
	 * RoutesController
	 */
	public static final String ROUTES = "routes";
}
