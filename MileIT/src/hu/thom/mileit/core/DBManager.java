package hu.thom.mileit.core;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import hu.thom.mileit.models.CarModel;
import hu.thom.mileit.models.MaintenanceModel;
import hu.thom.mileit.models.PaymentMethodModel;
import hu.thom.mileit.models.PlaceModel;
import hu.thom.mileit.models.RefuelModel;
import hu.thom.mileit.models.TyreModel;
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
			forceObtainDS();
		}
	}

	/**
	 * This is for to obtain the datasource from the application server because
	 * Liberty and Tomcat works differently
	 * 
	 * @return {@link DataSource} if obtained, null otherwise
	 */
	private void forceObtainDS() {
		try {
			ds = (DataSource) new InitialContext().lookup("jdbc/mileit");
		} catch (NamingException e) {
			try {
				ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/mileit");
			} catch (NamingException e2) {
				logger.logException("forceObtainDS()", e);
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

				ps.setTimestamp(3, car.getManufacturerDateAsTimestamp());
				ps.setString(4, car.getColor());
				ps.setString(5, car.getVin());
				ps.setString(6, car.getPlateNumber());
				ps.setDouble(7, car.getFuelCapacity());
				ps.setInt(8, car.getFuel().getCode());
				ps.setTimestamp(9, car.getStartDateAsTimestamp());
				ps.setTimestamp(10, car.getEndDateAsTimestamp());
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
				ps = con.prepareStatement(m.getOperation() == 0 ? DBCommands.SQL_I_MAINTENANCE : DBCommands.SQL_U_MAINTENANCE);

				ps.setInt(1, m.getCar().getId());
				ps.setInt(2, m.getPayment().getId());
				ps.setInt(3, m.getUser().getId());
				ps.setDouble(4, m.getOdometer());
				ps.setDouble(5, m.getOdometer());
				ps.setTimestamp(6, m.getMaintenanceDateAsTimestamp());
				ps.setString(7, m.getDescription());
				ps.setDouble(8, m.getAmount());
				ps.setInt(9, m.getUser().getId());

				if (m.getOperation() == 1) {
					ps.setInt(10, m.getId());
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
	 * Creates a new record or updates an existing one in table 'tyres'
	 * 
	 * @param car {@link TyreModel} a tyre object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdateTyre(TyreModel tyre) {
		if (tyre != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(tyre.getOperation() == 0 ? DBCommands.SQL_I_TYRE : DBCommands.SQL_U_TYRE);
				ps.setInt(1, tyre.getCar().getId());
				ps.setInt(2, tyre.getType().getCode());
				ps.setInt(3, tyre.getManufacturerId());
				ps.setString(4, tyre.getModel());
				ps.setInt(5, tyre.getAxis().getCode());
				ps.setInt(6, tyre.getSizeR());
				ps.setInt(7, tyre.getSizeH());
				ps.setInt(8, tyre.getSizeW());
				ps.setTimestamp(9, new Timestamp(tyre.getPurchaseDate().getTime()));

				if (tyre.getOperation() == 1) {
					ps.setInt(10, tyre.getUser().getId());
				} else {
					ps.setInt(10, tyre.getId());
				}

				if (ps.executeUpdate() == 1) {
					return true;
				}
			} catch (Exception e) {
				logger.logException("createUpdateTyre()", e);
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
				ps.setTimestamp(3, rf.getRefuelDateAsTimestamp());
				ps.setInt(4, rf.getUser().getId());
				ps.setDouble(5, rf.getOdometer());
				ps.setDouble(6, rf.getOdometer());

				ps.setDouble(7, rf.getUnitPrice());
				ps.setDouble(8, rf.getAmount() / rf.getUnitPrice());
				ps.setInt(9, rf.getPayment().getId());
				ps.setDouble(10, rf.getAmount());

				if (rf.getOperation() == 0) {
					ps.setInt(11, rf.getUser().getId());
				} else {
					ps.setInt(11, rf.getId());
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
				car.setId(rs.getInt(1));
				car.setManufacturer(rs.getInt(2));
				car.setModel(rs.getString(3));
				car.setManufacturerDate(rs.getTimestamp(4));
				car.setColor(rs.getString(5));
				car.setVin(rs.getString(6));
				car.setPlateNumber(rs.getString(7));
				car.setFuelCapacity(rs.getDouble(8));
				car.setFuel(rs.getInt(9));
				car.setStartDate(rs.getTimestamp(10));
				car.setEndDate(rs.getTimestamp(11));
				car.setDescription(rs.getString(12));
				car.setFriendlyName(rs.getString(13));
				car.setActive(rs.getInt(14) == 1 ? true : false);
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
				car.setId(rs.getInt(1));
				car.setManufacturer(rs.getInt(2));
				car.setModel(rs.getString(3));
				car.setManufacturerDate(rs.getTimestamp(4));
				car.setColor(rs.getString(5));
				car.setVin(rs.getString(6));
				car.setPlateNumber(rs.getString(7));
				car.setFuelCapacity(rs.getDouble(8));
				car.setFuel(rs.getInt(9));
				car.setStartDate(rs.getTimestamp(10));
				car.setEndDate(rs.getTimestamp(11));
				car.setDescription(rs.getString(12));
				car.setFriendlyName(rs.getString(13));
				car.setActive(rs.getInt(14) == 1 ? true : false);
				car.setManufacturerName(rs.getString(16));
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
		Map<Integer, String> carVendors = null;

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_CAR_VENDORS);

			rs = ps.executeQuery();

			Map<Integer, String> cvTmp = new HashMap<Integer, String>();
			while (rs.next()) {
				cvTmp.put(rs.getInt(1), rs.getString(2));
			}

			carVendors = cvTmp.entrySet().stream().sorted(Map.Entry.comparingByValue())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		} catch (Exception e) {
			logger.logException("getCarVendors()", e);
		} finally {
			closeConnection();
		}

		return carVendors;
	}

	/**
	 * Returns a single tyre record from the database identified by its ID
	 * 
	 * @param id int the car's ID
	 * @return {@link TyreModel} if found, null otherwise
	 */
	public TyreModel getTyre(int id) {
		TyreModel tyre = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_TYRE);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				tyre = new TyreModel(rs.getInt(1), rs.getInt(2), rs.getInt(3), (byte) rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7),
						rs.getInt(8), rs.getString(9), rs.getString(10), (byte) rs.getInt(11), rs.getTimestamp(12));
			}

		} catch (Exception e) {
			logger.logException("getTyre()", e);
		} finally {
			closeConnection();
		}
		return tyre;
	}

	/**
	 * Returns a {@link List} of tyre entries from the database identified by user
	 * ID
	 * 
	 * @param id int the user's ID
	 * @return a {@link List} of {@link TyreModel} objects if found, empty list
	 *         otherwise
	 */
	public List<TyreModel> getTyres(int user_id) {
		List<TyreModel> tyres = new ArrayList<TyreModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_TYRES);
			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			while (rs.next()) {
				tyres.add(new TyreModel(rs.getInt(1), rs.getInt(2), rs.getInt(3), (byte) rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7),
						rs.getInt(8), rs.getString(9), rs.getString(10), (byte) rs.getInt(11), rs.getTimestamp(12)));
			}
		} catch (Exception e) {
			logger.logException("getTyres()", e);
		} finally {
			closeConnection();
		}

		return tyres;
	}

	/**
	 * Collects the available, active car vendors from table 'sup_car_manufacturers'
	 * 
	 * @return returns a {@link Map} of {@link Integer} as key and a {@link String}
	 *         as value of the available car vendors, or empty {@link Map} otherwise
	 */
	public Map<Integer, String> getTyreVendors() {
		Map<Integer, String> tyreVendors = null;

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_TYRE_VENDORS);

			rs = ps.executeQuery();

			Map<Integer, String> tvTmp = new HashMap<Integer, String>();
			while (rs.next()) {
				tvTmp.put(rs.getInt(1), rs.getString(2));
			}

			tyreVendors = tvTmp.entrySet().stream().sorted(Map.Entry.comparingByValue())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		} catch (Exception e) {
			logger.logException("getTyreVendors()", e);
		} finally {
			closeConnection();
		}

		return tyreVendors;
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
				rf.getCar().setId(rs.getInt(1));
				rf.setId(rs.getInt(2));
				rf.setLocation(new PlaceModel(rs.getInt(3)));
				rf.setRefuelDate(rs.getTimestamp(4));
				rf.setOdometer(rs.getDouble(5));
				rf.setFuelAmount(rs.getDouble(6));
				rf.setAmount(rs.getDouble(7));
				rf.setUnitPrice(rs.getDouble(8));
				rf.setPayment(new PaymentMethodModel(rs.getInt(9)));
			}
		} catch (Exception e) {
			logger.logException("getLastRefuel()", e);
		} finally {
			closeConnection();
		}
		return rf;
	}

	/**
	 * Returns a {@link PlaceModel} of a given place
	 * 
	 * @param id int the place's ID
	 * @return {@link PlaceModel} if found, null otherwise
	 */
	public PlaceModel getPlace(int id) {
		PlaceModel l = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PLACE);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				l = new PlaceModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getDouble(6));
			}
		} catch (Exception e) {
			logger.logException("getLocation()", e);
		} finally {
			closeConnection();
		}
		return l;
	}

	/**
	 * Returns a {@link List} of place entries from the database identified by user
	 * ID
	 * 
	 * @param id int the user's ID
	 * @return a {@link List} of {@link PlaceModel} objects if found, empty list
	 *         otherwise
	 */
	public List<PlaceModel> getPlaces(int id) {
		List<PlaceModel> ls = new ArrayList<PlaceModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PLACES);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
				ls.add(new PlaceModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5)));
			}
		} catch (Exception e) {
			logger.logException("getLocations()", e);
		} finally {
			closeConnection();
		}

		return ls;
	}

	/**
	 * Returns a {@link List} of maintenance entries from the database identified by
	 * user ID
	 * 
	 * @param id int the user's ID
	 * @return a {@link List} of {@link MaintenanceModel} objects if found, empty
	 *         list otherwise
	 */
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
				m.setId(rs.getInt(1));
				m.setCar(new CarModel(rs.getInt(2), rs.getString(9), rs.getString(10)));
				m.setUser(new UserModel(rs.getInt(3)));
				m.setPayment(new PaymentMethodModel(rs.getInt(4), rs.getString(11), null));
				m.setOdometer(rs.getDouble(5));
				m.setMaintenanceDate(rs.getDate(6));
				m.setDescription(rs.getString(7));
				m.setAmount(rs.getDouble(8));

				ms.add(m);
			}
		} catch (Exception e) {
			logger.logException("getMaintenances()", e);
		} finally {
			closeConnection();
		}

		return ms;
	}

	/**
	 * Returns a {@link MaintenanceModel} of a given maintenance
	 * 
	 * @param id int the maintenance's ID
	 * @return {@link MaintenanceModel} if found, null otherwise
	 */
	public MaintenanceModel getMaintenance(int id) {
		MaintenanceModel mm = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_MAINTENANCE);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				mm = new MaintenanceModel();
				mm.setId(rs.getInt(1));
				mm.setCar(new CarModel(rs.getInt(2)));
				mm.setUser(new UserModel(rs.getInt(3)));
				mm.setPayment(new PaymentMethodModel(rs.getInt(4)));
				mm.setOdometer(rs.getDouble(5));
				mm.setMaintenanceDate(rs.getDate(6));
				mm.setDescription(rs.getString(7));
				mm.setAmount(rs.getDouble(8));
			}
		} catch (Exception e) {
			logger.logException("getMaintenance()", e);
		} finally {
			closeConnection();
		}
		return mm;
	}

	/**
	 * Returns a {@link PaymentMethodModel} of a given payment method
	 * 
	 * @param id int the place's ID
	 * @return {@link PaymentMethodModel} if found, null otherwise
	 */
	public PaymentMethodModel getPaymentMethod(int id) {
		PaymentMethodModel pm = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PAYMENT);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				pm = new PaymentMethodModel(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (Exception e) {
			logger.logException("getPaymentMethod()", e);
		} finally {
			closeConnection();
		}
		return pm;
	}

	/**
	 * Returns a {@link List} of payment method entries from the database identified
	 * by user ID
	 * 
	 * @param id int the user's ID
	 * @return a {@link List} of {@link PaymentMethodModel} objects if found, empty
	 *         list otherwise
	 */
	public List<PaymentMethodModel> getPaymentMethods(int id) {
		List<PaymentMethodModel> pms = new ArrayList<PaymentMethodModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PAYMENTS);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
				pms.add(new PaymentMethodModel(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (Exception e) {
			logger.logException("getPaymentMethods()", e);
		} finally {
			closeConnection();
		}

		return pms;
	}

	/**
	 * Returns a {@link RefuelModel} of a given refuel
	 * 
	 * @param id int the refuel's ID
	 * @return {@link RefuelModel} if found, null otherwise
	 */
	public RefuelModel getRefuel(int id) {
		RefuelModel rf = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_REFUEL);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				rf = new RefuelModel(rs.getInt(1));
				rf.setCar(new CarModel(rs.getInt(2)));
				rf.setLocation(new PlaceModel(rs.getInt(3)));
				rf.setRefuelDate(rs.getTimestamp(4));
				rf.setOdometer(rs.getDouble(5));
				rf.setUnitPrice(rs.getDouble(6));
				rf.setFuelAmount(rs.getDouble(7));
				rf.setPayment(new PaymentMethodModel(rs.getInt(8)));
				rf.setAmount(rs.getDouble(9));
			}
		} catch (Exception e) {
			logger.logException("getRefuel()", e);
		} finally {
			closeConnection();
		}
		return rf;
	}

	/**
	 * Returns a {@link List} of refuel entries from the database identified by user
	 * ID
	 * 
	 * @param id int the user's ID
	 * @return a {@link List} of {@link RefuelModel} objects if found, empty list
	 *         otherwise
	 */
	public List<RefuelModel> getRefuels(int user_id) {
		List<RefuelModel> refuels = new ArrayList<RefuelModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_REFUELS);
			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			RefuelModel refuel = null;
			while (rs.next()) {
				refuel = new RefuelModel(rs.getInt(1));
				refuel.setCar(new CarModel(rs.getInt(2), rs.getString(12), rs.getString(11)));
				refuel.setLocation(new PlaceModel(rs.getInt(3), rs.getString(13), null, 0, 0));
				refuel.setRefuelDate(rs.getTimestamp(4));
				refuel.setOdometer(rs.getDouble(5));
				refuel.setFuelAmount(rs.getDouble(7));
				refuel.setAmount(rs.getDouble(9));
				refuel.setUnitPrice(rs.getDouble(6));
				refuel.setDistance(rs.getDouble(15));
				refuel.setPayment(new PaymentMethodModel(rs.getInt(8), rs.getString(14), null));

				refuels.add(refuel);
			}
		} catch (Exception e) {
			logger.logException("getRefuels()", e);
		} finally {
			closeConnection();
		}

		return refuels;
	}

	/**
	 * Checks whether the user exists in the DB or not
	 * 
	 * @param username {@link String} the username provided upon sign in
	 * @return true if yes, false otherwise
	 */
	private boolean checkUserProfile(String username) {
		boolean status = false;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_CHECK_USER);
			ps.setString(1, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				status = rs.getInt(1) == 1 ? true : false;
			}
		} catch (Exception e) {
			logger.logException("checkUserProfile()", e);
		} finally {
			closeConnection();
		}
		return status;
	}

	/**
	 * Creates a user profile for the user after the very first login
	 * 
	 * @param username {@link String} the username provided upon sign in
	 * @return true on success, false otherwise
	 */
	private boolean createUserProfile(String username) {
		boolean status = false;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_I_USER);
			ps.setString(1, username);
			status = ps.executeUpdate() == 1 ? true : false;
		} catch (Exception e) {
			logger.logException("createUserProfile()", e);
		} finally {
			closeConnection();
		}
		return status;
	}

	/**
	 * Returns the user profile
	 * 
	 * @param user {@link String} username of the user
	 * @return the completed {@link UserModel}
	 */
	public UserModel getUserProfile(String username) {
		UserModel user = new UserModel();
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PROFILE);
			ps.setString(1, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				user.setCurrency(rs.getString(1));
				user.setLocale(rs.getString(2));
				user.setId(rs.getInt(3));
				user.setUsername(rs.getString(4));
				user.setDistance(rs.getInt(5));
				user.setRounded(rs.getInt(6));
			}
		} catch (Exception e) {
			logger.logException("getUserProfile()", e);
		} finally {
			closeConnection();
		}
		return user;
	}

	/**
	 * Returns the user profile
	 * 
	 * @param user {@link UserModel} object containing the username
	 * @return the completed {@link UserModel}
	 */
	public UserModel getUserProfile(UserModel user) {
		if (!checkUserProfile(user.getUsername())) {
			createUserProfile(user.getUsername());
		}
		return getUserProfile(user.getUsername());
	}

	/**
	 * Sets the primary car of the user. All users have only one active primar car
	 * at any given time
	 * 
	 * @param user_id  int ID of the user
	 * @param car_id   int ID of the car which will be the primary one
	 * @param isNewCar boolean this car is a newly added or an existing car
	 * @return true on success, false otherwise
	 */
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

	/**
	 * Updates the user profile in the database
	 * 
	 * @param user {@link UserModel} the user data need to be written into the DB
	 * @return boolean true on success, false otherwise
	 */
	public boolean updateUserProfile(UserModel user) {
		if (user.getUsername() != null && !"".equalsIgnoreCase(user.getUsername())) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(DBCommands.SQL_U_PROFILE);

				ps.setString(1, user.getCurrency());
				ps.setString(2, user.getLocale());
				ps.setInt(3, user.getDistance());
				ps.setInt(4, user.getRounded());
				ps.setInt(5, user.getId());

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
