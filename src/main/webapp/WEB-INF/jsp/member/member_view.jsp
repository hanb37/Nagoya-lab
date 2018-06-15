<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会員情報詳細ページ</title>
<%@ include file="../include/member_header.jsp" %>
<script>
    $(document).ready(function(){
        $("#btnUpdate").click(function(){
            // 確認ポップアップ    
            if(confirm("編集します。よろしいですか？")){
                document.form1.action = "${path}/member/update.do";
                document.form1.submit();
            }
        });
    });
    $(document).ready(function(){
        $("#btnDelete").click(function(){
            // 確認ポップアップ 
            if(confirm("削除します。よろしいですか？")){
                document.form1.action = "${path}/member/delete.do";
                document.form1.submit();
            }
        });
    });
</script>
</head>
<body>
<%@ include file="../include/member_menu.jsp" %>
    <h2>会員情報詳細ページ</h2>
    <form name="form1" method="post">
        <table border="1">
            <tr>
                <td>ID</td>
                 <!-- IDは編集できないように readonlyタイプを追加 -->
                <td><input name="userId" value="${dto.userId}" readonly="readonly"></td>
            </tr>
            <tr>
                <td>パスワード</td>
                <td><input type="password" name="userPw"></td>
            </tr>
            <tr>
                <td>名前</td>
                <td><input name="userName" value="${dto.userName}"></td>
            </tr>
            <tr>
            	<td>メール</td>
            	<td><input name="userEmail" value="${dto.userEmail}"></td>
            </tr>
            <tr>
                <td>会員登録日付</td>
                <td>
                    <fmt:formatDate value="${dto.userRegdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>
            <tr>
                <td>会員情報編集日付</td>
                <td>
                    <fmt:formatDate value="${dto.userUpdatedate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="button" value="伝送" id="btnUpdate">
                    <input type="button" value="取り消し" id="btnDelete">
                    <div style="color: red;">${message}</div>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
