package hu.thom.mileit.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Model file representing all attributes of a maintenance
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class MaintenanceModel extends Model {
	private static final long serialVersionUID = 660856971074877480L;

	private Date maintenanceDate;
	private double odometer;
	private String description;
	private double amount;
	private Date expiration;

	public MaintenanceModel() {
	}

	public MaintenanceModel(int id) {
		setId(id);
	}

	public MaintenanceModel(Map<String, String[]> params, UserModel user) {
		setCar(new CarModel(params.get("car")[0]));
		setUser(user);
		setPayment(new PaymentMethodModel(params.get("paymentMethod")[0]));
		setMaintenanceDate(params.get("maintenanceDate")[0]);
		setOdometer(params.get("odometer")[0]);
		setAmount(params.get("amount")[0]);
		setExpiration(params.get("expirationDate")[0]);
		this.description = params.get("description")[0];
	}

	public MaintenanceModel(String id) {
		setId(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaintenanceModel other = (MaintenanceModel) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (expiration == null) {
			if (other.expiration != null)
				return false;
		} else if (!expiration.equals(other.expiration))
			return false;
		if (maintenanceDate == null) {
			if (other.maintenanceDate != null)
				return false;
		} else if (!maintenanceDate.equals(other.maintenanceDate))
			return false;
		if (Double.doubleToLongBits(odometer) != Double.doubleToLongBits(other.odometer))
			return false;
		return true;
	}

	public double getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public Date getExpiration() {
		return expiration;
	}

	public Timestamp getExpirationAsTimestamp() {
		if (expiration == null) {
			return null;
		}

		return new Timestamp(expiration.getTime());
	}

	public Date getMaintenanceDate() {
		return maintenanceDate;
	}

	public Timestamp getMaintenanceDateAsTimestamp() {
		if (maintenanceDate == null) {
			return null;
		}

		return new Timestamp(maintenanceDate.getTime());
	}

	public double getOdometer() {
		return odometer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((expiration == null) ? 0 : expiration.hashCode());
		result = prime * result + ((maintenanceDate == null) ? 0 : maintenanceDate.hashCode());
		temp = Double.doubleToLongBits(odometer);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public MaintenanceModel setAmount(double amount) {
		this.amount = amount;
		return this;
	}

	public MaintenanceModel setAmount(String amount) {
		try {
			this.amount = Double.parseDouble(amount);
		} catch (Exception e) {
			this.amount = 0;
		}
		return this;
	}

	public MaintenanceModel setDescription(String description) {
		this.description = description;
		return this;
	}

	public MaintenanceModel setExpiration(Date expiration) {
		this.expiration = expiration;
		return this;
	}

	public MaintenanceModel setExpiration(String expiration) {
		try {
			this.expiration = this.getDateFormatter(DATETIMESEC).parse(expiration);
		} catch (Exception e) {
			this.expiration = null;
		}
		return this;
	}

	public MaintenanceModel setMaintenanceDate(Date maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
		return this;
	}

	public MaintenanceModel setMaintenanceDate(String maintenanceDate) {
		try {
			this.maintenanceDate = this.getDateFormatter(DATE).parse(maintenanceDate);
		} catch (Exception e) {
			this.maintenanceDate = new Date();
		}
		return this;
	}

	public MaintenanceModel setOdometer(double odometer) {
		this.odometer = odometer;
		return this;
	}

	public MaintenanceModel setOdometer(String odometer) {
		try {
			this.odometer = Double.parseDouble(odometer);
		} catch (Exception e) {
			this.odometer = 0;
		}
		return this;
	}

	@Override
	public String toString() {
		return "MaintenanceModel [maintenanceDate=" + maintenanceDate + ", odometer=" + odometer + ", description=" + description + ", amount="
				+ amount + ", expiration=" + expiration + ", toString()=" + super.toString() + "]";
	}

}
