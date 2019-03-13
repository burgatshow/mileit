package hu.thom.mileit.core;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggerHelper implements Serializable {
	private static final long serialVersionUID = 7114711640573939717L;

	public static final String LOG_EXCEPTION = "An application exception occured, see details:";

	private Class<?> loggingClass;

	private Logger logger;
	
	public LoggerHelper() {
		if (logger == null) {
			logger = Logger.getAnonymousLogger();
			this.loggingClass = LoggerHelper.class;
		}
	}

	public LoggerHelper(Class<?> loggingClass) {
		if (logger == null) {
			logger = Logger.getLogger(loggingClass.getCanonicalName());
			this.loggingClass = loggingClass;
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public void logException(String method, Throwable t) {
		logger.severe(LOG_EXCEPTION);
		if (logger.isLoggable(Level.FINEST)) {
			logger.logp(Level.FINEST, loggingClass.getCanonicalName(), method, LOG_EXCEPTION, t);
		}
	}
}