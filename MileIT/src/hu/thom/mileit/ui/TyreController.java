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
import hu.thom.mileit.models.TyreEventModel;
import hu.thom.mileit.models.TyreModel;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

/**
 * Servlet class to manage tyres related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/tyres")
public class TyreController extends Controller {
	private static final long serialVersionUID = 2631517143636421486L;

	private String userTyresKey = null;

	/**
	 * Constructor
	 */
	public TyreController() {
		super();
		assignedObjects.put(UIBindings.PAGE, UIBindings.TYRES);
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
		user = (UserModel) request.getSession().getAttribute(UIBindings.USER);
		if (user == null) {
			response.sendRedirect(UIBindings.LOGIN);
		} else {
			userTyresKey = user.getUsername() + "_" + UIBindings.TYRES;

			parseMode(request);

			if (dc.get(userTyresKey) == null) {
				dc.put(userTyresKey, db.getTyres(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
			}
			assignedObjects.put(UIBindings.TYRES, dc.get(userTyresKey));

			if (dc.get(UIBindings.TYRE_VENDORS) == null) {
				dc.put(UIBindings.TYRE_VENDORS, db.getTyreVendors(), DynaCacheManager.DC_TTL_FOREVER, "common");
			}
			assignedObjects.put(UIBindings.TYRE_VENDORS, dc.get(UIBindings.TYRE_VENDORS));

			if (dc.get(UIBindings.CAR_VENDORS) == null) {
				dc.put(UIBindings.CAR_VENDORS, db.getCarVendors(), DynaCacheManager.DC_TTL_FOREVER, "common");
			}
			assignedObjects.put(UIBindings.CAR_VENDORS, dc.get(UIBindings.CAR_VENDORS));

			TyreModel tm = null;

			switch (m) {
			case UIBindings.MODE_NEW:
				validationMessages.clear();
				assignedObjects.remove(UIBindings.TYRES);
				renderPage(TYRES_FORM, request, response);
				break;

			case UIBindings.MODE_ARCHIVE:
				parseId(request);

				assignedObjects.put(UIBindings.STATUS, db.archiveTyre(id) ? 1 : -1);
				assignedObjects.put(UIBindings.TYRES, db.getTyres(user.getId(), em));
				renderPage(TYRES, request, response);

				break;

			case UIBindings.MODE_UPDATE:
				parseId(request);

				tm = db.getTyre(id);
				if (tm != null) {
					assignedObjects.put(UIBindings.TYRES, tm);
					renderPage(TYRES_FORM, request, response);
				} else {
					assignedObjects.put(UIBindings.STATUS, -1);
					renderPage(TYRES_MAP, request, response);
				}
				break;
			case UIBindings.MODE_MAP:
				parseId(request);

				tm = db.getTyre(id);
				if (tm != null) {
					assignedObjects.put(UIBindings.CARS, db.getCars(user.getId(), em));
					assignedObjects.put(UIBindings.TYRES, tm);
					renderPage(TYRES_MAP, request, response);
				} else {
					assignedObjects.put(UIBindings.STATUS, -1);
					renderPage(TYRES, request, response);
				}

				break;
			case UIBindings.MODE_:
			case UIBindings.MODE_CANCEL:
			default:
				assignedObjects.remove(UIBindings.STATUS);
				renderPage(TYRES, request, response);
				break;
			}
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
		user = (UserModel) request.getSession().getAttribute(UIBindings.USER);
		if (user == null) {
			response.sendRedirect(UIBindings.LOGIN);
		} else {
			userTyresKey = user.getUsername() + "_" + UIBindings.TYRES;

			parseMode(request);

			if (m.equalsIgnoreCase(UIBindings.MODE_NEW) || m.equalsIgnoreCase(UIBindings.MODE_UPDATE)) {
				checkValidationMessages(UIBindings.FORM_ME_TYRE, validationMessages, request);
			}

			if (m.equalsIgnoreCase(UIBindings.MODE_MAP)) {
				checkValidationMessages(UIBindings.FORM_ME_TYRE_EVENT, validationMessages, request);
			}

			if (validationMessages.isEmpty()) {
				TyreModel tyre = null;
				TyreEventModel te = null;

				switch (m) {
				case UIBindings.MODE_NEW:
					tyre = new TyreModel(request.getParameterMap(), user);
					tyre.setOperation(0);
					break;
				case UIBindings.MODE_UPDATE:
					parseId(request);

					tyre = new TyreModel(request.getParameterMap(), user);

					tyre.setId(id);
					tyre.setOperation(1);
					break;

				case UIBindings.MODE_MAP:
					parseId(request);
					te = new TyreEventModel(request.getParameterMap(), user);
					te.setOperation(0);

					break;
				case UIBindings.MODE_:
				case UIBindings.MODE_CANCEL:
				default:
					break;
				}

				if (m.equalsIgnoreCase(UIBindings.MODE_NEW) || m.equalsIgnoreCase(UIBindings.MODE_UPDATE)) {
					assignedObjects.put(UIBindings.STATUS, db.createUpdateTyre(tyre) ? 1 : -1);
				}

				if (m.equalsIgnoreCase(UIBindings.MODE_MAP)) {
					assignedObjects.put(UIBindings.STATUS, db.createTyreEvent(te) ? 1 : -1);

				}

				dc.put(userTyresKey, db.getTyres(user.getId(), em), DynaCacheManager.DC_TTL_1H, user.getUsername());
				assignedObjects.put(UIBindings.TYRES, dc.get(userTyresKey));

				renderPage(TYRES, request, response);
			} else {
				assignedObjects.put(UIBindings.STATUS, -2);

				if (m.equalsIgnoreCase(UIBindings.MODE_NEW) || m.equalsIgnoreCase(UIBindings.MODE_UPDATE)) {
					renderPage(TYRES_FORM, request, response);
				}

				if (m.equalsIgnoreCase(UIBindings.MODE_MAP)) {
					renderPage(TYRES_MAP, request, response);
				}
			}
		}
	}
}
