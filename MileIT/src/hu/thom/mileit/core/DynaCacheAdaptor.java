package hu.thom.mileit.core;

import java.io.Serializable;
import java.util.logging.Level;

import javax.naming.InitialContext;

import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.cache.EntryInfo;

/**
 * Contains all the methods operating with Dynamic cache service
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class DynaCacheAdaptor implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 7151480629489552539L;

	/**
	 * Canonical name of this class used in loggers
	 */
	private final String CLAZZ = DynaCacheAdaptor.class.getCanonicalName();

	/**
	 * Default Dyna Cache timeout values
	 */
	public static final int DC_TTL_FOREVER = 0;
	public static final int DC_TTL_1D = 3600 * 24;
	public static final int DC_TTL_1W = 3600 * 24 * 7;
	public static final int DC_TTL_1H = 3600;
	public static final int DC_TTL_30M = 1800;

	/**
	 * Instance
	 */
	private static DynaCacheAdaptor dc;

	/**
	 * {@link DistributedMap} recommended implementation
	 */
	private static DistributedMap cacheInstance;

	/**
	 * Logger instance
	 */
	private static LoggerHelper logger = new LoggerHelper(DynaCacheAdaptor.class);

	/**
	 * Constructor
	 * 
	 * @return {@link DynaCacheAdaptor}
	 */
	public static DynaCacheAdaptor getInstance() {
		if (dc == null) {
			dc = new DynaCacheAdaptor();
		}
		return dc;
	}

	/**
	 * Private constructor
	 */
	private DynaCacheAdaptor() {
		logger.logEnter("DynaCacheAdaptor()");
		if (cacheInstance == null) {
			try {
				cacheInstance = (DistributedMap) new InitialContext().lookup("cache/mileit");
			} catch (Exception e) {
				logger.logException("DynaCacheAdaptor()", e);
			}
		}

		logger.logExit("DynaCacheAdaptor()");
	}

	/**
	 * Gets an object from the DynaCache
	 * 
	 * @param objectKey {@link String} the key of the object it put in
	 * @return {@link Object} the object from the cache (it always object to make it
	 *         general), null otherwsie
	 */
	public Object get(String objectKey) {
		logger.logEnter("get()");
		Object cacheObject = null;
		if (objectKey != null && !objectKey.isEmpty()) {
			cacheObject = cacheInstance.get(objectKey);
			logger.getLogger().logp(Level.FINE, CLAZZ, "get()", LoggerHelper.LOG_DC_I_RETRIEVE, objectKey);
		}
		logger.logExit("get()");
		return cacheObject;
	}

	/**
	 * Puts an object to DynaCache using a key
	 * 
	 * @param cacheKey   {@link String} the key for the cache entry
	 * @param cacheValue {@link Object} the value to cache
	 * @param timeout    int default timeout before the cache manager invalidates
	 *                   the entry. Negative value means default 1800 seconds, 0
	 *                   means keep forever, positive value the number of seconds
	 *                   the object needs to remain in the cache
	 * 
	 */
	public void put(String cacheKey, Object cacheValue, int timeout, String dependencyID) {
		logger.logEnter("put()");
		if (cacheKey != null && !cacheKey.isEmpty() && cacheValue != null) {
			if (timeout < 0) {
				timeout = 1800;
			}

			if (dependencyID == null || dependencyID.isEmpty()) {
				dependencyID = "";
			}

			cacheInstance.put(cacheKey, cacheValue, 1, timeout, EntryInfo.NOT_SHARED, new String[] { dependencyID });
			logger.getLogger().logp(Level.FINE, CLAZZ, "put()", LoggerHelper.LOG_DC_I_ADD, new Object[] { cacheKey, timeout, dependencyID });
		}
		logger.logExit("put()");
	}

	/**
	 * Clears the cache store if it is already exists
	 */
	public void clear() {
		logger.logEnter("clear()");
		if (cacheInstance != null) {
			logger.getLogger().fine(LoggerHelper.LOG_DC_I_CLEAR);
			cacheInstance.clear();
		}
		logger.logExit("clear()");
	}

	/**
	 * Removes all cached entries belongs to the given user
	 * 
	 * @param username {@link String} the username to whom entries need to be
	 *                 removed (based on dependency id) from cache
	 */
	public void cleanupUser(String username) {
		if (username != null && !username.isEmpty()) {
			cacheInstance.invalidate(username);
		}
	}

	public static DistributedMap getCacheInstance() {
		return cacheInstance;
	}

}