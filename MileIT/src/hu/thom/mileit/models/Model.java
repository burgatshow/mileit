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
	private MaintenanceModel maintenances;

	private boolean active;

	private boolean archived;

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
		if (maintenances == null) {
			if (other.maintenances != null)
				return false;
		} else if (!maintenances.equals(other.maintenances))
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

	public CarModel getCar() {
		return car;
	}

	public int getId() {
		return id;
	}

	public MaintenanceModel getMaintenances() {
		return maintenances;
	}

	public int getOperation() {
		return operation;
	}

	public PaymentMethodModel getPayment() {
		return payment;
	}

	public PlaceModel getPlace() {
		return place;
	}

	public RefuelModel getRefuel() {
		return refuel;
	}

	public TyreModel getTyre() {
		return tyre;
	}

	public TyreEventModel getTyreEvent() {
		return tyreEvent;
	}

	public UserModel getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + (archived ? 1231 : 1237);
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		result = prime * result + id;
		result = prime * result + ((maintenances == null) ? 0 : maintenances.hashCode());
		result = prime * result + operation;
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		result = prime * result + ((refuel == null) ? 0 : refuel.hashCode());
		result = prime * result + ((tyre == null) ? 0 : tyre.hashCode());
		result = prime * result + ((tyreEvent == null) ? 0 : tyreEvent.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isArchived() {
		return archived;
	}

	public Model setActive(boolean active) {
		this.active = active;
		return this;
	}

	public Model setActive(int active) {
		setActive(Integer.toString(active));
		return this;
	}

	public Model setActive(String active) {
		switch (active.toLowerCase()) {
		case "1":
		case "yes":
		case "true":
			this.active = true;
			break;

		default:
			this.active = false;
			break;
		}
		return this;
	}

	public Model setArchived(boolean archived) {
		this.archived = archived;
		return this;
	}

	public Model setArchived(int archived) {
		setArchived(Integer.toString(archived));
		return this;
	}

	public Model setArchived(String archived) {
		switch (archived.toLowerCase()) {
		case "1":
		case "yes":
		case "true":
			this.archived = true;
			break;

		default:
			this.archived = false;
			break;
		}
		return this;
	}

	public CarModel setCar(CarModel car) {
		this.car = car;
		return this.car;
	}

	public Model setId(int id) {
		this.id = id;
		return this;
	}

	public Model setId(String id) {
		try {
			this.id = Integer.parseInt(id);
		} catch (Exception e) {
		}
		return this;
	}

	public MaintenanceModel setMaintenances(MaintenanceModel maintenances) {
		this.maintenances = maintenances;
		return this.maintenances;
	}

	public Model setOperation(int operation) {
		this.operation = operation;
		return this;
	}

	public PaymentMethodModel setPayment(PaymentMethodModel payment) {
		this.payment = payment;
		return this.payment;
	}

	public PlaceModel setPlace(PlaceModel place) {
		this.place = place;
		return this.place;
	}

	public RefuelModel setRefuel(RefuelModel refuel) {
		this.refuel = refuel;
		return this.refuel;
	}

	public TyreModel setTyre(TyreModel tyre) {
		this.tyre = tyre;
		return this.tyre;
	}

	public TyreEventModel setTyreEvent(TyreEventModel tyreEvent) {
		this.tyreEvent = tyreEvent;
		return this.tyreEvent;
	}

	public UserModel setUser(UserModel user) {
		this.user = user;
		return this.user;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", operation=" + operation + ", car=" + car + ", user=" + user + ", payment=" + payment + ", place=" + place
				+ ", tyre=" + tyre + ", tyreEvent=" + tyreEvent + ", refuel=" + refuel + ", maintenances=" + maintenances + ", active=" + active
				+ ", archived=" + archived + ", toString()=" + super.toString() + "]";
	}

}
