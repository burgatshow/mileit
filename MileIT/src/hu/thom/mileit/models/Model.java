package hu.thom.mileit.models;

/**
 * Super model holding general attributes for other models
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class Model extends DateModel {
	private static final long serialVersionUID = -6095129005934760543L;

	private int id;
	private int operation;
	private CarModel car;
	private UserModel user;
	private PaymentMethodModel payment;
	private PlaceModel place;
	private TyreModel tyre;
	private TyreEventModel tyreEvent;
	private RefuelModel refuel;

	private boolean active;
	private boolean archived;

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public void setArchived(String archived) {
		switch (archived.toLowerCase()) {
		case "1":
		case "yes":
		case "true":
			this.archived = true;
			break;

		case "0":
		case "no":
		case "false":
		default:
			this.archived = false;
			break;
		}
	}

	public void setArchived(int archived) {
		setArchived(Integer.toString(archived));
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setActive(int active) {
		setActive(Integer.toString(active));
	}

	public void setActive(String active) {
		switch (active.toLowerCase()) {
		case "1":
		case "yes":
		case "true":
			this.archived = true;
			break;

		case "0":
		case "no":
		case "false":
		default:
			this.archived = false;
			break;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId(String id) {
		try {
			this.id = Integer.parseInt(id);
		} catch (Exception e) {
		}
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public CarModel getCar() {
		return car;
	}

	public void setCar(CarModel car) {
		this.car = car;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public PaymentMethodModel getPayment() {
		return payment;
	}

	public void setPayment(PaymentMethodModel payment) {
		this.payment = payment;
	}

	public PlaceModel getPlace() {
		return place;
	}

	public void setPlace(PlaceModel place) {
		this.place = place;
	}

	public TyreModel getTyre() {
		return tyre;
	}

	public void setTyre(TyreModel tyre) {
		this.tyre = tyre;
	}

	public TyreEventModel getTyreEvent() {
		return tyreEvent;
	}

	public void setTyreEvent(TyreEventModel tyreEvent) {
		this.tyreEvent = tyreEvent;
	}

	public RefuelModel getRefuel() {
		return refuel;
	}

	public void setRefuel(RefuelModel refuel) {
		this.refuel = refuel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + (archived ? 1231 : 1237);
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		result = prime * result + id;
		result = prime * result + operation;
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		result = prime * result + ((refuel == null) ? 0 : refuel.hashCode());
		result = prime * result + ((tyre == null) ? 0 : tyre.hashCode());
		result = prime * result + ((tyreEvent == null) ? 0 : tyreEvent.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Model other = (Model) obj;
		if (active != other.active)
			return false;
		if (archived != other.archived)
			return false;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		if (id != other.id)
			return false;
		if (operation != other.operation)
			return false;
		if (payment == null) {
			if (other.payment != null)
				return false;
		} else if (!payment.equals(other.payment))
			return false;
		if (place == null) {
			if (other.place != null)
				return false;
		} else if (!place.equals(other.place))
			return false;
		if (refuel == null) {
			if (other.refuel != null)
				return false;
		} else if (!refuel.equals(other.refuel))
			return false;
		if (tyre == null) {
			if (other.tyre != null)
				return false;
		} else if (!tyre.equals(other.tyre))
			return false;
		if (tyreEvent == null) {
			if (other.tyreEvent != null)
				return false;
		} else if (!tyreEvent.equals(other.tyreEvent))
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
		return "Model [id=" + id + ", operation=" + operation + ", car=" + car + ", user=" + user + ", payment=" + payment + ", place=" + place
				+ ", tyre=" + tyre + ", tyreEvent=" + tyreEvent + ", refuel=" + refuel + ", active=" + active + ", archived=" + archived
				+ ", toString()=" + super.toString() + "]";
	}

}
