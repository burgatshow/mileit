package hu.thom.mileit.core;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import hu.thom.mileit.models.CarModel;
import hu.thom.mileit.models.PlaceModel;
import hu.thom.mileit.models.MaintenanceModel;
import hu.thom.mileit.models.PaymentMethodModel;
import hu.thom.mileit.models.RefuelModel;
import hu.thom.mileit.models.UserModel;

/**
 * Contains all the methods operating with database tables
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class DBManager implements Serializable {
	private static final long serialVersionUID = -5527815745942370595L;

	private DataSource ds;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	/**
	 * Logger instance
	 */
	private static LoggerHelper logger = new LoggerHelper(DBManager.class);

	/**
	 * Constructor
	 */
	public DBManager() {
		if (ds == null) {
			try {
				ds = (DataSource) new InitialContext().lookup("jdbc/mileit");
			} catch (Exception e) {
				logger.logException("DBManager()", e);
			}
		}
	}

	/**
	 * Method to be able to archive a car when the user decided to.
	 * 
	 * @param id int the user id
	 * @return true on success, false otherwise
	 */
	public boolean archiveCar(int id) {
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_U_CAR_ARCHIVE);
			ps.setInt(1, id);

			if (ps.executeUpdate() >= 1) {
				return true;
			}

		} catch (Exception e) {
			logger.logException("archiveCar()", e);
		} finally {
			closeConnection();
		}
		return false;
	}

	/**
	 * Closes the {@link Connection}, {@link PreparedStatement} or {@link ResultSet}
	 * if not did so far
	 * 
	 */
	private void closeConnection() {
		if (rs != null) {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
			} catch (Exception e) {
				logger.logException("closeConnection()", e);
			}
		}

		if (ps != null) {
			try {
				if (!ps.isClosed()) {
					ps.close();
				}
			} catch (Exception e) {
				logger.logException("closeConnection()", e);
			}
		}

		if (con != null) {
			try {
				if (!con.isClosed()) {
					con.close();
				}
			} catch (Exception e) {
				logger.logException("closeConnection()", e);
			}
		}
	}

	/**
	 * Creates a new record or updates an existing one in table 'cars'
	 * 
	 * @param car {@link CarModel} a car object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdateCar(CarModel car) {
		if (car != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(car.getOperation() == 0 ? DBCommands.SQL_I_CAR : DBCommands.SQL_U_CAR);

				ps.setInt(1, car.getManufacturer());
				ps.setString(2, car.getModel());

				ps.setTimestamp(3,
						car.getManufacturerDate() == null ? null : new Timestamp(car.getManufacturerDate().getTime()));
				ps.setString(4, car.getColor());
				ps.setString(5, car.getVin());
				ps.setString(6, car.getPlateNumber());
				ps.setDouble(7, car.getFuelCapacity());
				ps.setInt(8, car.getFuel());
				ps.setTimestamp(9, car.getStartDate() == null ? null : new Timestamp(car.getStartDate().getTime()));
				ps.setTimestamp(10, car.getEndDate() == null ? null : new Timestamp(car.getEndDate().getTime()));
				ps.setString(11, car.getDescription());
				ps.setString(12, car.getFriendlyName());

				if (car.getOperation() == 0) {
					ps.setInt(13, car.getUser().getId());
					ps.setInt(14, car.isActive() ? 1 : 0);
				} else {
					ps.setInt(13, car.isActive() ? 1 : 0);
					ps.setInt(14, car.getId());
				}

				if (ps.executeUpdate() == 1) {
					if (setPrimaryCar(car.getUser().getId(), car.getId(), car.getOperation() == 0 ? true : false)) {
						return true;
					} else {
						return false;
					}
				}
			} catch (Exception e) {
				logger.logException("createUpdateCar()", e);
			} finally {
				closeConnection();
			}
		}

		return false;

	}

	/**
	 * Creates a new record or updates an existing one in table 'places'
	 * 
	 * @param car {@link PlaceModel} a place object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdatePlace(PlaceModel l) {
		if (l != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(l.getOperation() == 0 ? DBCommands.SQL_I_PLACE : DBCommands.SQL_U_PLACE);

				ps.setString(1, l.getName());
				ps.setString(2, l.getAddress());
				ps.setDouble(3, l.getLatitude());
				ps.setDouble(4, l.getLongitude());

				if (l.getOperation() == 0) {
					ps.setInt(5, l.getUser().getId());
				} else {
					ps.setInt(5, l.getId());
				}

				if (ps.executeUpdate() == 1) {
					return true;
				}
			} catch (Exception e) {
				logger.logException("createUpdateLocation()", e);
			} finally {
				closeConnection();
			}
		}

		return false;
	}

	/**
	 * Creates a new record or updates an existing one in table 'maintenances'
	 * 
	 * @param car {@link MaintenanceModel} a maintenance object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdateMaintenance(MaintenanceModel m) {
		if (m != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(
						m.getOperation() == 0 ? DBCommands.SQL_I_MAINTENANCE : DBCommands.SQL_U_MAINTENANCE);

				ps.setInt(1, m.getCar().getId());
				ps.setInt(2, m.getPayment().getId());
				ps.setDouble(3, m.getOdometer());
				ps.setTimestamp(4,
						m.getMaintenanceDate() == null ? null : new Timestamp(m.getMaintenanceDate().getTime()));
				ps.setString(5, m.getDescription());
				ps.setDouble(6, m.getAmount());
				ps.setInt(7, m.getUser().getId());

				if (m.getOperation() == 1) {
					ps.setInt(8, m.getId());
				}

				if (ps.executeUpdate() == 1) {
					return true;
				}
			} catch (Exception e) {
				logger.logException("createUpdateMaintenance()", e);
			} finally {
				closeConnection();
			}
		}
		return false;
	}

	/**
	 * Creates a new record or updates an existing one in table 'payment_method'
	 * 
	 * @param car {@link PaymentMethodModel} a payment method object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdatePaymentMethod(PaymentMethodModel pm) {
		if (pm != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(pm.getOperation() == 0 ? DBCommands.SQL_I_PAYMENT : DBCommands.SQL_U_PAYMENT);

				ps.setString(1, pm.getName());
				ps.setString(2, pm.getDescription());

				if (pm.getOperation() == 0) {
					ps.setInt(3, pm.getUser().getId());
				} else {
					ps.setInt(3, pm.getId());
				}

				if (ps.executeUpdate() == 1) {
					return true;
				}
			} catch (Exception e) {
				logger.logException("createUpdatePaymentMethod()", e);
			} finally {
				closeConnection();
			}
		}

		return false;
	}

	/**
	 * Creates a new record or updates an existing one in table 'refuels'
	 * 
	 * @param car {@link RefuelModel} a refuel object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdateRefuel(RefuelModel rf) {
		if (rf != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(rf.getOperation() == 0 ? DBCommands.SQL_I_REFUEL : DBCommands.SQL_U_REFUEL);

				ps.setInt(1, rf.getCar().getId());
				ps.setInt(2, rf.getLocation().getId());
				ps.setTimestamp(3, new Timestamp(rf.getRefuelTimestamp().getTime()));
				ps.setDouble(4, rf.getOdometer());
				ps.setDouble(5, rf.getUnitPrice());
				ps.setDouble(6, rf.getAmount() / rf.getUnitPrice());
				ps.setInt(7, rf.getPayment().getId());
				ps.setDouble(8, rf.getAmount());

				if (rf.getOperation() == 0) {
					ps.setInt(9, rf.getUser().getId());
				} else {
					ps.setInt(9, rf.getId());
				}

				if (ps.executeUpdate() == 1) {
					return true;
				}
			} catch (Exception e) {
				logger.logException("createUpdateRefuel()", e);
			} finally {
				closeConnection();
			}
		}

		return false;
	}

	/**
	 * Returns a single car record from the database identified by its ID
	 * 
	 * @param id int the car's ID
	 * @return {@link CarModel} if found, null otherwise
	 */
	public CarModel getCar(int id) {
		CarModel car = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_CAR);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				car = new CarModel();
				car.setId(rs.getInt("car_id"));
				car.setManufacturer(rs.getInt("manufacturer"));
				car.setModel(rs.getString("model"));
				car.setManufacturerDate(rs.getTimestamp("manufacture_date"));
				car.setColor(rs.getString("color"));
				car.setVin(rs.getString("vin"));
				car.setPlateNumber(rs.getString("plate_number"));
				car.setFuelCapacity(rs.getDouble("fuel_capacity"));
				car.setFuel(rs.getInt("fuel"));
				car.setStartDate(rs.getTimestamp("start_date"));
				car.setEndDate(rs.getTimestamp("end_date"));
				car.setDescription(rs.getString("description"));
				car.setFriendlyName(rs.getString("friendly_name"));
				car.setActive(rs.getInt("active") == 1 ? true : false);
			}
			
		} catch (Exception e) {
			logger.logException("getCar()", e);
		} finally {
			closeConnection();
		}
		return car;
	}

	/**
	 * Returns a {@link List} of car records from the database identified by user ID
	 * 
	 * @param id int the user's ID
	 * @return a {@link List} of {@link CarModel} objects if found, empty list
	 *         otherwise
	 */
	public List<CarModel> getCars(int user_id) {
		List<CarModel> cars = new ArrayList<CarModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_CARS);
			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			CarModel car = null;
			while (rs.next()) {
				car = new CarModel();
				car.setId(rs.getInt("car_id"));
				car.setManufacturer(rs.getInt("manufacturer_id"));
				car.setManufacturerName(rs.getString("name"));
				car.setModel(rs.getString("model"));
				car.setManufacturerDate(rs.getTimestamp("manufacture_date"));
				car.setColor(rs.getString("color"));
				car.setVin(rs.getString("vin"));
				car.setPlateNumber(rs.getString("plate_number"));
				car.setFuelCapacity(rs.getDouble("fuel_capacity"));
				car.setFuel(rs.getInt("fuel"));
				car.setStartDate(rs.getTimestamp("start_date"));
				car.setEndDate(rs.getTimestamp("end_date"));
				car.setDescription(rs.getString("description"));
				car.setFriendlyName(rs.getString("friendly_name"));
				car.setActive(rs.getInt("active") == 1 ? true : false);

				cars.add(car);
			}
		} catch (Exception e) {
			logger.logException("getCars()", e);
		} finally {
			closeConnection();
		}

		return cars;
	}

	/**
	 * Collects the available, active car vendors from table 'sup_car_manufacturers'
	 * 
	 * @return returns a {@link Map} of {@link Integer} as key and a {@link String}
	 *         as value of the available car vendors, or empty {@link Map} otherwise
	 */
	public Map<Integer, String> getCarVendors() {
		Map<Integer, String> carVendors = new HashMap<Integer, String>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_CAR_VENDORS);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				carVendors.put(rs.getInt("manufacturer_id"), rs.getString("name"));
			}

		} catch (Exception e) {
			logger.logException("getCarVendors()", e);
		} finally {
			closeConnection();
		}

		return carVendors;
	}

	/**
	 * Returns a {@link List} of refuel records from the database identified by user
	 * ID
	 * 
	 * @param id int the user's ID
	 * @return a {@link List} of {@link RefuelModel} objects if found, empty list
	 *         otherwise
	 */
	public List<RefuelModel> getFuelPriceStats(int user_id) {
		List<RefuelModel> statData = new ArrayList<RefuelModel>();
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PRICE_GRAPH);
			ps.setInt(1, user_id);

			rs = ps.executeQuery();
			while (rs.next()) {
				statData.add(new RefuelModel(rs.getTimestamp(1), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4)));
			}

		} catch (Exception e) {
			logger.logException("getFuelPriceStats()", e);
		} finally {
			closeConnection();
		}

		return statData;
	}

	/**
	 * Returns a {@link RefuelModel} for the active user with basic statistical data
	 * 
	 * @param user_id int the user's ID
	 * @return {@link RefuelModel} if found, null otherwise
	 */
	public RefuelModel getLastRefuel(int user_id) {
		RefuelModel rf = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_BIG_STAT);
			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			if (rs.next()) {
				rf = new RefuelModel();
				rf.setCar(new CarModel());
				rf.getCar().setId(rs.getInt("car_id"));
				rf.setId(rs.getInt("refuel_id"));
				rf.setLocation(new PlaceModel());
				rf.getLocation().setId(rs.getInt("place_id"));
				rf.setRefuelTimestamp(rs.getTimestamp("refuel_timestamp"));
				rf.setOdometer(rs.getDouble("odometer"));
				rf.setFuelAmount(rs.getDouble("fuel_amount"));
				rf.setAmount(rs.getDouble("amount"));
				rf.setUnitPrice(rs.getDouble("unit_price"));
				rf.setPayment(new PaymentMethodModel());
				rf.getPayment().setId(rs.getInt("pm_id"));
			}
		} catch (Exception e) {
			logger.logException("getLastRefuel()", e);
		} finally {
			closeConnection();
		}
		return rf;
	}

	public PlaceModel getLocation(int id) {
		PlaceModel l = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PLACE);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				l = new PlaceModel(rs.getInt("place_id"), rs.getString("name"), rs.getString("address"),
						rs.getInt("user_id"), rs.getDouble("longitude"), rs.getDouble("latitude"));
			}
		} catch (Exception e) {
			logger.logException("getLocation()", e);
		} finally {
			closeConnection();
		}
		return l;
	}

	public List<PlaceModel> getLocations(int id) {
		List<PlaceModel> ls = new ArrayList<PlaceModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PLACES);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			PlaceModel l = null;
			while (rs.next()) {
				l = new PlaceModel();
				l.setId(rs.getInt("place_id"));
				l.setAddress(rs.getString("address"));
				l.setName(rs.getString("name"));
				l.setLongitude(rs.getDouble("longitude"));
				l.setLatitude(rs.getDouble("latitude"));

				ls.add(l);
			}
		} catch (Exception e) {
			logger.logException("getLocations()", e);
		} finally {
			closeConnection();
		}

		return ls;
	}

	public List<MaintenanceModel> getMaintenances(int id) {
		List<MaintenanceModel> ms = new ArrayList<MaintenanceModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_MAINTENANCES);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			MaintenanceModel m = null;
			while (rs.next()) {
				m = new MaintenanceModel();
				m.setId(rs.getInt("mntnc_id"));
				m.setCar(new CarModel(rs.getInt("car_id")));
				m.getCar().setFriendlyName(rs.getString("friendly_name"));
				m.getCar().setPlateNumber(rs.getString("plate_number"));
				m.setUser(new UserModel(rs.getInt("user_id")));
				m.setPayment(new PaymentMethodModel(rs.getInt("pm_id")));
				m.getPayment().setName(rs.getString("name"));
				m.setOdometer(rs.getDouble("odometer"));
				m.setDescription(rs.getString("description"));
				m.setMaintenanceDate(rs.getDate("date"));
				m.setAmount(rs.getDouble("amount"));

				ms.add(m);
			}
		} catch (Exception e) {
			logger.logException("getMaintenances()", e);
		} finally {
			closeConnection();
		}

		return ms;
	}

	public MaintenanceModel getMaintenance(int id) {
		MaintenanceModel mm = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_MAINTENANCE);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				mm = new MaintenanceModel();
				mm.setId(rs.getInt("mntnc_id"));
				mm.setCar(new CarModel(rs.getInt("car_id")));
				mm.setUser(new UserModel(rs.getInt("user_id")));
				mm.setPayment(new PaymentMethodModel(rs.getInt("pm_id")));
				mm.setOdometer(rs.getDouble("odometer"));
				mm.setDescription(rs.getString("description"));
				mm.setMaintenanceDate(rs.getDate("date"));
				mm.setAmount(rs.getDouble("amount"));
			}
		} catch (Exception e) {
			logger.logException("getMaintenance()", e);
		} finally {
			closeConnection();
		}
		return mm;
	}

	public PaymentMethodModel getPaymentMethod(int id) {
		PaymentMethodModel pm = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PAYMENT);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				pm = new PaymentMethodModel();
				pm.setId(rs.getInt("pm_id"));
				pm.setName(rs.getString("name"));
				pm.setDescription(rs.getString("description"));
			}
		} catch (Exception e) {
			logger.logException("getPaymentMethod()", e);
		} finally {
			closeConnection();
		}
		return pm;
	}

	public List<PaymentMethodModel> getPaymentMethods(int id) {
		List<PaymentMethodModel> pms = new ArrayList<PaymentMethodModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PAYMENTS);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
				pms.add(new PaymentMethodModel(rs.getInt("pm_id"), rs.getString("name"), rs.getString("description")));
			}
		} catch (Exception e) {
			logger.logException("getPaymentMethods()", e);
		} finally {
			closeConnection();
		}

		return pms;
	}

	public RefuelModel getRefuel(int id) {
		RefuelModel rf = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_REFUEL);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				rf = new RefuelModel();

				rf.setCar(new CarModel(rs.getInt("car_id")));
				rf.setId(rs.getInt("refuel_id"));
				rf.setLocation(new PlaceModel(rs.getInt("place_id")));
				rf.setRefuelTimestamp(rs.getTimestamp("refuel_timestamp"));
				rf.setOdometer(rs.getDouble("odometer"));
				rf.setFuelAmount(rs.getDouble("fuel_amount"));
				rf.setAmount(rs.getDouble("amount"));
				rf.setUnitPrice(rs.getDouble("unit_price"));
				rf.setPayment(new PaymentMethodModel(rs.getInt("pm_id")));
			}
		} catch (Exception e) {
			logger.logException("getRefuel()", e);
		} finally {
			closeConnection();
		}
		return rf;
	}

	public List<RefuelModel> getRefuels(int user_id) {
		List<RefuelModel> refuels = new ArrayList<RefuelModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_REFUELS);
			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			RefuelModel refuel = null;
			while (rs.next()) {
				refuel = new RefuelModel();
				refuel.setCar(new CarModel());
				refuel.getCar().setPlateNumber(rs.getString("plate_number"));
				refuel.getCar().setFriendlyName(rs.getString("friendly_name"));

				refuel.setId(rs.getInt("refuel_id"));

				refuel.setLocation(new PlaceModel());
				refuel.getLocation().setId(rs.getInt("place_id"));
				refuel.getLocation().setName(rs.getString("location"));
				refuel.setRefuelTimestamp(rs.getTimestamp("refuel_timestamp"));
				refuel.setOdometer(rs.getDouble("odometer"));
				refuel.setFuelAmount(rs.getDouble("fuel_amount"));
				refuel.setAmount(rs.getDouble("amount"));
				refuel.setUnitPrice(rs.getDouble("unit_price"));
				refuel.setDistance(rs.getDouble("prev_refuel_diff"));

				refuel.setPayment(new PaymentMethodModel());
				refuel.getPayment().setId(rs.getInt("pm_id"));
				refuel.getPayment().setName(rs.getString("payment_method_name"));

				refuels.add(refuel);
			}
		} catch (Exception e) {
			logger.logException("getRefuels()", e);
		} finally {
			closeConnection();
		}

		return refuels;
	}

	public UserModel getUserProfile(String username) {
		UserModel user = new UserModel();
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PROFILE);
			ps.setString(1, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				user.setCurrency(rs.getString("currency"));
				user.setLocale(rs.getString("locale"));
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
			}
		} catch (Exception e) {
			logger.logException("getUserProfile()", e);
		} finally {
			closeConnection();
		}
		return user;
	}

	public UserModel getUserProfile(UserModel user) {
		return getUserProfile(user.getUsername());
	}

	private boolean setPrimaryCar(int user_id, int car_id, boolean isNewCar) {
		boolean status = false;

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(isNewCar ? DBCommands.SQL_I_CAR_PRIMARY : DBCommands.SQL_U_CAR_PRIMARY);
			if (isNewCar) {
				ps.setInt(1, user_id);
			} else {
				ps.setInt(1, car_id);
			}

			ps.setInt(2, user_id);

			if (ps.executeUpdate() > 0) {
				status = true;
			}

		} catch (Exception e) {
			logger.logException("setPrimaryCar()", e);
		} finally {
			closeConnection();
		}

		return status;
	}

	public boolean updateUserProfile(UserModel user) {
		if (user.getUsername() != null && !"".equalsIgnoreCase(user.getUsername())) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(DBCommands.SQL_U_PROFILE);

				ps.setString(1, (user.getCurrency() == null || "".equalsIgnoreCase(user.getCurrency())) ? "Ft"
						: user.getCurrency());
				ps.setString(2,
						(user.getLocale() == null || "".equalsIgnoreCase(user.getLocale())) ? "hu" : user.getLocale());
				ps.setString(3, user.getUsername());

				if (ps.executeUpdate() == 1) {
					return true;
				}
			} catch (Exception e) {
				logger.logException("updateUserProfile()", e);
			} finally {
				closeConnection();
			}
		}

		return false;
	}
}
