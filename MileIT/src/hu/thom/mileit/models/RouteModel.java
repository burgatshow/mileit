package hu.thom.mileit.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Model file representing all attributes of a route entry
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class RouteModel extends Model {
	public static enum RouteType {
		BUSINESS((byte) 1), PRIVATE((byte) 0);

		public static RouteType fromCode(byte code) {
			switch (code) {
			case 1:
				return BUSINESS;
			case 0:
			default:
				return PRIVATE;
			}
		}

		public static int toCode(RouteType tyre) {
			switch (tyre) {
			case BUSINESS:
				return 1;
			case PRIVATE:
			default:
				return 0;
			}
		}

		private final byte code;

		private RouteType(byte code) {
			this.code = code;
		}

		public byte getCode() {
			return code;
		}
	}

	private static final long serialVersionUID = 8048192341961068140L;

	private PlaceModel startPlace;
	private PlaceModel endPlace;
	private Date routeDatetime;
	private RouteType routeType;
	private double distance;
	private boolean roundTrip;

	public RouteModel() {
	}

	public RouteModel(int id) {
		setId(id);
	}

	public RouteModel(Map<String, String[]> params, UserModel user) {
		setCar(new CarModel(params.get("car")[0]));
		setStartPlace(new PlaceModel(params.get("startPlace")[0]));
		setEndPlace(new PlaceModel(params.get("endPlace")[0]));
		setRouteType(params.get("type")[0]);
		setDistance(params.get("distance")[0]);
		setRouteDatetime(params.get("routeDatetime")[0]);
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
		RouteModel other = (RouteModel) obj;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (endPlace == null) {
			if (other.endPlace != null)
				return false;
		} else if (!endPlace.equals(other.endPlace))
			return false;
		if (roundTrip != other.roundTrip)
			return false;
		if (routeDatetime == null) {
			if (other.routeDatetime != null)
				return false;
		} else if (!routeDatetime.equals(other.routeDatetime))
			return false;
		if (routeType != other.routeType)
			return false;
		if (startPlace == null) {
			if (other.startPlace != null)
				return false;
		} else if (!startPlace.equals(other.startPlace))
			return false;
		return true;
	}

	public double getDistance() {
		return distance;
	}

	public PlaceModel getEndPlace() {
		return endPlace;
	}

	public Date getRouteDatetime() {
		return routeDatetime;
	}

	public Timestamp getRouteDatetimeAsTimestamp() {
		if (routeDatetime == null) {
			return null;
		}

		return new Timestamp(routeDatetime.getTime());
	}

	public RouteType getRouteType() {
		return routeType;
	}

	public PlaceModel getStartPlace() {
		return startPlace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((endPlace == null) ? 0 : endPlace.hashCode());
		result = prime * result + (roundTrip ? 1231 : 1237);
		result = prime * result + ((routeDatetime == null) ? 0 : routeDatetime.hashCode());
		result = prime * result + ((routeType == null) ? 0 : routeType.hashCode());
		result = prime * result + ((startPlace == null) ? 0 : startPlace.hashCode());
		return result;
	}

	public boolean isRoundTrip() {
		return roundTrip;
	}

	public RouteModel setDistance(double distance) {
		this.distance = distance;
		return this;
	}

	public RouteModel setDistance(String distance) {
		try {
			this.distance = Double.parseDouble(distance);
		} catch (Exception e) {
			this.distance = 0;
		}
		return this;
	}

	public RouteModel setEndPlace(PlaceModel endPlace) {
		this.endPlace = endPlace;
		return this;
	}

	public RouteModel setRoundTrip(boolean roundTrip) {
		this.roundTrip = roundTrip;
		return this;
	}

	public RouteModel setRoundTrip(String roundTrip) {
		switch (roundTrip.toLowerCase()) {
		case "1":
		case "yes":
		case "true":
			this.roundTrip = true;
			break;

		default:
			this.roundTrip = false;
			break;
		}
		return this;
	}

	public RouteModel setRouteDatetime(Date routeDatetime) {
		this.routeDatetime = routeDatetime;
		return this;
	}

	public RouteModel setRouteDatetime(String routeDatetime) {
		try {
			this.routeDatetime = this.getDateFormatter(DATETIME).parse(routeDatetime);
		} catch (Exception e) {
			this.routeDatetime = new Date();
		}
		return this;
	}

	public RouteModel setRouteType(RouteType routeType) {
		this.routeType = routeType;
		return this;
	}

	public RouteModel setRouteType(String routeType) {
		try {
			this.routeType = RouteType.fromCode((byte) Integer.parseInt(routeType));
		} catch (Exception e) {
			this.routeType = RouteType.PRIVATE;
		}
		return this;
	}

	public RouteModel setStartPlace(PlaceModel startPlace) {
		this.startPlace = startPlace;
		return this;
	}

	@Override
	public String toString() {
		return "RouteModel [startPlace=" + startPlace + ", endPlace=" + endPlace + ", routeDatetime=" + routeDatetime + ", routeType=" + routeType
				+ ", distance=" + distance + ", roundTrip=" + roundTrip + ", toString()=" + super.toString() + "]";
	}

}
