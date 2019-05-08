package hu.thom.mileit.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import javax.net.ssl.HttpsURLConnection;

public class PushManager implements Serializable {
	private static final long serialVersionUID = -9146376235788834522L;

//	curl -s -F "token=aoibifuy8chx7qsc89arbupughtnu2" -F "user=ussx29sf79zytyye472rty8mjwu772" -F "title=Dell OptiPlex" -F "message=$1" https://api.pushover.net/1/messages.json > /dev/null
//	curl -s -X POST https://api.pushbullet.com/v2/pushes -H 'Access-Token: o.MvlB1BlB8avFXDEnOb8G8xQUW3324brs' -H 'Content-Type: application/json' --data-binary '{"email": "btotyi@gmail.com", "type": "note", "title": "Dell OptiPlex", "body": "'"$1"'"}' > /dev/null

	/**
	 * Constructor
	 */
	public PushManager() {
	}

	public boolean sendPushoverPost(String key, String payload) {
		boolean status = false;

		try {
			URL obj = new URL("https://api.pushover.net/1/messages.json");
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");

			Map<String, String> formAttributes = new HashMap<String, String>();
			formAttributes.put("token", "aoibifuy8chx7qsc89arbupughtnu2");
			formAttributes.put("user", "ussx29sf79zytyye472rty8mjwu772");
			formAttributes.put("title", "MileIT notification");
			formAttributes.put("message", payload);

			StringJoiner sj = new StringJoiner("&");
			for (Map.Entry<String, String> entry : formAttributes.entrySet())
				sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
			byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
			int length = out.length;

			// Send post request
			con.setDoOutput(true);
			con.setFixedLengthStreamingMode(length);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.connect();

			try (OutputStream os = con.getOutputStream()) {
				os.write(out);
			} catch (Exception e) {
				e.printStackTrace();
			}

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL.");
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} catch (Exception e) {
		}

		return status;
	}
}
