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
 * Servlet class to manage user login events
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/login")
public class LoginController extends Controller {
	private static final long serialVersionUID = -6309136241125776104L;

	/**
	 * Constructor
	 */
	public LoginController() {
		super();
	}

	/**
	 * Init method for this servlet
	 * 
	 * @see HttpServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
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
		
		if (request.getSession().getAttribute("user") == null) {
			validationMessages.clear();
			renderPage(LOGIN, request, response);
		} else {
			response.sendRedirect("index");
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

		if (request.getSession().getAttribute("user") == null) {

			checkValidationMessages(UIBindings.FORM_LOGIN, validationMessages, request);

			if (validationMessages.isEmpty()) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				if (db.authenticateUser(username, em.encrypt(password))) {
					UserModel user = db.getUserProfile(username, em);
					request.getSession().setAttribute("user", user);
					response.sendRedirect("index");
				} else {
					assignedObjects.put(UIBindings.STATUS, -1);
					renderPage(LOGIN, request, response);
				}
			} else {
				assignedObjects.put(UIBindings.STATUS, -2);
				renderPage(LOGIN, request, response);
			}
		} else {
			response.sendRedirect("index");
		}
	}
}
