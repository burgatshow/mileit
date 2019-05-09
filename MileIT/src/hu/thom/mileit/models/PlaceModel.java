package hu.thom.mileit.models;

import java.util.Map;

/**
 * Model file representing all attributes of a place
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class PlaceModel extends Model {
	private static final long serialVersionUID = -7572511771682602027L;

	private String name;
	private String address;
	private double longitude;
	private double latitude;
	private boolean fuelStation;

	public PlaceModel() {
	}

	public PlaceModel(int id) {
		setId(id);
	}

	public PlaceModel(int id, String name, String address, double longitude, double latitude, int fuelStation) {
		setId(id);
		this.name = name;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		setFuelStation(fuelStation);
	}

	public PlaceModel(int id, String name, String address, int userId, double longitude, double latitude, int fuelStation) {
		setId(id);
		this.name = name;
		this.address = address;
		setUser(new UserModel(userId));
		this.latitude = latitude;
		this.longitude = longitude;
		setFuelStation(fuelStation);
	}

	public PlaceModel(Map<String, String[]> params, UserModel user) {
		this.name = params.get("name")[0];
		this.address = params.get("address")[0];
		setLongitude(params.get("longitude")[0]);
		setLatitude(params.get("latitude")[0]);
		setFuelStation(params.get("fuelStation")[0]);
		setUser(user);
	}

	public PlaceModel(String id) {
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
		PlaceModel other = (PlaceModel) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getAddress() {
		return address;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean isFuelStation() {
		return fuelStation;
	}

	public PlaceModel setAddress(String address) {
		this.address = address;
		return this;
	}

	public PlaceModel setFuelStation(boolean fuelStation) {
		this.fuelStation = fuelStation;
		return this;
	}

	public PlaceModel setFuelStation(int fuelStation) {
		setFuelStation(Integer.toString(fuelStation));
		return this;
	}

	public PlaceModel setFuelStation(String fuelStation) {
		switch (fuelStation.toLowerCase()) {
		case "1":
		case "true":
		case "yes":
			this.fuelStation = true;
			break;

		default:
			this.fuelStation = false;
			break;
		}
		return this;
	}

	public PlaceModel setLatitude(double latitude) {
		this.latitude = latitude;
		return this;
	}

	public PlaceModel setLatitude(String latitude) {
		try {
			this.latitude = Double.parseDouble(latitude);
		} catch (Exception e) {
			this.latitude = 0;
		}
		return this;
	}

	public PlaceModel setLongitude(double longitude) {
		this.longitude = longitude;
		return this;
	}

	public PlaceModel setLongitude(String longitude) {
		try {
			this.longitude = Double.parseDouble(longitude);
		} catch (Exception e) {
			this.longitude = 0;
		}
		return this;
	}

	public PlaceModel setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "PlaceModel [name=" + name + ", address=" + address + ", longitude=" + longitude + ", latitude=" + latitude + ", toString()="
				+ super.toString() + "]";
	}

}
