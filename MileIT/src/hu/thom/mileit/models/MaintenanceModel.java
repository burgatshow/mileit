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

	public MaintenanceModel() {
	}

	public MaintenanceModel(int id) {
		setId(id);
	}

	public MaintenanceModel(String id) {
		setId(id);
	}

	public MaintenanceModel(Map<String, String[]> params, UserModel user) {
		this.setCar(new CarModel(params.get("car")[0]));
		this.setUser(user);
		this.setPayment(new PaymentMethodModel(params.get("paymentMethod")[0]));
		setMaintenanceDate(params.get("maintenanceDate")[0]);
		setOdometer(params.get("odometer")[0]);
		setAmount(params.get("amount")[0]);
		this.description = params.get("description")[0];
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

	public void setMaintenanceDate(Date maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	public void setMaintenanceDate(String maintenanceDate) {
		try {
			this.maintenanceDate = this.getSdfDates().parse(maintenanceDate);
		} catch (Exception e) {
			this.maintenanceDate = new Date();
		}
	}

	public double getOdometer() {
		return odometer;
	}

	public void setOdometer(double odometer) {
		this.odometer = odometer;
	}

	public void setOdometer(String odometer) {
		try {
			this.odometer = Double.parseDouble(odometer);
		} catch (Exception e) {
			this.odometer = 0;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setAmount(String amount) {
		try {
			this.amount = Double.parseDouble(amount);
		} catch (Exception e) {
			this.amount = 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((maintenanceDate == null) ? 0 : maintenanceDate.hashCode());
		temp = Double.doubleToLongBits(odometer);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
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
		if (maintenanceDate == null) {
			if (other.maintenanceDate != null)
				return false;
		} else if (!maintenanceDate.equals(other.maintenanceDate))
			return false;
		if (Double.doubleToLongBits(odometer) != Double.doubleToLongBits(other.odometer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MaintenanceModel [maintenanceDate=" + maintenanceDate + ", odometer=" + odometer + ", description="
				+ description + ", amount=" + amount + ", toString()=" + super.toString() + "]";
	}

}
