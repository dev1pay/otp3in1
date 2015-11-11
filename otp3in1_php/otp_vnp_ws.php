<?php

$access_key = $_GET["access_key"];
$msisdn = $_GET['msisdn'];   
$amount = $_GET['amount'];    // thấp nhất là 1000 vnd.
$errorCode = $_GET['errorCode'];
$errorMessage = $_GET['errorMessage'];   
$requestId = $_GET['requestId'];
$requestTime = $_GET["request_time"];
$transId = $_GET["transId"];
$sign = $_GET["signature"];
$secret = ""; //secret key cung cap boi 1PAY  

$data = "access_key=$access_key&amount=$amount&errorCode=$errorCode&errorMessage=$errorMessage&msisdn=$msisdn&requestId=$requestId&request_time=$requestTime&transId=$transId";
$signature = hash_hmac("sha256", $data, $secret); 
if($sign == $signature){
	$arResponse['status'] = 1;
	$arResponse['sms'] = "Thanh toan thanh cong";
	$arResponse['type'] = "text";
}else{
	$arResponse['status'] = 0;
	$arResponse['sms'] = "Thanh toan that bai";
	$arResponse['type'] = "text";
}
//Merchant xu ly SQL 

echo json_encode($arResponse);
?>