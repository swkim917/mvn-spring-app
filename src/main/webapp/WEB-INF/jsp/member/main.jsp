<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>멤버 메인화면</title>
	<!-- meta tags 필요 -->
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
</head>
<c:if test="${id eq null}">
    <c:redirect url="login"/>
</c:if>
<body>
<div class="container">
	<h1>메인페이지</h1>
	<br>
	${id}님 로그인 하셨습니다.
	<input type="button" value="로그아웃" onclick="location.href='logout'" class="btn btn-primary">
	<br><br>
	<nav class="nav flex-column">
		<a href="get" class="nav-link">회원정보 조회</a>
		<a href="update" class="nav-link">회원정보 수정</a>
		<a href="delete" class="nav-link">회원정보 삭제</a>
		<c:if test="${id ne null and id eq 'admin'}">
			<a href="list" class="nav-link">회원목록</a>
		</c:if>	
		<a href="../board/add" class="nav-link">게시판 글쓰기</a>
		<a href="../board/list" class="nav-link">게시판 글목록</a>
	</nav>
</div>


	<!-- Optional JavaScript -->
    <!-- 먼저 jQuery가 오고 그 다음 Popper.js 그 다음 Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</body>
</html>




