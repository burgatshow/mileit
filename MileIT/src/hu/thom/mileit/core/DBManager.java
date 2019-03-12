package hu.thom.mileit.core;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import hu.thom.mileit.models.CarModel;
import hu.thom.mileit.models.LocationModel;
import hu.thom.mileit.models.PaymentMethodModel;
import hu.thom.mileit.models.RefuelsModel;
import hu.thom.mileit.models.UserModel;

public class DBManager implements Serializable {
	private static final long serialVersionUID = -5527815745942370595L;

	private DataSource ds;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	/**
	 * Constructor
	 */
	public DBManager() {
		if (ds == null) {
			try {
				ds = (DataSource) new InitialContext().lookup("jdbc/mileit");
			} catch (Exception e) {
				// FIXME
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the basic data from the last fuel for the user
	 * 
	 * @param username
	 * @return
	 */
	public RefuelsModel getLastRefuel(int user_id) {
		RefuelsModel rf = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_BIG_STAT);
			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			if (rs.next()) {
				rf = new RefuelsModel();
				rf.setCar(new CarModel());
				rf.getCar().setId(rs.getInt("car_id"));
				rf.setId(rs.getInt("refuel_id"));
				rf.setLocation(new LocationModel());
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
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return rf;
	}

	public UserModel getUserProfile(UserModel user) {
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PROFILE);
			ps.setString(1, user.getUsername());

			rs = ps.executeQuery();

			if (rs.next()) {
				user.setCurrency(rs.getString("currency"));
				user.setLocale(rs.getString("locale"));
				user.setId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
			}
		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return user;
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
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return user;
	}

	public boolean updateUserProfile(UserModel user) {
		if (user.getUsername() != null && !"".equalsIgnoreCase(user.getUsername())) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(DBCommands.SQL_U_PROFILE);

				ps.setString(1, (user.getCurrency() == null || "".equalsIgnoreCase(user.getCurrency())) ? "Ft" : user.getCurrency());
				ps.setString(2, (user.getLocale() == null || "".equalsIgnoreCase(user.getLocale())) ? "hu" : user.getLocale());
				ps.setString(3, user.getUsername());

				if (ps.executeUpdate() == 1) {
					return true;
				}
			} catch (Exception e) {
				// FIXME
				e.printStackTrace();
			} finally {
				closeConnection();
			}
		}

		return false;
	}

	public boolean createUpdateCar(CarModel car) {
		if (car != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(car.getOperation() == 0 ? DBCommands.SQL_I_CAR : DBCommands.SQL_U_CAR);

				ps.setString(1, car.getManufacturer());
				ps.setString(2, car.getModel());

				ps.setTimestamp(3, car.getManufacturerDate() == null ? null : new Timestamp(car.getManufacturerDate().getTime()));
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

				boolean isOK = false;
				if (ps.executeUpdate() == 1) {
					isOK = true;
				}

				if (isOK) {
					if (setPrimaryCar(car.getUser().getId(), car.getId(), car.getOperation() == 0 ? true : false)) {
						return true;
					} else {
						return false;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeConnection();
			}
		}

		return false;

	}

	private boolean setPrimaryCar(int user_id, int car_id, boolean isNewCar) {
		boolean status = false;

		try {
			con = ds.getConnection();
			if (isNewCar) {
				ps = con.prepareStatement(DBCommands.SQL_I_CAR_PRIMARY);
				ps.setInt(1, user_id);
				ps.setInt(2, user_id);
			} else {
				ps = con.prepareStatement(DBCommands.SQL_U_CAR_PRIMARY);
				ps.setInt(1, car_id);
				ps.setInt(2, user_id);
			}

			if (ps.executeUpdate() > 0) {
				status = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}

		return status;
	}

	public boolean archiveCar(int id) {
		System.out.println(id);
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_U_CAR_ARCHIVE);
			ps.setInt(1, id);

			if (ps.executeUpdate() >= 1) {
				return true;
			}

		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

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
				car.setManufacturer(rs.getString("manufacturer"));
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
				// FIXME
				car.setActive(rs.getInt("active") == 1 ? true : false);

				cars.add(car);
			}
		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}

