<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="./WEB-INF/tld/calendartag.tld" prefix="cal" %>
<link rel="stylesheet" href="/styles/calendartag/calendar.css" type="text/css" />
<link rel="stylesheet" href="/styles/calendartag/minicalendar.css" type="text/css" />
<title>Weekly Calendar</title>
<div id="cal">
	<h1>Reservations for ${entity} for the week of ${startDate} - ${endDate}</h1>

	<cal:calendar cssPrefix="mini" dayWidth="20" dayHeight="20" id="minical"/>
	
    <cal:calendar decorator="com.jmw.konfman.web.ReservationCalendarDecorator" 
    	startDate="${startDate}" dayHeight="200" endDate="${endDate}"/> 
</div>
