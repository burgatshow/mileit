package hu.thom.mileit.core;

import java.io.Serializable;

public class DBCommands implements Serializable {
	private static final long serialVersionUID = -1208055450418159794L;

	// Home
	public static final String SQL_S_BIG_STAT = "SELECT * FROM refuels WHERE user_id = ? ORDER BY refuel_timestamp DESC LIMIT 1";
	public static final String SQL_S_PRICE_GRAPH = "SELECT refuel_timestamp, odometer, amount, unit_price FROM refuels WHERE user_id = ? ORDER BY refuel_timestamp LIMIT 10";

	// User
	public static final String SQL_S_PROFILE = "SELECT * FROM users WHERE username = ?";
	public static final String SQL_U_PROFILE = "UPDATE users SET currency = ?, locale = ? WHERE user_id = ?";

	// Cars
	public static final String SQL_I_CAR = "INSERT INTO cars (manufacturer, model, manufacture_date, color, vin, plate_number, fuel_capacity, fuel, start_date, end_date, description, friendly_name, user_id, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String SQL_U_CAR = "UPDATE cars SET manufacturer = ?, model = ?, manufacture_date = ?, color = ?, vin = ?, plate_number = ?, fuel_capacity = ?, fuel = ?, start_date = ?, end_date = ?, description = ?, friendly_name = ?, active = ? WHERE car_id = ?";
	public static final String SQL_U_CAR_ARCHIVE = "UPDATE cars SET archived = 1 WHERE car_id = ?";
	public static final String SQL_S_CARS = "SELECT * FROM cars WHERE user_id = ? AND archived = 0 ORDER BY active DESC, plate_number ASC";
	public static final String SQL_S_CAR = "SELECT * FROM cars WHERE car_id = ?";
	public static final String SQL_I_CAR_PRIMARY = "UPDATE cars SET active = 0 WHERE car_id <> (SELECT MAX(car_id) FROM cars WHERE user_id = ? LIMIT 1) AND user_id = ?";
	public static final String SQL_U_CAR_PRIMARY = "UPDATE cars SET active = 0 WHERE car_id <> ? AND user_id = ?";
	
	// Maintenances
	public static final String SQL_S_MAINTENANCES = "SELECT * FROM maintenances ORDER BY date DESC ";

	// Payment methods
	public static final String SQL_S_PAYMENTS = "SELECT * FROM payment_method WHERE user_id = ? ORDER BY name";
	public static final String SQL_I_PAYMENT = "INSERT INTO payment_method (name, description, user_id) VALUES (?, ?, ?)";
	public static final String SQL_U_PAYMENT = "UPDATE payment_method SET name = ?, description = ? WHERE pm_id = ?";
	public static final String SQL_S_PAYMENT = "SELECT * FROM payment_method WHERE pm_id = ?";

	// Locations
	public static final String SQL_S_LOCATIONS = "SELECT * FROM places WHERE user_id = ? ORDER BY name";
	public static final String SQL_I_LOCATION = "INSERT INTO places (name, address, latitude, longitude, user_id) VALUES (?, ?, ?, ?, ?)";
	public static final String SQL_U_LOCATION = "UPDATE places SET name = ?, address = ?, latitude = ?, longitude = ? WHERE place_id = ?";
	public static final String SQL_S_LOCATION = "SELECT * FROM places WHERE place_id = ?";

	// Refuels
	public static final String SQL_S_REFUELS = "SELECT r.*, c.plate_number, c.friendly_name, p.name AS location, pm.name AS payment_method_name FROM cars c, refuels r, places p, payment_method pm WHERE r.car_id IN (SELECT car_id FROM cars WHERE user_id = ?) AND pm.pm_id = r.pm_id AND c.car_id = r.car_id AND p.place_id = r.place_id ORDER BY r.refuel_timestamp DESC LIMIT 0,25";
	public static final String SQL_I_REFUEL = "INSERT INTO refuels (car_id, place_id, refuel_timestamp, odometer, unit_price, fuel_amount, pm_id, amount, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String SQL_U_REFUEL = "UPDATE refuels SET car_id = ?, place_id = ?, refuel_timestamp = ?, odometer = ?, unit_price = ?, fuel_amount = ?, pm_id = ?, amount = ? WHERE refuel_id = ?";
	public static final String SQL_S_REFUEL = "SELECT * FROM refuels WHERE refuel_id = ?";
}