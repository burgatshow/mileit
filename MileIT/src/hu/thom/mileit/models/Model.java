package hu.thom.mileit.models;

import java.io.Serializable;

/**
 * Super model holding general attributes for other models
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class Model implements Serializable {
	private static final long serialVersionUID = -6095129005934760543L;

	private int id;

	private int operation;

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
			//FIXME
			e.printStackTrace();
		}
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + operation;
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
		if (id != other.id)
			return false;
		if (operation != other.operation)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", operation=" + operation + "]";
	}

}
