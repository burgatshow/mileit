package hu.thom.mileit.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model file representing all attributes of a refuel
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class RefuelModel extends Model {
	private static final long serialVersionUID = -672842337477969286L;

	private SimpleDateFormat sdfDates = new SimpleDateFormat("yyyy-MM-dd");

	private Date refuelDate;
	private double odometer;
	private double unitPrice;
	private double fuelAmount;
	private double amount;
	private double distance;
	private UserModel user;
	private CarModel car;
	private PlaceModel location;
	private PaymentMethodModel payment;

	public RefuelModel() {
	}

	public RefuelModel(int id) {
		setId(id);
	}

	public RefuelModel(String id) {
		setId(id);
	}

	public RefuelModel(Date refuelDate, double odometer, double amount, double unitPrice) {
		this.refuelDate = refuelDate;
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

	public Date getRefuelDate() {
		return refuelDate;
	}

	public Timestamp getRefuelDateAsTimestamp() {
		if (refuelDate == null) {
			return null;
		}
		
		return new Timestamp(refuelDate.getTime());
	}

	public void setRefuelDate(Date refuelDate) {
		this.refuelDate = refuelDate;
	}

	public void setRefuelTimestamp(String refuelDate) {
		try {
			this.refuelDate = sdfDates.parse(refuelDate);
		} catch (Exception e) {
			this.refuelDate = new Date();
		}
	}

	public CarModel getCar() {
		return car;
	}

	public void setCar(CarModel car) {
		this.car = car;
	}

	public PlaceModel getLocation() {
		return location;
	}

	public void setLocation(PlaceModel location) {
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
			this.odometer = 0;
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
			this.unitPrice = 0;
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
			this.fuelAmount = 0;
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
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fuelAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		temp = Double.doubleToLongBits(odometer);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result + ((refuelDate == null) ? 0 : refuelDate.hashCode());
		result = prime * result + ((sdfDates == null) ? 0 : sdfDates.hashCode());
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
		RefuelModel other = (RefuelModel) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
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
		if (refuelDate == null) {
			if (other.refuelDate != null)
				return false;
		} else if (!refuelDate.equals(other.refuelDate))
			return false;
		if (sdfDates == null) {
			if (other.sdfDates != null)
				return false;
		} else if (!sdfDates.equals(other.sdfDates))
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
		return "RefuelsModel [sdfDates=" + sdfDates + ", refuelDate=" + refuelDate + ", odometer=" + odometer + ", unitPrice=" + unitPrice
				+ ", fuelAmount=" + fuelAmount + ", amount=" + amount + ", user=" + user + ", car=" + car + ", location=" + location + ", payment="
				+ payment + ", toString()=" + super.toString() + "]";
	}

}
