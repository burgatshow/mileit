package hu.thom.mileit.ui.xss;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet filter to be able to avoid XSS attack
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class XSSFilter implements Filter, Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 3318304833803444808L;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		filterChain.doFilter(new XSSRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}
