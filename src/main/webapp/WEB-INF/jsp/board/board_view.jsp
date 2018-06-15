<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投稿画面</title>
<%@ include file="../include/member_header.jsp" %>
<script>
    $(document).ready(function(){
        $("#btnDelete").click(function(){
            if(confirm("削除してもよろしいですか？")){
                document.form1.action = "${path}/board/delete.do";
                document.form1.submit();
            }
        });
        
        $("#btnUpdete").click(function(){
            //var title = document.form1.title.value; ==> nameで処理する場合
            //var content = document.form1.content.value;
            var brdWriter = $("#brdWriter").val();
            var brdComment = $("#brdComment").val();
            if(brdWriter == ""){
                alert("タイトルを入力してください。");
                document.form1.brdComment.focus();
                return;
            }
			
            document.form1.action = "${path}/board/update.do";
			document.form1.submit();
        });
    });
</script>
</head>
<body>
<%@ include file="../include/member_menu.jsp" %>
<h2>詳細画面</h2>
<form name="form1" method="post">
    <div>        <!-- 日付をカスタマイズする為fmtタッグを使用 -->
        作成日付 : <fmt:formatDate value="${dto.brdDate}" pattern="yyyy-MM-dd a HH:mm:ss"/>
                <!-- 日付形式 => yyyy 4桁年度, MM 月, dd 日, a 午前/午後, HH 24時間制, hh 12時間制, mm 分, ss 秒 -->
    </div>
    <div>
        投稿者名
        <input name="brdWriter" id="brdWriter" size="80" value="${dto.brdWriter}" placeholder="投稿者名を入力してください。">
    </div>
    <div>
        内容
        <textarea name="brdComment" id="brdComment" rows="4" cols="80" placeholder="内容を入力してください">${dto.brdComment}</textarea>
    </div>
    
    <div style="width:650px; text-align: center;">
        <!-- 投稿番号をhiddenで処理 -->
        <input type="hidden" name="brdNo" value="${dto.brdNo}">
        <button type="button" id="btnUpdete">編集</button>
        <button type="button" id="btnDelete">削除</button>
    </div>
</form>
</body>
</html>
