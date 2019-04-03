package hu.thom.mileit.core;

import java.io.Serializable;

/**
 * Class to hold key names for UI assignments
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class UIKeys implements Serializable {
	private static final long serialVersionUID = -1572929004551162917L;

	// General controller keys
	public static final String VERSION = "v";
	public static final String PAGE = "page";
	public static final String USER = "user";
	public static final String VALIDATION_MSGS = "validationMessages";
	public static final String STATUS = "status";
	public static final String MODE_NEW = "new";
	public static final String MODE_ = "";
	public static final String MODE_ARCHIVE = "archive";
	public static final String MODE_UPDATE = "update";
	public static final String MODE_CANCEL = "cancel";
	
	// IndexController
	public static final String LAST_REFUEL = "lr";
	public static final String STAT_KEY = "fuelStatsKey";
	public static final String STAT_VAL = "fuelStatsVal";
	public static final String STAT_PAID_AMOUNT = "fuelPaidAmount";

	// CarController
	public static final String CAR_VENDORS = "carVendors";
	public static final String CARS = "cars";

	// PaymentMethodController
	public static final String PMS = "pms";
	
	// RefuelController
	public static final String REFUELS = "refuels";
	
	// PlaceController
	public static final String PLACES = "places";
	
	// MaintenanceController
	public static final String MAINTENANCES = "maintenances";
}
