package hu.thom.mileit.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Model file representing all attributes of a car
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class CarModel extends Model {
	private static final long serialVersionUID = -5197813796132584702L;

	public static enum Fuel {
		PETROL((byte) 1), DIESEL((byte) 2), ELECTRIC((byte) 3), BIOETHANOL((byte) 4), OTHER((byte) 5);

		private final byte code;

		private Fuel(byte code) {
			this.code = code;
		}

		public byte getCode() {
			return code;
		}

		public static Fuel fromCode(byte code) {
			switch (code) {
			case 1:
				return PETROL;
			case 2:
				return DIESEL;
			case 3:
				return ELECTRIC;
			case 4:
				return BIOETHANOL;
			case 5:
			default:
				return OTHER;
			}
		}

		public static int toCode(Fuel fuel) {
			switch (fuel) {
			case PETROL:
				return 1;
			case DIESEL:
				return 2;
			case ELECTRIC:
				return 3;
			case BIOETHANOL:
				return 4;
			case OTHER:
			default:
				return 5;
			}
		}
	}

	private int manufacturer;
	private String manufacturerName;
	private String model;
	private Date manufacturerDate;
	private String color;
	private String vin;
	private String plateNumber;
	private Fuel fuel;
	private double fuelCapacity;
	private Date startDate;
	private Date endDate;
	private String description;
	private String friendlyName;

	public CarModel() {
	}

	public CarModel(int id) {
		setId(id);
	}

	public CarModel(String id) {
		setId(id);
	}

	public CarModel(int id, String friendlyName, String plateNumber) {
		setId(id);
		this.friendlyName = friendlyName;
		this.plateNumber = plateNumber;
	}

	public CarModel(Map<String, String[]> params, UserModel user) {
		setManufacturer(params.get("manufacturer")[0]);
		this.model = params.get("model")[0];
		setManufacturerDate(params.get("manufactureDate")[0]);
		setStartDate(params.get("startDate")[0]);
		setEndDate(params.get("endDate")[0]);
		this.color = params.get("color")[0];
		this.vin = params.get("vin")[0];
		this.plateNumber = params.get("plateNumber")[0];
		setFuelCapacity(params.get("fuelCapacity")[0]);
		setFuel(params.get("fuel")[0]);
		this.description = params.get("description")[0];
		this.friendlyName = params.get("friendlyName")[0];
		setActive(params.get("status")[0]);

		setUser(user);
	}
	
	public Map<String, Object> getCarForRest() {
		Map<String, Object> cm = new HashMap<String, Object>(1);
		
		cm.put("car_id", getId());
		cm.put("manufacturer", manufacturerName);
		cm.put("model", model);
		cm.put("manufacturerDate", manufacturerDate);
		cm.put("startDate", startDate);
		cm.put("endDate", endDate);
		cm.put("plateNumber", plateNumber);
		cm.put("friendlyName", friendlyName);
		cm.put("color", color);
		cm.put("fuelCapacity", fuelCapacity);
		cm.put("fuel", Fuel.toCode(fuel));
		cm.put("vin", vin);
		cm.put("active", isActive());
		cm.put("description", description);
		
		return cm;
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

	public Fuel getFuel() {
		return fuel;
	}

	public void setFuel(Fuel fuel) {
		this.fuel = fuel;
	}

	public void setFuel(int fuel) {
		setFuel(String.valueOf(fuel));
	}

	public void setFuel(String fuel) {
		switch (fuel) {
		case "1":
			this.fuel = Fuel.PETROL;
			break;

		case "2":
			this.fuel = Fuel.DIESEL;
			break;

		case "3":
			this.fuel = Fuel.ELECTRIC;
			break;

		case "4":
			this.fuel = Fuel.BIOETHANOL;
			break;

		case "5":
		default:
			this.fuel = Fuel.OTHER;
			break;
		}
	}

	public Date getManufacturerDate() {
		return manufacturerDate;
	}

	public void setManufacturerDate(Date manufacturerDate) {
		this.manufacturerDate = manufacturerDate;
	}

	public Timestamp getManufacturerDateAsTimestamp() {
		if (manufacturerDate == null) {
			return null;
		}

		return new Timestamp(manufacturerDate.getTime());
	}

	public void setManufacturerDate(String manufacturerDate) {
		try {
			this.manufacturerDate = this.getDateFormatter(DATE_YEAR_ONLY).parse(manufacturerDate);
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
			this.fuelCapacity = 0;
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public Timestamp getStartDateAsTimestamp() {
		if (startDate == null) {
			return null;
		}

		return new Timestamp(startDate.getTime());
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStartDate(String startDate) {
		try {
			this.startDate = this.getDateFormatter().parse(startDate);
		} catch (Exception e) {
			this.startDate = null;
		}
	}

	public Date getEndDate() {
		return endDate;
	}

	public Timestamp getEndDateAsTimestamp() {
		if (endDate == null) {
			return null;
		}

		return new Timestamp(endDate.getTime());
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndDate(String endDate) {
		try {
			this.endDate = this.getDateFormatter().parse(endDate);
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((friendlyName == null) ? 0 : friendlyName.hashCode());
		result = prime * result + ((fuel == null) ? 0 : fuel.hashCode());
		long temp;
		temp = Double.doubleToLongBits(fuelCapacity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + manufacturer;
		result = prime * result + ((manufacturerDate == null) ? 0 : manufacturerDate.hashCode());
		result = prime * result + ((manufacturerName == null) ? 0 : manufacturerName.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((plateNumber == null) ? 0 : plateNumber.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		if (Double.doubleToLongBits(fuelCapacity) != Double.doubleToLongBits(other.fuelCapacity))
			return false;
		if (manufacturer != other.manufacturer)
			return false;
		if (manufacturerDate == null) {
			if (other.manufacturerDate != null)
				return false;
		} else if (!manufacturerDate.equals(other.manufacturerDate))
			return false;
		if (manufacturerName == null) {
			if (other.manufacturerName != null)
				return false;
		} else if (!manufacturerName.equals(other.manufacturerName))
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
		if (vin == null) {
			if (other.vin != null)
				return false;
		} else if (!vin.equals(other.vin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CarModel [manufacturer=" + manufacturer + ", manufacturerName=" + manufacturerName + ", model=" + model + ", manufacturerDate="
				+ manufacturerDate + ", color=" + color + ", vin=" + vin + ", plateNumber=" + plateNumber + ", fuel=" + fuel + ", fuelCapacity="
				+ fuelCapacity + ", startDate=" + startDate + ", endDate=" + endDate + ", description=" + description + ", friendlyName="
				+ friendlyName + ", toString()=" + super.toString() + "]";
	}

}
