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
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.thom.mileit.core.DynaCacheManager;
import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

@WebServlet(value = "/setup_2fa")
public class Configure2FAController extends Controller {
	private static final long serialVersionUID = 4292656186802533449L;

	/**
	 * Constructor
	 */
	public Configure2FAController() {
		super();
		assignedObjects.put(UIBindings.PAGE, "setup_2fa");
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

		assignedObjects.remove("status");

		user = (UserModel) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login");
		} else {
			if (user.getTotpEnabled() == 0) {
				if (dc.get(String.format(UIBindings.TOTP_SECRET_USER, user.getUsername())) == null) {
					dc.put(String.format(UIBindings.TOTP_SECRET_USER, user.getUsername()), totp.generateBase32Secret(), DynaCacheManager.DC_TTL_5M,
							user.getUsername());

					Calendar c = Calendar.getInstance();
					dc.put(String.format(UIBindings.TOTP_START_USER, user.getUsername()), c.getTime(), DynaCacheManager.DC_TTL_5M,
							user.getUsername());

					c.add(Calendar.SECOND, DynaCacheManager.DC_TTL_5M);
					dc.put(String.format(UIBindings.TOTP_END_USER, user.getUsername()), c.getTime(), DynaCacheManager.DC_TTL_5M, user.getUsername());

					renderPage(PROFILE_2FA, request, response);
				}
			} else {
				assignedObjects.put("status", -99);
				renderPage(PROFILE_FORM, request, response);
			}
		}
	}

	/**
	 * Method to manage HTTP POST method.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		user = (UserModel) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login");
		} else {
			String verificationCode = request.getParameter("totp_code");
			String secret = dc.getString(String.format(UIBindings.TOTP_SECRET_USER, user.getUsername()));
			try {
				Date current = Calendar.getInstance().getTime();
				Date start = dc.getDate(String.format(UIBindings.TOTP_START_USER, user.getUsername()));
				Date end = dc.getDate(String.format(UIBindings.TOTP_END_USER, user.getUsername()));

				if (start != null && end != null && current.after(start) && current.before(end)) {
					if (verificationCode.equals(totp.generateCurrentNumber(secret))) {
						user.setTotpSecret(secret);
						user.setTotpEnabled(1);

						int[] backupCodes = new int[6];
						Random r = new Random();
						IntStream.range(0, 6).forEach(n -> {
							backupCodes[n] = 100000 + r.nextInt(900000);
						});
						user.setTotpBackupCodes(backupCodes);
						if (db.updateUserProfileTOTP(user, em)) {
							for (int i = 0; i < user.getTotpBackupCodes().length; i++) {
								assignedObjects.put("backup_code_" + i, user.getTotpBackupCodes()[i]);
							}
							assignedObjects.put("status", 100);
							renderPage(PROFILE_FORM, request, response);
						} else {
							// DB update failed, stay on no TOTP
							System.err.println("Hupsz");
						}
					} else {
						// Provided code does not match
						assignedObjects.put("status", -2);
						renderPage(PROFILE_2FA, request, response);
					}
				} else {
					// Ran out of time during enrollment or invalid start or end date
					assignedObjects.put("status", -4);
					renderPage(PROFILE_2FA, request, response);
				}
			} catch (GeneralSecurityException e) {
				// An issue occured, notify user
				assignedObjects.put("status", -3);
				renderPage(PROFILE_2FA, request, response);
			}
		}
	}
}
