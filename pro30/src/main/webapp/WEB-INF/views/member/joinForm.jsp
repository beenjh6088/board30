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
<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<!-- 
<script src="https://code.jquery.com/jquery-2.2.1.js"></script>
 -->
<script type="text/javascript">
	var $inp_id;
	var $btn_submit;
	var $msg_id;
	
	$(document).ready(function(){
		init();
		initEvent();
	});
	
	function init(){
		$inp_id = $("input[name='id']");
		$btn_submit = $("input[name='submit']");
		$msg_id = $("#msg_id");
	}
	
	function initEvent(){
		$inp_id.change(function(){
			var id = $inp_id.val();
			checkId(id);
		});
	}
	
	function checkId(id){
		$.ajax({
			type:"post",
			async:false,
			url:"http://localhost:9008/pro30/member/checkId",
			dataType:"text",
			data:{id:id},
			success:function(data, textStatus){
				if(data != "PASS"){
					$inp_id.addClass("caution_item");
					$btn_submit.attr("disabled", true);
					$msg_id.text("ID가 중복입니다.");
				}else{
					$inp_id.removeClass("caution_item");
					$btn_submit.attr("disabled", false);
					$msg_id.text("");
				}
			}
		});
	}
</script>
<body>

	<form method="post"   action="${contextPath}/member/insertMember.do">
	<h1  class="text_center">회원 가입창</h1>
	<table style="margin: 0 auto;">
		<tr>
			<td width="200"><p align="right">아이디</p></td>
			<td width="400"><input type="text" name="id"></td>
			<td><span id="msg_id"></span></td>
		</tr>
		<tr>
			<td width="200"><p align="right">비밀번호</p></td>
			<td width="400"><input type="password" name="pwd"></td>
		</tr>
		<tr>
			<td width="200"><p align="right">이름</p></td>
			<td width="400"><p><input type="text" name="name"></td>
		</tr>
		<tr>
			<td width="200"><p align="right">이메일</p></td>
			<td width="400"><p><input type="text" name="email"></td>
		</tr>
		<tr>
			<td width="200"><p>&nbsp;</p></td>
			<td width="400"><input type="submit" value="가입하기" name="submit"><input type="reset" value="다시입력" name="reset"></td>
		</tr>
	</table>
	</form>

</body>
</html>