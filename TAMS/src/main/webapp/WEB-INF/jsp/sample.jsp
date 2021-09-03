<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Sample Page</title>
		<link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">	
		<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
		<script src="/resources/js/common.js"></script>
		<script>
			$(document).ready(function(){
				let url = 'http://localhost:8080/sample/boardList';
				let reqType = 'GET';				
				let data = {
						"currentPage":"1",
						"numOfRows":"10",
						"searchTitle": "title2"
				}
				$.commRequest(url, reqType, data)
					.then((res) => {
						console.log(res.code);
						console.log(res.message);
						console.log(res.data);
					})
					.catch((error) => {
						console.log(error)
					})
			});		
		</script> 
	</head>
	<body>
		<h2>Sample Page</h2> 
	</body>
</html>