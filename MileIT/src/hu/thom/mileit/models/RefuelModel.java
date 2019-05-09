package hu.thom.mileit.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Model file representing all attributes of a refuel
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class RefuelModel extends Model {
	private static final long serialVersionUID = -672842337477969286L;

	private Date refuelDate;
	private double odometer;
	private double unitPrice;
	private double fuelAmount;
	private double amount;
	private boolean partialRefuel;
	private double distance;

	public RefuelModel() {
	}

	public RefuelModel(Date refuelDate, double odometer, double amount, double unitPrice) {
		this.refuelDate = refuelDate;
		this.odometer = odometer;
		this.amount = amount;
		this.unitPrice = unitPrice;
	}

	public RefuelModel(int id) {
		setId(id);
	}

	public RefuelModel(Map<String, String[]> params, UserModel user) {
		setCar(new CarModel(params.get("car")[0]));
		setPlace(new PlaceModel(params.get("place")[0]));
		setPayment(new PaymentMethodModel(params.get("paymentMethod")[0]));
		setRefuelTimestamp(params.get("refuelTimestamp")[0]);
		setOdometer(params.get("odometer")[0]);
		setUnitPrice(params.get("unitPrice")[0]);
		setAmount(params.get("amount")[0]);
		setPartialRefuel(params.get("partialRefuel") == null ? false : true);

		setUser(user);
	}

	public RefuelModel(String id) {
		setId(id);
	}

	public double getAmount() {
		return amount;
	}

	public double getDistance() {
		return distance;
	}

	public double getFuelAmount() {
		return fuelAmount;
	}

	public double getOdometer() {
		return odometer;
	}

	public Date getRefuelDate() {
		return refuelDate;
	}

	public Timestamp getRefuelDateAsTimestamp() {
		if (refuelDate == null) {
			return null;
		}

		return new Timestamp(refuelDate.getTime());
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public boolean isPartialRefuel() {
		return partialRefuel;
	}

	public RefuelModel setAmount(double amount) {
		this.amount = amount;
		return this;
	}

	public RefuelModel setAmount(String amount) {
		try {
			this.amount = Double.parseDouble(amount);
		} catch (Exception e) {
			this.amount = 0;
		}
		return this;
	}

	public RefuelModel setDistance(double distance) {
		this.distance = distance;
		return this;
	}

	public RefuelModel setFuelAmount(double fuelAmount) {
		this.fuelAmount = fuelAmount;
		return this;
	}

	public RefuelModel setFuelAmount(String fuelAmount) {
		try {
			this.fuelAmount = Double.parseDouble(fuelAmount);
		} catch (Exception e) {
			this.fuelAmount = 0;
		}
		return this;
	}

	public RefuelModel setOdometer(double odometer) {
		this.odometer = odometer;
		return this;
	}

	public RefuelModel setOdometer(String odometer) {
		try {
			this.odometer = Double.parseDouble(odometer);
		} catch (Exception e) {
			this.odometer = 0;
		}
		return this;
	}

	public RefuelModel setPartialRefuel(boolean partialRefuel) {
		this.partialRefuel = partialRefuel;
		return this;
	}

	public RefuelModel setPartialRefuel(int partialRefuel) {
		setPartialRefuel(Integer.toString(partialRefuel));
		return this;
	}

	public RefuelModel setPartialRefuel(String partialRefuel) {
		System.out.println(partialRefuel);
		switch (partialRefuel.toLowerCase()) {
		case "1":
		case "yes":
		case "true":
			this.partialRefuel = true;
			break;

		default:
			this.partialRefuel = false;
			break;
		}
		return this;
	}

	public RefuelModel setRefuelDate(Date refuelDate) {
		this.refuelDate = refuelDate;
		return this;
	}

	public RefuelModel setRefuelTimestamp(String refuelDate) {
		try {
			this.refuelDate = this.getDateFormatter(DATETIME).parse(refuelDate);
		} catch (Exception e) {
			this.refuelDate = new Date();
		}
		return this;
	}

	public RefuelModel setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
		return this;
	}

	public RefuelModel setUnitPrice(String unitPrice) {
		try {
			this.unitPrice = Double.parseDouble(unitPrice);
		} catch (Exception e) {
			this.unitPrice = 0;
		}
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fuelAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(odometer);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (partialRefuel ? 1231 : 1237);
		result = prime * result + ((refuelDate == null) ? 0 : refuelDate.hashCode());
		temp = Double.doubleToLongBits(unitPrice);
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
		RefuelModel other = (RefuelModel) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (Double.doubleToLongBits(fuelAmount) != Double.doubleToLongBits(other.fuelAmount))
			return false;
		if (Double.doubleToLongBits(odometer) != Double.doubleToLongBits(other.odometer))
			return false;
		if (partialRefuel != other.partialRefuel)
			return false;
		if (refuelDate == null) {
			if (other.refuelDate != null)
				return false;
		} else if (!refuelDate.equals(other.refuelDate))
			return false;
		if (Double.doubleToLongBits(unitPrice) != Double.doubleToLongBits(other.unitPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RefuelModel [refuelDate=" + refuelDate + ", odometer=" + odometer + ", unitPrice=" + unitPrice + ", fuelAmount=" + fuelAmount
				+ ", amount=" + amount + ", partialRefuel=" + partialRefuel + ", distance=" + distance + ", toString()=" + super.toString() + "]";
	}

}
