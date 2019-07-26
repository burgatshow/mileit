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
