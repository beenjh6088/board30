<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="imageFileName" value="aa" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	
	#tr_file_upload{
		display:none;
	}
	
	#tr_btn_modify{
		display:none;
	}

</style>
</head>
<script src="${contextPath }/resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	
});

function backToList(obj){
	obj.action="${contextPath}/board/listArticles.do";
	obj.submit();
}

function fn_enable(obj){
	var tr_file_upload = "${arrImageFileSize }";
	var numImageFileSize = parseInt(tr_file_upload);
	document.getElementById("i_title").disabled=false;
	document.getElementById("i_content").disabled=false;
	for(var i = 0; i < numImageFileSize; i ++){
		document.getElementById("i_imageFileName"+i).disabled=false;
	}
	document.getElementById("tr_btn_modify").style.display="block";
	if(tr_file_upload == "0"){
		document.getElementById("tr_file_upload").style.display="table-row";
	}
	document.getElementById("tr_btn").style.display="none";
}

function fn_disable(){
	var tr_file_upload = "${arrImageFileSize }";
	var numImageFileSize = parseInt(tr_file_upload);
	document.getElementById("i_title").disabled=true;
	document.getElementById("i_content").disabled=true;
	for(var i = 0; i < numImageFileSize; i ++){
		document.getElementById("i_imageFileName"+i).disabled=true;
	}
	document.getElementById("tr_btn_modify").style.display="none";
	if(tr_file_upload == "0"){
		document.getElementById("tr_file_upload").style.display="none";
	}
	document.getElementById("tr_btn").style.display="table-row";
}

function fn_modify_article(obj){
	obj.action="${contextPath}/board/modArticle.do";
	obj.submit();
}

function readURL(input) {
	if (input.files && input.files[0]) {
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

function fn_remove_article(url,articleNO){
	var form = document.createElement("form");
	form.setAttribute("method", "post");
	form.setAttribute("action", url);
	var articleNOInput = document.createElement("input");
	articleNOInput.setAttribute("type","hidden");
	articleNOInput.setAttribute("name","articleNO");
	articleNOInput.setAttribute("value", articleNO);

	form.appendChild(articleNOInput);
	document.body.appendChild(form);
	form.submit();
}

function fn_reply_form(url, parentNO){
	var form = document.createElement("form");
	form.setAttribute("method", "get");
	form.setAttribute("action", url);
	var parentNOInput = document.createElement("input");
	parentNOInput.setAttribute("type","hidden");
	parentNOInput.setAttribute("name","parentNO");
	parentNOInput.setAttribute("value", parentNO);
	
	form.appendChild(parentNOInput);
	document.body.appendChild(form);
	form.submit();
}

</script>
<body>

	<form name="frmArticle" method="post" action="${contextPath }" enctype="multipart/form-data">
		<table border="1" style="margin: 0 auto">
			<tr>
				<td width=150 align="center" bgcolor=#FF9933>
					글번호
				</td>
				<td >
					<input type="text"  value="${article.articleNO }"  disabled="disabled" style="width: 450px;" />
					<input type="hidden" name="articleNO" value="${article.articleNO}"  />
				</td>
			</tr>
			<tr>
				<td width=150 align="center" bgcolor=#FF9933>
					작성자 아이디
				</td>
				<td >
					<input type=text value="${article.id }" name="writer"  disabled="disabled" style="width: 450px;" />
				</td>
			</tr>
			<tr>
				<td width=150 align="center" bgcolor=#FF9933>
					제목
				</td>
				<td>
					<input type=text value="${article.title }"  name="title"  id="i_title" disabled="disabled" style="width: 450px;" />
				</td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">
					내용
				</td>
				<td>
					<textarea rows="20" cols="60" name="content" id="i_content" disabled="disabled">${article.content }</textarea>
				</td>
			</tr>
			<c:choose>
				<c:when test="${not empty arrImageFile }">
					<tr>
						<td width="150" align="center" bgcolor="#FF9933" rowspan="${arrImageFileSize+1 }">
							이미지
						</td>
					</tr>
					<c:forEach var="imageFile" items="${arrImageFile }" varStatus="vsImageFile">
						<tr>
							<td>
								<input type= "hidden" name="originalFileName${vsImageFile.index }" value="${imageFile.imageFileName }" />
								<input type= "hidden" name="imageFileNO${vsImageFile.index }" value="${vsImageFile.index }" />
								<img src="${contextPath}/download.do?articleNO=${imageFile.articleNO}&imageFileName=${imageFile.imageFileName}" id="preview${vsImageFile.index }" width="120" height="120" /><br>
								<input type="file" name="imageFileName${vsImageFile.index }" id="i_imageFileName${vsImageFile.index }"  onchange="readURL(this);" disabled="disabled" />
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr id="tr_file_upload">
						<td width="150" align="center" bgcolor="#FF9933">
							이미지
						</td>
						<td>
							<input type= "hidden" name="originalFileName" value="${article.imageFileName }" />
							<img id="preview"/><br>
							<input  type="file"  name="imageFileName" id="i_imageFileName" onchange="readURL(this);"   />
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<td width="150" align="center" bgcolor="#FF9933">
					등록일자
				</td>
				<td>
					<input type=text value="<fmt:formatDate value="${article.writeDate}" />" disabled="disabled" style="width: 450px;" />
				</td>
			</tr>
			<tr id="tr_btn_modify"  align="center">
				<td colspan="2"   >
					<input type=button value="수정반영하기"   onClick="fn_modify_article(frmArticle)"  >
					<input type=button value="취소"  onClick="fn_disable()">
				</td>   
			</tr>
    
			<tr id="tr_btn">
				<td colspan="2" align="center">
					<c:if test="${member.id == article.id }">
						<input type=button value="수정하기" onClick="fn_enable(this.form)">
						<input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/board/removeArticle.do', ${article.articleNO})">
					</c:if>
					<input type=button value="리스트로 돌아가기"  onClick="backToList(this.form)">
					<input type=button value="답글쓰기"  onClick="fn_reply_form('${contextPath}/board/replyForm.do', ${article.articleNO})">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>