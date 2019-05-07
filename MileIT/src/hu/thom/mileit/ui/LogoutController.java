package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.models.UserModel;

/**
 * Servlet class to manage user logout
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/logout")
public class LogoutController extends Controller {
	private static final long serialVersionUID = -1161650807822947664L;

	/**
	 * Constructor
	 */
	public LogoutController() {
		super();
	}

	/**
	 * Method to manage HTTP GET method.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		UserModel user = (UserModel) request.getSession().getAttribute("user");
		if (user != null) {
			// Clean up cache
			dc.cleanupUser(user.getUsername());

			// Destroy user's session then log out
			request.getSession().invalidate();
			request.logout();
		}
		response.sendRedirect("login");
	}

	/**
	 * Method to manage HTTP POST method.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		doGet(request, response);
	}
}
