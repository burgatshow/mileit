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
package hu.thom.mileit.core.data;

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

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.core.EncryptionManager;
import hu.thom.mileit.models.CarModel;
import hu.thom.mileit.models.MaintenanceModel;
import hu.thom.mileit.models.Model;
import hu.thom.mileit.models.PaymentMethodModel;
import hu.thom.mileit.models.PlaceModel;
import hu.thom.mileit.models.RefuelModel;
import hu.thom.mileit.models.RouteModel;
import hu.thom.mileit.models.RouteModel.RouteType;
import hu.thom.mileit.models.TyreEventModel;
import hu.thom.mileit.models.TyreModel;
import hu.thom.mileit.models.TyreModel.Axis;
import hu.thom.mileit.models.TyreModel.TyreType;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.LogManager;
import hu.thom.mileit.utils.LogMessages;

/**
 * Contains all the methods operating with database tables
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class DBManager implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5527815745942370595L;

	/**
	 * Logger instance
	 */
	private static LogManager logger = new LogManager(DBManager.class);
	private DataSource ds;
	private Connection con;
	private PreparedStatement ps;

	private ResultSet rs;

	/**
	 * Instance
	 */
	private static DBManager db;

	/**
	 * Constructor
	 * 
	 * @return {@link DynaCacheManager}
	 */
	public static DBManager getInstance() {
		logger.logEnter("getInstance()");
		if (db == null) {
			db = new DBManager();
		}

		logger.logExit("getInstance()");
		return db;
	}

	/**
	 * Constructor
	 */
	private DBManager() {
		logger.logEnter("DBManager()");
		if (ds == null) {
			try {
				ds = (DataSource) new InitialContext().lookup("jdbc/mileit");
			} catch (NamingException e) {
				logger.logException("DBManager()", e);
			}
		}
		logger.logExit("DBManager()");
	}

	/**
	 * Method to be able to archive a car when the user decided to.
	 * 
	 * @param id int the car's id
	 * @return true on success, false otherwise
	 */
	public boolean archiveOrActivateCar(int id, int value) {
		logger.logEnter("archiveOrActivateCar()");

		boolean status = false;

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_U_CAR_ARCHIVE_OR_ACTIVATE);
			logger.logTrace("archiveOrActivateCar()", LogMessages.SQL_COMMAND, DBCommands.SQL_U_CAR_ARCHIVE_OR_ACTIVATE);
			ps.setInt(1, value);
			ps.setInt(2, id);

			status = ps.executeUpdate() >= 1 ? true : false;

		} catch (Exception e) {
			logger.logException("archiveOrActivateCar()", e);
		} finally {
			closeConnection();
		}
		logger.logExit("archiveOrActivateCar()");
		return status;
	}

	/**
	 * Method to be able to archive a payment method when the user decided to.
	 * 
	 * @param id int the payment method's ID
	 * @return true on success, false otherwise
	 */
	public boolean archivePaymentMethod(int id) {
		logger.logEnter("archivePaymentMethod()");
		boolean status = false;

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_U_PAYMENT_ARCHIVE);
			logger.logTrace("archivePaymentMethod()", LogMessages.SQL_COMMAND, DBCommands.SQL_U_PAYMENT_ARCHIVE);
			ps.setInt(1, id);

			status = ps.executeUpdate() >= 1 ? true : false;

		} catch (Exception e) {
			logger.logException("archivePaymentMethod()", e);
		} finally {
			closeConnection();
		}
		logger.logExit("archivePaymentMethod()");
		return status;
	}

	/**
	 * Method to be able to archive a place when the user decided to.
	 * 
	 * @param id int the place's ID
	 * @return true on success, false otherwise
	 */
	public boolean archivePlace(int id) {
		logger.logEnter("archivePlace()");
		boolean status = false;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_U_PLACE_ARCHIVE);
			logger.logTrace("archivePlace()", LogMessages.SQL_COMMAND, DBCommands.SQL_U_PLACE_ARCHIVE);

			ps.setInt(1, id);

			status = ps.executeUpdate() >= 1 ? true : false;

		} catch (Exception e) {
			logger.logException("archivePlace()", e);
		} finally {
			closeConnection();
		}
		logger.logExit("archivePlace()");
		return status;
	}

	/**
	 * Method to be able to archive a tyre when the user decided to.
	 * 
	 * @param id int the tyre's ID
	 * @return true on success, false otherwise
	 */
	public boolean archiveTyre(int id) {
		logger.logEnter("archiveTyre()");
		boolean status = false;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_U_TYRE_ARCHIVE);
			logger.logTrace("archiveTyre()", LogMessages.SQL_COMMAND, DBCommands.SQL_U_TYRE_ARCHIVE);
			ps.setInt(1, id);

			status = ps.executeUpdate() >= 1 ? true : false;

		} catch (Exception e) {
			logger.logException("archiveTyre()", e);
		} finally {
			closeConnection();
		}
		logger.logExit("archiveTyre()");
		return status;
	}

	/**
	 * Authenticates the user during logon process on the UI or the API access
	 * 
	 * @param username {@link String} the user's username
	 * @param password {@link String} the user's encrypted password
	 * @return true if valid, false otherwise
	 */
	public boolean authenticateUser(String username, String password) {
		logger.logEnter("authenticateUser()");
		boolean status = false;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_AUTH);
			logger.logTrace("authenticateUser()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_AUTH);

			ps.setString(1, username);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs.next()) {
				status = true;
			}
		} catch (Exception e) {
			logger.logException("authenticateUser()", e);
		} finally {
			closeConnection();
		}
		logger.logExit("authenticateUser()");
		return status;
	}

	/**
	 * Closes the {@link Connection}, {@link PreparedStatement} or {@link ResultSet}
	 * if not did so far
	 * 
	 */
	private void closeConnection() {
		logger.logEnter("closeConnection()");
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
		logger.logExit("closeConnection()");
	}

	/**
	 * Creates a new record in table 'tyres_events'
	 * 
	 * @param car {@link TyreEventModel} a tyre event object
	 * @return true on success, false otherwise
	 */
	public boolean createTyreEvent(TyreEventModel tyreEvent) {
		logger.logEnter("createTyreEvent()");
		boolean status = false;
		if (tyreEvent != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(DBCommands.SQL_I_TYRE_EVENT);
				logger.logTrace("createTyreEvent()", LogMessages.SQL_COMMAND, DBCommands.SQL_I_TYRE_EVENT);

				ps.setInt(1, tyreEvent.getTyre().getId());
				ps.setInt(2, tyreEvent.getUser().getId());
				ps.setInt(3, tyreEvent.getCar().getId());
				ps.setDouble(4, tyreEvent.getOdometerStart());
				ps.setDouble(5, tyreEvent.getOdometerEnd());
				ps.setTimestamp(6, new Timestamp(tyreEvent.getEventDate().getTime()));

				status = ps.executeUpdate() == 1 ? true : false;
			} catch (Exception e) {
				logger.logException("createTyreEvent()", e);
			} finally {
				closeConnection();
			}
		}

		logger.logExit("createTyreEvent()");
		return status;
	}

	/**
	 * Creates a new record or updates an existing one in table 'cars'
	 * 
	 * @param car {@link CarModel} a car object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdateCar(CarModel car) {
		logger.logEnter("createUpdateCar()");
		boolean status = false;
		if (car != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(car.getOperation() == 0 ? DBCommands.SQL_I_CAR : DBCommands.SQL_U_CAR);
				logger.logTrace("createUpdateCar()", LogMessages.SQL_COMMAND, car.getOperation() == 0 ? DBCommands.SQL_I_CAR : DBCommands.SQL_U_CAR);

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

				ps.setInt(13, car.getOperation() == 0 ? car.getUser().getId() : car.isActive() ? 1 : 0);
				ps.setInt(14, car.getOperation() == 0 ? car.isActive() ? 1 : 0 : car.getId());

				if (ps.executeUpdate() == 1) {
					if (setPrimaryCar(car.getUser().getId(), car.getId(), car.getOperation() == 0 ? true : false)) {
						status = true;
					}
				}
			} catch (Exception e) {
				logger.logException("createUpdateCar()", e);
			} finally {
				closeConnection();
			}
		}

		logger.logExit("createUpdateMaintenance()");
		return status;

	}

	/**
	 * Creates a new record or updates an existing one in table 'maintenances'
	 * 
	 * @param car {@link MaintenanceModel} a maintenance object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdateMaintenance(MaintenanceModel m) {
		logger.logEnter("createUpdateMaintenance()");
		boolean status = false;
		if (m != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(m.getOperation() == 0 ? DBCommands.SQL_I_MAINTENANCE : DBCommands.SQL_U_MAINTENANCE);
				logger.logTrace("createUpdateMaintenance()", LogMessages.SQL_COMMAND,
						m.getOperation() == 0 ? DBCommands.SQL_I_MAINTENANCE : DBCommands.SQL_U_MAINTENANCE);

				ps.setInt(1, m.getCar().getId());
				ps.setInt(2, m.getPayment().getId());
				ps.setInt(3, m.getUser().getId());
				ps.setDouble(4, m.getOdometer());
				ps.setDouble(5, m.getOdometer());
				ps.setTimestamp(6, m.getMaintenanceDateAsTimestamp());
				ps.setString(7, m.getDescription());
				ps.setDouble(8, m.getAmount());
				ps.setTimestamp(9, m.getExpirationAsTimestamp());
				ps.setInt(10, m.getUser().getId());

				if (m.getOperation() == 1) {
					ps.setInt(11, m.getId());
				}

				status = ps.executeUpdate() == 1 ? true : false;
			} catch (Exception e) {
				logger.logException("createUpdateMaintenance()", e);
			} finally {
				closeConnection();
			}
		}

		logger.logExit("createUpdateMaintenance()");
		return status;
	}

	/**
	 * Creates a new record or updates an existing one in table 'payment_method'
	 * 
	 * @param car {@link PaymentMethodModel} a payment method object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdatePaymentMethod(PaymentMethodModel pm) {
		logger.logEnter("createUpdatePaymentMethod()");
		boolean status = false;
		if (pm != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(pm.getOperation() == 0 ? DBCommands.SQL_I_PAYMENT : DBCommands.SQL_U_PAYMENT);
				logger.logTrace("createUpdatePaymentMethod()", LogMessages.SQL_COMMAND,
						pm.getOperation() == 0 ? DBCommands.SQL_I_PAYMENT : DBCommands.SQL_U_PAYMENT);

				ps.setString(1, pm.getName());
				ps.setString(2, pm.getDescription());
				ps.setInt(3, pm.getOperation() == 0 ? pm.getUser().getId() : pm.getId());

				status = ps.executeUpdate() == 1 ? true : false;
			} catch (Exception e) {
				logger.logException("createUpdatePaymentMethod()", e);
			} finally {
				closeConnection();
			}
		}

		logger.logExit("createUpdatePaymentMethod()");
		return status;
	}

	/**
	 * Creates a new record or updates an existing one in table 'places'
	 * 
	 * @param car {@link PlaceModel} a place object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdatePlace(PlaceModel l) {
		logger.logEnter("createUpdatePlace()");
		boolean status = false;
		if (l != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(l.getOperation() == 0 ? DBCommands.SQL_I_PLACE : DBCommands.SQL_U_PLACE);
				logger.logTrace("createUpdatePlace()", LogMessages.SQL_COMMAND,
						l.getOperation() == 0 ? DBCommands.SQL_I_PLACE : DBCommands.SQL_U_PLACE);

				ps.setString(1, l.getName());
				ps.setString(2, l.getAddress());
				ps.setDouble(3, l.getLatitude());
				ps.setDouble(4, l.getLongitude());
				ps.setInt(5, l.isFuelStation() ? 1 : 0);
				ps.setInt(6, l.getOperation() == 0 ? l.getUser().getId() : l.getId());

				status = ps.executeUpdate() == 1 ? true : false;
			} catch (Exception e) {
				logger.logException("createUpdatePlace()", e);
			} finally {
				closeConnection();
			}
		}
		logger.logExit("createUpdatePlace()");
		return status;
	}

	/**
	 * Creates a new record or updates an existing one in table 'refuels'
	 * 
	 * @param car {@link RefuelModel} a refuel object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdateRefuel(RefuelModel rf) {
		logger.logEnter("createUpdateRefuel()");
		boolean status = false;

		if (rf != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(rf.getOperation() == 0 ? DBCommands.SQL_I_REFUEL : DBCommands.SQL_U_REFUEL);
				logger.logTrace("createUpdateRefuel()", LogMessages.SQL_COMMAND,
						rf.getOperation() == 0 ? DBCommands.SQL_I_REFUEL : DBCommands.SQL_U_REFUEL);

				ps.setInt(1, rf.getCar().getId());
				ps.setInt(2, rf.getPlace().getId());
				ps.setTimestamp(3, rf.getRefuelDateAsTimestamp());
				ps.setInt(4, rf.getUser().getId());
				ps.setDouble(5, rf.getOdometer());
				ps.setDouble(6, rf.getOdometer());

				ps.setDouble(7, rf.getUnitPrice());
				ps.setDouble(8, rf.getAmount() / rf.getUnitPrice());
				ps.setInt(9, rf.getPayment().getId());
				ps.setDouble(10, rf.getAmount());
				ps.setInt(11, rf.isPartialRefuel() ? 1 : 0);

				ps.setInt(12, rf.getOperation() == 0 ? rf.getUser().getId() : rf.getId());

				status = ps.executeUpdate() == 1 ? true : false;
			} catch (Exception e) {
				logger.logException("createUpdateRefuel()", e);
			} finally {
				closeConnection();
			}
		}

		logger.logExit("createUpdateRefuel()");
		return status;
	}

	/**
	 * Creates a new record or updates an existing one in table 'routes'
	 * 
	 * @param route {@link RouteModel} a route object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdateRoute(RouteModel r) {
		logger.logEnter("createUpdateRoute()");
		boolean status = false;
		if (r != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(r.getOperation() == 0 ? DBCommands.SQL_I_ROUTE : DBCommands.SQL_U_ROUTE);
				logger.logTrace("createUpdateRoute()", LogMessages.SQL_COMMAND,
						r.getOperation() == 0 ? DBCommands.SQL_I_ROUTE : DBCommands.SQL_U_ROUTE);

				ps.setInt(1, r.getCar().getId());
				ps.setInt(2, r.getStartPlace().getId());
				ps.setInt(3, r.getEndPlace().getId());
				ps.setInt(4, RouteType.toCode(r.getRouteType()));
				ps.setTimestamp(5, r.getRouteDatetimeAsTimestamp());
				ps.setDouble(6, r.getDistance());

				ps.setInt(7, r.getOperation() == 0 ? r.getUser().getId() : r.getId());

				if (ps.executeUpdate() == 1) {
					if (r.isRoundTrip()) {
						RouteModel rr = new RouteModel();
						rr.setCar(r.getCar());
						rr.setStartPlace(r.getEndPlace());
						rr.setEndPlace(r.getStartPlace());
						rr.setRouteType(r.getRouteType());
						rr.setRouteDatetime(r.getRouteDatetime());
						rr.setDistance(r.getDistance());
						rr.setUser(r.getUser());
						rr.setOperation(0);
						rr.setRoundTrip(false);

						status = createUpdateRoute(rr) ? true : false;
					}

					status = true;
				}
			} catch (Exception e) {
				logger.logException("createUpdateRoute()", e);
			} finally {
				closeConnection();
			}
		}

		logger.logExit("createUpdateRoute()");
		return status;
	}

	/**
	 * Creates a new record or updates an existing one in table 'tyres'
	 * 
	 * @param car {@link TyreModel} a tyre object
	 * @return true on success, false otherwise
	 */
	public boolean createUpdateTyre(TyreModel t) {
		logger.logEnter("createUpdateTyre()");
		if (t != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(t.getOperation() == 0 ? DBCommands.SQL_I_TYRE : DBCommands.SQL_U_TYRE);
				logger.logTrace("createUpdateTyre()", LogMessages.SQL_COMMAND, t.getOperation() == 0 ? DBCommands.SQL_I_TYRE : DBCommands.SQL_U_TYRE);

				ps.setInt(1, t.getType().getCode());
				ps.setInt(2, t.getManufacturerId());
				ps.setString(3, t.getModel());
				ps.setInt(4, t.getAxis().getCode());
				ps.setInt(5, t.getSizeR());
				ps.setInt(6, t.getSizeH());
				ps.setInt(7, t.getSizeW());
				ps.setTimestamp(8, new Timestamp(t.getPurchaseDate().getTime()));

				ps.setInt(9, t.getOperation() == 0 ? t.getUser().getId() : t.getId());

				if (ps.executeUpdate() == 1) {
					return true;
				}
			} catch (Exception e) {
				logger.logException("createUpdateTyre()", e);
			} finally {
				closeConnection();
			}
		}

		logger.logExit("createUpdateTyre()");
		return false;
	}

	/**
	 * Creates a user profile for the user after the very first login
	 * 
	 * @param username {@link String} the username provided upon sign in
	 * @return true on success, false otherwise
	 */
	private boolean createUserProfile(String username) {
		logger.logEnter("createUserProfile()");
		boolean status = false;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_I_USER);
			logger.logTrace("createUserProfile()", LogMessages.SQL_COMMAND, DBCommands.SQL_I_USER);

			ps.setString(1, username);
			status = ps.executeUpdate() == 1 ? true : false;
		} catch (Exception e) {
			logger.logException("createUserProfile()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("createUserProfile()");
		return status;
	}

	/**
	 * Deletes a route from the database
	 * 
	 * @param id ID of the route entry
	 * @return true on success, false otherwise
	 */
	public boolean deleteRoute(int id) {
		logger.logEnter("deleteRoute()");
		boolean status = false;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_D_ROUTE);
			logger.logTrace("deleteRoute()", LogMessages.SQL_COMMAND, DBCommands.SQL_D_ROUTE);

			ps.setInt(1, id);
			status = ps.executeUpdate() == 1 ? true : false;
		} catch (Exception e) {
			logger.logException("deleteRoute()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("deleteRoute()");
		return status;
	}

	/**
	 * Deletes a refuel from the database
	 * 
	 * @param id ID of the refuel entry
	 * @return true on success, false otherwise
	 */
	public boolean deleteRefuel(int id) {
		logger.logEnter("deleteRefuel()");
		boolean status = false;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_D_REFUEL);
			logger.logTrace("deleteRefuel()", LogMessages.SQL_COMMAND, DBCommands.SQL_D_REFUEL);

			ps.setInt(1, id);
			status = ps.executeUpdate() == 1 ? true : false;
		} catch (Exception e) {
			logger.logException("deleteRefuel()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("deleteRefuel()");
		return status;
	}

	/**
	 * Returns a single car record from the database identified by its ID
	 * 
	 * @param id int the car's ID
	 * @return {@link CarModel} if found, null otherwise
	 */
	public CarModel getCar(int id, EncryptionManager em) {
		logger.logEnter("getCar()");
		CarModel car = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_CAR);
			logger.logTrace("getCar()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_CAR);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				car = new CarModel(rs.getInt(1));
				car.setManufacturer(rs.getInt(2)).setModel(rs.getString(3)).setManufacturerDate(rs.getTimestamp(4)).setColor(rs.getString(5))
						.setVin(em != null ? em.decrypt(rs.getString(6)) : rs.getString(6))
						.setPlateNumber(em != null ? em.decrypt(rs.getString(7)) : rs.getString(7)).setFuelCapacity(rs.getDouble(8))
						.setFuel(rs.getInt(9)).setStartDate(rs.getTimestamp(10)).setEndDate(rs.getTimestamp(11)).setDescription(rs.getString(12))
						.setFriendlyName(rs.getString(13)).setActive(rs.getInt(14)).setArchived(rs.getInt(15));
			}

		} catch (Exception e) {
			logger.logException("getCar()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getCar()");
		return car;
	}

	/**
	 * Returns a {@link List} of car records from the database identified by user ID
	 * 
	 * @param id int the user's ID
	 * @return a {@link List} of {@link CarModel} objects if found, empty list
	 *         otherwise
	 */
	public List<CarModel> getCars(int user_id, EncryptionManager em) {
		logger.logEnter("getCars()");
		List<CarModel> cars = new ArrayList<CarModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_CARS);
			logger.logTrace("getCars()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_CARS);
			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			CarModel car = null;
			while (rs.next()) {
				car = new CarModel();
				car.setId(rs.getInt(1));
				car.setManufacturer(rs.getInt(2)).setModel(rs.getString(3)).setManufacturerDate(rs.getTimestamp(4)).setColor(rs.getString(5));

				if (em != null) {
					car.setVin(em.decrypt(rs.getString(6))).setPlateNumber(em.decrypt(rs.getString(7)));
				} else {
					car.setVin(null).setPlateNumber(null);
				}
				car.setFuelCapacity(rs.getDouble(8)).setFuel(rs.getInt(9)).setStartDate(rs.getTimestamp(10)).setEndDate(rs.getTimestamp(11))
						.setDescription(rs.getString(12)).setFriendlyName(rs.getString(13)).setActive(rs.getInt(14)).setArchived(rs.getInt(17));
				car.setManufacturerName(rs.getString(16));

				cars.add(car);
			}
		} catch (Exception e) {
			logger.logException("getCars()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getCars()");
		return cars;
	}

	/**
	 * Collects the available, active car vendors from table 'sup_car_manufacturers'
	 * 
	 * @return returns a {@link Map} of {@link Integer} as key and a {@link String}
	 *         as value of the available car vendors, or empty {@link Map} otherwise
	 */
	public Map<Integer, String> getCarVendors() {
		logger.logEnter("getCarVendors()");
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

		logger.logExit("getCarVendors()");
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
		logger.logEnter("getFuelPriceStats()");
		List<RefuelModel> statData = new ArrayList<RefuelModel>();
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PRICE_GRAPH);
			logger.logTrace("getFuelPriceStats()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_PRICE_GRAPH);

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

		logger.logExit("getFuelPriceStats()");
		return statData;
	}

	/**
	 * Returns a {@link RefuelModel} for the active user with basic statistical data
	 * 
	 * @param user_id int the user's ID
	 * @return {@link RefuelModel} if found, null otherwise
	 */
	public RefuelModel getLastRefuel(int user_id) {
		logger.logEnter("getLastRefuel()");
		RefuelModel rf = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_BIG_STAT);
			logger.logTrace("getLastRefuel()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_BIG_STAT);

			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			if (rs.next()) {
				rf = new RefuelModel();
				rf.setCar(new CarModel());
				rf.getCar().setId(rs.getInt(1));
				rf.setId(rs.getInt(2));
				rf.setPlace(new PlaceModel(rs.getInt(3)));
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

		logger.logExit("getLastRefuel()");
		return rf;
	}

	/**
	 * Returns a {@link MaintenanceModel} of a given maintenance
	 * 
	 * @param id int the maintenance's ID
	 * @return {@link MaintenanceModel} if found, null otherwise
	 */
	public MaintenanceModel getMaintenance(int id) {
		logger.logEnter("getMaintenance()");
		MaintenanceModel mm = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_MAINTENANCE);
			logger.logTrace("getMaintenance()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_MAINTENANCE);

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
				mm.setExpiration(rs.getTimestamp(9));
			}
		} catch (Exception e) {
			logger.logException("getMaintenance()", e);
		} finally {
			closeConnection();
		}
		logger.logExit("getMaintenance()");
		return mm;
	}

	/**
	 * Returns a {@link List} of maintenance entries from the database identified by
	 * user ID
	 * 
	 * @param id int the user's ID
	 * @return a {@link List} of {@link MaintenanceModel} objects if found, empty
	 *         list otherwise
	 */
	public List<MaintenanceModel> getMaintenances(int id, EncryptionManager em) {
		logger.logEnter("getMaintenances()");
		List<MaintenanceModel> ms = new ArrayList<MaintenanceModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_MAINTENANCES);
			logger.logTrace("getMaintenances()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_MAINTENANCES);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			MaintenanceModel m = null;
			while (rs.next()) {
				m = new MaintenanceModel();
				m.setId(rs.getInt(1));

				m.setCar(new CarModel(rs.getInt(2), rs.getString(9), em != null ? em.decrypt(rs.getString(10)) : rs.getString(10)));
				m.setUser(new UserModel(rs.getInt(3)));
				m.setPayment(new PaymentMethodModel(rs.getInt(4), rs.getString(11), null));
				m.setOdometer(rs.getDouble(5));
				m.setMaintenanceDate(rs.getDate(6));
				m.setDescription(rs.getString(7));
				m.setAmount(rs.getDouble(8));
				m.setExpiration(rs.getTimestamp(12));

				ms.add(m);
			}
		} catch (Exception e) {
			logger.logException("getMaintenances()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getMaintenances()");
		return ms;
	}

	/**
	 * Selects all items from the maintenance table regardless of the user to
	 * collect all items which will expire in the following N day(s)
	 * 
	 * @param deadlineDay int value to compute the deadline value (current datetime
	 *                    + N day(s))
	 * @param em          {@link EncryptionManager} to be able to decode API keys,
	 *                    email addresses and car plate number
	 */
	public List<Model> getNotifiableItems(int deadlineDay, EncryptionManager em) {
		logger.logEnter("getNotifiableItems()");

		List<Model> notifiableItems = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_NOTIFIABLE_ITEMS);
			logger.logTrace("getNotifiableItems()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_NOTIFIABLE_ITEMS);

			ps.setInt(1, deadlineDay);

			rs = ps.executeQuery();

			notifiableItems = new ArrayList<Model>();
			Model m = null;
			while (rs.next()) {
				m = new Model();
				m.setId(rs.getInt(5));
				m.setCar(new CarModel(rs.getInt(1)));
				m.getCar().setFriendlyName(rs.getString(11));
				m.setUser(new UserModel(rs.getInt(2)).setUsername(rs.getString(6)));

				if (em != null) {
					m.getCar().setPlateNumber(em.decrypt(rs.getString(12)));
					m.getUser().setEmail(em.decrypt(rs.getString(7))).setPushoverUserKey(em.decrypt(rs.getString(8)))
							.setPushoverAPIKey(em.decrypt(rs.getString(9))).setPushbulletAPIKey(em.decrypt(rs.getString(10)));
				} else {
					m.getCar().setPlateNumber(null);
					m.getUser().setEmail(null).setPushoverUserKey(null).setPushoverAPIKey(null).setPushbulletAPIKey(null);
				}

				m.setMaintenances(new MaintenanceModel().setExpiration(rs.getTimestamp(4)).setDescription(rs.getString(3)));

				notifiableItems.add(m);
			}
		} catch (Exception e) {
			logger.logException("getNotifiableItems()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getNotifiableItems()");
		return notifiableItems;
	}

	/**
	 * Returns a {@link PaymentMethodModel} of a given payment method
	 * 
	 * @param id int the place's ID
	 * @return {@link PaymentMethodModel} if found, null otherwise
	 */
	public PaymentMethodModel getPaymentMethod(int id) {
		logger.logEnter("getPaymentMethod()");
		PaymentMethodModel pm = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PAYMENT);
			logger.logTrace("getPaymentMethod()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_PAYMENT);

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
		logger.logExit("getPaymentMethod()");
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
		logger.logEnter("getPaymentMethods()");
		List<PaymentMethodModel> pms = new ArrayList<PaymentMethodModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PAYMENTS);
			logger.logTrace("getPaymentMethods()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_PAYMENTS);
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

		logger.logExit("getPaymentMethods()");
		return pms;
	}

	/**
	 * Returns a {@link PlaceModel} of a given place
	 * 
	 * @param id int the place's ID
	 * @return {@link PlaceModel} if found, null otherwise
	 */
	public PlaceModel getPlace(int id) {
		logger.logEnter("getPlace()");
		PlaceModel l = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PLACE);
			logger.logTrace("getPlace()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_PLACE);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				l = new PlaceModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getDouble(6), rs.getInt(7));
			}
		} catch (Exception e) {
			logger.logException("getPlace()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getPlace()");
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
		logger.logEnter("getPlaces()");
		List<PlaceModel> ls = new ArrayList<PlaceModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PLACES);
			logger.logTrace("getPlaces()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_PLACES);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
				ls.add(new PlaceModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getInt(6)));
			}
		} catch (Exception e) {
			logger.logException("getPlaces()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getPlaces()");
		return ls;
	}

	/**
	 * Returns a {@link RefuelModel} of a given refuel
	 * 
	 * @param id int the refuel's ID
	 * @return {@link RefuelModel} if found, null otherwise
	 */
	public RefuelModel getRefuel(int id) {
		logger.logEnter("getRefuel()");
		RefuelModel rf = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_REFUEL);
			logger.logTrace("getRefuel()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_REFUEL);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				rf = new RefuelModel(rs.getInt(1));
				rf.setCar(new CarModel(rs.getInt(2))).setPlace(new PlaceModel(rs.getInt(3))).setPayment(new PaymentMethodModel(rs.getInt(8)));
				rf.setRefuelDate(rs.getTimestamp(4)).setOdometer(rs.getDouble(5)).setUnitPrice(rs.getDouble(6)).setFuelAmount(rs.getDouble(7))
						.setAmount(rs.getDouble(9)).setPartialRefuel(rs.getInt(11));
			}

		} catch (Exception e) {
			logger.logException("getRefuel()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getRefuel()");
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
	public List<RefuelModel> getRefuels(int user_id, EncryptionManager em) {
		logger.logEnter("getRefuels()");
		List<RefuelModel> refuels = new ArrayList<RefuelModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_REFUELS);
			logger.logTrace("getRefuels()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_REFUELS);

			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			RefuelModel refuel = null;
			while (rs.next()) {
				refuel = new RefuelModel(rs.getInt(1));
				refuel.setRefuelDate(rs.getTimestamp(4)).setOdometer(rs.getDouble(5)).setFuelAmount(rs.getDouble(7)).setAmount(rs.getDouble(9))
						.setUnitPrice(rs.getDouble(6)).setDistance(rs.getDouble(15));

				refuel.setCar(new CarModel(rs.getInt(2), rs.getString(12), em != null ? em.decrypt(rs.getString(11)) : rs.getString(11)));
				refuel.setPlace(new PlaceModel(rs.getInt(3), rs.getString(13), null, 0, 0, 1));

				refuel.setPayment(new PaymentMethodModel(rs.getInt(8), rs.getString(14), null));

				refuels.add(refuel);
			}
		} catch (Exception e) {
			logger.logException("getRefuels()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getRefuels()");
		return refuels;
	}

	/**
	 * Returns a {@link RouteModel} of route entry from the database identified by
	 * its ID ID
	 * 
	 * @param id int the route's ID
	 * @return a {@link RouteModel} object if found, null otherwise
	 */
	public RouteModel getRoute(int route_id) {
		logger.logEnter("getRoute()");
		RouteModel r = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_ROUTE);
			logger.logTrace("getRoute()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_ROUTE);

			ps.setInt(1, route_id);

			rs = ps.executeQuery();

			if (rs.next()) {
				r = new RouteModel(rs.getInt(1)).setRouteDatetime(rs.getTimestamp(2)).setDistance(rs.getDouble(3))
						.setRouteType(RouteType.fromCode((byte) rs.getInt(4)));

				r.setCar(new CarModel(rs.getInt(5), rs.getString(7), rs.getString(6)));
				r.setStartPlace(new PlaceModel(rs.getInt(8), rs.getString(9), rs.getString(10), 0, 0, 0));
				r.setEndPlace(new PlaceModel(rs.getInt(11), rs.getString(12), rs.getString(13), 0, 0, 0));
			}
		} catch (Exception e) {
			logger.logException("getRoute()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getRoute()");
		return r;
	}

	/**
	 * Returns a {@link List} of refuel entries from the database identified by user
	 * ID
	 * 
	 * @param id int the user's ID
	 * @return a {@link List} of {@link RefuelModel} objects if found, empty list
	 *         otherwise
	 */
	public List<RouteModel> getRoutes(int user_id, EncryptionManager em) {
		logger.logEnter("getRoutes()");
		List<RouteModel> routes = new ArrayList<RouteModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_ROUTES);
			logger.logTrace("getRoutes()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_ROUTES);

			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			RouteModel route = null;
			while (rs.next()) {
				route = new RouteModel(rs.getInt(1)).setRouteDatetime(rs.getTimestamp(2)).setDistance(rs.getDouble(3))
						.setRouteType(RouteType.fromCode((byte) rs.getInt(4)));

				route.setCar(new CarModel(rs.getInt(5), rs.getString(7), em != null ? em.decrypt(rs.getString(6)) : rs.getString(6)));
				route.setStartPlace(new PlaceModel(rs.getInt(8), rs.getString(9), rs.getString(10), 0, 0, 0));
				route.setEndPlace(new PlaceModel(rs.getInt(11), rs.getString(12), rs.getString(13), 0, 0, 0));

				routes.add(route);
			}
		} catch (Exception e) {
			logger.logException("getRoutes()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getRoutes()");
		return routes;
	}

	/**
	 * Returns a single tyre record from the database identified by its ID
	 * 
	 * @param id int the car's ID
	 * @return {@link TyreModel} if found, null otherwise
	 */
	public TyreModel getTyre(int id) {
		logger.logEnter("getTyre()");
		TyreModel tyre = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_TYRE);
			logger.logTrace("getTyre()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_TYRE);

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				tyre = new TyreModel(rs.getInt(1), rs.getInt(2), (byte) rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7),
						rs.getString(8), rs.getString(9), (byte) rs.getInt(10), rs.getTimestamp(11), rs.getInt(12));
			}

		} catch (Exception e) {
			logger.logException("getTyre()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getTyre()");
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
	public List<TyreModel> getTyres(int user_id, EncryptionManager em) {
		logger.logEnter("getTyres()");
		List<TyreModel> tyres = new ArrayList<TyreModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_TYRES);
			logger.logTrace("getTyres()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_TYRES);

			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			TyreModel t = null;
			while (rs.next()) {
				t = new TyreModel(rs.getInt(1)).setSizeR(rs.getInt(2)).setSizeW(rs.getInt(3)).setSizeH(rs.getInt(4)).setManufacturerId(rs.getInt(5))
						.setType(TyreType.fromCode((byte) rs.getInt(6))).setAxis(Axis.fromCode((byte) rs.getInt(7)))
						.setPurchaseDate(rs.getTimestamp(8)).setManufacturerName(rs.getString(9)).setModel(rs.getString(10));
				t.setTyreEvent(new TyreEventModel(rs.getTimestamp(13), rs.getTimestamp(14), rs.getDouble(15), rs.getDouble(11), rs.getDouble(12)));
				t.setCar(new CarModel(rs.getInt(16), rs.getString(17), em != null ? em.decrypt(rs.getString(18)) : rs.getString(18)));
				t.setArchived(rs.getInt(19));

				tyres.add(t);
			}
		} catch (Exception e) {
			logger.logException("getTyres()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("getTyres()");
		return tyres;
	}

	/**
	 * Collects the available, active car vendors from table 'sup_car_manufacturers'
	 * 
	 * @return returns a {@link Map} of {@link Integer} as key and a {@link String}
	 *         as value of the available car vendors, or empty {@link Map} otherwise
	 */
	public Map<Integer, String> getTyreVendors() {
		logger.logEnter("getTyreVendors()");
		Map<Integer, String> tyreVendors = null;

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_TYRE_VENDORS);
			logger.logTrace("getTyreVendors()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_TYRE_VENDORS);

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

		logger.logExit("getTyreVendors()");

		return tyreVendors;
	}

	/**
	 * Returns the user profile
	 * 
	 * @param user {@link String} username of the user
	 * @return the completed {@link UserModel} on success, null otherwise
	 */
	public UserModel getUserProfile(String username, EncryptionManager em) {
		logger.logEnter("getUserProfile()");
		UserModel user = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PROFILE);
			logger.logTrace("getUserProfile()", LogMessages.SQL_COMMAND, DBCommands.SQL_S_PROFILE);

			ps.setString(1, username);

			rs = ps.executeQuery();

			if (rs.next()) {
				user = new UserModel(rs.getInt(3));
				user.setCurrency(rs.getString(1)).setLocale(rs.getString(2)).setUsername(rs.getString(4)).setDistance(rs.getInt(5))
						.setRounded(rs.getInt(6)).setDateFormat(rs.getString(12)).setTimeFormat(rs.getString(13)).setTotpEnabled(rs.getInt(14));

				if (em != null) {
					user.setEmail(em.decrypt(rs.getString(7))).setPushoverUserKey(em.decrypt(rs.getString(8)))
							.setPushoverAPIKey(em.decrypt(rs.getString(9))).setPushbulletAPIKey(em.decrypt(rs.getString(10)));

					if (user.getTotpEnabled() == 1) {
						user.setTotpSecret(em.decrypt(rs.getString(15)));

						user.setTotpBackupCodes(new int[6]);
						for (int i = 0; i < 6; i++) {
							user.getTotpBackupCodes()[i] = Integer.valueOf(em.decrypt(rs.getString(i + 16)));
						}
					}
				} else {
					user.setEmail(null).setPushoverUserKey(null).setPushoverAPIKey(null).setPushbulletAPIKey(null).setTotpSecret(null)
							.setTotpBackupCodes(null);
				}

				user.setArchived(rs.getInt(11));

			}

		} catch (Exception e) {
			logger.logException("getUserProfile()", e);
		} finally {
			closeConnection();
		}
		logger.logExit("getUserProfile()");
		return user;
	}

	/**
	 * Returns the user profile
	 * 
	 * @param user {@link UserModel} object containing the username
	 * @return the completed {@link UserModel}
	 */
	public UserModel getUserProfile(UserModel user, EncryptionManager em) {
		createUserProfile(user.getUsername());
		return getUserProfile(user.getUsername(), em);
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
		logger.logEnter("setPrimaryCar()");
		boolean status = false;

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(isNewCar ? DBCommands.SQL_I_CAR_PRIMARY : DBCommands.SQL_U_CAR_PRIMARY);
			logger.logTrace("setPrimaryCar()", LogMessages.SQL_COMMAND, isNewCar ? DBCommands.SQL_I_CAR_PRIMARY : DBCommands.SQL_U_CAR_PRIMARY);

			ps.setInt(1, isNewCar ? user_id : car_id);
			ps.setInt(2, user_id);

			status = ps.executeUpdate() > 0 ? true : false;
		} catch (Exception e) {
			logger.logException("setPrimaryCar()", e);
		} finally {
			closeConnection();
		}

		logger.logExit("setPrimaryCar()");
		return status;
	}

	/**
	 * Updates the user profile in the database
	 * 
	 * @param user {@link UserModel} the user data need to be written into the DB
	 * @return boolean true on success, false otherwise
	 */
	public boolean updateUserProfile(UserModel user, EncryptionManager em) {
		logger.logEnter("updateUserProfile()");
		boolean status = false;
		if (user.getUsername() != null && !user.getUsername().isEmpty()) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(DBCommands.SQL_U_PROFILE);
				logger.logTrace("updateUserProfile()", LogMessages.SQL_COMMAND, DBCommands.SQL_U_PROFILE);

				ps.setString(1, user.getCurrency());
				ps.setString(2, user.getLocale());
				ps.setInt(3, user.getDistance());
				ps.setInt(4, user.getRounded());
				ps.setString(5, em.encrypt(user.getEmail()));
				ps.setString(6, em.encrypt(user.getPushoverUserKey()));
				ps.setString(7, em.encrypt(user.getPushoverAPIKey()));
				ps.setString(8, em.encrypt(user.getPushbulletAPIKey()));
				ps.setString(9, user.getDateFormat());
				ps.setString(10, user.getTimeFormat());
				ps.setInt(11, user.getId());

				status = ps.executeUpdate() == 1 ? true : false;
			} catch (Exception e) {
				logger.logException("updateUserProfile()", e);
			} finally {
				closeConnection();
			}
		}

		logger.logExit("updateUserProfile()");
		return status;
	}

	/**
	 * Updates the user profile to enable or disable TOTP
	 * 
	 * @param user {@link UserModel} the user data need to be written into the DB
	 * @return boolean true on success, false otherwise
	 */
	public boolean updateUserProfileTOTP(UserModel user, EncryptionManager em) {
		logger.logEnter("updateUserProfileTOTP()");
		boolean status = false;
		if (user.getUsername() != null && !user.getUsername().isEmpty()) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(DBCommands.SQL_U_2FA);

				ps.setInt(1, user.getTotpEnabled());
				ps.setString(2, em.encrypt(user.getTotpSecret()));
				ps.setString(3, em.encrypt(String.valueOf(user.getTotpBackupCodes()[0])));
				ps.setString(4, em.encrypt(String.valueOf(user.getTotpBackupCodes()[1])));
				ps.setString(5, em.encrypt(String.valueOf(user.getTotpBackupCodes()[2])));
				ps.setString(6, em.encrypt(String.valueOf(user.getTotpBackupCodes()[3])));
				ps.setString(7, em.encrypt(String.valueOf(user.getTotpBackupCodes()[4])));
				ps.setString(8, em.encrypt(String.valueOf(user.getTotpBackupCodes()[5])));
				ps.setInt(9, user.getId());

				status = ps.executeUpdate() == 1 ? true : false;
			} catch (Exception e) {
				logger.logException("updateUserProfileTOTP()", e);
			} finally {
				closeConnection();
			}
		}

		logger.logExit("updateUserProfileTOTP()");
		return status;
	}
}