		return cars;
	}

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
				car.setManufacturer(rs.getString("manufacturer"));
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
				// FIXME
				// car.setId(rs.getInt("user_id"));
				car.setActive(rs.getInt("active") == 1 ? true : false);
			}
		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return car;
	}

	public List<RefuelsModel> getRefuels(int user_id) {
		List<RefuelsModel> refuels = new ArrayList<RefuelsModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_REFUELS);
			ps.setInt(1, user_id);

			rs = ps.executeQuery();

			RefuelsModel refuel = null;
			while (rs.next()) {
				refuel = new RefuelsModel();
				refuel.setCar(new CarModel());
				refuel.getCar().setPlateNumber(rs.getString("plate_number"));
				refuel.getCar().setFriendlyName(rs.getString("friendly_name"));

				refuel.setId(rs.getInt("refuel_id"));

				refuel.setLocation(new LocationModel());
				refuel.getLocation().setId(rs.getInt("place_id"));
				refuel.getLocation().setName(rs.getString("location"));
				refuel.setRefuelTimestamp(rs.getTimestamp("refuel_timestamp"));
				refuel.setOdometer(rs.getDouble("odometer"));
				refuel.setFuelAmount(rs.getDouble("fuel_amount"));
				refuel.setAmount(rs.getDouble("amount"));
				refuel.setUnitPrice(rs.getDouble("unit_price"));

				refuel.setPayment(new PaymentMethodModel());
				refuel.getPayment().setId(rs.getInt("pm_id"));
				refuel.getPayment().setName(rs.getString("payment_method_name"));

				refuels.add(refuel);
			}
		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}

		return refuels;
	}

	public RefuelsModel getRefuel(int id) {
		RefuelsModel rf = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_REFUEL);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				rf = new RefuelsModel();

				rf.setCar(new CarModel(rs.getInt("car_id")));
				rf.setId(rs.getInt("refuel_id"));
				rf.setLocation(new LocationModel(rs.getInt("place_id")));
				rf.setRefuelTimestamp(rs.getTimestamp("refuel_timestamp"));
				rf.setOdometer(rs.getDouble("odometer"));
				rf.setFuelAmount(rs.getDouble("fuel_amount"));
				rf.setAmount(rs.getDouble("amount"));
				rf.setUnitPrice(rs.getDouble("unit_price"));
				rf.setPayment(new PaymentMethodModel(rs.getInt("pm_id")));
			}
		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return rf;
	}

	public boolean createUpdateRefuel(RefuelsModel rf) {
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
				// FIXME
				e.printStackTrace();
			} finally {
				closeConnection();
			}
		}

		return false;
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
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}

		return pms;
	}

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
				// FIXME
				e.printStackTrace();
			} finally {
				closeConnection();
			}
		}

		return false;
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
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return pm;
	}

	public List<LocationModel> getLocations(int id) {
		List<LocationModel> ls = new ArrayList<LocationModel>();

		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_LOCATIONS);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			LocationModel l = null;
			while (rs.next()) {
				l = new LocationModel();
				l.setId(rs.getInt("place_id"));
				l.setAddress(rs.getString("address"));
				l.setName(rs.getString("name"));
				l.setLongitude(rs.getDouble("longitude"));
				l.setLatitude(rs.getDouble("latitude"));

				ls.add(l);
			}
		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}

		return ls;
	}

	public boolean createUpdateLocation(LocationModel l) {
		if (l != null) {
			try {
				con = ds.getConnection();
				ps = con.prepareStatement(l.getOperation() == 0 ? DBCommands.SQL_I_LOCATION : DBCommands.SQL_U_LOCATION);

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
				// FIXME
				e.printStackTrace();
			} finally {
				closeConnection();
			}
		}

		return false;
	}

	public LocationModel getLocation(int id) {
		LocationModel l = null;
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_LOCATION);
			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				l = new LocationModel(rs.getInt("place_id"), rs.getString("name"), rs.getString("address"), rs.getInt("user_id"),
						rs.getDouble("longitude"), rs.getDouble("latitude"));
			}
		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return l;
	}

	public List<RefuelsModel> getFuelPriceStats(int user_id) {
		List<RefuelsModel> statData = new ArrayList<RefuelsModel>();
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(DBCommands.SQL_S_PRICE_GRAPH);
			ps.setInt(1, user_id);

			rs = ps.executeQuery();
			while (rs.next()) {
				statData.add(new RefuelsModel(rs.getTimestamp(1), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4)));
			}

		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		} finally {
			closeConnection();
		}

		return statData;
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
				// FIXME
				e.printStackTrace();
			}
		}

		if (ps != null) {
			try {
				if (!ps.isClosed()) {
					ps.close();
				}
			} catch (Exception e) {
				// FIXME
				e.printStackTrace();
			}
		}

		if (con != null) {
			try {
				if (!con.isClosed()) {
					con.close();
				}
			} catch (Exception e) {
				// FIXME
				e.printStackTrace();
			}
		}
	}
}
