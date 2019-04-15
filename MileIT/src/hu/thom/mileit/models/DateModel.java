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

	private final DateFormat sdfManufacturerDate = new SimpleDateFormat("yyyy");
	private final DateFormat sdfDates = new SimpleDateFormat("yyyy-MM-dd");

	public DateFormat getSdfManufacturerDate() {
		return sdfManufacturerDate;
	}

	public DateFormat getSdfDates() {
		return sdfDates;
	}

}
