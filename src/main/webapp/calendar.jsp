<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="./WEB-INF/tld/calendartag.tld" prefix="cal" %>
<link rel="stylesheet" href="calendartag/calendar.css" type="text/css" />
<div id="intro">
    <cal:calendar decorator="com.jmw.konfman.web.ReservationCalendarDecorator"/> 
</div>
