<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
<%@ include file="WEB-INF/jsp/include/member_header.jsp" %>
</head>
<body>
<%@ include file="WEB-INF/jsp/include/member_menu.jsp" %>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
