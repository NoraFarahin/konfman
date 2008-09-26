<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="./WEB-INF/tld/calendartag.tld" prefix="cal" %>
<link rel="stylesheet" href="/styles/calendartag/calendar.css" type="text/css" />
<title>Daily Calendar</title>
<div id="cal">
	<h1>Reservations for ${entity} for ${date}</h1>
	<display:table name="hours" class="table" requestURI="" id="hours">
	    <display:column property="time" sortable="false" titleKey="reservation.comment" escapeXml="true"/>
	    <display:column property="reservation.comment" sortable="false" titleKey="reservation.comment" escapeXml="true"/>
	    <c:if test="${empty requestScope.userId}">
	    	<display:column property="reservation.room" sortable="false" titleKey="reservation.room" escapeXml="true"/>
		</c:if>	    	
	    <c:if test="${empty requestScope.roomId}">
	    	<display:column property="reservation.user" sortable="false" titleKey="reservation.user" escapeXml="true"/>
		</c:if>	    	
	</display:table>
</div>
