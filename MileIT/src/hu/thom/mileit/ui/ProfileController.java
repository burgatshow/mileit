package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

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
		assignedObjects.put(UIBindings.PAGE, "profile");
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

			user.setCurrency(request.getParameter("currency")).setLocale(request.getParameter("locale")).setDistance(request.getParameter("distance"))
					.setRounded(request.getParameter("rounded")).setEmail(request.getParameter("email"))
					.setPushbulletAPIKey(request.getParameter("pushbulletKey")).setPushoverUserKey(request.getParameter("pushoverUser"))
					.setPushoverAPIKey(request.getParameter("pushoverAPIKey")).setDateFormat(request.getParameter("dateformat"))
					.setTimeFormat(request.getParameter("datetimeformat"));

			if (request.getParameter("dateFormat") != null && !request.getParameter("dateFormat").isEmpty()) {
				for (String s : UIBindings.VALID_PATTERNS) {
					if (s.equals(request.getParameter("dateFormat"))) {
						user.setDateFormat(request.getParameter("dateFormat"));
						break;
					}
				}

				if (user.getDateFormat() == null) {
					user.setDateFormat("yyyy-MM-dd");
				}
			}

			if (request.getParameter("timeFormat") != null && !request.getParameter("timeFormat").isEmpty()) {
				for (String s : UIBindings.VALID_PATTERNS) {
					if (s.equals(request.getParameter("timeFormat"))) {
						user.setTimeFormat(request.getParameter("timeFormat"));
						break;
					}
				}

				if (user.getTimeFormat() == null) {
					user.setTimeFormat("HH:mm");
				}
			}

			assignedObjects.put(UIBindings.STATUS, db.updateUserProfile(user, em) ? 0 : -1);
			renderPage(PROFILE_FORM, request, response);
		}
	}
}