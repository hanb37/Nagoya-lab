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
            if(brdComment == ""){
                alert("投稿内容を入力してください。");
                document.form1.brdComment.focus();
                return;
            }
            
            document.form1.action="${path}/board/update.do"
            // 폼에 입력한 데이터를 서버로 전송
            document.form1.submit();
        });
    });
</script>
</head>
<body>
<%@ include file="../include/member_menu.jsp" %>
<h2>게시글 보기</h2>
<form name="form1" method="post">
    <div>        <!-- 원하는 날짜형식으로 출력하기 위해 fmt태그 사용 -->
        작성일자 : <fmt:formatDate value="${dto.brdDate}" pattern="yyyy-MM-dd a HH:mm:ss"/>
                <!-- 날짜 형식 => yyyy 4자리연도, MM 월, dd 일, a 오전/오후, HH 24시간제, hh 12시간제, mm 분, ss 초 -->
    </div>
    <div>
        제목
        <input name="brdWriter" id="brdWriter" size="80" value="${dto.brdWriter}" placeholder="제목을 입력해주세요">
    </div>
    <div>
        내용
        <textarea name="brdComment" id="brdComment" rows="4" cols="80" placeholder="내용을 입력해주세요">${dto.brdComment}</textarea>
    </div>
    
    <div style="width:650px; text-align: center;">
        <!-- 게시물번호를 hidden으로 처리 -->
        <input type="hidden" name="brdNo" value="${dto.brdNo}">
        <button type="button" id="btnUpdete">수정</button>
        <button type="button" id="btnDelete">삭제</button>
    </div>
</form>
</body>
</html>
