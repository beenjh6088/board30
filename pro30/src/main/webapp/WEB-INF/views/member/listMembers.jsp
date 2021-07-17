<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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


<table border="1" style="margin: 0 auto; width: 80%;">
	<tr align="center"  bgcolor="lightgreen">
		<td><b>아이디</b></td>
		<td><b>비밀번호</b></td>
		<td><b>이름</b></td>
		<td><b>이메일</b></td>
		<td><b>가입일</b></td>
		<td><b>삭제</b></td>
	</tr>
	<c:choose>
		<c:when test="${isLogOn == true && member != null}">
			<c:forEach var="member" items="${listMembers }">
				<tr>
					<td>${member.id}</td>
					<td>${member.pwd}</td>
					<td>${member.name}</td>
					<td>${member.email}</td>
					<td><fmt:formatDate value="${member.joinDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
					<td><a href="${contextPath }/member/deleteMember.do?id=${member.id}">삭제하기</a></td>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
				<tr>
					<td colspan="6">로그인 먼저</td>
				</tr>
		</c:otherwise>
	</c:choose>
</table>
<h1 align="center"><a href="${contextPath }/member/joinForm.do">회원가입</a></h1>

</body>
</html>