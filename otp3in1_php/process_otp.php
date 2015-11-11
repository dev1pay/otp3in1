<?php
	function execPostRequest($url, $data)
	{
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_POST, 1);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
		curl_setopt($ch, CURLOPT_USERAGENT, $_SERVER['HTTP_USER_AGENT']);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
		curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 2);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		$result = curl_exec($ch);
		curl_close($ch);
		return $result;
	}
	$access_key = "";           // access_key được cấp bởi 1pay, thay bằng access_key của bạn
	$secret = "";   // secret key được cấp bởi 1pay, thay bằng secret_key của bạn                                                                              // bạn thay bằng return_url của bạn
	$msisdn = $_POST['msisdn'];
	$amount = $_POST['amount'];				// >10000
	$content = $_POST['content'];			// Mô tả hóa đơn	
	$requestId = $_POST['requestId'];
	$telcos = $_POST['telcos'];                     
	if($telcos == 'vnp'){
		$data = "access_key=" .$access_key. "&amount=" .$amount. "&content=" .$content. "&msisdn=" .$msisdn. "&requestId=" .$requestId;
		$signature = hash_hmac("sha256", $data, $secret);
		$data.= "&signature=" . $signature; //"&backUrl=".$back_url.
		$json_Charging = execPostRequest('http://api.1pay.vn/direct-charging/charge/request', $data);

		$decode_Charging=json_decode($json_Charging,true);  // decode json
		$errorMessage = $decode_Charging["errorMessage"];
		$requestId_back = $decode_Charging["requestId"];
		$transId = $decode_Charging["transId"];
		$errorCode = $decode_Charging["errorCode"];
		$redirect_url = $decode_Charging["redirectUrl"]; // link ws khai bao tran trang san pham 1pay

 header("Location: $redirect_url");  //URL address implement submit request (redirect)
   
	}else{
		$data = "access_key=".$access_key."&amount=".$amount."&content=".$content."&msisdn=".$msisdn."&requestId=".$requestId;
		$signature = hash_hmac("sha256", $data, $secret);
		$data.= "&signature=" . $signature;
	    $json_bankCharging = execPostRequest('http://api.1pay.vn/direct-charging/charge/request', $data);
		$decode_bankCharging=json_decode($json_bankCharging,true);		// decode json
		$errorMessage = $decode_bankCharging["errorMessage"];
	    $requestId_back = $decode_bankCharging["requestId"];
	    $transId = $decode_bankCharging["transId"];
	    $errorCode = $decode_bankCharging["errorCode"];
		$url = "&access_key=".$access_key."&amount=".$amount."&requestId=".$requestId."&transId=".$transId."&telcos=".$telcos;
		$pay_url= "./confirm.php?".$url;
		//echo $pay_url;
		header("Location: $pay_url");
	}

?>

