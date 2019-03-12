package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class ProfileController extends Controller {
	private static final long serialVersionUID = -2632090274800635855L;

	public ProfileController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);

		request.setAttribute("page", "profile");
		request.getRequestDispatcher(PROFILE_FORM).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doPost(request, response);
		request.setAttribute("page", "profile");

		parseMode(request);

		user.setCurrency(request.getParameter("currency"));
		user.setLocale(request.getParameter("locale"));

		if (dbm.updateUserProfile(user)) {
			request.setAttribute("status", 0);
		} else {
			request.setAttribute("status", -1);
		}

		request.getRequestDispatcher(PROFILE_FORM).forward(request, response);
	}
}
