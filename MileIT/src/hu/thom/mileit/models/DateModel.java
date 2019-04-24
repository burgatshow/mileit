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
	private static final long serialVersionUID = 1541291534338945726L;

	public final String DATE_YEAR_ONLY = "yyyy";
	public final String DATE = "yyyy-MM-dd";
	public final String DATETIME = "yyyy-MM-dd HH:mm";

	public DateFormat getDateFormatter(String... pattern) {
		if ("".equalsIgnoreCase(pattern[0]) || pattern[0] == null) {
			return new SimpleDateFormat(DATE);
		}
		return new SimpleDateFormat(pattern[0]);
	}
}
