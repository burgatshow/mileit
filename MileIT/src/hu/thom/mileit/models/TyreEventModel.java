package hu.thom.mileit.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TyreEventModel extends Model {
	private static final long serialVersionUID = 736208459104609557L;

	private SimpleDateFormat sdfDates = new SimpleDateFormat("yyyy-MM-dd");

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
		this.setCar(new CarModel(params.get("car")[0]));
		this.setTyre(new TyreModel(params.get("id")[0]));
		setOdometerStart(params.get("odometer_start")[0]);
		setOdometerEnd(params.get("odometer_end")[0]);
		setEventDate(params.get("changeDate")[0]);
		this.setUser(user);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(odometerEnd);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(odometerStart);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalDistance);
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
		TyreEventModel other = (TyreEventModel) obj;
		if (dateEnd == null) {
			if (other.dateEnd != null)
				return false;
		} else if (!dateEnd.equals(other.dateEnd))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
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
		if (Double.doubleToLongBits(totalDistance) != Double.doubleToLongBits(other.totalDistance))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TyreEventModel [odometerStart=" + odometerStart + ", odometerEnd=" + odometerEnd + ", totalDistance=" + totalDistance + ", dateStart="
				+ dateStart + ", dateEnd=" + dateEnd + ", eventDate=" + eventDate + ", toString()=" + super.toString() + "]";
	}

}
