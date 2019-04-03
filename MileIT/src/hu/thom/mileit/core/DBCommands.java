package hu.thom.mileit.core;

import java.io.Serializable;

/**
 * All SQL commands used in {@link DBManager}
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class DBCommands implements Serializable {
	private static final long serialVersionUID = -1208055450418159794L;

	// Home
	public static final String SQL_S_BIG_STAT = "SELECT * FROM refuels WHERE user_id = ? ORDER BY refuel_timestamp DESC LIMIT 1";
	public static final String SQL_S_PRICE_GRAPH = "SELECT refuel_timestamp, odometer, amount, unit_price FROM refuels WHERE user_id = ? ORDER BY refuel_timestamp LIMIT 10";

	// User
	public static final String SQL_S_PROFILE = "SELECT u.currency, u.locale, u.user_id, u.username, u.distance, u.rounded FROM users AS u WHERE u.username = ?";
	public static final String SQL_U_PROFILE = "UPDATE users SET currency = ?, locale = ?, distance = ?, rounded = ? WHERE user_id = ?";

	// Cars
	public static final String SQL_I_CAR = "INSERT INTO cars (manufacturer, model, manufacture_date, color, UPPER(vin), UPPER(plate_number), fuel_capacity, fuel, start_date, end_date, description, friendly_name, user_id, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String SQL_U_CAR = "UPDATE cars SET manufacturer = ?, model = ?, manufacture_date = ?, color = ?, vin = UPPER(?), plate_number = UPPER(?), fuel_capacity = ?, fuel = ?, start_date = ?, end_date = ?, description = ?, friendly_name = ?, active = ? WHERE car_id = ?";
	public static final String SQL_U_CAR_ARCHIVE = "UPDATE cars SET archived = 1 WHERE car_id = ?";
	public static final String SQL_S_CARS = "SELECT c.*, v.manufacturer_id, IF(v.NAME = 'BMW', 'BMW', CONCAT(UPPER(SUBSTR(v.NAME, 1, 1)), LOWER(SUBSTR(v.NAME, 2, LENGTH(v.NAME)-1)))) AS name FROM cars AS c, sup_car_manufacturers AS v WHERE c.user_id = ? AND c.archived = 0 AND c.manufacturer = v.manufacturer_id ORDER BY c.active DESC, c.plate_number ASC";
	public static final String SQL_S_CAR = "SELECT * FROM cars WHERE car_id = ?";
	public static final String SQL_I_CAR_PRIMARY = "UPDATE cars SET active = 0 WHERE car_id <> (SELECT MAX(car_id) FROM cars WHERE user_id = ? LIMIT 1) AND user_id = ?";
	public static final String SQL_U_CAR_PRIMARY = "UPDATE cars SET active = 0 WHERE car_id <> ? AND user_id = ?";
	public static final String SQL_S_CAR_VENDORS = "SELECT manufacturer_id, IF(NAME = 'BMW', 'BMW', CONCAT(UPPER(SUBSTR(NAME, 1, 1)), LOWER(SUBSTR(NAME, 2, LENGTH(NAME)-1)))) AS name FROM sup_car_manufacturers WHERE active = 1 ORDER BY name ASC";

	// Maintenances
	public static final String SQL_S_MAINTENANCES = "SELECT m.mntnc_id, m.car_id, m.user_id, m.pm_id, IF(((SELECT distance FROM users AS u WHERE u.user_id = m.user_id) = 1), m.odometer, (m.odometer * 0.621)) AS odometer, m.date, m.description, m.amount, c.friendly_name, UPPER(c.plate_number) AS plate_number, pm.name  FROM maintenances AS m, cars AS c, payment_method AS pm WHERE c.car_id = m.car_id AND m.pm_id = pm.pm_id AND m.user_id = ? ORDER BY m.date DESC";
	public static final String SQL_I_MAINTENANCE = "INSERT INTO maintenances (car_id, pm_id, odometer, date, description, amount, user_id) VALUES (?, ?, IF(((SELECT distance FROM users WHERE user_id = ?) = 1), ?, (? / 0.621)), ?, ?, ?, ?)";
	public static final String SQL_U_MAINTENANCE = "UPDATE maintenances SET car_id = ?, pm_id = ?, odometer = IF(((SELECT distance FROM users WHERE user_id = ?) = 1), ?, (? / 0.621)), date = ?, description = ?, amount = ? WHERE user_id = ? AND mntnc_id = ?";
	public static final String SQL_S_MAINTENANCE = "SELECT m.mntnc_id, m.car_id, m.user_id, m.pm_id, IF(((SELECT distance FROM users AS u WHERE u.user_id = m.user_id) = 1), m.odometer, (m.odometer * 0.621)) as odometer, m.date, m.description, m.amount FROM maintenances AS m WHERE m.mntnc_id = ?";

	// Payment methods
	public static final String SQL_S_PAYMENTS = "SELECT * FROM payment_method WHERE user_id = ? ORDER BY name";
	public static final String SQL_I_PAYMENT = "INSERT INTO payment_method (name, description, user_id) VALUES (?, ?, ?)";
	public static final String SQL_U_PAYMENT = "UPDATE payment_method SET name = ?, description = ? WHERE pm_id = ?";
	public static final String SQL_S_PAYMENT = "SELECT * FROM payment_method WHERE pm_id = ?";

	// Locations
	public static final String SQL_S_PLACES = "SELECT * FROM places WHERE user_id = ? ORDER BY name";
	public static final String SQL_I_PLACE = "INSERT INTO places (name, address, latitude, longitude, user_id) VALUES (?, ?, ?, ?, ?)";
	public static final String SQL_U_PLACE = "UPDATE places SET name = ?, address = ?, latitude = ?, longitude = ? WHERE place_id = ?";
	public static final String SQL_S_PLACE = "SELECT * FROM places WHERE place_id = ?";

	// Refuels
	public static final String SQL_S_REFUELS = "SELECT r.refuel_id, r.car_id, r.place_id, r.refuel_timestamp, IF(((SELECT u.distance FROM users AS u WHERE u.user_id = r.user_id) = 1), r.odometer, (r.odometer * 0.621)) AS odometer, r.unit_price, r.fuel_amount, r.pm_id, r.amount, r.user_id, UPPER(c.plate_number) AS plate_number, c.friendly_name, p.name AS location, pm.name AS payment_method_name, IFNULL(IF(((SELECT u.distance FROM users AS u WHERE u.user_id = r.user_id) = 1), r.odometer, (r.odometer * 0.621)) - (SELECT IF(((SELECT u.distance FROM users AS u WHERE u.user_id = r.user_id) = 1), p.odometer, (p.odometer * 0.621)) FROM refuels AS p WHERE p.refuel_timestamp = (SELECT MAX(pp.refuel_timestamp) FROM refuels AS pp WHERE pp.refuel_timestamp < r.refuel_timestamp ) ), 0) AS prev_refuel_diff FROM cars AS c, refuels AS r, places AS p, payment_method AS pm WHERE r.car_id IN (SELECT c.car_id FROM cars AS c WHERE c.user_id = ? AND c.archived = 0 ) AND pm.pm_id = r.pm_id AND c.car_id = r.car_id AND p.place_id = r.place_id ORDER BY r.refuel_timestamp DESC";
	public static final String SQL_I_REFUEL = "INSERT INTO refuels (car_id, place_id, refuel_timestamp, odometer, unit_price, fuel_amount, pm_id, amount, user_id) VALUES (?, ?, ?, IF(((SELECT distance FROM users WHERE user_id = ?) = 1), ?, (? / 0.621)), ?, ?, ?, ?, ?)";
	public static final String SQL_U_REFUEL = "UPDATE refuels SET car_id = ?, place_id = ?, refuel_timestamp = ?, odometer = IF(((SELECT distance FROM users WHERE user_id = ?) = 1), ?, (? / 0.621)), unit_price = ?, fuel_amount = ?, pm_id = ?, amount = ? WHERE refuel_id = ?";
	public static final String SQL_S_REFUEL = "SELECT r.refuel_id, r.car_id, r.place_id, r.refuel_timestamp, IF(((SELECT u.distance FROM users AS u WHERE u.user_id = r.user_id) = 1), r.odometer, (r.odometer * 0.621)) AS odometer, r.unit_price, r.fuel_amount, r.pm_id, r.amount, r.user_id FROM refuels AS r WHERE r.refuel_id = ?";
}