<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="./WEB-INF/tld/calendartag.tld" prefix="cal" %>
<link rel="stylesheet" href="/styles/calendartag/calendar.css" type="text/css" />
<title>Monthly Calendar</title>
<div id="cal">
	<b>Reservations for ${entity} for the month of ${month}</b><br/>
	<a href='./cal-week.html?<%=request.getQueryString()%>'>Weekly Calendar</a> | 
	<a href='./cal-day.html?<%=request.getQueryString()%>'>Daily Calendar</a> 
    <cal:calendar decorator="com.jmw.konfman.web.ReservationCalendarDecorator"/> 
</div>
