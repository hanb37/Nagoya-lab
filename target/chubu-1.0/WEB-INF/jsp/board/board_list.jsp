<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<%@ include file="../include/member_header.jsp" %>
<script>
    $(document).ready(function(){
        $("#btnWrite").click(function(){
            // 페이지 주소 변경(이동)
            location.href = "${path}/board/write.do";
        });
    });
</script>
</head>
<body>
<%@ include file="../include/member_menu.jsp" %>
<h2>게시글 목록</h2>
<button type="button" id="btnWrite">글쓰기</button>
	<c:set var="i" value="0" />
	<c:set var="j" value="7" />
<table border="1" style="width:1000px height:1000px">
    <c:choose>
			<c:when test="${list != null && fn:length(list) > 0 }">
				<c:forEach items="${list}" var="list" varStatus="status">
					<c:url var="link" value="view.do">
						<c:param name="brdNo" value="${list.brdNo}" />
					</c:url>
					<c:if test="${i%j == 0}">
						<tr>
					</c:if>
					<td><a href="${link}"> <c:out value="${list.brdNo}" /><br>
						<c:out value="${list.brdComment}" /><br> 
						<c:out value="${list.brdWriter}" /><br>
						<fmt:formatDate value="${list.brdDate}" pattern="MM-dd HH:mm"/></a>
					<c:if test="${i%j == j-1}">
						</tr>
					</c:if>
					<c:set var="i" value="${i+1}" />
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td>まだ登録されていません。</td>
				</tr>
			</c:otherwise>
		</c:choose>
</table>
</body>
</html>
