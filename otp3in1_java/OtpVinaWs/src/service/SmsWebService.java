package service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/")
public class SmsWebService {
	
	@GET
	@Path("/ServiceOtp")
	@Produces({MediaType.APPLICATION_JSON})
	public Response charging(
			@DefaultValue("0") @QueryParam("access_key") String accKey,
			@DefaultValue("0") @QueryParam("amount") String amount,
			@QueryParam("errorCode") String errorCode,
			@QueryParam("errorMessage") String errorMessage,
			@QueryParam("msisdn") String msisdn,
			@QueryParam("requestId") String requestId,
			@QueryParam("request_time") String request_time,
			@QueryParam("transId") String transId,
			@QueryParam("signature") String signature) {
		JSONObject json = new JSONObject();
		try {
			String secret = ""; // Secret Key do 1pay cung cap. Thay bang Secret Key cua ban.
			String signatureGen = generateSignature(accKey, amount, errorCode, errorMessage, msisdn, requestId, request_time, transId, secret);
			if (signature.equalsIgnoreCase(signatureGen)) {				
				json.put("status", 1);
				json.put("sms", "Thanh toan thanh cong");
			}else {
				json.put("status", 0);
				json.put("sms", "Thanh toan that bai");
			}
			json.put("type", "text");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).entity(json.toString()).build();
	}
	
	public static String hmacDigest(String msg, String keyString, String algo) {
		String digest = "";
		try {
			if (keyString != null && keyString.length() > 0) {
				SecretKeySpec key = new SecretKeySpec(
						(keyString).getBytes("UTF-8"), algo);
				Mac mac = Mac.getInstance(algo);
				mac.init(key);
				byte[] bytes = mac.doFinal(msg.getBytes("UTF-8"));
				StringBuffer hash = new StringBuffer();
				for (int i = 0; i < bytes.length; i++) {
					String hex = Integer.toHexString(0xFF & bytes[i]);
					if (hex.length() == 1) {
						hash.append('0');
					}
					hash.append(hex);
				}
				digest = hash.toString();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return digest;
	}

	public String generateSignature(String access_key, String amount,
			String errorCode, String errorMessage, String msisdn,
			String requestId, String request_time,String transId, String secret) {
		String urlParameters = "";
		String signature = "";
		if (access_key != null && amount != null && errorCode != null
				&& errorMessage != null && msisdn != null && requestId != null
				&& request_time != null && transId != null && secret != null) {
			urlParameters = "access_key="+access_key+"&amount="+amount+"&errorCode="+errorCode
					+ "&errorMessage="+errorMessage+"&msisdn="+msisdn+"&requestId="+requestId+"&request_time="+request_time+"&transId="+transId;			

			signature = hmacDigest(urlParameters, secret, "HmacSHA256");
			System.out.println("Signature:" + signature);
		}
		return signature;
	}

}
