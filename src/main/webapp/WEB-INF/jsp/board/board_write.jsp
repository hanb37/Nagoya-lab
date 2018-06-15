<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投稿画面</title>
<%@ include file="../include/member_header.jsp"%>
<script>
	$(document).ready(function() {
		$("#btnSave").click(function() {
			//var title = document.form1.title.value; ==> nameタイプで処理する場合
			//var content = document.form1.content.value;
			//var writer = document.form1.writer.value;
			var brdWriter = $("#brdWriter").val();
			var brdComment = $("#brdComment").val();
			if (brdWriter == "") {
				alert("投稿者を入力してください。");
				document.form1.title.focus();
				return;
			}
			if (brdComment == "") {
				alert("コメントを入力してください。");
				document.form1.content.focus();
				return;
			}
			
			// Formで入力したデータをサーバーへ伝送
			document.form1.submit();
		});
	});
</script>
</head>
<body>
	<%@ include file="../include/member_menu.jsp"%>
	<h2>投稿画面</h2>
	<form name="form1" method="post" action="${path}/board/insert.do">
		<table border="1" style="width:600px">
		<tr>
			<td>投稿者</td>
			<td><input type="text" name="brdWriter" id= "brdWriter" size="20" maxlength="20"></td>
		</tr>
		<tr>
			<td>コメント</td>
			<td><textarea name="brdComment" id= "brdComment" rows="5" cols="60"></textarea></td>
		</tr>
	</table>
	<button id="btnSave">保存</button>
<!-- 	<a href="#" onclick="form1.submit()">保存</a>  -->
	 <a href="${path}/board/list.do">キャンセル</a> 
	</form>
</body>
</html>
