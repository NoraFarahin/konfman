<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="./WEB-INF/tld/calendartag.tld" prefix="cal" %>
<link rel="stylesheet" href="/styles/calendartag/minicalendar.css" type="text/css" />
<cal:calendar cssPrefix="mini" dayWidth="20" dayHeight="20" id="minical" decorator="com.jmw.konfman.web.MiniCalCalendarDecorator"/>
