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
package hu.thom.mileit.models;

import java.util.Date;
import java.util.Map;

/**
 * Model file representing all attributes of a tyre event
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class TyreEventModel extends Model {
	private static final long serialVersionUID = 736208459104609557L;

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
		setCar(new CarModel(params.get("car")[0]));
		setTyre(new TyreModel(params.get("id")[0]));
		setOdometerStart(params.get("odometer_start")[0]);
		setOdometerEnd(params.get("odometer_end")[0]);
		setEventDate(params.get("changeDate")[0]);
		setUser(user);
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

	public Date getDateEnd() {
		return dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public double getOdometerEnd() {
		return odometerEnd;
	}

	public double getOdometerStart() {
		return odometerStart;
	}

	public double getTotalDistance() {
		return totalDistance;
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

	public TyreEventModel setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
		return this;
	}

	public TyreEventModel setDateStart(Date dateStart) {
		this.dateStart = dateStart;
		return this;
	}

	public TyreEventModel setEventDate(Date eventDate) {
		this.eventDate = eventDate;
		return this;
	}

	public TyreEventModel setEventDate(String eventDate) {
		try {
			this.eventDate = this.getDateFormatter(null).parse(eventDate);
		} catch (Exception e) {
			this.eventDate = new Date();
		}
		return this;
	}

	public TyreEventModel setOdometerEnd(double odometerEnd) {
		this.odometerEnd = odometerEnd;
		return this;
	}

	public TyreEventModel setOdometerEnd(String odometerEnd) {
		try {
			this.odometerEnd = Double.parseDouble(odometerEnd);
		} catch (Exception e) {
			this.odometerEnd = 0;
		}
		return this;
	}

	public TyreEventModel setOdometerStart(double odometerStart) {
		this.odometerStart = odometerStart;
		return this;
	}

	public TyreEventModel setOdometerStart(String odometerStart) {
		try {
			this.odometerStart = Double.parseDouble(odometerStart);
		} catch (Exception e) {
			this.odometerStart = 0;
		}
		return this;
	}

	public TyreEventModel setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
		return this;
	}

	@Override
	public String toString() {
		return "TyreEventModel [odometerStart=" + odometerStart + ", odometerEnd=" + odometerEnd + ", totalDistance=" + totalDistance + ", dateStart="
				+ dateStart + ", dateEnd=" + dateEnd + ", eventDate=" + eventDate + ", toString()=" + super.toString() + "]";
	}

}
