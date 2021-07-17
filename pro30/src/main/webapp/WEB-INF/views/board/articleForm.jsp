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
	function readURL(input) {
		if(input.files && input.files[0]) {
			var id = $(input).attr("id");
			var reg = new RegExp("[0-9]+");
			var id_dec = reg.exec(id);
			var reader = new FileReader();
			reader.onload = function (e) {
				$('#preview'+id_dec).attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	var cnt=1;
	function fn_addFile(){
		$("#d_file_group").append("<div id='d_file"+cnt+"'><input type='file' id='i_file"+cnt+"' name='imageFileName"+cnt+"' onchange='readURL(this);' /><img id='preview"+cnt+"' src='#' width='120' height='120'></div>");
		cnt++;
	}

	function backToList(obj){
		obj.action="${contextPath}/board/listArticles.do";
		obj.submit();
	}
</script>
<body>

<form name="articleForm" method="post" action="${contextPath }/board/addNewArticle.do" enctype="multipart/form-data">
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
			<td align="right">이미지파일 첨부:  </td>
			<td colspan="2">
				<input type="file" id="i_file0" name="imageFileName0" onchange="readURL(this);" />
				<img  id="preview0" src="#" width=120 height=120/>
			</td>
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
				<input type="button" value="파일 추가" onClick="fn_addFile()"/>
				<input type="submit" value="글쓰기" />
				<input type=button value="목록보기"onClick="backToList(this.form)" />
			</td>
		</tr>
	</table>
</form>

</body>
</html>