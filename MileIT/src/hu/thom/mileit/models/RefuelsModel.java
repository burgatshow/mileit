package hu.thom.mileit.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RefuelsModel extends Model {
	private static final long serialVersionUID = -672842337477969286L;

	private SimpleDateFormat sdfDates = new SimpleDateFormat("yyyy-MM-dd");

	private Date refuelTimestamp;
	private double odometer;
	private double unitPrice;
	private double fuelAmount;
	private double amount;
	private double distance;
	private UserModel user;
	private CarModel car;
	private LocationModel location;
	private PaymentMethodModel payment;

	public RefuelsModel() {
	}

	public RefuelsModel(int id) {
		setId(id);
	}

	public RefuelsModel(String id) {
		setId(id);
	}

	public RefuelsModel(Date refuelTimestamp, double odometer, double amount, double unitPrice) {
		this.refuelTimestamp = refuelTimestamp;
		this.odometer = odometer;
		this.amount = amount;
		this.unitPrice = unitPrice;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public Date getRefuelTimestamp() {
		return refuelTimestamp;
	}

	public void setRefuelTimestamp(Date refuelTimestamp) {
		this.refuelTimestamp = refuelTimestamp;
	}

	public void setRefuelTimestamp(String refuelTimestamp) {
		try {
			this.refuelTimestamp = sdfDates.parse(refuelTimestamp);
		} catch (Exception e) {
			this.refuelTimestamp = new Date();
		}
	}

	public CarModel getCar() {
		return car;
	}

	public void setCar(CarModel car) {
		this.car = car;
	}

	public LocationModel getLocation() {
		return location;
	}

	public void setLocation(LocationModel location) {
		this.location = location;
	}

	public PaymentMethodModel getPayment() {
		return payment;
	}

	public void setPayment(PaymentMethodModel payment) {
		this.payment = payment;
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
			this.odometer = 0.0;
		}
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		try {
			this.unitPrice = Double.parseDouble(unitPrice);
		} catch (Exception e) {
			this.unitPrice = 0.0;
		}
	}

	public double getFuelAmount() {
		return fuelAmount;
	}

	public void setFuelAmount(double fuelAmount) {
		this.fuelAmount = fuelAmount;
	}

	public void setFuelAmount(String fuelAmount) {
		try {
			this.fuelAmount = Double.parseDouble(fuelAmount);
		} catch (Exception e) {
			this.fuelAmount = 0.0;
		}
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
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
			this.amount = 0.0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		temp = Double.doubleToLongBits(fuelAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		temp = Double.doubleToLongBits(odometer);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result + ((refuelTimestamp == null) ? 0 : refuelTimestamp.hashCode());
		temp = Double.doubleToLongBits(unitPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		RefuelsModel other = (RefuelsModel) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		if (Double.doubleToLongBits(fuelAmount) != Double.doubleToLongBits(other.fuelAmount))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (Double.doubleToLongBits(odometer) != Double.doubleToLongBits(other.odometer))
			return false;
		if (payment == null) {
			if (other.payment != null)
				return false;
		} else if (!payment.equals(other.payment))
			return false;
		if (refuelTimestamp == null) {
			if (other.refuelTimestamp != null)
				return false;
		} else if (!refuelTimestamp.equals(other.refuelTimestamp))
			return false;
		if (Double.doubleToLongBits(unitPrice) != Double.doubleToLongBits(other.unitPrice))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RefuelsModel [sdfDates=" + sdfDates + ", refuelTimestamp=" + refuelTimestamp + ", odometer=" + odometer
				+ ", unitPrice=" + unitPrice + ", fuelAmount=" + fuelAmount + ", amount=" + amount + ", user=" + user
				+ ", car=" + car + ", location=" + location + ", payment=" + payment + ", toString()="
				+ super.toString() + "]";
	}

}
