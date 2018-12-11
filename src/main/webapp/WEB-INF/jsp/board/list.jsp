<%@page import="com.example.domain.Board"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>게시판 목록</title>
	<!-- meta tags 필요 -->
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
</head>
<c:if test="${id eq null}">
    <c:redirect url="/app/member/login"/>
</c:if>
<%-- request 속성값 가져오기 --%>
<c:set var="totalRowCount" value="${requestScope.totalRowCount}" />
<c:set var="pageNum" value="${requestScope.pageNum}" />
<c:set var="boards" value="${requestScope.boards}" />
<c:set var="pageCount" value="${requestScope.pageCount}" />
<c:set var="pageBlock" value="${requestScope.pageBlock}" />
<c:set var="startPage" value="${requestScope.startPage}" />
<c:set var="endPage" value="${requestScope.endPage}" />
<body>
<div class="container">
	<h1>글목록(전체글: ${totalRowCount})</h1>
	<br>
	<h3><a href="add?pageNum=${pageNum}">글쓰기</a></h3>
	<br>
	<table class="table table-bordered table-hover">
	<thead>
	    <tr>
	        <th class="col-sm-1">번호</th><th class="col-sm-4">제목</th><th class="col-sm-2">작성자</th>
	        <th class="col-sm-2">작성일</th><th class="col-sm-1">조회수</th><th class="col-sm-2">IP</th>
	    </tr>
	</thead>
	<tbody>
	    <c:choose>
	    <c:when test="${totalRowCount gt 0}">
	       <% SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일"); %>
	       <c:forEach var="board" items="${boards}">
	           <% Timestamp timestamp = ((Board) pageContext.getAttribute("board")).getReg_date(); %>
	           <% Date date = new Date(timestamp.getTime()); %>
	           <tr>
	               <td>${board.num}</td>
	               <td width="250">
	                   <c:if test="${board.re_lev gt 0}">
	                       <c:set var="wid" value="${board.re_lev * 15}" />
	                       <img src="../img/level.gif" width="${wid}" height="10">
	                       <img src="../img/re.gif">
	                   </c:if>
	                   <a href="detail?num=${board.num}&pageNum=${pageNum}">${board.subject}</a>
	               </td>
	               <td>${board.name}</td>
	               <td><%=sdf.format(date) %></td>
	               <td>${board.readcount}</td>
	               <td>${board.ip}</td>
	           </tr>
	       </c:forEach>
	    </c:when>
	    <c:otherwise>
	        <tr>
	            <td colspan="6">게시판 글 없음</td>
	        </tr>
	    </c:otherwise>
	    </c:choose>
	</tbody>
	</table>
	
	<br>
	
	<c:if test="${totalRowCount gt 0}">
		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">
			    <%-- [이전] --%>
			    <c:choose>
		        <c:when test="${startPage gt pageBlock}">
		        	<li class="page-item">
			        	<a class="page-link" href="list?pageNum=${startPage - pageBlock}">이전</a>
			        </li>
		        </c:when>
		        <c:otherwise>
		        	<li class="page-item disabled">
			        	<a class="page-link" href="list?pageNum=${startPage - pageBlock}">이전</a>
			        </li>
		        </c:otherwise>
		        </c:choose>

			    <%-- 1~10 페이지블록 범위 출력 --%>
			    <c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
			        <c:choose>
			        <c:when test="${i eq pageNum}">
			        	<li class="page-item active">
		        			<a class="page-link" href="list?pageNum=${i}">${i}</a>
		        		</li>
			        </c:when>
			        <c:otherwise>
			        	<li class="page-item">
		        			<a class="page-link" href="list?pageNum=${i}">${i}</a>
		        		</li>
			        </c:otherwise>
			        </c:choose>
			    </c:forEach>
			    
			    <%-- [다음] --%>
			    <c:choose>
		        <c:when test="${endPage lt pageCount}">
		        	<li class="page-item">
			        	<a class="page-link" href="list?pageNum=${startPage + pageBlock}">다음</a>
			        </li>
		        </c:when>
		        <c:otherwise>
		        	<li class="page-item disabled">
			        	<a class="page-link" href="list?pageNum=${startPage + pageBlock}">다음</a>
			        </li>
		        </c:otherwise>
		        </c:choose>
			</ul>
		</nav>
	</c:if>
</div>


	<!-- Optional JavaScript -->
    <!-- 먼저 jQuery가 오고 그 다음 Popper.js 그 다음 Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</body>
</html>





