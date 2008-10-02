<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="./WEB-INF/tld/calendartag.tld" prefix="cal" %>
<link rel="stylesheet" href="/styles/calendartag/calendar.css" type="text/css" />
<link rel="stylesheet" href="/styles/calendartag/minicalendar.css" type="text/css" />
<title>Weekly Calendar</title>
<div id="cal">
	<b>Reservations for ${entity} for the week of ${startDate} - ${endDate}</b> <br/>
	<a href='./cal-month.html?<%=request.getQueryString()%>'>Monthly Calendar</a> | 
	<a href='./cal-day.html?<%=request.getQueryString()%>'>Daily Calendar</a> 

    <cal:calendar decorator="com.jmw.konfman.web.ReservationCalendarDecorator" 
    	startDate="${startDate}" dayHeight="200" endDate="${endDate}"/> 
</div>
