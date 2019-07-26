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
package hu.thom.mileit.utils;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger class for application logging
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public final class LogManager implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 7114711640573939717L;

	private Class<?> loggingClass;
	private Logger logger;

	/**
	 * Constructor
	 */
	public LogManager() {
		if (logger == null) {
			logger = Logger.getAnonymousLogger();
			this.loggingClass = LogManager.class;
		}
	}

	/**
	 * Constructor using a {@link Class} name to configure.
	 * 
	 * @param loggingClass {@link Class} which instantiate the {@link Logger}
	 */
	public LogManager(Class<?> loggingClass) {
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
//	public Logger getLogger() {
//		return logger;
//	}

	/**
	 * Logs any exception from the given method if {@link Level} FINEST is enabled
	 * 
	 * @param method {@link String} the method name logging the exception
	 * @param t      {@link Throwable} standard object for exceptions
	 */
	public void logException(String method, Throwable t) {
		if (logger.isLoggable(Level.FINEST)) {
			logger.logp(Level.FINEST, loggingClass.getCanonicalName(), method, t.getMessage(), t);
		}
	}

	/**
	 * Write to the log on level {@link Level.} INFO
	 * 
	 * @param method in which method the logger invoked
	 * @param msg    the log message
	 */
	public void logInfo(String method, String msg) {
		if (logger.isLoggable(Level.INFO)) {

			if (method == null || method.isEmpty()) {
				method = "?()";
			}

			if (msg == null || msg.isEmpty()) {
				msg = "Dump values:";
			}

			logger.logp(Level.INFO, loggingClass.getCanonicalName(), method, msg);
		}
	}

	/**
	 * Write to the log on level {@link Level.} WARNING
	 * 
	 * @param method in which method the logger invoked
	 * @param msg    the log message
	 */
	public void logWarn(String method, String msg, Object... dumpVals) {
		if (logger.isLoggable(Level.WARNING)) {

			if (method == null || method.isEmpty()) {
				method = "?()";
			}

			if (msg == null || msg.isEmpty()) {
				msg = "Dump values:";
			}

			logger.logp(Level.WARNING, loggingClass.getCanonicalName(), method, msg, dumpVals);
		}
	}

	/**
	 * Write to the log on level {@link Level.} SEVERE
	 * 
	 * @param method in which method the logger invoked
	 * @param msg    the log message
	 */
	public void logError(String method, String msg, Object... dumpVals) {
		if (logger.isLoggable(Level.SEVERE)) {

			if (method == null || method.isEmpty()) {
				method = "?()";
			}

			if (msg == null || msg.isEmpty()) {
				msg = "Dump values:";
			}

			logger.logp(Level.SEVERE, loggingClass.getCanonicalName(), method, msg, dumpVals);
		}
	}

	/**
	 * Write to the log on level {@link Level.} FINE
	 * 
	 * @param method in which method the logger invoked
	 * @param msg    the log message
	 */
	public void logDebug(String method, String msg, Object... dumpVals) {
		if (logger.isLoggable(Level.FINE)) {

			if (method == null || method.isEmpty()) {
				method = "?()";
			}

			if (msg == null || msg.isEmpty()) {
				msg = "Dump values:";
			}

			logger.logp(Level.FINE, loggingClass.getCanonicalName(), method, msg, dumpVals);
		}
	}

	/**
	 * Write to the log on level {@link Level.} FINEST
	 * 
	 * @param method in which method the logger invoked
	 * @param msg    the log message
	 */
	public void logTrace(String method, String msg, Object... dumpVals) {
		if (logger.isLoggable(Level.FINEST)) {

			if (method == null || method.isEmpty()) {
				method = "?()";
			}

			if (msg == null || msg.isEmpty()) {
				msg = "Dump values:";
			}

			logger.logp(Level.FINEST, loggingClass.getCanonicalName(), method, msg, dumpVals);
		}
	}

	/**
	 * Logs an entry point for the given method
	 * 
	 * @param method {@link String} name of the method
	 */
	public void logEnter(String method) {
		logger.entering(loggingClass.getCanonicalName(), method);
	}

	/**
	 * Logs an exit point for the given method
	 * 
	 * @param method {@link String} name of the method
	 */
	public void logExit(String method) {
		logger.exiting(loggingClass.getCanonicalName(), method);
	}
}