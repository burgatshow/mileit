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

/**
 * All log messages used in the application
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public final class LogMessages implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 7743087297685610335L;

	/**
	 * Error message strings
	 */
	public static final String LOG_EXCEPTION 			= "MILEIT0001E: An application exception occured, see details.";
	public static final String LOG_DC_E_GET 			= "MILEIT0002E: Unable to get object from Dyna Cache!";
	public static final String LOG_DC_E_PUT 			= "MILEIT0002E: Unable to put object to Dyna Cache!";
	public static final String INVALID_INPUT			= "MILEIT0004E: Invalid or insufficient amount of parameters.";
	public static final String CSRF_NO_SUITABLE_CIPHER 	= "MILEIT0005E: No cipher found with name %s to generate CSRF token!";
	public static final String CSRF_VICTIM 				= "MILEIT0006E: Potential CSRF detected! Redirecting user to login page and destroy its session!";
	
	/**
	 * Info message strings
	 */
	public static final String LOG_DC_I_RETRIEVE 		= "MILEIT0001I: DynaCache service retrieved the following object belongs to key:";
	public static final String LOG_DC_I_ADD 			= "MILEIT0002I: DynaCache service added an object into cache using key with timeout value for dependency ID:";
	public static final String LOG_DC_I_INVALIDATED		= "MILEIT0003I: Entry gets invalidated: ";
	public static final String LOG_DC_I_CLEAR 			= "MILEIT0004I: DynaCache instance is cleared.";
	public static final String LOG_SCHED_NOTIF_START	= "MILEIT0005I: Notification scheduler has successfully started and running every:";
	public static final String LOG_SCHED_NOTIF_STOP		= "MILEIT0006I: Notification scheduler has successfully stopped.";
	public static final String APPLICATION_STARTING		= "MILEIT0007I: MileIT application is starting...";
	public static final String APPLICATION_STARTED		= "MILEIT0008I: MileIT application is successfully started.";
	public static final String APPLICATION_STOPPING		= "MILEIT0009I: MileIT application is stopping...";
	public static final String APPLICATION_STOPPED		= "MILEIT0010I: MileIT application is successfully stopped.";
	public static final String APPLICATION_TOTP_LICENSE = "MILEIT0011I: TOTP implementation is copyright 2015, Gray Watson (https://github.com/j256/java-two-factor-auth)";
	
	/**
	 * Warning message strings
	 */
	public static final String LOG_SCHED_NOTIF_DISABLED = "MILEIT0001W: Notification scheduler is disabled!";
	public static final String API_TOKEN_NOT_FOUND 		= "MILEIT0002W: User and token not found in cache, fetching user and adding to cache.";
	
	/**
	 * Debug message strings
	 */
	public static final String LOG_SEC_F_SECRET 		= "MILEIT0001D: Secret key is not set, setting now.";
	public static final String LOG_SEC_F_SALT 			= "MILEIT0002D: Salt is not set, setting now.";
	public static final String LOG_SEC_FFF_J2C 			= "MILEIT0003D: Obtain J2C credential: ";
	public static final String DC_CLEAR 				= "MILEIT0004D:	Dynamic cache content wiped out.";
	public static final String NO_USERS_TO_NOTIFY 		= "MILEIT0005D: Currently, there are no expiring items.";
	public static final String SQL_COMMAND 				= "MILEIT0006D: SQL command used:";
	public static final String API_TOKEN_FOUND 			= "MILEIT0007D: User and token found in cache, using.";
	
	/**
	 * Trace message strings
	 */
	public static final String START_EM 				= "MILEIT0001T: Creating, configuring and loading Encryption manager into cache.";
	public static final String START_DC 				= "MILEIT0002T: Starting Dynamic Cache manager.";
	public static final String START_DB 				= "MILEIT0003T: Starting Database manager.";
	public static final String ENCRYPT 					= "MILEIT0004T: Data encrypted.";
	public static final String DECRYPT 					= "MILEIT0005T: Data decrypted.";
	public static final String START_TOTP 				= "MILEIT0006T: Starting TOTP Manager.";
}
