<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会員登録ページ</title>
<%@ include file="../include/member_header.jsp" %>
</head>
<body>
<%@ include file="../include/member_menu.jsp" %>
    <h2>会員登録</h2>
    <form name="form1" method="post" action="${path}/member/insert.do">
        <table border="1" width="400px">
            <tr>
                <td>ID</td>
                <td><input name="userId"></td>
            </tr>
            <tr>
                <td>パスワード</td>
                <td><input type="password" name="userPw"></td>
            </tr>
            <tr>
                <td>名前</td>
                <td><input name="userName"></td>
            </tr>
            <tr>
                <td>メール</td>
                <td><input name="userEmail"></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="送信">
                    <input type="reset" value="取り消す">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
