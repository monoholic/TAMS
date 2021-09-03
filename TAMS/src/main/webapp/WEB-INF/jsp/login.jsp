<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
		<link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">	 
	</head>
	<body>
		<h2>로그인 </h2>
			<form name="form1" method="post" action="/login">
				<table>
				    <tr height="40px">
				        <td>유저ID</td>
				        <td><input type="text" name="email" value="youngho.kim@trito.co.kr"></td>
				    </tr>
				    <tr height="40px">
				        <td>패스워드</td>
				        <td><input type="password" name="password" value=""></td>
				    </tr>
				</table>
				<table>
				    <tr>
				        <td align="center"><input type="submit" value="로그인"></td>
				        <td align="center"><input type="reset" value="리셋"></td>
				        <td align="center">
				        	<input type="button" value="회원가입" onClick="location.href='/signupView'">
				        </td>				        
				    </tr>
				</table>
			</form>
	</body>
</html>