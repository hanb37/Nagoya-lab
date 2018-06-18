<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>掲示板</title>
<%@ include file="../include/member_header.jsp" %>
<script>
    $(document).ready(function(){
        $("#btnWrite").click(function(){
        	//ページアドレス更新
            location.href = "${path}/board/write.do";
        });
        $("#nextDay").click(function(){
        	var str = $(this).val();
        	alert(str);
        	//ページアドレス更新
   //         location.href = "${path}/board/next.do?nextDay='str'";
        });
    });
</script>

<style>
	.wrap{
		background-color: #4d94ff;
		width : 92%;
		margin: auto;
	    border: 2px solid #0052cc;
	}
	.date{
		margin-left: 20px;
		color: #33cc00;
	}
	#table-staff {
	    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	    border-collapse: collapse;
	    margin-left: 20px;	
	    margin-top: 10px;    
	    margin-right: 20px;
	    width: 97%; 
	    table-layout: fixed;
	}	
	#table-staff td{
	    border: 2px solid #0052cc;
	    height: 100px;
	    width: 140px ;	    
	}
	.tr0{
		background-color: #e6f0ff;
	}
	.tr1{
		background-color: #cce0ff;
	}
	#btnWrite{
		margin-left: 20px;
	}
	h3{
		margin-left: 20px;
	}
	.meeting{
		margin-left: 20px;	
		margin-top: 10px; 
		color: #33cc00;
	}
	#table-meeting{
		font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	    border-collapse: collapse;
	    margin-left: 20px;	
	    margin-top: 10px; 
	    table-layout: fixed;
    	width: 97%;  
    	margin-right: 10px;	
    	text-align: center;
    	
	}
	#table-meeting td{
	    overflow: hidden;
	}
	.trRoom td{
		border: 2px solid 	#ffb366;
		background-color:  #fff3e6;
	}
	.trMeetingRoom td{
		border: 2px solid 	#ffb366;
		background-color:  #ffe6cc;	
	}
	.trHour{
		border: 2px solid 	#3366ff;
	}
	
	.cell0{
		background-color: #70db70 !important;
	}
</style>

</head>
<body>
	<%@ include file="../include/member_menu.jsp" %>	
	<div class="wrap">
		<h3>掲示板</h3>
		<div class = "date"> 
			<c:url var="link" value="next.do">
				<c:param name="day" value="${date }前"/><br>
			</c:url>
			<a href="${link}">Back</a>
			
			<c:out value="${date }"/>
			
			<c:url var="link" value="next.do">
				<c:param name="day" value="${date }次"/><br>
			</c:url>
			<a href="${link}">Next</a>
			<button type="button" id="btnWrite">投稿</button>
		</div>	
		
		<c:set var="rowMax" value="3" />
		<c:set var="columnMax" value="7" />
		<c:set var="i" value="0" />
		<table id ="table-staff">
			    <c:forEach var = "k" begin = "0" end = "${rowMax}" >
					<tr class = "tr${k%2}">
		        	<c:forEach var = "l" begin = "0" end = "${columnMax}">
			        	<td>
				        	<c:choose>
				        		<c:when test="${ i < fn:length(list)}">			        			
				        			<c:url var="link" value="view.do">
				        				<c:param name="brdNo" value="${list.get(i).brdNo}" /><br>				        				
				        			</c:url>
				        			<a href="${link}">
					        			<c:out value="${list.get(i).brdWriter}" /><br>
					        			<c:out value="${list.get(i).brdComment}" /><br>					        			
				        			</a>
				        		</c:when>
				        	</c:choose>
				        	<c:set var="i" value="${i+1}" /> 
			        	</td>
					</c:forEach>
					</tr>
				</c:forEach>
		</table>
		
		<div class="meeting">会議室．ミーティング</div>
		<c:set var="startTime" value="8" />
		<c:set var="endTime" value="22" />
		<c:set var="rangeTime" value="${endTime - startTime}" />
		<c:set var="timeSpan" value="4" />
		<c:set var="RoomMeeting" value="会議室" />
		<c:set var="RoomSeminar" value="セミナールーム" />
		
		<table id = "table-meeting">	
			<tr >
				<td colspan="${timeSpan}" class ="trHour">時間</td>
				<c:forEach var = "j" begin = "${startTime}" end = "${endTime}" >
		        	<td colspan="${timeSpan}" class ="trHour">${j}:00</td>
		        </c:forEach>
			</tr>

		    <tr	class="trRoom">
		        <td colspan="${timeSpan}">セミナールーム</td>
		        
				<c:set var="countCell" value="0" /> 
		        <c:forEach var = "j" begin = "${startTime*2}" end = "${endTime*2 + 1}" >			        					        	             
		        	<c:forEach items="${listMessages}" var="message">   		        	
		        		<c:set var="numStart" value="${message.getNumberStartTime()}" />
		        		<c:set var="numEnd" value="${message.getNumberEndTime()}" />
		        		<c:set var="room" value="${message.location}" />
		        		
	        			<c:choose>	        				
							<c:when test = "${(j == (numStart * 2)) && (room eq RoomSeminar)}">	
								<c:set var="rangeTimeMessage" value="${(numEnd - numStart)*timeSpan}"/>
								<td colspan="${rangeTimeMessage}" class = "cell0">
									<c:out value="${message.mailFrom}" /> 
								</td>
								<c:set var="countCell" value="${(rangeTimeMessage*2)/timeSpan}" />		
							</c:when>
						</c:choose>							
		        	</c:forEach> 		        	
		        	<c:choose>	        				
		            	<c:when test = "${countCell == 0}">
							<td colspan="${timeSpan/2}"></td>									
		            	</c:when>
		            	<c:otherwise>
		            		<c:set var="countCell" value="${countCell - 1}" />
		            	</c:otherwise>
					</c:choose>
		        </c:forEach>
	    	</tr>
		     
	    	<tr class="trMeetingRoom">
		        <td colspan="${timeSpan}">会議室</td>
		        
				<c:set var="countCell" value="0" /> 
		        <c:forEach var = "j" begin = "${startTime*2}" end = "${endTime*2 + 1}" >			        					        	             
		        	<c:forEach items="${listMessages}" var="message">   		        	
		        		<c:set var="numStart" value="${message.getNumberStartTime()}" />
		        		<c:set var="numEnd" value="${message.getNumberEndTime()}" />
		        		<c:set var="room" value="${message.location}" />
		        		
	        			<c:choose>	        				
							<c:when test = "${(j == (numStart * 2)) && (room eq RoomMeeting)}">	
								<c:set var="rangeTimeMessage" value="${(numEnd - numStart)*timeSpan}"/>
								<td colspan="${rangeTimeMessage}" class = "cell0">
									<c:out value="${message.mailFrom}" /> 
								</td>
								<c:set var="countCell" value="${(rangeTimeMessage*2)/timeSpan}" />		
							</c:when>
						</c:choose>							
		        	</c:forEach> 		        	
		        	<c:choose>	        				
		            	<c:when test = "${countCell == 0}">
							<td colspan="${timeSpan/2}"></td>									
		            	</c:when>
		            	<c:otherwise>
		            		<c:set var="countCell" value="${countCell - 1}" />
		            	</c:otherwise>
					</c:choose>
		        </c:forEach>		      
			</tr>
			
		</table>  
		
	</div>	
</body>
</html>
