package hu.thom.mileit.models;

public class TyreModel extends Model {
	private static final long serialVersionUID = -4885680246070902751L;

	public static enum Tyre {
		WINTER((byte) 1), SUMMER((byte) 2), ALLSEASONS((byte) 3), OTHER((byte) 4);

		private final byte code;

		private Tyre(byte code) {
			this.code = code;
		}

		public byte getCode() {
			return code;
		}

		public static Tyre fromCode(byte code) {
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

		public static int toCode(Tyre tyre) {
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

	private Tyre type;
	private String manufacturer;
	private String model;
	private int widthSize;
	private int wallSize;
	private int diameter;

	public Tyre getType() {
		return type;
	}

	public void setType(Tyre type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getWidthSize() {
		return widthSize;
	}

	public void setWidthSize(int widthSize) {
		this.widthSize = widthSize;
	}

	public int getWallSize() {
		return wallSize;
	}

	public void setWallSize(int wallSize) {
		this.wallSize = wallSize;
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + diameter;
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + wallSize;
		result = prime * result + widthSize;
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
		if (diameter != other.diameter)
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (type != other.type)
			return false;
		if (wallSize != other.wallSize)
			return false;
		if (widthSize != other.widthSize)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TyreModel [type=" + type + ", manufacturer=" + manufacturer + ", model=" + model + ", widthSize=" + widthSize + ", wallSize="
				+ wallSize + ", diameter=" + diameter + ", toString()=" + super.toString() + "]";
	}

}
