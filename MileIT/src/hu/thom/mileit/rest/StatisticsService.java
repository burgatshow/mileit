package hu.thom.mileit.rest;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

import hu.thom.mileit.core.DBManager;
import hu.thom.mileit.models.RefuelModel;

@Path(value = "/v1/statistics")
@Produces(value = "application/json")
public class StatisticsService implements Serializable {
	private static final long serialVersionUID = -70619809156784834L;

	private Gson gson = null;
	private DBManager dbm = null;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public StatisticsService() {
		if (gson == null) {
			gson = new Gson();
		}

		if (dbm == null) {
			dbm = new DBManager();
		}
	}

	@GET
	@Path(value = "/fuel/pricechanges/{userid}")
	public String getChartDataFuelPriceChanges(@PathParam(value = "userid") int user_id) {
		// FIXME check user id
		List<Map<String, Object>> chartData = new ArrayList<Map<String, Object>>();

		// FIXME check data
		List<RefuelModel> fuelStats = dbm.getFuelPriceStats(user_id);

		Map<String, Object> item = null;
		for (RefuelModel rm : fuelStats) {
			item = new HashMap<String, Object>(1);
			item.put("date", df.format(rm.getRefuelDate()));
			item.put("unitPrice", rm.getUnitPrice());

			chartData.add(item);
		}

		return gson.toJson(chartData);
	}
	
	@GET
	@Path(value = "/fuel/amountpaid/{userid}")
	public String getChartDataAmountPaid(@PathParam(value = "userid") int user_id) {
		// FIXME check user id
		List<Map<String, Object>> chartData = new ArrayList<Map<String, Object>>();

		// FIXME check data
		List<RefuelModel> fuelStats = dbm.getFuelPriceStats(user_id);

		Map<String, Object> item = null;
		for (RefuelModel rm : fuelStats) {
			item = new HashMap<String, Object>(1);
			item.put("date", df.format(rm.getRefuelDate()));
			item.put("paid", rm.getAmount());

			chartData.add(item);
		}

		return gson.toJson(chartData);
	}
}
