<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="articlesList"  value="${resultMap.articlesList}"  />
<c:set var="section"  value="${resultMap.section}"  />
<c:set var="pageNum"  value="${resultMap.pageNum}"  />
<c:set var="totArticles"  value="${resultMap.totArticles}"  />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <style>
	.no-uline{text-decoration: none;}
	.sel-page{text-decoration: none;color: red;}
	.cls1{text-decoration:none;}
	.cls2{text-align:center; font-size:30px;}
</style>
</head>
<script src="${contextPath }/resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">

	function fn_articleForm(isLogOn,articleForm,loginForm){
		if(isLogOn != '' && isLogOn != 'false'){
			location.href=articleForm;
		}else{
			alert("로그인 후 글쓰기가 가능합니다.")
			location.href=loginForm+'?action=/board/articleForm.do';
		}
	}

</script>
<body>

<table border="1" style="margin: 0 auto; width: 80%;">
	<tr align="center"  bgcolor="lightgreen">
		<td><b>글번호</b></td>
		<td><b>작성자</b></td>
		<td><b>제목</b></td>
		<td><b>작성일</b></td>
	</tr>
	<c:choose>
		<c:when test="${isLogOn == true && member != null}">
			<c:choose>
				<c:when test="${articlesList != null }">
					<c:forEach var="article" items="${articlesList }" varStatus="articleNum">
						<tr align="center">
							<td width="5%">${articleNum.count }</td>
							<td width="10%">${article.id }</td>
							<td width="35%" align='left'>
								<span style="padding-left: 20px"></span>
								<c:choose>
									<c:when test="${article.lvl > 1 }">
										<c:forEach begin="1" end="${article.lvl }" step="1">
											<span style="padding-left:20px"></span>
										</c:forEach>
										<span style="font-size:12px;">[답변]</span>
										<a class="cls1" href="${contextPath }/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>
									</c:when>
									<c:otherwise>
										<a class="cls1" href="${contextPath }/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="10%"><fmt:formatDate value="${article.writeDate }" pattern="yyyy-MM-dd HH:mm:ss E" /></td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</c:when>
		<c:otherwise>
				<tr>
					<td colspan="4">로그인 먼저</td>
				</tr>
		</c:otherwise>
	</c:choose>
</table>
<div>
	<c:if test="${totArticles != null && isLogOn == true && member != null }">
		<c:choose>
			<c:when test="${totArticles > 100 }">
				<c:forEach var="page" begin="1" end="10" step="1">
					<c:if test="${section > 1 && page == 1 }">
						<a class="no-uline" href="${contextPath }/board/listArticles.do?section=${section-1}&pageNum=1">&nbsp;pre</a>
					</c:if>
					<c:if test="${(section-1)*10+page <= (totArticles/10+1) }">
						<c:choose>
							<c:when test="${page==pageNum }">
								<a class="sel-page" href="${contextPath }/board/listArticles.do?section=${section}&pageNum=${page}">${(section-1)*10+page }</a>
							</c:when>
							<c:otherwise>
								<a class="no-uline" href="${contextPath }/board/listArticles.do?section=${section}&pageNum=${page}">${(section-1)*10+page }</a>
							</c:otherwise>
						</c:choose>
						<c:if test="${page == 10 }">
							<a class="no-uline" href="${contextPath }/board/listArticles.do?section=${section+1}&pageNum=1">&nbsp;next</a>
						</c:if>
					</c:if>
				</c:forEach>
			</c:when>
			<c:when test="${totArticles == 100 }">
				<c:forEach var="page" begin="1" end="${totArticles/10 + 1 }" step="1">
					<a class="no-uline" href="#">${page }</a>
				</c:forEach>
			</c:when>
			<c:when test="${totArticles < 100 }">
				<c:forEach var="page" begin="1" end="${totArticles/10 + 1 }" step="1">
					<c:choose>
						<c:when test="${page==pageNum }">
							<a class="sel-page" href="${contextPath }/board/listArticles.do?section=${section}&pageNum=${page}">${page }</a>
						</c:when>
						<c:otherwise>
							<a class="no-uline" href="${contextPath }/board/listArticles.do?section=${section}&pageNum=${page}">${page }</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:when>
		</c:choose>
	</c:if>
</div>
<p class="cls2"><a class="cls1"  href="javascript:fn_articleForm('${isLogOn}','${contextPath}/board/articleForm.do', '${contextPath}/member/loginForm.do')">글쓰기</a></p>

</body>
</html>