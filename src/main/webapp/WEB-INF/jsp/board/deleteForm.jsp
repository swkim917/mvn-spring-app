<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>게시판 글삭제</title>
	<!-- meta tags 필요 -->
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
</head>
<c:if test="${id eq null}">
    <c:redirect url="/app/member/loginForm"/>
</c:if>
<body>
<div class="container">
	<h1>게시판 글삭제</h1>
	<br>
	<form action="delete?pageNum=${param.pageNum}" method="post">
	<input type="hidden" name="num" value="${param.num}">
	<table class="table">
		<tr>
			<th>패스워드</th>
			<td><input type="password" name="passwd" required></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="글삭제" class="btn btn-primary">
				<input type="reset" value="다시쓰기" class="btn btn-primary">
				<input type="button" value="글목록" class="btn btn-primary" onclick="location.href='list?pageNum=${param.pageNum}'">	
			</td>
		</tr>
	</table>
	</form>
</div>


    <!-- Optional JavaScript -->
    <!-- 먼저 jQuery가 오고 그 다음 Popper.js 그 다음 Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</body>
</html>









