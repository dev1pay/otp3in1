<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="POST" action="ChargeReqestResult" id="form">



<table width="100%" border="0" cellpadding="3" cellspacing="3">
	<tr>
    	<td colspan="2" align="center">
        	<h2>OTP CHARGING</h2>
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