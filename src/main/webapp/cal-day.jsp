<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="./WEB-INF/tld/calendartag.tld" prefix="cal" %>
<link rel="stylesheet" href="/styles/calendartag/calendar.css" type="text/css" />
<title>Daily Calendar</title>
<div id="cal">
	<b>Reservations for ${entity}</b><br/>
	<a href='./cal-month.html?<%=request.getQueryString()%>'>Monthly Calendar</a> | 
	<a href='./cal-week.html?<%=request.getQueryString()%>'>Weekly Calendar</a> 
	<display:table name="hours" class="table" requestURI="" id="hours">
		<display:caption>
			<center>${prev}&nbsp;&nbsp;&nbsp;&nbsp;${date} &nbsp;&nbsp;&nbsp;&nbsp;${next}</center>
		</display:caption>
	    <display:column property="time" sortable="false" escapeXml="true"/>
	    <display:column property="reservation.comment" sortable="false" titleKey="reservation.comment" escapeXml="false"/>
	    <c:if test="${not empty param.userId}">
	    	<display:column property="reservation.room" sortable="false" titleKey="reservation.room.name" escapeXml="true"/>
		</c:if>	    	
	    <c:if test="${not empty param.roomId}">
	    	<display:column property="reservation.user" sortable="false" titleKey="reservation.user.fullName" escapeXml="true"/>
		</c:if>	    	
	</display:table>
</div>
