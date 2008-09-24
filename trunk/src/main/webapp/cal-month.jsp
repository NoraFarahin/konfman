<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="./WEB-INF/tld/calendartag.tld" prefix="cal" %>
<link rel="stylesheet" href="/styles/calendartag/calendar.css" type="text/css" />
<title>Monthly Calendar</title>
<div id="cal">
	<h1>Reservations for ${entity} for the month of ???</h1>
    <cal:calendar decorator="com.jmw.konfman.web.ReservationCalendarDecorator"/> 
</div>
