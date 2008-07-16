<%@ include file="/taglibs.jsp"%>
<c:out value="<a href='rooms.html?floorId=${room.floor.id}'>Return to room list</a>" escapeXml="false"/>
<title>Reservations for user:
	<c:out value="${user.fullName}"/>
</title> 
<button onclick="location.href='reservationform.html?userId=${user.id}'"style="float: right; margin-top: -30px; width: 100px">Add Reservation</button>
<display:table name="user.reservations" class="table" requestURI="" id="user" export="true" pagesize="10">
	<display:setProperty name="export.pdf.filename" value="reservations.pdf"/>
    <display:column property="id" sortable="true" href="reservationform.html" media="html"
        paramId="id" paramProperty="id" titleKey="reservation.id"/>
    <display:column property="id" media="csv excel xml pdf" titleKey="reservation.id"/>
    <display:column property="comment" sortable="true" titleKey="reservation.comment" escapeXml="true"/>
    <display:column property="date" sortable="true" titleKey="reservation.date" escapeXml="true"/>
    <display:column property="startTime" sortable="true" titleKey="reservation.startTime" escapeXml="true"/>
    <display:column property="endTime" sortable="true" titleKey="reservation.endTime" escapeXml="true"/>
    <display:column property="room.name" sortable="true" titleKey="room.name" escapeXml="true"/>
</display:table>

<script type="text/javascript">highlightTableRows("user");</script>
