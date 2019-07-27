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
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.models.RefuelModel;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

/**
 * Servlet class to manage car related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/ajax")
public class AjaxController extends Controller {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -1790582648465801733L;

	/**
	 * Constructor
	 */
	public AjaxController() {
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
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);

		user = (UserModel) request.getSession().getAttribute(UIBindings.USER);

		if (user == null) {
			response.sendRedirect(UIBindings.LOGIN);
		} else {
			PrintWriter w = response.getWriter();

			String userKey = user.getUsername() + "_" + UIBindings.FUEL_STATS;

			List<RefuelModel> fuelStats = null;
			if (dc.get(userKey) == null) {
				fuelStats = (List<RefuelModel>) db.getFuelPriceStats(user.getId());
				dc.put(userKey, fuelStats, DynaCacheManager.DC_TTL_1H, user.getUsername());
			} else {
				fuelStats = (List<RefuelModel>) dc.get(userKey);
			}

			if (fuelStats != null && fuelStats.size() > 0) {
				response.setContentType(UIBindings.MEDIATYPE_JSON);

				parseMode(request);

				JSONArray chartData = new JSONArray();

				JSONObject item = null;

				switch (m) {
				case UIBindings.MODE_AJAX_FUELSTAT:
					for (RefuelModel rm : fuelStats) {
						item = new JSONObject();
						item.put(UIBindings.DATE, df.format(rm.getRefuelDate()));
						item.put(UIBindings.UNIT_PRICE, rm.getUnitPrice());

						chartData.add(item);
					}

					break;

				case UIBindings.MODE_AJAX_AMOUNTPAID:
					for (RefuelModel rm : fuelStats) {
						item = new JSONObject();
						item.put(UIBindings.DATE, df.format(rm.getRefuelDate()));
						item.put(UIBindings.PAID, rm.getAmount());

						chartData.add(item);
					}
					break;

				default:

					break;
				}

				w.write(chartData.serialize());

			} else {
				// FIXME bibi van
				response.setContentType(UIBindings.MEDIATYPE_HTML);
			}

			w.flush();
			w.close();
		}
	}
}