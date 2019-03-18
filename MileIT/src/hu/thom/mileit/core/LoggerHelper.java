package hu.thom.mileit.core;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger class for application logging
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public final class LoggerHelper implements Serializable {
	private static final long serialVersionUID = 7114711640573939717L;

	public static final String LOG_EXCEPTION = "An application exception occured, see details:";

	private Class<?> loggingClass;

	private Logger logger;

	/**
	 * Constructor
	 */
	public LoggerHelper() {
		if (logger == null) {
			logger = Logger.getAnonymousLogger();
			this.loggingClass = LoggerHelper.class;
		}
	}

	/**
	 * Constructor using a {@link Class} name to configure.
	 * 
	 * @param loggingClass {@link Class} which instantiate the {@link Logger}
	 */
	public LoggerHelper(Class<?> loggingClass) {
		if (logger == null) {
			logger = Logger.getLogger(loggingClass.getCanonicalName());
			this.loggingClass = loggingClass;
		}
	}

	/**
	 * Returns the configured {@link Logger} instance
	 * 
	 * @return {@link Logger}
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Logs any exception from the given method if {@link Level} FINEST is enabled
	 * 
	 * @param method {@link String} the method name logging the exception
	 * @param t      {@link Throwable} standard object for exceptions
	 */
	public void logException(String method, Throwable t) {
		logger.severe(LOG_EXCEPTION);
		if (logger.isLoggable(Level.FINEST)) {
			logger.logp(Level.FINEST, loggingClass.getCanonicalName(), method, LOG_EXCEPTION, t);
		}
	}
}