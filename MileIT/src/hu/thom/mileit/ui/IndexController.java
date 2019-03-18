package hu.thom.mileit.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.models.RefuelModel;

/**
 * Servlet class to manage homepage
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
@WebServlet("/index")
public class IndexController extends Controller {
	private static final long serialVersionUID = -2632090274800635855L;

	/**
	 * Constructor
	 */
	public IndexController() {
		super();
	}

	/**
	 * Method to manage HTTP GET method.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);

		request.setAttribute("page", "index");
		request.setAttribute("lr", dbm.getLastRefuel(user.getId()));

		List<RefuelModel> fuelStats = dbm.getFuelPriceStats(user.getId());
		StringBuffer fuelStatKeys = new StringBuffer();
		StringBuffer fuelStatValues = new StringBuffer();
		StringBuffer fuelPaidAmount = new StringBuffer();
		int i = 1;
		for (RefuelModel rm : fuelStats) {
			fuelStatKeys.append("'").append(df.format(rm.getRefuelTimestamp())).append("'");
			fuelStatValues.append(rm.getUnitPrice());
			fuelPaidAmount.append(rm.getAmount());

			if (i != fuelStats.size()) {
				fuelStatKeys.append(", ");
				fuelStatValues.append(", ");
				fuelPaidAmount.append(", ");
			}
			i++;
		}

		request.setAttribute("fuelStatsKey", fuelStatKeys);
		request.setAttribute("fuelStatsVal", fuelStatValues);
		request.setAttribute("fuelPaidAmount", fuelPaidAmount);

		request.getRequestDispatcher(HOME).forward(request, response);
	}

}
