<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm</title>
</head>
<body>
	<form action="ConfirmResult" method="POST">
<table width="100%" border="0" cellpadding="3" cellspacing="3">
	<tr>
    	<td colspan="2" align="center">
        	<h2>OTP CONFIRM</h2>
        </td>
        
    </tr>
    
    <tr><td width="35%" align="right">
        	Mã OTP :
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
		<input type="hidden" name="access_key" value="${accKey}"/>
		<input type="hidden" name="requestId" value="${requestId}" /> 
		<input type="hidden" name="transId" value="${transId}"/>

</form>
</body>
</html>