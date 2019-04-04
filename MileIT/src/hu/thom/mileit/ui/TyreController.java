package hu.thom.mileit.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.TyreModel;

/**
 * Servlet class to manage tyres related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/tyres")
public class TyreController extends Controller {
	private static final long serialVersionUID = 2631517143636421486L;

	/**
	 * Constructor
	 */
	public TyreController() {
		super();
		assignedObjects.put(UIKeys.PAGE, "tyres");
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

		parseMode(request);

		assignedObjects.put(UIKeys.TYRES, dbm.getTyres(user.getId()));
		assignedObjects.put(UIKeys.TYRE_VENDORS, dbm.getTyreVendors());

		switch (m) {
		case UIKeys.MODE_NEW:
			assignedObjects.remove(UIKeys.TYRES);
			renderPage(TYRES_FORM, request, response);
			break;

		case UIKeys.MODE_UPDATE:
			parseId(request);

			TyreModel tm = dbm.getTyre(id);
			if (tm != null) {
				assignedObjects.put(UIKeys.TYRES, tm);
				renderPage(TYRES_FORM, request, response);
			} else {
				assignedObjects.put(UIKeys.STATUS, -1);
				renderPage(TYRES, request, response);
			}
			break;
		case UIKeys.MODE_:
		case UIKeys.MODE_CANCEL:
		default:
			renderPage(TYRES, request, response);
			break;
		}
	}

	/**
	 * Method to manage HTTP POST method.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);

		parseMode(request);
		
		String[] mustElements = { "manufacturer", "model", "width", "height", "radius", "axis", "type" };
		for (String key : mustElements) {
			if (request.getParameter(key) == null || "".equalsIgnoreCase(request.getParameter(key))) {
				validationMessages.add(key);
			} else {
				validationMessages.remove(key);
			}
		}

		if (validationMessages.isEmpty()) {
			TyreModel tyre = new TyreModel(request.getParameterMap(), user);

			switch (m) {
			case UIKeys.MODE_NEW:
				tyre.setOperation(0);
				break;
			case UIKeys.MODE_UPDATE:
				parseId(request);
				tyre.setId(id);
				tyre.setOperation(1);
				break;

			case UIKeys.MODE_:
			case UIKeys.MODE_CANCEL:
			default:
				break;
			}

			assignedObjects.put(UIKeys.STATUS, dbm.createUpdateTyre(tyre) ? 1 : -1);
			assignedObjects.put(UIKeys.TYRES, dbm.getTyres(user.getId()));
			renderPage(TYRES, request, response);
		} else {
			assignedObjects.put(UIKeys.STATUS, -2);
			renderPage(TYRES_FORM, request, response);
		}
	}
}
