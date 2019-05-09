package hu.thom.mileit.utils;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.core.EncryptionManager;
import hu.thom.mileit.core.data.DBManager;
import hu.thom.mileit.core.schedulers.NotificationScheduler;

/**
 * Listener class to execute methods after the application has been started on
 * the application server
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebListener
public class StartupListener implements ServletContextListener, Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5650705389031931935L;

	/**
	 * Logger instance
	 */
	private LogManager logger = new LogManager(getClass());

	/**
	 * {@link NotificationScheduler} instance
	 */
	private NotificationScheduler notifSch = null;

	/**
	 * Constructor
	 */
	public StartupListener() {
	}

	/**
	 * This method will be executed by the runtime once the application has been
	 * started
	 * 
	 * @param servletCtxEvent {@link ServletContextEvent}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.logEnter("contextInitialized()");
		logger.logInfo("contextInitialized()", LogMessages.APPLICATION_STARTING);

		DynaCacheManager dc = DynaCacheManager.getInstance();
		logger.logTrace("contextInitialized()", LogMessages.START_DC);

		dc.clear();

		if (dc.get("em") == null) {
			dc.put("em", EncryptionManager.getInstance(), DynaCacheManager.DC_TTL_FOREVER, "application");
			logger.logTrace("contextInitialized()", LogMessages.START_EM);
		}

		DBManager db = null;
		if (dc.get("db") == null) {
			db = DBManager.getInstance();
			dc.put("db", db, DynaCacheManager.DC_TTL_FOREVER, "application");
			logger.logTrace("contextInitialized()", LogMessages.START_DB);

		}

		if (dc.get(UIBindings.CAR_VENDORS) == null) {
			dc.put(UIBindings.CAR_VENDORS, db.getCarVendors(), DynaCacheManager.DC_TTL_1H, "common");
		}

		if (dc.get(UIBindings.TYRE_VENDORS) == null) {
			dc.put(UIBindings.TYRE_VENDORS, db.getTyreVendors(), DynaCacheManager.DC_TTL_1H, "common");
		}

		if (notifSch == null) {
			notifSch = new NotificationScheduler();
			notifSch.initialize();
		}

		logger.logInfo("contextInitialized()", LogMessages.APPLICATION_STARTED);
		logger.logExit("contextInitialized()");
	}

	/**
	 * This method will be executed by the runtime when the application going to be
	 * stopped
	 * 
	 * @param servletCtxEvent {@link ServletContextEvent}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.logEnter("contextDestroyed()");
		logger.logInfo("contextDestroyed()", LogMessages.APPLICATION_STOPPING);

		notifSch.cancelTask();

		logger.logInfo("contextDestroyed()", LogMessages.APPLICATION_STOPPED);
		logger.logExit("contextDestroyed()");
	}

}
