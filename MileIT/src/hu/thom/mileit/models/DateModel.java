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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Model file representing all formatters for dates and times
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class DateModel implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1541291534338945726L;

	public final String DATE_YEAR_ONLY = "yyyy";
	public final String DATE = "yyyy-MM-dd";
	public final String DATETIME = "yyyy-MM-dd HH:mm";
	public final String DATETIMESEC = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Constructor
	 * 
	 * @param pattern {@link String} of pattern which used to configure the
	 *                {@link DateFormat}. if null, the default DATE will be used
	 * @return {@link DateFormat} a configured formatted
	 */
	public DateFormat getDateFormatter(String pattern) {
		if (pattern == null || pattern.isEmpty()) {
			return new SimpleDateFormat(DATE);
		}
		return new SimpleDateFormat(pattern);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DATE == null) ? 0 : DATE.hashCode());
		result = prime * result + ((DATETIME == null) ? 0 : DATETIME.hashCode());
		result = prime * result + ((DATETIMESEC == null) ? 0 : DATETIMESEC.hashCode());
		result = prime * result + ((DATE_YEAR_ONLY == null) ? 0 : DATE_YEAR_ONLY.hashCode());
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
		DateModel other = (DateModel) obj;
		if (DATE == null) {
			if (other.DATE != null)
				return false;
		} else if (!DATE.equals(other.DATE))
			return false;
		if (DATETIME == null) {
			if (other.DATETIME != null)
				return false;
		} else if (!DATETIME.equals(other.DATETIME))
			return false;
		if (DATETIMESEC == null) {
			if (other.DATETIMESEC != null)
				return false;
		} else if (!DATETIMESEC.equals(other.DATETIMESEC))
			return false;
		if (DATE_YEAR_ONLY == null) {
			if (other.DATE_YEAR_ONLY != null)
				return false;
		} else if (!DATE_YEAR_ONLY.equals(other.DATE_YEAR_ONLY))
			return false;
		return true;
	}
}
