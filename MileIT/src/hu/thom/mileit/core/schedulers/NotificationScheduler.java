package hu.thom.mileit.core.schedulers;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.naming.InitialContext;

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.core.data.DBManager;
import hu.thom.mileit.utils.LogManager;
import hu.thom.mileit.utils.LogMessages;

/**
 * Scheduler implementation for notifications
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class NotificationScheduler implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5167105533214280246L;

	/**
	 * Logger instance
	 */
	private LogManager logger = new LogManager(NotificationScheduler.class);

	/**
	 * WAS' DynaCache manager implementation
	 */
	private DynaCacheManager dc = DynaCacheManager.getInstance();

	/**
	 * {@link PersistenceManager} instance
	 */
	private DBManager dbm = null;

	/**
	 * {@link ScheduledFuture} field
	 */
	private ScheduledFuture<?> scheduledFuture = null;

	/**
	 * Constructor
	 */
	public NotificationScheduler() {
		logger.logEnter("NotificationScheduler()");

		if (dbm == null) {
			dbm = (DBManager) dc.get("dbm");
		}

		logger.logExit("NotificationScheduler()");
	}

	public void initialize() {
		logger.logEnter("initialize()");
		try {
			ManagedScheduledExecutorService executor = (ManagedScheduledExecutorService) new InitialContext()
					.lookup("scheduler/mileit_notifications");

			scheduledFuture = executor.scheduleAtFixedRate(runRunnable(), 0, 5, TimeUnit.SECONDS);

			logger.logDebug("initialize()", LogMessages.LOG_SCHED_NOTIF_START, new Object[] { 5, "seconds" });

		} catch (Exception e) {
			logger.logException("initialize()", e);
		}

		logger.logExit("initialize()");
	}

	/**
	 * Main task executed by the scheduler
	 * 
	 * @return {@link Runnable}
	 */
	private Runnable runRunnable() {
		return new Runnable() {

			@Override
			public void run() {
			}
		};

	};

	public void cancelTask() {
		logger.logEnter("cancelTask()");

		if (!scheduledFuture.isCancelled()) {
			scheduledFuture.cancel(false);
			logger.logDebug("cancelTask()", LogMessages.LOG_SCHED_NOTIF_STOP);
		}

		logger.logExit("cancelTask()");
	}
}