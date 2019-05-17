package hu.thom.mileit.core.schedulers;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.naming.InitialContext;

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.core.EncryptionManager;
import hu.thom.mileit.core.PushManager;
import hu.thom.mileit.core.data.DBManager;
import hu.thom.mileit.models.Model;
import hu.thom.mileit.models.UserModel;
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

	private static final boolean ENABLE_SCHEDULER = false;

	/**
	 * Logger instance
	 */
	private LogManager logger = new LogManager(NotificationScheduler.class);

	/**
	 * Dynamic Cache Manager implementation
	 */
	private DynaCacheManager dc = DynaCacheManager.getInstance();

	/**
	 * Push Manager implementation
	 */
	private PushManager pm = PushManager.getInstance();

	/**
	 * {@link PersistenceManager} instance
	 */
	private DBManager db = null;
	private EncryptionManager em = null;

	/**
	 * {@link ScheduledFuture} field
	 */
	private ScheduledFuture<?> scheduledFuture = null;

	/**
	 * Constructor
	 */
	public NotificationScheduler() {
		logger.logEnter("NotificationScheduler()");

		if (db == null) {
			db = (DBManager) dc.get("db");
		}

		if (em == null) {
			em = (EncryptionManager) dc.get("em");
		}

		logger.logExit("NotificationScheduler()");
	}

	public void initialize() {
		logger.logEnter("initialize()");
		if (ENABLE_SCHEDULER) {
			try {
				ManagedScheduledExecutorService executor = (ManagedScheduledExecutorService) new InitialContext()
						.lookup("scheduler/mileit_notifications");

				scheduledFuture = executor.scheduleAtFixedRate(runRunnable(), 0, 10, TimeUnit.SECONDS);

				logger.logDebug("initialize()", LogMessages.LOG_SCHED_NOTIF_START, new Object[] { 5, "seconds" });

			} catch (Exception e) {
				logger.logException("initialize()", e);
			}

		} else {
			logger.logWarn("initialize()", LogMessages.LOG_SCHED_NOTIF_DISABLED);
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
				logger.logEnter("run()");

				List<Model> notifItems = db.getNotifiableItems(3, em);
				if (!notifItems.isEmpty()) {
					// Please document, that all platforms will be used for notification if user
					// configured all

					UserModel u = null;
					for (Model m : notifItems) {
						u = m.getUser();
						// Pushover
						if (u.getPushoverUserKey() != null && !u.getPushoverUserKey().isEmpty() && u.getPushoverAPIKey() != null
								&& !u.getPushoverAPIKey().isEmpty()) {
							pm.sendPush(u.getPushoverUserKey(), u.getPushoverAPIKey(), "Dear burgatshow, the following item will expire in 2 days.",
									0);
						}

						// Pushbullet
						if (u.getPushbulletAPIKey() != null && !u.getPushbulletAPIKey().isEmpty() && u.getEmail() != null
								&& !u.getEmail().isEmpty()) {
							pm.sendPush(u.getEmail(), u.getPushbulletAPIKey(), "Dear burgatshow, the following item will expire in 2 days.", 1);
						}
					}
				} else {
					logger.logDebug("run()", LogMessages.NO_USERS_TO_NOTIFY);
				}

				logger.logExit("run()");
			}
		};

	};

	public void cancelTask() {
		logger.logEnter("cancelTask()");

		if (ENABLE_SCHEDULER) {
			if (!scheduledFuture.isCancelled()) {
				scheduledFuture.cancel(false);
				logger.logDebug("cancelTask()", LogMessages.LOG_SCHED_NOTIF_STOP);
			}
		}

		logger.logExit("cancelTask()");
	}
}