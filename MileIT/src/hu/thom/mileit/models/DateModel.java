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
