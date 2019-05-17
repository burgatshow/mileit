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
	private String email;
	private String currency;
	private String locale;
	private int distance;
	private int rounded;
	private String dateFormat;
	private String timeFormat;
	private String pushoverUserKey;
	private String pushoverAPIKey;
	private String pushbulletAPIKey;

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
		if (dateFormat == null) {
			if (other.dateFormat != null)
				return false;
		} else if (!dateFormat.equals(other.dateFormat))
			return false;
		if (timeFormat == null) {
			if (other.timeFormat != null)
				return false;
		} else if (!timeFormat.equals(other.timeFormat))
			return false;
		if (distance != other.distance)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (pushbulletAPIKey == null) {
			if (other.pushbulletAPIKey != null)
				return false;
		} else if (!pushbulletAPIKey.equals(other.pushbulletAPIKey))
			return false;
		if (pushoverAPIKey == null) {
			if (other.pushoverAPIKey != null)
				return false;
		} else if (!pushoverAPIKey.equals(other.pushoverAPIKey))
			return false;
		if (pushoverUserKey == null) {
			if (other.pushoverUserKey != null)
				return false;
		} else if (!pushoverUserKey.equals(other.pushoverUserKey))
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

	public String getDateFormat() {
		return dateFormat;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public int getDistance() {
		return distance;
	}

	public String getEmail() {
		return email;
	}

	public String getLocale() {
		return (locale == null || "".equalsIgnoreCase(locale)) ? "hu" : locale;
	}

	public String getPushbulletAPIKey() {
		return pushbulletAPIKey;
	}

	public String getPushoverAPIKey() {
		return pushoverAPIKey;
	}

	public String getPushoverUserKey() {
		return pushoverUserKey;
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
		result = prime * result + ((dateFormat == null) ? 0 : dateFormat.hashCode());
		result = prime * result + ((timeFormat == null) ? 0 : timeFormat.hashCode());
		result = prime * result + distance;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((pushbulletAPIKey == null) ? 0 : pushbulletAPIKey.hashCode());
		result = prime * result + ((pushoverAPIKey == null) ? 0 : pushoverAPIKey.hashCode());
		result = prime * result + ((pushoverUserKey == null) ? 0 : pushoverUserKey.hashCode());
		result = prime * result + rounded;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	public UserModel setCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public UserModel setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
		return this;
	}

	public UserModel setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
		return this;
	}

	public UserModel setDistance(int distance) {
		this.distance = distance;
		return this;
	}

	public UserModel setDistance(String distance) {
		try {
			this.distance = Integer.parseInt(distance);
		} catch (Exception e) {
			this.distance = 1;
		}
		return this;
	}

	public UserModel setEmail(String email) {
		this.email = email;
		return this;
	}

	public UserModel setLocale(String locale) {
		this.locale = locale;
		return this;
	}

	public UserModel setPushbulletAPIKey(String pushbulletAPIKey) {
		this.pushbulletAPIKey = pushbulletAPIKey;
		return this;
	}

	public UserModel setPushoverAPIKey(String pushoverAPIKey) {
		this.pushoverAPIKey = pushoverAPIKey;
		return this;
	}

	public UserModel setPushoverUserKey(String pushoverUserKey) {
		this.pushoverUserKey = pushoverUserKey;
		return this;
	}

	public UserModel setRounded(int rounded) {
		this.rounded = rounded;
		return this;
	}

	public UserModel setRounded(String rounded) {
		try {
			this.rounded = Integer.parseInt(rounded);
		} catch (Exception e) {
			this.rounded = 1;
		}
		return this;
	}

	public UserModel setUsername(String username) {
		this.username = username;
		return this;
	}

	@Override
	public String toString() {
		return "UserModel [username=" + username + ", email=" + email + ", currency=" + currency + ", locale=" + locale + ", distance=" + distance
				+ ", rounded=" + rounded + ", dateFormat=" + dateFormat + ", timeFormat=" + timeFormat + ", datetimesecondsFormat="
				+ pushoverUserKey + ", pushoverAPIKey=" + pushoverAPIKey + ", pushbulletAPIKey=" + pushbulletAPIKey + ", toString()="
				+ super.toString() + "]";
	}

}
