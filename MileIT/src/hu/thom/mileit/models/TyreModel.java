package hu.thom.mileit.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Model file representing all attributes of a tyre
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class TyreModel extends Model {
	public static enum Axis {
		FRONT((byte) 1), REAR((byte) 2), BOTH((byte) 3), NONE((byte) 4);

		public static Axis fromCode(byte code) {
			switch (code) {
			case 1:
				return FRONT;
			case 2:
				return REAR;
			case 3:
				return BOTH;
			case 4:
			default:
				return NONE;
			}
		}

		public static int toCode(Axis axis) {
			switch (axis) {
			case FRONT:
				return 1;
			case REAR:
				return 2;
			case BOTH:
				return 3;
			case NONE:
			default:
				return 4;
			}
		}

		private final byte code;

		private Axis(byte code) {
			this.code = code;
		}

		public byte getCode() {
			return code;
		}
	}

	public static enum TyreType {
		WINTER((byte) 1), SUMMER((byte) 2), ALLSEASONS((byte) 3), OTHER((byte) 4);

		public static TyreType fromCode(byte code) {
			switch (code) {
			case 1:
				return WINTER;
			case 2:
				return SUMMER;
			case 3:
				return ALLSEASONS;
			case 4:
			default:
				return OTHER;
			}
		}

		public static int toCode(TyreType tyre) {
			switch (tyre) {
			case WINTER:
				return 1;
			case SUMMER:
				return 2;
			case ALLSEASONS:
				return 3;
			case OTHER:
			default:
				return 4;
			}
		}

		private final byte code;

		private TyreType(byte code) {
			this.code = code;
		}

		public byte getCode() {
			return code;
		}
	}

	private static final long serialVersionUID = -4885680246070902751L;

	private TyreType type;
	private int manufacturerId;
	private String manufacturerName;
	private String model;
	private int sizeW;
	private int sizeH;
	private int sizeR;
	private Axis axis;
	private Date purchaseDate;

	public TyreModel() {
	}

	public TyreModel(int id) {
		setId(id);
	}

	public TyreModel(int tyre_id, int user_id, byte tyreType, int sizeW, int sizeH, int sizeR, int manufacturerId, String manufacturerName,
			String model, byte axis, Timestamp purchaseDate, int archived) {
		setId(tyre_id);
		setUser(new UserModel(user_id));
		this.type = TyreType.fromCode(tyreType);
		this.sizeW = sizeW;
		this.sizeR = sizeR;
		this.sizeH = sizeH;
		this.manufacturerId = manufacturerId;
		this.manufacturerName = manufacturerName;
		this.model = model;
		this.axis = Axis.fromCode(axis);
		this.purchaseDate = purchaseDate;
		setArchived(archived);
	}

	public TyreModel(Map<String, String[]> params, UserModel user) {
		setManufacturerId(params.get("manufacturer")[0]);
		this.model = params.get("model")[0];

		setSizeW(params.get("width")[0]);
		setSizeH(params.get("height")[0]);
		setSizeR(params.get("radius")[0]);
		setType(params.get("type")[0]);
		setAxis(params.get("axis")[0]);
		setPurchaseDate(params.get("purchaseDate")[0]);
		setUser(user);
	}

	public TyreModel(String id) {
		setId(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TyreModel other = (TyreModel) obj;
		if (axis != other.axis)
			return false;
		if (manufacturerId != other.manufacturerId)
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
		if (purchaseDate == null) {
			if (other.purchaseDate != null)
				return false;
		} else if (!purchaseDate.equals(other.purchaseDate))
			return false;
		if (sizeH != other.sizeH)
			return false;
		if (sizeR != other.sizeR)
			return false;
		if (sizeW != other.sizeW)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public Axis getAxis() {
		return axis;
	}

	public int getManufacturerId() {
		return manufacturerId;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public String getModel() {
		return model;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public int getSizeH() {
		return sizeH;
	}

	public int getSizeR() {
		return sizeR;
	}

	public int getSizeW() {
		return sizeW;
	}

	public TyreType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((axis == null) ? 0 : axis.hashCode());
		result = prime * result + manufacturerId;
		result = prime * result + ((manufacturerName == null) ? 0 : manufacturerName.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((purchaseDate == null) ? 0 : purchaseDate.hashCode());
		result = prime * result + sizeH;
		result = prime * result + sizeR;
		result = prime * result + sizeW;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	public TyreModel setAxis(Axis axis) {
		this.axis = axis;
		return this;
	}

	public TyreModel setAxis(String axis) {
		try {
			this.axis = Axis.fromCode((byte) Integer.parseInt(axis));
		} catch (Exception e) {
			this.axis = Axis.NONE;
		}
		return this;
	}

	public TyreModel setManufacturerId(int manufacturer) {
		this.manufacturerId = manufacturer;
		return this;
	}

	public TyreModel setManufacturerId(String manufacturer) {
		try {
			this.manufacturerId = Integer.parseInt(manufacturer);
		} catch (Exception e) {
			this.manufacturerId = 0;
		}
		return this;
	}

	public TyreModel setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
		return this;
	}

	public TyreModel setModel(String model) {
		this.model = model;
		return this;
	}

	public TyreModel setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
		return this;
	}

	public TyreModel setPurchaseDate(String purchaseDate) {
		try {
			this.purchaseDate = this.getDateFormatter(null).parse(purchaseDate);
		} catch (Exception e) {
			this.purchaseDate = new Date();
		}
		return this;
	}

	public TyreModel setSizeH(int sizeH) {
		this.sizeH = sizeH;
		return this;
	}

	public TyreModel setSizeH(String sizeH) {
		try {
			this.sizeH = Integer.parseInt(sizeH);
		} catch (Exception e) {
			this.sizeH = 0;
		}
		return this;
	}

	public TyreModel setSizeR(int sizeR) {
		this.sizeR = sizeR;
		return this;
	}

	public TyreModel setSizeR(String sizeR) {
		try {
			this.sizeR = Integer.parseInt(sizeR);
		} catch (Exception e) {
			this.sizeR = 0;
		}
		return this;
	}

	public TyreModel setSizeW(int sizeW) {
		this.sizeW = sizeW;
		return this;
	}

	public TyreModel setSizeW(String sizeW) {
		try {
			this.sizeW = Integer.parseInt(sizeW);
		} catch (Exception e) {
			this.sizeW = 0;
		}
		return this;
	}

	public TyreModel setType(String type) {
		try {
			this.type = TyreType.fromCode((byte) Integer.parseInt(type));
		} catch (Exception e) {
			this.type = TyreType.OTHER;
		}
		return this;
	}

	public TyreModel setType(TyreType type) {
		this.type = type;
		return this;
	}

	@Override
	public String toString() {
		return "TyreModel [type=" + type + ", manufacturerId=" + manufacturerId + ", manufacturerName=" + manufacturerName + ", model=" + model
				+ ", sizeW=" + sizeW + ", sizeH=" + sizeH + ", sizeR=" + sizeR + ", axis=" + axis + ", purchaseDate=" + purchaseDate + ", toString()="
				+ super.toString() + "]";
	}

}
