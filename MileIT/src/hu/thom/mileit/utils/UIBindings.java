/**
 * MIT License
 *
 * Copyright (c) 2019 Tamas BURES
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
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
	public static final String[] FORM_LOGIN_2FA = { "2fa_verification" };
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
	 * LoginController
	 */
	public static final String TWO_FA_REQUIRED = "two_fa_required";
	
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
	public static final String MODE_2FA = "2fa";
	public static final String LOAD_MAPS = "loadMap";
	public static final String LOAD_CHARTS = "loadCharts";
	public static final String PARAM_MODE = "m";
	public static final String PARAM_ID = "id";

	/**
	 * IndexController
	 */
	public static final String INDEX = "index";
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

	/**
	 * ProfileController & Configure2FAController
	 */
	public static final String TOTP_SECRET_USER = "totp_secret_%s";
	public static final String TOTP_START_USER = "totp_start_%s";
	public static final String TOTP_END_USER = "totp_end_%s";
}
