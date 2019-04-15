package hu.thom.mileit.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TyreEventModel extends Model {
	private static final long serialVersionUID = 736208459104609557L;

	private SimpleDateFormat sdfDates = new SimpleDateFormat("yyyy-MM-dd");

	private CarModel car;
	private TyreModel tyre;
	private UserModel user;
	private double odometerStart;
	private double odometerEnd;
	private double totalDistance;
	private Date dateStart;
	private Date dateEnd;
	private Date eventDate;

	public TyreEventModel() {
	}

	public TyreEventModel(Date dateStart, Date dateEnd, double totalDistance, double odometerStart, double odometerEnd) {
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.totalDistance = totalDistance;
		this.odometerStart = odometerStart;
		this.odometerEnd = odometerEnd;
	}

	public TyreEventModel(Map<String, String[]> params, UserModel user) {
		this.car = new CarModel(params.get("car")[0]);
		this.tyre = new TyreModel(params.get("id")[0]);
		setOdometerStart(params.get("odometer_start")[0]);
		setOdometerEnd(params.get("odometer_end")[0]);
		setEventDate(params.get("changeDate")[0]);
		this.user = user;
	}

	public CarModel getCar() {
		return car;
	}

	public void setCar(CarModel car) {
		this.car = car;
	}

	public TyreModel getTyre() {
		return tyre;
	}

	public void setTyre(TyreModel tyre) {
		this.tyre = tyre;
	}

	public double getOdometerStart() {
		return odometerStart;
	}

	public void setOdometerStart(double odometerStart) {
		this.odometerStart = odometerStart;
	}

	public void setOdometerStart(String odometerStart) {
		try {
			this.odometerStart = Double.parseDouble(odometerStart);
		} catch (Exception e) {
			this.odometerStart = 0;
		}
	}

	public double getOdometerEnd() {
		return odometerEnd;
	}

	public void setOdometerEnd(double odometerEnd) {
		this.odometerEnd = odometerEnd;
	}

	public void setOdometerEnd(String odometerEnd) {
		try {
			this.odometerEnd = Double.parseDouble(odometerEnd);
		} catch (Exception e) {
			this.odometerEnd = 0;
		}
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public void setEventDate(String eventDate) {
		try {
			this.eventDate = sdfDates.parse(eventDate);
		} catch (Exception e) {
			this.eventDate = new Date();
		}
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(odometerEnd);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(odometerStart);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((tyre == null) ? 0 : tyre.hashCode());
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
		TyreEventModel other = (TyreEventModel) obj;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (Double.doubleToLongBits(odometerEnd) != Double.doubleToLongBits(other.odometerEnd))
			return false;
		if (Double.doubleToLongBits(odometerStart) != Double.doubleToLongBits(other.odometerStart))
			return false;
		if (tyre == null) {
			if (other.tyre != null)
				return false;
		} else if (!tyre.equals(other.tyre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TyreEventModel [car=" + car + ", tyre=" + tyre + ", odometerStart=" + odometerStart + ", odometerEnd=" + odometerEnd + ", eventDate="
				+ eventDate + ", toString()=" + super.toString() + "]";
	}

}
