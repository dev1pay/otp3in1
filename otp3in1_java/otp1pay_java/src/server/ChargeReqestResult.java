package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import server.HmacSHA256;

@WebServlet("/ChargeReqestResult")
public class ChargeReqestResult extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String json;


	public ChargeReqestResult() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String errorMessage;
		String requests;
		String transId;
		String errorCode;	 
		String redirectUrl;
		
		String amount = request.getParameter("amount"); // minimum 1000 vnd
		String phone = request.getParameter("msisdn");
		String content = request.getParameter("content");
		String requestId = randomString();
		final String access_key = ""; //access key do 1pay cung cap. Thay bang access key cua ban
		final String secret_key = ""; //secret key do 1pay cung cap. Thay bang secret key cua ban

		try {			
			sendPost(access_key, amount, content, phone, requestId, secret_key);

			// decode json
			JSONObject jObj = new JSONObject(json);
			errorMessage = jObj.getString("errorMessage");
			requests = jObj.getString("requestId");
			transId = jObj.getString("transId");
			errorCode = jObj.getString("errorCode");
			if (jObj.has("redirectUrl")) {
				redirectUrl = jObj.getString("redirectUrl");
				response.sendRedirect(redirectUrl);
			}else {
				request.setAttribute("accKey", access_key);
				request.setAttribute("errorMessage", errorMessage);
				request.setAttribute("requestId", requests);
				request.setAttribute("transId", transId);
				request.setAttribute("errorCode", errorCode);
				response.setContentType("application/json;charset=UTF-8");
				request.getRequestDispatcher("WEB-INF/jsp/Confirm.jsp").forward(
						request, response);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//transaction id (created by merchant system)
	private String randomString() {
		final String RAND = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			builder.append(RAND.charAt(random.nextInt(RAND.length())));
		}
		return builder.toString();
	}

	private void sendPost(String access_key, String amount, String content,
			String msisdn, String requestId, String key) throws Exception {
		String url = "http://api.1pay.vn/direct-charging/charge/request";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = "access_key="+access_key+"&amount="+amount+"&content="+content+"&msisdn="+msisdn+"&requestId="+requestId;

		HmacSHA256 hmacSHA256 = HmacSHA256.getInstance(key);
		String signature=hmacSHA256.sign(urlParameters);
		System.out.println("Signature:" + signature);
		urlParameters = urlParameters+"&signature="+signature;
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("URL : " + url);
		System.out.println("Parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		json = response.toString();
		System.out.println(json);

	}
}
