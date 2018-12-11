<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>글내용 보기</title>
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
	<h1>글내용 보기</h1>
	<br>
	<table class="table table-bordered">
		<tr>
			<th>글번호</th><td>${board.num}</td>
			<th>조회수</th><td>${board.readcount}</td>
		</tr>
		<tr>
			<th>작성자</th><td>${board.name}</td>
			<th>작성일</th><td>${board.reg_date}</td>
		</tr>
		<tr>
			<th>글제목</th>
			<td colspan="3">${board.subject}</td>
		</tr>
		<tr>
			<th>파일</th>
			<td colspan="3">
				<a href="../upload/${board.filename}">${board.filename}</a>
				<img src="../upload/${board.filename}" width="50" height="50">
			</td>
		</tr>
		<tr>
			<th>글내용</th>
			<td colspan="3"><pre>${board.content}</pre></td>
		</tr>
		<tr>
			<td colspan="4">
				<input type="button" value="글수정" class="btn btn-primary" onclick="location.href='update?num=${board.num}&pageNum=${pageNum}'">
				<input type="button" value="글삭제" class="btn btn-primary" onclick="location.href='delete?num=${board.num}&pageNum=${pageNum}'">
				<input type="button" value="답글쓰기" class="btn btn-primary" onclick="location.href='reAdd?re_ref=${board.re_ref}&re_lev=${board.re_lev}&re_seq=${board.re_seq}&pageNum=${pageNum}'">
				<input type="button" value="글목록" class="btn btn-primary" onclick="location.href='list?pageNum=${pageNum}'">
			</td>
		</tr>
	</table>
</div>


    <!-- Optional JavaScript -->
    <!-- 먼저 jQuery가 오고 그 다음 Popper.js 그 다음 Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</body>
</html>












