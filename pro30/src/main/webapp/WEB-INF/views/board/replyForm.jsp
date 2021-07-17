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
<script src="${contextPath }/resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">

	function backToList(obj){
		obj.action="${contextPath}/board/listArticles.do";
		obj.submit();
	}
	
</script>
<body>

	<h1>답글쓰기</h1>
	<form name="frmReply" method="post"  action="${contextPath}/board/addReply.do" enctype="multipart/form-data">
		<table style="margin: 0 auto; border: 0;">
			<tr>
				<td align="right">
					작성자
				</td>
				<td colspan="2" align="left">
					<input type="text" size="20" maxlength="100" value="${member.name }" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td align="right">글제목: </td>
				<td colspan="2"><input type="text" size="67"  maxlength="500" name="title" /></td>
			</tr>
			<tr>
				<td align="right" valign="top"><br>글내용: </td>
				<td colspan=2><textarea name="content" rows="10" cols="65" maxlength="4000"></textarea> </td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td colspan="2" id="d_file_group">
				</td>
			</tr>
			<tr>
				<td align="right"> </td>
				<td colspan="3">
					<input type="submit" value="글쓰기" />
					<input type=button value="목록보기"onClick="backToList(this.form)" />
				</td>
			</tr>
		</table>
		<input type="hidden" name="parentNO" value="${parentNO }">
	</form>

</body>
</html>