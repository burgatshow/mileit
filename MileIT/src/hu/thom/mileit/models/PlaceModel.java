package hu.thom.mileit.models;

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
	private UserModel user;

	public PlaceModel() {
	}

	public PlaceModel(int id) {
		setId(id);
	}

	public PlaceModel(String id) {
		setId(id);
	}

	public PlaceModel(int id, String name, String address, int userId, double longitude, double latitude) {
		setId(id);
		this.name = name;
		this.address = address;
		setUser(new UserModel());
		getUser().setId(userId);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public PlaceModel(UserModel user, String name, String address, String longitude, String latitude) {
		this.user = user;
		this.name = name;
		this.address = address;
		setLongitude(longitude);
		setLatitude(latitude);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public void setLongitude(String longitude) {
		try {
			this.longitude = Double.parseDouble(longitude);
		} catch (Exception e) {
			this.longitude = 0;
		}
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLatitude(String latitude) {
		try {
			this.latitude = Double.parseDouble(latitude);
		} catch (Exception e) {
			this.latitude = 0;
		}
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
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocationModel [name=" + name + ", address=" + address + ", longitude=" + longitude + ", latitude=" + latitude + ", getUser_id()="
				+ getId() + "]";
	}

}
