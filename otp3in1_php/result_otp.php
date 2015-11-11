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
	// lấy dữ liệu truyền vào	
		
		$requestId = isset($_GET["requestId"]) ? $_REQUEST["requestId"] : NULL;
		$transId = isset($_GET["transId"]) ? $_REQUEST["transId"] : NULL;
		$otp = isset($_GET["otp"]) ? $_GET["otp"] : NULL;
		$access_key = isset($_GET["access_key"]) ? $_REQUEST["access_key"] : NULL;
	    $amount = isset($_GET["amount"]) ? $_REQUEST["amount"] : NULL;
	    $telcos = isset($_GET["telcos"]) ? $_REQUEST["telcos"] : NULL;
        $secret = "k22027o02b0bnzvca3sxex3fzbolsdw2";               // secret key được cấp bởi 1pay, thay bằng secret_key của bạn
		$data = "access_key=".$access_key."&otp=".$otp."&requestId=".$requestId."&transId=".$transId;
	
	$signature = hash_hmac("sha256", $data, $secret);
	$data.= "&signature=" . $signature;
    $json_bankCharging = execPostRequest('http://api.1pay.vn/direct-charging/charge/confirm', $data);
//decode json
	$decode_bankCharging=json_decode($json_bankCharging,true);
	$errorMessage = $decode_bankCharging["errorMessage"];
    $requestId_back = $decode_bankCharging["requestId"];
    $transId = $decode_bankCharging["transId"];
    $errorCode = $decode_bankCharging["errorCode"];
// Merchant xu ly sql tai day

?>

