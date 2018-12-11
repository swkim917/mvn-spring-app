<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>안녕하세요 ${name}</h2>
	<%
	    int age = 18;
	    if (age > 20) {
	        out.println("성인입니다.<br>");
	    } else {
	%>미성년자입니다.
	<br>
	<%
	    }
	%>
</body>
</html>