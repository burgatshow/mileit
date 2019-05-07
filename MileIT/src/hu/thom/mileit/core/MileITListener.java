package hu.thom.mileit.core;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MileITListener implements ServletContextListener, Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5650705389031931935L;

	/**
	 * Logger instance
	 */
	private LoggerHelper logger = new LoggerHelper(getClass());

	/**
	 * Constructor
	 */
	public MileITListener() {
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

		DynaCacheAdaptor dc = DynaCacheAdaptor.getInstance();
		dc.clear();

		if (dc.get("sm") == null) {
			dc.put("sm", new EncryptManager(), DynaCacheAdaptor.DC_TTL_FOREVER, "application");
		}

		DBManager dbm = null;
		if (dc.get("dbm") == null) {
			dbm = new DBManager();
			dc.put("dbm", dbm, DynaCacheAdaptor.DC_TTL_FOREVER, "application");
		}

		if (dc.get(UIKeys.CAR_VENDORS) == null) {
			dc.put(UIKeys.CAR_VENDORS, dbm.getCarVendors(), DynaCacheAdaptor.DC_TTL_1H, "common");
		}

		if (dc.get(UIKeys.TYRE_VENDORS) == null) {
			dc.put(UIKeys.TYRE_VENDORS, dbm.getTyreVendors(), DynaCacheAdaptor.DC_TTL_1H, "common");
		}

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
		logger.logExit("contextDestroyed()");
	}

}
