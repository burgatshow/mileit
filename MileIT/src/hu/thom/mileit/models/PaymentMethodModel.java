package hu.thom.mileit.models;

/**
 * Model file representing all attributes of a payment method
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class PaymentMethodModel extends Model {
	private static final long serialVersionUID = -7938279588120027259L;

	private String name;
	private String description;
	private UserModel user;

	public PaymentMethodModel() {
	}

	public PaymentMethodModel(int id) {
		setId(id);
	}

	public PaymentMethodModel(String id) {
		setId(id);
	}

	public PaymentMethodModel(int id, String name, String description) {
		setId(id);
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		PaymentMethodModel other = (PaymentMethodModel) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		return "PaymentMethodModel [name=" + name + ", description=" + description + ", user=" + user + ", toString()=" + super.toString() + "]";
	}

}
