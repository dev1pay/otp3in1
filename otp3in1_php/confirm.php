

<form action="result_otp.php" method="GET">
<table width="100%" border="0" cellpadding="3" cellspacing="3">
	<tr>
    	<td colspan="2" align="center">
        	<h2>OTP CONFIRM</h2>
        </td>
        
    </tr>
    
    <tr><td width="35%" align="right">
        	OTP :
        </td>
        <td width="65%">
        	<input type="text" id="otp" name="otp" value=""/>
        </td>
    </tr>
     <tr>
    	<td align="right">
        	
        </td>
        <td>
        	<input type="submit" value="Submit" />
        </td>
    </tr>
</table>
<input type="hidden" name="requestId" value="<?php $requestId = isset($_GET["requestId"]) ? $_REQUEST["requestId"] : NULL; echo $requestId; ?>"/>

<input type="hidden" name="transId" value="<?php $transId = isset($_GET["transId"]) ? $_REQUEST["transId"] : NULL;echo $transId;?>"/>

<input type="hidden" name="access_key" value="<?php $access_key = isset($_GET["access_key"]) ? $_REQUEST["access_key"] : NULL;echo $access_key;?>"/>

<input type="hidden" name="amount" value="<?php $amount = isset($_GET["amount"]) ? $_REQUEST["amount"] : NULL;echo $amount;?>"/>
<input type="hidden" name="telcos" value="<?php $amount = isset($_GET["telcos"]) ? $_REQUEST["telcos"] : NULL;echo $telcos;?>"/>

</form>
<?php
//function POST request
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

 // gui du lieu den file result de xu ly
$data = "&access_key=".$access_key."&amount=".$amount."&requestId=".$requestId."&transId=".$transId."&otp=".$otp."&telcos=".$telcos;

$hearder = execPostRequest('./result_otp.php', $data);

?>


