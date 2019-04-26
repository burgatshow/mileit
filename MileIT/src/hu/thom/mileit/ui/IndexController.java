package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hu.thom.mileit.core.UIKeys;

/**
 * Servlet class to manage homepage
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/index")
public class IndexController extends Controller {
	private static final long serialVersionUID = -2632090274800635855L;

	/**
	 * Constructor
	 */
	public IndexController() {
		super();
		assignedObjects.put(UIKeys.PAGE, "index");
		assignedObjects.put(UIKeys.LOAD_CHARTS, 1);
	}

	/**
	 * Method to manage HTTP GET method.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);

		HttpSession session = request.getSession();
		if (session.getAttribute(UIKeys.FUEL_STATS) == null) {
			session.setAttribute(UIKeys.FUEL_STATS, dbm.getFuelPriceStats(user.getId()));
		}

		if (session.getAttribute(UIKeys.LAST_REFUEL) == null) {
			session.setAttribute(UIKeys.LAST_REFUEL, dbm.getLastRefuel(user.getId()));
		}

		assignedObjects.put(UIKeys.LAST_REFUEL, session.getAttribute(UIKeys.LAST_REFUEL));
		assignedObjects.remove(UIKeys.STATUS);

		renderPage(HOME, request, response);
	}

}
