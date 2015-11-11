package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ConfirmResult")
public class ConfirmResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String json = "";
	
    public ConfirmResult() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accKey = request.getParameter("access_key");
		String otp = request.getParameter("otp");
		String requestId = request.getParameter("requestId");
		String transId = request.getParameter("transId");
		String secKey = ""; //secret key do 1pay cung cap. Thay bang secret key cua ban
		try {
			sendPost(accKey, otp, requestId, transId, secKey);
			request.setAttribute("json", json);
			request.getRequestDispatcher("WEB-INF/jsp/ResultOtp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendPost(String access_key, String otp, String requestId, String transId, String key) throws Exception {
		String url = "http://api.1pay.vn/direct-charging/charge/confirm";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = "access_key="+access_key+"&otp="+otp+"&requestId="+requestId+"&transId="+transId;
		
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
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
		String inputLine;
		StringBuffer response  = new StringBuffer();
		while ((inputLine = in.readLine())!=null) {
			response.append(inputLine);
		}
		in.close();	
		json = response.toString();
		System.out.println(json);
	}
}
