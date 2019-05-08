package hu.thom.mileit.core.data;

import java.io.Serializable;

/**
 * All SQL commands used in {@link DBManager}
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public final class DBCommands implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -1208055450418159794L;

	/**
	 * Home
	 */
	public static final String SQL_S_BIG_STAT = "SELECT r.car_id, r.refuel_id, r.place_id, r.refuel_timestamp, r.odometer, r.fuel_amount, r.amount, r.unit_price, r.pm_id FROM refuels AS r WHERE r.user_id = ? ORDER BY r.refuel_timestamp DESC LIMIT 1";
	public static final String SQL_S_PRICE_GRAPH = "SELECT * FROM ( SELECT r.refuel_timestamp, r.odometer, r.amount, unit_price FROM refuels AS r WHERE r.user_id = ? ORDER BY r.refuel_timestamp DESC LIMIT 20) AS sub ORDER BY refuel_timestamp ASC";

	/**
	 * User
	 */
	public static final String SQL_I_USER = "INSERT IGNORE INTO users (username) VALUES (?)";
	public static final String SQL_S_AUTH = "SELECT u.username FROM users AS u WHERE u.username = ? AND u.password = ?";
	public static final String SQL_S_PROFILE = "SELECT u.currency, u.locale, u.user_id, u.username, u.distance, u.rounded, u.email, u.pushover_api_user, u.pushover_api_key, u.pushbullet_api_key FROM users AS u WHERE u.username = ?";
	public static final String SQL_U_PROFILE = "UPDATE users SET currency = ?, locale = ?, distance = ?, rounded = ?, email = ?, pushover_api_user = ?, pushover_api_key = ?, pushbullet_api_key = ? WHERE user_id = ?";

	/**
	 * Cars
	 */
	public static final String SQL_I_CAR = "INSERT INTO cars (manufacturer, model, manufacture_date, color, vin, plate_number, fuel_capacity, fuel, start_date, end_date, description, friendly_name, user_id, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String SQL_U_CAR = "UPDATE cars SET manufacturer = ?, model = ?, manufacture_date = ?, color = ?, vin = ?, plate_number = ?, fuel_capacity = ?, fuel = ?, start_date = ?, end_date = ?, description = ?, friendly_name = ?, active = ? WHERE car_id = ?";
	public static final String SQL_U_CAR_ARCHIVE_OR_ACTIVATE = "UPDATE cars SET archived = ? WHERE car_id = ?";
	public static final String SQL_S_CARS = "SELECT c.car_id, c.manufacturer, c.model, c.manufacture_date, c.color, c.vin, c.plate_number, c.fuel_capacity, c.fuel, c.start_date, c.end_date, c.description, c.friendly_name, c.active, v.manufacturer_id, IF(v.NAME = 'BMW', 'BMW', CONCAT(UPPER(SUBSTR(v.NAME, 1, 1)), LOWER(SUBSTR(v.NAME, 2, LENGTH(v.NAME)-1)))) AS name, c.archived FROM cars AS c, sup_car_manufacturers AS v WHERE c.user_id = ? AND c.manufacturer = v.manufacturer_id ORDER BY c.active DESC, c.plate_number ASC";
	public static final String SQL_S_CAR = "SELECT c.car_id, c.manufacturer, c.model, c.manufacture_date, c.color, c.vin, c.plate_number, c.fuel_capacity, c.fuel, c.start_date, c.end_date, c.description, c.friendly_name, c.active, c.archived FROM cars AS c WHERE c.car_id = ?";
	public static final String SQL_I_CAR_PRIMARY = "UPDATE cars SET active = 0 WHERE car_id <> (SELECT MAX(car_id) FROM cars WHERE user_id = ? LIMIT 1) AND user_id = ?";
	public static final String SQL_U_CAR_PRIMARY = "UPDATE cars SET active = 0 WHERE car_id <> ? AND user_id = ?";
	public static final String SQL_S_CAR_VENDORS = "SELECT manufacturer_id, IF(NAME = 'BMW', 'BMW', CONCAT(UPPER(SUBSTR(NAME, 1, 1)), LOWER(SUBSTR(NAME, 2, LENGTH(NAME)-1)))) AS name FROM sup_car_manufacturers WHERE active = 1 ORDER BY name ASC";

	/**
	 * Maintenances
	 */
	public static final String SQL_S_MAINTENANCES = "SELECT m.mntnc_id, m.car_id, m.user_id, m.pm_id, IF(((SELECT distance FROM users AS u WHERE u.user_id = m.user_id) = 1), m.odometer, (m.odometer * 0.621)) AS odometer, m.date, m.description, m.amount, c.friendly_name, c.plate_number AS plate_number, pm.name  FROM maintenances AS m, cars AS c, payment_method AS pm WHERE c.car_id = m.car_id AND c.archived = 0 AND m.pm_id = pm.pm_id AND m.user_id = ? ORDER BY m.date DESC";
	public static final String SQL_I_MAINTENANCE = "INSERT INTO maintenances (car_id, pm_id, odometer, date, description, amount, user_id) VALUES (?, ?, IF(((SELECT distance FROM users WHERE user_id = ?) = 1), ?, (? / 0.621)), ?, ?, ?, ?)";
	public static final String SQL_U_MAINTENANCE = "UPDATE maintenances SET car_id = ?, pm_id = ?, odometer = IF(((SELECT distance FROM users WHERE user_id = ?) = 1), ?, (? / 0.621)), date = ?, description = ?, amount = ? WHERE user_id = ? AND mntnc_id = ?";
	public static final String SQL_S_MAINTENANCE = "SELECT m.mntnc_id, m.car_id, m.user_id, m.pm_id, IF(((SELECT distance FROM users AS u WHERE u.user_id = m.user_id) = 1), m.odometer, (m.odometer * 0.621)) as odometer, m.date, m.description, m.amount FROM maintenances AS m WHERE m.mntnc_id = ?";

	/**
	 * Payment methods
	 */
	public static final String SQL_S_PAYMENTS = "SELECT pm.pm_id, pm.name, pm.description FROM payment_method AS pm WHERE pm.user_id = ? AND pm.archived = 0 ORDER BY pm.name ASC";
	public static final String SQL_I_PAYMENT = "INSERT INTO payment_method (name, description, user_id) VALUES (?, ?, ?)";
	public static final String SQL_U_PAYMENT = "UPDATE payment_method SET name = ?, description = ? WHERE pm_id = ?";
	public static final String SQL_S_PAYMENT = "SELECT pm.pm_id, pm.name, pm.description FROM payment_method AS pm WHERE pm.pm_id = ?";
	public static final String SQL_U_PAYMENT_ARCHIVE = "UPDATE payment_method SET archived = 1 WHERE pm_id = ?";

	/**
	 * Places
	 */
	public static final String SQL_S_PLACES = "SELECT p.place_id, p.name, p.address, p.longitude, p.latitude, p.fuel_station FROM places AS p WHERE p.user_id = ? AND p.archived = 0 ORDER BY p.name ASC";
	public static final String SQL_I_PLACE = "INSERT INTO places (name, address, latitude, longitude, fuel_station, user_id) VALUES (?, ?, ?, ?, ?, ?)";
	public static final String SQL_U_PLACE = "UPDATE places SET name = ?, address = ?, latitude = ?, longitude = ?, fuel_station = ? WHERE place_id = ?";
	public static final String SQL_S_PLACE = "SELECT p.place_id, p.name, p.address, p.user_id, p.longitude, p.latitude, p.fuel_station FROM places AS p WHERE p.place_id = ?";
	public static final String SQL_U_PLACE_ARCHIVE = "UPDATE places SET archived = 1 WHERE place_id = ?";

	/**
	 * Refuels
	 */
	public static final String SQL_S_REFUELS = "SELECT r.refuel_id, r.car_id, r.place_id, r.refuel_timestamp, IF(((SELECT u.distance FROM users AS u WHERE u.user_id = r.user_id) = 1), r.odometer, (r.odometer * 0.621)) AS odometer, r.unit_price, r.fuel_amount, r.pm_id, r.amount, r.user_id, c.plate_number AS plate_number, c.friendly_name, p.name AS place, pm.name AS payment_method_name, IFNULL(IF(((SELECT u.distance FROM users AS u WHERE u.user_id = r.user_id) = 1), r.odometer, (r.odometer * 0.621)) - (SELECT IF(((SELECT u.distance FROM users AS u WHERE u.user_id = r.user_id) = 1), p.odometer, (p.odometer * 0.621)) FROM refuels AS p WHERE p.refuel_timestamp = (SELECT MAX(pp.refuel_timestamp) FROM refuels AS pp WHERE pp.refuel_timestamp < r.refuel_timestamp AND pp.car_id = r.car_id )), 0) AS prev_refuel_diff FROM cars AS c, refuels AS r, places AS p, payment_method AS pm WHERE r.car_id IN (SELECT c.car_id FROM cars AS c WHERE c.user_id = ? AND c.archived = 0) AND pm.pm_id = r.pm_id AND c.car_id = r.car_id AND p.place_id = r.place_id ORDER BY r.refuel_timestamp DESC";
	public static final String SQL_I_REFUEL = "INSERT INTO refuels (car_id, place_id, refuel_timestamp, odometer, unit_price, fuel_amount, pm_id, amount, partial_refuel, user_id) VALUES (?, ?, ?, IF(((SELECT distance FROM users WHERE user_id = ?) = 1), ?, (? / 0.621)), ?, ?, ?, ?, ?, ?)";
	public static final String SQL_U_REFUEL = "UPDATE refuels SET car_id = ?, place_id = ?, refuel_timestamp = ?, odometer = IF(((SELECT distance FROM users WHERE user_id = ?) = 1), ?, (? / 0.621)), unit_price = ?, fuel_amount = ?, pm_id = ?, amount = ?, partial_refuel = ? WHERE refuel_id = ?";
	public static final String SQL_S_REFUEL = "SELECT r.refuel_id, r.car_id, r.place_id, r.refuel_timestamp, IF(((SELECT u.distance FROM users AS u WHERE u.user_id = r.user_id) = 1), r.odometer, (r.odometer * 0.621)) AS odometer, r.unit_price, r.fuel_amount, r.pm_id, r.amount, r.user_id, r.partial_refuel FROM refuels AS r WHERE r.refuel_id = ?";
	public static final String SQL_D_REFUEL = "DELETE FROM refuels WHERE refuel_id = ?";

	/**
	 * Tyres
	 */
	public static final String SQL_S_TYRES = "SELECT t.tyre_id, t.size_r, t.size_w, t.size_h, t.manufacturer, t.type, t.axis, t.purchase_date, CONCAT(UPPER(SUBSTR(stm.name, 1, 1)), LOWER(SUBSTR(stm.name, 2, LENGTH(stm.name)-1))) AS name, t.model, IFNULL((SELECT MIN(odometer_start) FROM tyres_events WHERE tyre_id = t.tyre_id), 0) AS tyre_first_odometer, IFNULL((SELECT MAX(odometer_end) FROM tyres_events WHERE tyre_id = t.tyre_id), 0) AS tyre_last_odometer, (SELECT MIN(change_date) FROM tyres_events WHERE tyre_id = t.tyre_id) AS tyre_first_date, (SELECT MAX(change_date) FROM tyres_events WHERE tyre_id = t.tyre_id) AS tyre_last_date, IFNULL((SELECT SUM(odometer_end - odometer_start) FROM tyres_events WHERE tyre_id = t.tyre_id), 0) AS tyre_total_distance, (SELECT car_id FROM cars AS c WHERE c.car_id = (SELECT DISTINCT car_id FROM tyres_events WHERE tyre_id = t.tyre_id)) AS car_id, (SELECT friendly_name FROM cars AS c WHERE c.car_id = (SELECT DISTINCT car_id FROM tyres_events WHERE tyre_id = t.tyre_id)) AS friendly_name, (SELECT plate_number FROM cars AS c WHERE c.car_id = (SELECT DISTINCT car_id FROM tyres_events WHERE tyre_id = t.tyre_id)) AS plate_number, t.archived FROM tyres AS t, sup_tyre_manufacturers AS stm WHERE t.manufacturer = stm.manufacturer_id AND t.archived = 0 AND t.user_id = ?";
	public static final String SQL_S_TYRE = "SELECT t.tyre_id, t.user_id, t.type, t.size_w, t.size_h, t.size_r, st.manufacturer_id, CONCAT(UPPER(SUBSTR(st.name, 1, 1)), LOWER(SUBSTR(st.name, 2, LENGTH(st.name)-1))) AS name, t.model, t.axis, t.purchase_date, t.archived FROM tyres AS t, sup_tyre_manufacturers AS st WHERE t.manufacturer = st.manufacturer_id AND tyre_id = ?";
	public static final String SQL_S_TYRE_VENDORS = "SELECT st.manufacturer_id, CONCAT(UPPER(SUBSTR(st.name, 1, 1)), LOWER(SUBSTR(st.name, 2, LENGTH(st.name)-1))) AS name FROM sup_tyre_manufacturers AS st ORDER BY st.name ASC";
	public static final String SQL_I_TYRE = "INSERT INTO tyres (type, manufacturer, model, axis, size_r, size_h, size_w, purchase_date, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String SQL_U_TYRE = "UPDATE tyres SET type = ?, manufacturer = ?, model = ?, axis = ?, size_r = ?, size_h = ?, size_w = ?, purchase_date = ? WHERE tyre_id = ?";
	public static final String SQL_I_TYRE_EVENT = "INSERT INTO tyres_events (tyre_id, user_id, car_id, odometer_start, odometer_end, change_date) VALUES (?, ?, ?, ?, ?, ?)";
	public static final String SQL_U_TYRE_ARCHIVE = "UPDATE tyres SET archived = 1 WHERE tyre_id = ?";

	/**
	 * Routes
	 */
	public static final String SQL_S_ROUTES = "SELECT r.route_id, r.route_datetime, IF(((SELECT u.distance FROM users AS u WHERE u.user_id = r.user_id) = 1), r.route_distance, (r.route_distance * 0.621)) AS route_distance, r.route_type, r.car_id, c.plate_number, c.friendly_name, r.start_place_id, p.name AS place_start_name, p.address AS place_start_address, r.end_place_id, pp.name AS place_end_name, pp.address AS place_end_address FROM routes AS r, cars AS c, places AS p, places AS pp WHERE r.car_id = c.car_id AND p.place_id = r.start_place_id AND pp.place_id = r.end_place_id AND r.archived = 0 AND r.user_id = ? ORDER BY r.route_datetime ASC";
	public static final String SQL_S_ROUTE = "SELECT r.route_id, r.route_datetime, r.route_distance, r.route_type, r.car_id, c.plate_number, c.friendly_name, r.start_place_id, p.name AS place_start_name, p.address AS place_start_address, r.end_place_id, pp.name AS place_end_name, pp.address AS place_end_address FROM routes AS r, cars AS c, places AS p, places AS pp WHERE r.car_id = c.car_id AND p.place_id = r.start_place_id AND pp.place_id = r.end_place_id AND r.route_id = ?";
	public static final String SQL_D_ROUTE = "DELETE FROM routes WHERE route_id = ?";
	public static final String SQL_I_ROUTE = "INSERT INTO routes (car_id, start_place_id, end_place_id, route_type, route_datetime, route_distance, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	public static final String SQL_U_ROUTE = "UPDATE routes SET car_id = ?, start_place_id = ?, end_place_id = ?, route_type = ?, route_datetime = ?, route_distance = ? WHERE route_id = ?";
}