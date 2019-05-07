package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.DynaCacheAdaptor;
import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.RefuelModel;
import hu.thom.mileit.models.UserModel;

/**
 * Servlet class to manage homepage
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet(value = { "/index" })
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

		user = (UserModel) request.getSession().getAttribute("user");

		if (user == null) {
			response.sendRedirect("login");
		} else {
			assignedObjects.put(UIKeys.USER, user);
			String lastRefuelKey = user.getUsername() + "_" + UIKeys.LAST_REFUEL;

			RefuelModel userLastRefuel = (RefuelModel) dc.get(lastRefuelKey);
			if (userLastRefuel == null) {
				userLastRefuel = dbm.getLastRefuel(user.getId());
				dc.put(lastRefuelKey, userLastRefuel, DynaCacheAdaptor.DC_TTL_1H, user.getUsername());
			}

			assignedObjects.put(UIKeys.LAST_REFUEL, dc.get(lastRefuelKey));
			assignedObjects.remove(UIKeys.STATUS);

			renderPage(HOME, request, response);
		}
	}

}
