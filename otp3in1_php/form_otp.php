<?php

require_once('function.php');
?>

<form method="post" action="1pay/process_otp.php" id="form">
<input type="hidden" name="requestId" value="<?php echo $transRef;?>"/>



<table width="100%" border="0" cellpadding="3" cellspacing="3">
	<tr>
    	<td colspan="2" align="center">
        	<h2>OTP CHARGING</h2>
        </td>
        
    </tr>
    <tr>
        <td align="right" width="40%">
            Chọn nhà mạng:
        </td>
        <td>
            <select id="telcos" name="telcos">
                    <option value="vtm">Viettel</option>
                    <option value="vms">MobiFone</option>
                    <option value="vnp">VinaPhone</option>
        
                    
                </select>
        </td>
    </tr>
    <tr>
    	<td align="right" width="40%">
        	Chọn giá :
        </td>
        <td>
        	<select id="amount" name="amount">
                    <option value="1000">1000</option>
                    <option value="5000">5000</option>
                    <option value="10000">10000</option>
                    <option value="20000">20000</option>
                    <option value="30000">30000</option>
                    <option value="50000">50000</option>
                    <option value="100000">100000</option>
                    
                </select>
        </td>
    </tr>
    <tr>
    	
    	<td align="right">
        	Số điện thoại :
        </td>
        <td>
        	<input type="text" id="msisdn" name="msisdn" />
        </td>
    </tr>
    <tr><td align="right">
        	Nội dung :
        </td>
        <td>
        	<input type="text" id="content" name="content" />
        </td>
    </tr>
     <tr>
    	<td align="right">
        	
        </td>
        <td>
        	<input type="submit" value="Nạp" />
        </td>
    </tr>
</table>
</form>

</body>
</html>




