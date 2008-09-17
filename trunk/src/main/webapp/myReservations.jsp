<%@ include file="/taglibs.jsp"%>
<title>Reservations for:
	<c:out value="${me}"/>
</title> 
<button onclick="location.href='reservationform.html?dest=myreservations.html&userId=${me.id}'"style="float: right; margin-top: -30px; width: 100px">Add Reservation</button>
<a href="myreservations.html">Current Reservations</a> | 
<a href="myreservations.html?subset=past">Past Reservations</a> |
<a href="myreservations.html?subset=all">All Reservations</a>  
<display:table name="reservations" class="table" requestURI="" id="user" export="true" pagesize="10">
	<display:setProperty name="export.pdf.filename" value="reservations.pdf"/>
    <display:column sortable="true" href="reservationform.html?dest=myreservations.html" media="html"
        paramId="id" paramProperty="id" titleKey="reservation.id">
        Edit
    </display:column>
    <display:column property="id" media="csv excel xml pdf" titleKey="reservation.id"/>
    <display:column property="comment" sortable="true" titleKey="reservation.comment" escapeXml="true"/>
    <display:column property="date" sortable="true" titleKey="reservation.date" escapeXml="true"/>
    <display:column property="startTime" sortable="true" titleKey="reservation.startTime" escapeXml="true"/>
    <display:column property="endTime" sortable="true" titleKey="reservation.endTime" escapeXml="true"/>
    <display:column property="room.name" sortable="true" titleKey="room.name" escapeXml="true"/>
</display:table>

<script type="text/javascript">highlightTableRows("user");</script>
