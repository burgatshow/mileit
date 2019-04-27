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

import hu.thom.mileit.core.UIKeys;
import hu.thom.mileit.models.RefuelModel;

/**
 * Servlet class to manage car related operations
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/ajax")
public class AjaxController extends Controller {
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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);

		PrintWriter w = response.getWriter();

		//FIXME convert it to dynacache
		List<RefuelModel> fuelStats = (List<RefuelModel>) dbm.getFuelPriceStats(user.getId());

		if (fuelStats != null && fuelStats.size() > 0) {
			response.setContentType("application/json");

			parseMode(request);
			
			JSONArray chartData = new JSONArray();

			JSONObject item = null;

			switch (m) {
			case UIKeys.MODE_AJAX_FUELSTAT:
				for (RefuelModel rm : fuelStats) {
					item = new JSONObject();
					item.put("date", df.format(rm.getRefuelDate()));
					item.put("unitPrice", rm.getUnitPrice());

					chartData.add(item);
				}

				break;

			case UIKeys.MODE_AJAX_AMOUNTPAID:
				for (RefuelModel rm : fuelStats) {
					item = new JSONObject();
					item.put("date", df.format(rm.getRefuelDate()));
					item.put("paid", rm.getAmount());

					chartData.add(item);
				}
				break;

			default:

				break;
			}

			w.write(chartData.serialize());

		} else {
			// FIXME bibi van
			response.setContentType("text/html");
		}

		w.flush();
		w.close();

	}
}