package hu.thom.mileit.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

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

	private Gson gson = new Gson();

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);

		PrintWriter w = response.getWriter();

		@SuppressWarnings("unchecked")
		List<RefuelModel> fuelStats = (List<RefuelModel>) request.getSession().getAttribute("fuelStats");

		if (fuelStats != null && fuelStats.size() > 0) {
			response.setContentType("application/json");

			parseMode(request);

			List<Map<String, Object>> chartData = new ArrayList<Map<String, Object>>();
			Map<String, Object> item = null;

			switch (m) {
			case UIKeys.MODE_AJAX_FUELSTAT:
				for (RefuelModel rm : fuelStats) {
					item = new HashMap<String, Object>(1);
					item.put("date", df.format(rm.getRefuelDate()));
					item.put("unitPrice", rm.getUnitPrice());

					chartData.add(item);
				}

				break;

			case UIKeys.MODE_AJAX_AMOUNTPAID:
				for (RefuelModel rm : fuelStats) {
					item = new HashMap<String, Object>(1);
					item.put("date", df.format(rm.getRefuelDate()));
					item.put("paid", rm.getAmount());

					chartData.add(item);
				}
				break;

			default:

				break;
			}

			w.write(gson.toJson(chartData));

		} else {
			// FIXME bibi van
			response.setContentType("text/html");
		}

		w.flush();
		w.close();

	}
}