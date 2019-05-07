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
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 7114711640573939717L;

	/**
	 * Default log message strings
	 */
	public static final String LOG_EXCEPTION = "An application exception occured, see details:";
	
	public static final String LOG_DC_E_GET = "Unable to get object from Dyna Cache!";
	public static final String LOG_DC_E_PUT = "Unable to put object to Dyna Cache!";
	public static final String LOG_DC_I_RETRIEVE = "DynaCache service retrieved the following object belongs to key:";
	public static final String LOG_DC_I_ADD = "DynaCache service added an object into cache using key with timeout value for dependency ID:";
	public static final String LOG_DC_I_INVALIDATED = "Entry gets invalidated: ";
	public static final String LOG_DC_I_CLEAR = "DynaCache instance is cleared.";
	
	public static final String LOG_SEC_F_SECRET = "Secret key is not set, setting now.";
	public static final String LOG_SEC_F_SALT = "Salt is not set, setting now.";
	public static final String LGO_SEC_FFF_J2C = "Obtain J2C credential: ";

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
	
	/**
	 * Logs an entry point for the given method
	 * @param method {@link String} name of the method
	 */
	public void logEnter(String method) {
		logger.entering(logger.getName(), method);
	}
	
	/**
	 * Logs an exit point for the given method
	 * @param method {@link String} name of the method
	 */
	public void logExit(String method) {
		logger.exiting(logger.getName(), method);
	}
}