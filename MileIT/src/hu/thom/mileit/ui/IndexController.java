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

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.models.RefuelModel;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

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
		assignedObjects.put(UIBindings.PAGE, UIBindings.INDEX);
		assignedObjects.put(UIBindings.LOAD_CHARTS, 1);
	}

	/**
	 * Method to manage HTTP GET method.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);

		user = (UserModel) request.getSession().getAttribute(UIBindings.USER);

		if (user == null) {
			response.sendRedirect(UIBindings.LOGIN);
		} else {
			assignedObjects.put(UIBindings.USER, user);
			String lastRefuelKey = user.getUsername() + "_" + UIBindings.LAST_REFUEL;

			RefuelModel userLastRefuel = (RefuelModel) dc.get(lastRefuelKey);
			if (userLastRefuel == null) {
				userLastRefuel = db.getLastRefuel(user.getId());
				dc.put(lastRefuelKey, userLastRefuel, DynaCacheManager.DC_TTL_1H, user.getUsername());
			}

			assignedObjects.put(UIBindings.LAST_REFUEL, dc.get(lastRefuelKey));
			assignedObjects.remove(UIBindings.STATUS);

			renderPage(HOME, request, response);
		}
	}

}
