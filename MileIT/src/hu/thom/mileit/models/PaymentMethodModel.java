/**
 * MIT License
 *
 * Copyright (c) 2019 Tamas BURES
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package hu.thom.mileit.models;

import java.util.Map;

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

	public PaymentMethodModel() {
	}

	public PaymentMethodModel(int id) {
		setId(id);
	}

	public PaymentMethodModel(int id, String name, String description) {
		setId(id);
		this.name = name;
		this.description = description;
	}

	public PaymentMethodModel(Map<String, String[]> params, UserModel user) {
		this.name = params.get("name")[0];
		this.description = params.get("description")[0];
		setUser(user);
	}

	public PaymentMethodModel(String id) {
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
		return true;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public PaymentMethodModel setDescription(String description) {
		this.description = description;
		return this;
	}

	public PaymentMethodModel setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "PaymentMethodModel [name=" + name + ", description=" + description + ", toString()=" + super.toString() + "]";
	}

}
