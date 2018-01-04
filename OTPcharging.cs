﻿using System;
using System.IO;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using _1Pay;

namespace OTPCharging
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            String access_key = "";
            String secret_key = "";
            String amount = "";
            String content = "";
            String requestId = "";
            String msisdn = "";
            result = sendPost(access_key, secret_key, amount, content, msisdn, requestId);
        }

        public String sendPost(String access_key, String secret_key, String amount, String content, String requestId, String msisdn)
        {
            String result = "";
            String url = "http://api.1pay.vn/direct-charging/charge/request";
            My1Pay my1Pay = new My1Pay();
            String signature = my1Pay.generateSignature_ApiOtp_RequestCharging(access_key, amount, content, requestId, msisdn, secret_key); //create signature
            String urlParameter = String.Format("access_key={0}&amount={1}&content={2}&requestId={3}&signature={4}&msisdn={5}", access_key, amount, content, requestId, signature, msisdn);
            try
            {
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
                request.KeepAlive = false;
                request.ProtocolVersion = HttpVersion.Version10;
                request.Method = "POST";
                request.ContentType = "application/x-www-form-urlencoded";
                request.UserAgent = "Mozilla/5.0";
                WebHeaderCollection headerReader = request.Headers;
                headerReader.Add("Accept-Language", "en-US,en;q=0.5");
                var data = Encoding.ASCII.GetBytes(urlParameter);
                request.ContentLength = data.Length;
                Stream requestStream = request.GetRequestStream();
                // send url param
                requestStream.Write(data, 0, data.Length);
                requestStream.Close();
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                result = new StreamReader(response.GetResponseStream()).ReadToEnd();
                response.Close();
            }
            catch (Exception e)
            {
                result = e.GetBaseException().ToString();
            }
            return result;
        }
    }
}