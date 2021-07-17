<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
  request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<table border="1" style="width: 100%;">
	<tr>
		<td>
			<a href="${contextPath }/member/listMembers.do">
				<img src="${contextPath }/resources/image/duke_swing.gif"/>
			</a>
		</td>
		<td>
			<h1><font size="30">스프링실습 홈페이지!!!</font></h1>
		</td>
		<td>
			<!-- <h3><a href="#">로그인</a></h3> -->
			<c:choose>
				<c:when test="${isLogOn == true && member != null }">
					<h3>환영합니다.${member.name }님</h3>
					<h3><a href="${contextPath }/member/logout.do">로그아웃</a></h3>
				</c:when>
				<c:otherwise>
					<h3><a href="${contextPath }/member/loginForm.do">로그인</a></h3>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>


</body>
</html>