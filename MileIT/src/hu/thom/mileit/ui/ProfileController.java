package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.UserModel;

/**
 * Servlet class to manage user profile
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/profile")
public class ProfileController extends Controller {
	private static final long serialVersionUID = -2632090274800635855L;

	/**
	 * Constructor
	 */
	public ProfileController() {
		super();
		assignedObjects.put(UIKeys.PAGE, "profile");
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
		user = (UserModel) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login");
		} else {
			renderPage(PROFILE_FORM, request, response);
		}
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
		user = (UserModel) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login");
		} else {
			parseMode(request);

			user.setCurrency(request.getParameter("currency"));
			user.setLocale(request.getParameter("locale"));
			user.setDistance(request.getParameter("distance"));
			user.setRounded(request.getParameter("rounded"));

			assignedObjects.put(UIKeys.STATUS, dbm.updateUserProfile(user) ? 0 : -1);
			renderPage(PROFILE_FORM, request, response);
		}
	}
}