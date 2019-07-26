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
package hu.thom.mileit.security;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import hu.thom.mileit.utils.LogManager;
import hu.thom.mileit.utils.LogMessages;

/**
 * 
 * @author tamasbures
 *
 */
public class CSRFLoader implements Filter, Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 6556184388145659914L;

	/**
	 * {@link LogManager} instance
	 */
	private static LogManager logger = new LogManager(CSRFLoader.class);

	private MessageDigest md = null;

	@Override
	public void destroy() {
		logger.logEnter("destroy()");
		logger.logExit("destroy()");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		logger.logEnter("doFilter()");

		HttpServletRequest req = (HttpServletRequest) servletRequest;

		@SuppressWarnings("unchecked")
		Map<String, Boolean> ct = (Map<String, Boolean>) req.getSession().getAttribute("ct");

		if (ct == null) {
			ct = new HashMap<String, Boolean>();
			req.getSession().setAttribute("ct", ct);
		}

		StringBuffer csrfToken = new StringBuffer();
		if (md != null) {
			md.update(UUID.randomUUID().toString().getBytes("UTF-8"));

			byte[] saltByteArray = md.digest();
			String hex = null;
			for (int i = 0; i < saltByteArray.length; i++) {
				hex = Integer.toHexString(0xFF & saltByteArray[i]);
				if (hex.length() == 1) {
					csrfToken.append("0");
				}
				csrfToken.append(hex);
			}
		} else {
			csrfToken.append(UUID.randomUUID());
		}

		ct.put(csrfToken.toString(), Boolean.TRUE);
		req.setAttribute("ct", csrfToken.toString());

		filterChain.doFilter(servletRequest, servletResponse);
		logger.logExit("doFilter()");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.logEnter("init()");

		if (md == null) {
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				logger.logError("init()", LogMessages.CSRF_NO_SUITABLE_CIPHER, "SHA-256");
				logger.logException("init()", e);
				try {
					md = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e2) {
					logger.logError("init()", LogMessages.CSRF_NO_SUITABLE_CIPHER, "MD5");
					logger.logException("init()", e2);
				}
			}
		}
		logger.logExit("init()");
	}

}
