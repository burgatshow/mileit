package hu.thom.mileit.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TyreModel extends Model {
	private static final long serialVersionUID = -4885680246070902751L;

	private SimpleDateFormat sdfDates = new SimpleDateFormat("yyyy-MM-dd");

	public static enum TyreType {
		WINTER((byte) 1), SUMMER((byte) 2), ALLSEASONS((byte) 3), OTHER((byte) 4);

		private final byte code;

		private TyreType(byte code) {
			this.code = code;
		}

		public byte getCode() {
			return code;
		}

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
	}

	public static enum Axis {
		FRONT((byte) 1), REAR((byte) 2), BOTH((byte) 3), NONE((byte) 4);

		private final byte code;

		private Axis(byte code) {
			this.code = code;
		}

		public byte getCode() {
			return code;
		}

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
	}

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

	public TyreModel(String id) {
		setId(id);
	}

	public TyreModel(int tyre_id, int user_id, byte tyreType, int sizeW, int sizeH, int sizeR, int manufacturerId, String manufacturerName,
			String model, byte axis, Timestamp purchaseDate) {
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
		this.setUser(user);
	}

	public TyreType getType() {
		return type;
	}

	public void setType(TyreType type) {
		this.type = type;
	}

	public void setType(String type) {
		try {
			this.type = TyreType.fromCode((byte) Integer.parseInt(type));
		} catch (Exception e) {
			this.type = TyreType.OTHER;
		}
	}

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturer) {
		this.manufacturerId = manufacturer;
	}

	public void setManufacturerId(String manufacturer) {
		try {
			this.manufacturerId = Integer.parseInt(manufacturer);
		} catch (Exception e) {
			this.manufacturerId = 0;
		}
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getSizeW() {
		return sizeW;
	}

	public void setSizeW(int sizeW) {
		this.sizeW = sizeW;
	}

	public void setSizeW(String sizeW) {
		try {
			this.sizeW = Integer.parseInt(sizeW);
		} catch (Exception e) {
			this.sizeW = 0;
		}
	}

	public int getSizeH() {
		return sizeH;
	}

	public void setSizeH(int sizeH) {
		this.sizeH = sizeH;
	}

	public void setSizeH(String sizeH) {
		try {
			this.sizeH = Integer.parseInt(sizeH);
		} catch (Exception e) {
			this.sizeH = 0;
		}
	}

	public int getSizeR() {
		return sizeR;
	}

	public void setSizeR(int sizeR) {
		this.sizeR = sizeR;
	}

	public void setSizeR(String sizeR) {
		try {
			this.sizeR = Integer.parseInt(sizeR);
		} catch (Exception e) {
			this.sizeR = 0;
		}
	}

	public Axis getAxis() {
		return axis;
	}

	public void setAxis(Axis axis) {
		this.axis = axis;
	}

	public void setAxis(String axis) {
		try {
			this.axis = Axis.fromCode((byte) Integer.parseInt(axis));
		} catch (Exception e) {
			this.axis = Axis.NONE;
		}
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		try {
			this.purchaseDate = sdfDates.parse(purchaseDate);
		} catch (Exception e) {
			this.purchaseDate = new Date();
		}
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

	@Override
	public String toString() {
		return "TyreModel [type=" + type + ", manufacturerId=" + manufacturerId + ", manufacturerName=" + manufacturerName + ", model=" + model
				+ ", sizeW=" + sizeW + ", sizeH=" + sizeH + ", sizeR=" + sizeR + ", axis=" + axis + ", purchaseDate=" + purchaseDate + ", toString()="
				+ super.toString() + "]";
	}

}
