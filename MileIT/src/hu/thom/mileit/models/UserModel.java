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

	public UserModel() {
	}

	public UserModel(int id) {
		setId(id);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserModel(String username) {
		this.setUsername(username);
	}

	public UserModel(String currency, String locale) {
		this.currency = currency;
		this.locale = locale;
	}

	public String getCurrency() {
		return (currency == null || "".equalsIgnoreCase(currency)) ? "Ft" : currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLocale() {
		return (locale == null || "".equalsIgnoreCase(locale)) ? "hu" : locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		UserModel other = (UserModel) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserModel [username=" + username + ", currency=" + currency + ", locale=" + locale + ", toString()=" + super.toString() + "]";
	}

}
