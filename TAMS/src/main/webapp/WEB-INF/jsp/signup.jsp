<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<title>Login</title>
		<link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">	
		<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
		<script src="/resources/js/common.js"></script>		
		<script>
			function signup(){								
				let url = 'http://localhost:8080/user/regist';
				let reqType = 'POST';				
				let data = {
					"email": $('#email').val(),
					"password" : $('#password').val(),
					"name": $('#name').val()
				};
				$.commRequest(url, reqType, JSON.stringify(data))
					.then((res) => {
						consoel.log('signup success......');
					})
					.catch((error) => {
						alert('회원가입 실패!!');
					});								
			}
		</script>	
	</head>

	<body>
		<h2>로그인 </h2>
			<form id="form" method="post" action="/user/signup">
				<table>
				    <tr height="40px">
				        <td>유저ID</td>
				        <td><input type="text" id="email"></td>
				    </tr>
				    <tr height="40px">
				        <td>유저명</td>
				        <td><input type="text" id="name"></td>
				    </tr>				    				    
				    <tr height="40px">
				        <td>패스워드</td>
				        <td><input type="text" id="password"></td>
				    </tr>
				</table>
				<table>
				    <tr>
				        <td align="center"><input type="button" value="회원가입" onClick="signup()"></td>
				        <td align="center">
				        	<input type="button" value="로그인" onClick="location.href='/user/login'">
				        </td>
				    </tr>
				</table>
			</form>
	</body>
</html>