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
package hu.thom.mileit.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.cache.EntryInfo;

import hu.thom.mileit.utils.LogManager;
import hu.thom.mileit.utils.LogMessages;

/**
 * Contains all the methods operating with Dynamic cache service
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class DynaCacheManager implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 7151480629489552539L;

	/**
	 * Default Dynamic Cache timeout values
	 */
	public static final int DC_TTL_FOREVER = 0;
	public static final int DC_TTL_1D = 3600 * 24;
	public static final int DC_TTL_1W = 3600 * 24 * 7;
	public static final int DC_TTL_1H = 3600;
	public static final int DC_TTL_30M = 1800;
	public static final int DC_TTL_5M = 300;

	/**
	 * Instance
	 */
	private static DynaCacheManager dc;

	/**
	 * {@link DistributedMap} recommended implementation
	 */
	private static DistributedMap cacheInstance;

	/**
	 * Logger instance
	 */
	private static LogManager logger = new LogManager(DynaCacheManager.class);

	/**
	 * Constructor
	 * 
	 * @return {@link DynaCacheManager}
	 */
	public static DynaCacheManager getInstance() {
		if (dc == null) {
			dc = new DynaCacheManager();
		}
		return dc;
	}

	/**
	 * Private constructor
	 */
	private DynaCacheManager() {
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
			logger.logDebug("get()", LogMessages.LOG_DC_I_RETRIEVE, objectKey);
		}
		logger.logExit("get()");
		return cacheObject;
	}

	public Date getDate(String objectKey) {
		return (Date) get(objectKey);
	}
	
	public String getString(String objectKey) {
		return (String) get(objectKey);
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
			logger.logDebug("put()", LogMessages.LOG_DC_I_ADD, new Object[] { cacheKey, timeout, dependencyID });
		}
		logger.logExit("put()");
	}

	/**
	 * Clears the cache store if it is already exists
	 */
	public void clear() {
		logger.logEnter("clear()");
		if (cacheInstance != null) {
			logger.logDebug("clear()", LogMessages.LOG_DC_I_CLEAR);
			cacheInstance.clear();
		}
		logger.logExit("clear()");
	}

	/**
	 * Removes all cached entries belongs to the given user
	 * 
	 * @param key {@link String} Invalidates the object in cache
	 */
	public void invalidate(String key) {
		logger.logEnter("invalidate()");
		if (key != null && !key.isEmpty()) {
			cacheInstance.invalidate(key);
			logger.logDebug("invalidate()", LogMessages.DC_CLEAR);
		}
		logger.logExit("invalidate()");
	}

	public static DistributedMap getCacheInstance() {
		return cacheInstance;
	}

}