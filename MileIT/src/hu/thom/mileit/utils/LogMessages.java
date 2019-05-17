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
	
	/**
	 * Warning message strings
	 */
	public static final String LOG_SCHED_NOTIF_DISABLED = "MILEIT0001W: Notification scheduler is disabled!";
	
	/**
	 * Debug message strings
	 */
	public static final String LOG_SEC_F_SECRET 		= "MILEIT0001D: Secret key is not set, setting now.";
	public static final String LOG_SEC_F_SALT 			= "MILEIT0002D: Salt is not set, setting now.";
	public static final String LOG_SEC_FFF_J2C 			= "MILEIT0003D: Obtain J2C credential: ";
	public static final String DC_CLEAR 				= "MILEIT0004D:	Dynamic cache content wiped out.";
	public static final String NO_USERS_TO_NOTIFY 		= "MILEIT0005D: Currently, there are no expiring items.";
	public static final String SQL_COMMAND 				= "MILEIT0006D: SQL command used:";
	
	/**
	 * Trace message strings
	 */
	public static final String START_EM 				= "MILEIT0001T:	Creating, configuring and loading Encryption manager into cache.";
	public static final String START_DC 				= "MILEIT0002T:	Starting Dynamic Cache manager.";
	public static final String START_DB 				= "MILEIT0003T:	Starting Database manager.";
}
