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

import hu.thom.mileit.core.DBManager;
import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.UserModel;

/**
 * Parent servlet class for child servlet classes
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1849843955087394555L;

	public static final String VERSION = "0.0.4 (beta)";
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
	public static final String MAINTENANCES = "/WEB-INF/pages/maintenances.jsp";
	public static final String MAINTENANCES_FORM = "/WEB-INF/pages/maintenances-form.jsp";
	public static final String TYRES = "/WEB-INF/pages/tyres.jsp";
	public static final String TYRES_FORM = "/WEB-INF/pages/tyres-form.jsp";
	public static final String TYRES_MAP = "/WEB-INF/pages/tyres-map.jsp";
	public static final String ROUTES = "/WEB-INF/pages/routes.jsp";
	public static final String ROUTES_FORM = "/WEB-INF/pages/routes-form.jsp";

	public DBManager dbm = null;

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
		if (dbm == null) {
			dbm = new DBManager();
		}

		assignedObjects.put(UIKeys.VERSION, VERSION);
		assignedObjects.put(UIKeys.PAGE, "index");

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

		if (request.getSession().getAttribute("user") == null) {
			if (request.getUserPrincipal() != null) {
				user = dbm.getUserProfile(new UserModel(request.getUserPrincipal().getName()));
				request.getSession().setAttribute("user", user);
			}
		} else {
			user = (UserModel) request.getSession().getAttribute("user");
		}

		assignedObjects.put(UIKeys.USER, user);
	}

	/**
	 * Method to manage HTTP POST method.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		if (request.getSession().getAttribute("user") == null) {
			if (request.getUserPrincipal() != null) {
				user = dbm.getUserProfile(new UserModel(request.getUserPrincipal().getName()));
				request.getSession().setAttribute("user", user);
			}
		} else {
			user = (UserModel) request.getSession().getAttribute("user");
		}

		assignedObjects.put(UIKeys.USER, user);
	}

	/**
	 * Parses the operation switcher received as a parameter
	 * 
	 * @param request {@link HttpServletRequest} the request where the parameter is
	 *                coming from
	 */
	public void parseMode(HttpServletRequest request) {
		if (request != null) {
			if (request.getParameter("m") != null && !"".equalsIgnoreCase(request.getParameter("m"))) {
				this.m = request.getParameter("m");
			} else {
				this.m = UIKeys.MODE_CANCEL;
			}
		} else {
			this.m = UIKeys.MODE_CANCEL;
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
		if (assignedObjects != null && assignedObjects.size() > 0) {
			for (Map.Entry<String, Object> entry : assignedObjects.entrySet()) {
				request.setAttribute(entry.getKey(), entry.getValue());
			}
		}

		if (!validationMessages.isEmpty()) {
			request.setAttribute(UIKeys.VALIDATION_MSGS, validationMessages);
		}
		request.getRequestDispatcher(targetJSP).forward(request, response);
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
				this.id = Integer.parseInt(request.getParameter("id"));
			} catch (Exception e) {
				this.id = -1;
			}
		} else {
			this.id = -1;
		}
	}

	public void checkValidationMessages(String[] mustElements, final Set<String> validationMessages, HttpServletRequest request) {
		if (mustElements != null && mustElements.length != 0 && request != null && validationMessages != null) {
			for (String key : mustElements) {
				if (request.getParameter(key) == null || "".equalsIgnoreCase(request.getParameter(key))) {
					validationMessages.add(key);
				} else {
					validationMessages.remove(key);
				}
			}
		}
	}
}
