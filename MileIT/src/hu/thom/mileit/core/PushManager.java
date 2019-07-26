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
package hu.thom.mileit.core;

import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import javax.net.ssl.HttpsURLConnection;

import com.ibm.json.java.JSONObject;

import hu.thom.mileit.utils.LogManager;
import hu.thom.mileit.utils.LogMessages;

/**
 * Class to implement notification providers
 * 
 * @author thom <tamas.bures@protonmail.com>
 *
 */
public class PushManager implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -9146376235788834522L;

	/**
	 * Logger instance
	 */
	private LogManager logger = new LogManager(PushManager.class);

	/**
	 * Instance
	 */
	private static PushManager pm;

	/**
	 * Constructor
	 * 
	 * @return {@link PushManager}
	 */
	public static PushManager getInstance() {
		if (pm == null) {
			pm = new PushManager();
		}
		return pm;
	}

	/**
	 * Constructor
	 */
	private PushManager() {
		logger.logEnter("PushManager()");
		logger.logExit("PushManager()");
	}

	public boolean sendPush(String user, String token, String payload, int type) {
		logger.logEnter("sendPush()");
		boolean status = false;

		if (user != null && !user.isEmpty() && token != null && !token.isEmpty() && payload != null && !payload.isEmpty()) {
			try {
				URL endpointURL = null;
				String postPayload = null;
				String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
				if (type == 0) {
					// Pushover
					endpointURL = new URL("https://api.pushover.net/1/messages.json");

					Map<String, String> formAttributes = new HashMap<String, String>();
					formAttributes.put("user", user);
					formAttributes.put("token", token);
					formAttributes.put("title", "MileIT notification");
					formAttributes.put("message", payload);

					StringJoiner sj = new StringJoiner("&");
					for (Map.Entry<String, String> entry : formAttributes.entrySet()) {
						sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
					}
					postPayload = sj.toString();
				} else {
					// Pushbullet
					endpointURL = new URL("https://api.pushbullet.com/v2/pushes");

					JSONObject pushbulletData = new JSONObject();
					pushbulletData.put("email", user);
					pushbulletData.put("type", "note");
					pushbulletData.put("title", "MileIT notification");
					pushbulletData.put("body", payload);
					postPayload = pushbulletData.serialize();
					contentType = "application/json; charset=UTF-8";
				}

				HttpsURLConnection con = (HttpsURLConnection) endpointURL.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", contentType);
				if (type == 1) {
					con.setRequestProperty("Access-Token", token);
				}

				byte[] out = postPayload.getBytes(StandardCharsets.UTF_8);
				con.setFixedLengthStreamingMode(out.length);

				con.connect();

				try (OutputStream os = con.getOutputStream()) {
					os.write(out);
				} catch (Exception e) {
					logger.logException("sendPush()", e);
				}

				int rc = con.getResponseCode();
				switch (rc) {
				case 400:
					break;
				default:
					// 200
					status = true;
					break;
				}
				con.disconnect();
			} catch (Exception e) {
				logger.logException("sendPush()", e);
			}
		} else {
			logger.logError("sendPush()", LogMessages.INVALID_INPUT);
		}

		logger.logExit("sendPush()");
		return status;
	}
}
