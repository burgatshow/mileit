package hu.thom.mileit.models;

public class UserModel extends Model {
	private static final long serialVersionUID = -8687951190661595457L;

	private String username;
	private String currency;
	private String locale;

	public UserModel() {
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
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		return "UserModel [username=" + username + ", currency=" + currency + ", locale=" + locale + ", toString()="
				+ super.toString() + "]";
	}

}
