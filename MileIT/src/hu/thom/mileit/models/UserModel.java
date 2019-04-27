package hu.thom.mileit.models;

/**
 * Model file representing all attributes of a user
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class UserModel extends Model {
	private static final long serialVersionUID = -8687951190661595457L;

	private String username;
	private String currency;
	private String locale;
	private int distance;
	private int rounded;

	public UserModel() {
	}

	public UserModel(int id) {
		setId(id);
	}

	public UserModel(String username) {
		this.setUsername(username);
	}

	public UserModel(String currency, String locale) {
		this.currency = currency;
		this.locale = locale;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (distance != other.distance)
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (rounded != other.rounded)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public String getCurrency() {
		return (currency == null || "".equalsIgnoreCase(currency)) ? "Ft" : currency;
	}

	public int getDistance() {
		return distance;
	}

	public String getLocale() {
		return (locale == null || "".equalsIgnoreCase(locale)) ? "hu" : locale;
	}

	public int getRounded() {
		return rounded;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + distance;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + rounded;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setDistance(String distance) {
		try {
			this.distance = Integer.parseInt(distance);
		} catch (Exception e) {
			this.distance = 1;
		}
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public void setRounded(int rounded) {
		this.rounded = rounded;
	}

	public void setRounded(String rounded) {
		try {
			this.rounded = Integer.parseInt(rounded);
		} catch (Exception e) {
			this.rounded = 1;
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserModel [username=" + username + ", currency=" + currency + ", locale=" + locale + ", distance=" + distance + ", rounded=" + rounded
				+ ", toString()=" + super.toString() + "]";
	}

}
