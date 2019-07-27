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
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import hu.thom.mileit.models.UserModel;
import hu.thom.mileit.utils.UIBindings;

@WebServlet(value = "/qr_code")
public class QRCodeGenerator extends Controller {
	private static final long serialVersionUID = -2207503484323385610L;

	/**
	 * Constructor
	 */
	public QRCodeGenerator() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		user = (UserModel) request.getSession().getAttribute(UIBindings.USER);
		if (user == null) {
			response.sendRedirect("login");
		} else {
			String s = dc.getString(String.format(UIBindings.TOTP_SECRET_USER, user.getUsername()));
			if (s != null && !"".equalsIgnoreCase(s) && user.getTotpEnabled() == 0) {
				String qrContent = totp.enrollToken(user.getUsername(), s);

				try {
					OutputStream outStream = response.getOutputStream();
					QRCodeWriter qrCodeWriter = new QRCodeWriter();
					BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300);

					MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outStream);

					response.setContentType("image/png");

					outStream.flush();
					outStream.close();
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.err.println("AAAAAAUUUCH!!!");
			}
		}
	}
}
