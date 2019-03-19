package hu.thom.mileit.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model file representing all attributes of a car
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class CarModel extends Model {
	private static final long serialVersionUID = -5197813796132584702L;

	private SimpleDateFormat sdfManufactureDate = new SimpleDateFormat("yyyy");
	private SimpleDateFormat sdfDates = new SimpleDateFormat("yyyy-MM-dd");

	private int manufacturer;
	private String manufacturerName;
	private String model;
	private Date manufacturerDate;
	private String color;
	private String vin;
	private String plateNumber;
	private int fuel;
	private Double fuelCapacity;
	private Date startDate;
	private Date endDate;
	private String description;
	private String friendlyName;
	private boolean active;
	private UserModel user;

	public CarModel() {
	}

	public CarModel(int id) {
		setId(id);
	}

	public CarModel(String id) {
		setId(id);
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public int getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(int manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		try {
			this.manufacturer = Integer.parseInt(manufacturer);
		} catch (Exception e) {
			this.manufacturer = 0;
		}
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public void setFuel(String fuel) {
		try {
			this.fuel = Integer.parseInt(fuel);
		} catch (Exception e) {
			this.fuel = 0;
		}
	}

	public Date getManufacturerDate() {
		return manufacturerDate;
	}

	public void setManufacturerDate(Date manufacturerDate) {
		this.manufacturerDate = manufacturerDate;
	}

	public Timestamp getManufacturerDateAsTimestamp() {
		return new Timestamp(getManufacturerDate() == null ? null : getManufacturerDate().getTime());
	}

	public void setManufacturerDate(String manufacturerDate) {
		try {
			this.manufacturerDate = sdfManufactureDate.parse(manufacturerDate);
		} catch (Exception e) {
			this.manufacturerDate = null;
		}
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Double getFuelCapacity() {
		return fuelCapacity;
	}

	public void setFuelCapacity(Double fuelCapacity) {
		this.fuelCapacity = fuelCapacity;
	}

	public void setFuelCapacity(int fuelCapacity) {
		this.fuelCapacity = (double) fuelCapacity;
	}

	public void setFuelCapacity(String fuelCapacity) {
		try {
			this.fuelCapacity = Double.parseDouble(fuelCapacity);
		} catch (Exception e) {
			this.fuelCapacity = 0.0;
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public Timestamp getStartDateAsTimestamp() {
		return new Timestamp(getStartDate() == null ? null : getStartDate().getTime());
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStartDate(String startDate) {
		try {
			this.startDate = sdfDates.parse(startDate);
		} catch (Exception e) {
			this.startDate = null;
		}
	}

	public Date getEndDate() {
		return endDate;
	}

	public Timestamp getEndDateAsTimestamp() {
		return new Timestamp(getEndDate() == null ? null : getEndDate().getTime());
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndDate(String endDate) {
		try {
			this.endDate = sdfDates.parse(endDate);
		} catch (Exception e) {
			this.endDate = null;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setActive(String active) {
		switch (active.toLowerCase()) {
		case "1":
		case "true":
			this.active = true;
			break;

		case "0":
		case "false":
		default:
			this.active = false;
			break;
		}
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((friendlyName == null) ? 0 : friendlyName.hashCode());
		result = prime * result + fuel;
		result = prime * result + ((fuelCapacity == null) ? 0 : fuelCapacity.hashCode());
		result = prime * result + manufacturer;
		result = prime * result + ((manufacturerDate == null) ? 0 : manufacturerDate.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((plateNumber == null) ? 0 : plateNumber.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((vin == null) ? 0 : vin.hashCode());
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
		CarModel other = (CarModel) obj;
		if (active != other.active)
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (friendlyName == null) {
			if (other.friendlyName != null)
				return false;
		} else if (!friendlyName.equals(other.friendlyName))
			return false;
		if (fuel != other.fuel)
			return false;
		if (fuelCapacity == null) {
			if (other.fuelCapacity != null)
				return false;
		} else if (!fuelCapacity.equals(other.fuelCapacity))
			return false;
		if (manufacturer != other.manufacturer)
			return false;
		if (manufacturerDate == null) {
			if (other.manufacturerDate != null)
				return false;
		} else if (!manufacturerDate.equals(other.manufacturerDate))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (plateNumber == null) {
			if (other.plateNumber != null)
				return false;
		} else if (!plateNumber.equals(other.plateNumber))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (vin == null) {
			if (other.vin != null)
				return false;
		} else if (!vin.equals(other.vin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CarModel [sdfManufactureDate=" + sdfManufactureDate + ", sdfDates=" + sdfDates + ", manufacturer=" + manufacturer + ", model=" + model
				+ ", manufacturerDate=" + manufacturerDate + ", color=" + color + ", vin=" + vin + ", plateNumber=" + plateNumber + ", fuel=" + fuel
				+ ", fuelCapacity=" + fuelCapacity + ", startDate=" + startDate + ", endDate=" + endDate + ", description=" + description
				+ ", friendlyName=" + friendlyName + ", active=" + active + ", user=" + user + "]";
	}

}
