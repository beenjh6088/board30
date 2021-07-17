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
<style>
	.text_center{
		text-align:center;
	}
	.caution_item{
		border: 3px solid red;
	}
</style>
</head>
<script src="${contextPath }/resources/js/jquery-3.5.1.min.js"></script>
<body>

<c:choose>
	<c:when test="${isLogOn == false }">
		<script type="text/javascript">
			window.onload = function(){
				alert("아이디나 비밀번호가 틀립니다. 다시 입력해주세요");
			}
		</script>
	</c:when>
</c:choose>
<form name="frmLogin" method="post" action="${contextPath }/member/login.do">
	<table border="1" style="width: 80%; margin: 0 auto;">
		<tr align="center">
			<td>아이디</td>
			<td>비밀번호</td>
		</tr>
		<tr align="center">
			<td><input type="text" name="id" value="" size="20"></td>
			<td><input type="password" name="pwd" value="" size="20"></td>
		</tr>
		<tr align="center">
			<td colspan="2">
				<input type="submit" value="로그인" > 
				<input type="reset"  value="다시입력" > 
			</td>
		</tr>
	</table>
</form>

</body>
</html>