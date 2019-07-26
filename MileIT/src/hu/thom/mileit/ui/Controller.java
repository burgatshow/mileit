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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.core.EncryptionManager;
import hu.thom.mileit.core.TOTPManager;
import hu.thom.mileit.core.data.DBManager;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

/**
 * Parent servlet class for child servlet classes
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class Controller extends HttpServlet {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1849843955087394555L;

	public static final String VERSION = "0.1.0";
	public static final String LOGIN = "/WEB-INF/pages/login.jsp";
	public static final String HOME = "/WEB-INF/pages/home.jsp";
	public static final String REGISTER = "/WEB-INF/pages/register.jsp";
	public static final String CARS = "/WEB-INF/pages/cars.jsp";
	public static final String CARS_FORM = "/WEB-INF/pages/cars-form.jsp";
	public static final String REFUELS = "/WEB-INF/pages/refuels.jsp";
	public static final String REFUELS_FORM = "/WEB-INF/pages/refuels-form.jsp";
	public static final String PAYMENT_METHODS = "/WEB-INF/pages/payment-methods.jsp";
	public static final String PAYMENT_METHODS_FORM = "/WEB-INF/pages/payment-methods-form.jsp";
	public static final String PLACES = "/WEB-INF/pages/places.jsp";
	public static final String PLACES_FORM = "/WEB-INF/pages/places-form.jsp";
	public static final String PROFILE_FORM = "/WEB-INF/pages/profile-form.jsp";
	public static final String PROFILE_2FA = "/WEB-INF/pages/profile-2fa.jsp";
	public static final String MAINTENANCES = "/WEB-INF/pages/maintenances.jsp";
	public static final String MAINTENANCES_FORM = "/WEB-INF/pages/maintenances-form.jsp";
	public static final String TYRES = "/WEB-INF/pages/tyres.jsp";
	public static final String TYRES_FORM = "/WEB-INF/pages/tyres-form.jsp";
	public static final String TYRES_MAP = "/WEB-INF/pages/tyres-map.jsp";
	public static final String ROUTES = "/WEB-INF/pages/routes.jsp";
	public static final String ROUTES_FORM = "/WEB-INF/pages/routes-form.jsp";

	public DBManager db = null;
	public DynaCacheManager dc = null;
	public EncryptionManager em = null;
	public TOTPManager totp = null;

	public String m = "";
	public int id = 0;

	public UserModel user;

	public DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public final Set<String> validationMessages = new HashSet<String>();
	public final Map<String, Object> assignedObjects = new HashMap<String, Object>();

	/**
	 * Constructor
	 */
	public Controller() {
		if (dc == null) {
			dc = DynaCacheManager.getInstance();
		}

		db = (DBManager) dc.get("db");
		em = (EncryptionManager) dc.get("em");
		totp = (TOTPManager) dc.get("totp");

		assignedObjects.put(UIBindings.VERSION, VERSION);
		assignedObjects.put(UIBindings.PAGE, "index");

		validationMessages.clear();
	}

	/**
	 * Method to manage HTTP GET method.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	}

	/**
	 * Method to manage HTTP POST method.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	}

	/**
	 * Parses the operation switcher received as a parameter
	 * 
	 * @param request {@link HttpServletRequest} the request where the parameter is
	 *                coming from
	 */
	public void parseMode(HttpServletRequest request) {
		if (request != null) {
			if (request.getParameter(UIBindings.PARAM_MODE) != null && !request.getParameter(UIBindings.PARAM_MODE).isEmpty()) {
				this.m = request.getParameter(UIBindings.PARAM_MODE);
			} else {
				this.m = UIBindings.MODE_CANCEL;
			}
		} else {
			this.m = UIBindings.MODE_CANCEL;
		}
	}

	/**
	 * Renders the given page for the response
	 * 
	 * @param targetJSP {@link String} the target JSP page defined as static fields
	 *                  in {@link Controller}
	 * @param request   {@link HttpServletRequest} the HTTP request
	 * @param response  {@link HttpServletResponse} the HTTP response
	 * @throws ServletException
	 * @throws IOException
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void renderPage(String targetJSP, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (assignedObjects != null && !assignedObjects.isEmpty()) {
			for (Map.Entry<String, Object> entry : assignedObjects.entrySet()) {
				request.setAttribute(entry.getKey(), entry.getValue());
			}
		}

		if (!validationMessages.isEmpty()) {
			request.setAttribute(UIBindings.VALIDATION_MSGS, validationMessages);
		}
		request.getRequestDispatcher(targetJSP).forward(request, response);
		return;
	}

	/**
	 * Parses the given entity's ID value received as a parameter
	 * 
	 * @param request {@link HttpServletRequest} the request where the ID is coming
	 *                from
	 */
	public void parseId(HttpServletRequest request) {
		if (request != null) {
			try {
				this.id = Integer.parseInt(request.getParameter(UIBindings.PARAM_ID));
			} catch (Exception e) {
				this.id = -1;
			}
		} else {
			this.id = -1;
		}
	}

	/**
	 * Checks the input parameters and put them to the validationMessages list if
	 * they failed based on the required elemenets
	 * 
	 * @param mustElements       a {@link String} array of parameters need to be
	 *                           validated
	 * @param validationMessages a {@link Set} of {@link String} where to the
	 *                           paremeter will be added if it fails during
	 *                           validation
	 * @param request            the {@link HttpServletRequest} where the parameters
	 *                           will come from
	 */
	public void checkValidationMessages(String[] mustElements, final Set<String> validationMessages, HttpServletRequest request) {
		if (mustElements != null && mustElements.length != 0 && request != null && validationMessages != null) {
			for (String key : mustElements) {
				if (request.getParameter(key) == null || request.getParameter(key).isEmpty()) {
					validationMessages.add(key);
				} else {
					validationMessages.remove(key);
				}
			}
		}
	}
}
