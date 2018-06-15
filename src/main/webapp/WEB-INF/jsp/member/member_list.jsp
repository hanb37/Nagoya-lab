<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会社員一覧</title>
<%@ include file="../include/member_header.jsp" %>
</head>
<body>
<%@ include file="../include/member_menu.jsp" %>
    <h2>会社員一覧</h2>
<input type="button" value="登録" onclick="location.href='${path}/member/write.do'">
    <table border="1" style = "width : 700px">
        <tr>
            <th>ID</th>
            <th>名前</th>
            <th>メール</th>
            <th>登録日時</th>
        </tr>
        <c:forEach var="row" items="${list}">
        <tr>
            <td>${row.userId}</td>
            <td><a href="${path}/member/view.do?userId=${row.userId}">${row.userName}</a></td>
            <td>${row.userEmail}</td>
            <td>${row.userRegdate}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
