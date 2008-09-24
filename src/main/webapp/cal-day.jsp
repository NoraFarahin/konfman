<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="./WEB-INF/tld/calendartag.tld" prefix="cal" %>
<link rel="stylesheet" href="/styles/calendartag/calendar.css" type="text/css" />
<title>Daily Calendar</title>
<div id="cal">
	<h1>Reservations for ${entity} for ${date}</h1>
	<display:table name="hours" class="table" requestURI="" id="hours">
	    <display:column property="time" sortable="true" titleKey="reservation.comment" escapeXml="true"/>
	    <display:column property="reservation.comment" sortable="true" titleKey="reservation.comment" escapeXml="true"/>
	</display:table>
</div>
