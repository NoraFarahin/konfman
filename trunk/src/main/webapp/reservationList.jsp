<%@ include file="/taglibs.jsp"%>
<c:out value="<a href='rooms.html?floorId=${room.floor.id}'>Return to room list</a>" escapeXml="false"/>
<title>Reservations for room:
	<c:out value="${room.floor.building.name}"/> 
	<c:out value="${room.floor.name}"/> 
	<c:out value="${room.name}"/> - <c:out value="${room.title}"/></title>
<button onclick="location.href='reservationform.html?dest=reservations.html&roomId=<%=request.getParameter("roomId")%>'"style="float: right; margin-top: -30px; width: 100px">Add Reservation</button>
<p>
	<a href="reservations.html?roomId=<%=request.getParameter("roomId")%>">Current Reservations</a> | 
	<a href="reservations.html?subset=past&roomId=<%=request.getParameter("roomId")%>">Past Reservations</a> |
	<a href="reservations.html?subset=all&roomId=<%=request.getParameter("roomId")%>">All Reservations</a>
</p>  
<display:table name="reservations" class="table" requestURI="" id="room" export="true" pagesize="10">
	<display:setProperty name="export.pdf.filename" value="reservations.pdf"/>
    <display:column property="id" sortable="true" href="reservationform.html?dest=reservations.html" media="html"
        paramId="id" paramProperty="id" titleKey="reservation.id"/>
    <display:column property="id" media="csv excel xml pdf" titleKey="reservation.id"/>
    <display:column property="comment" sortable="true" titleKey="reservation.comment" escapeXml="true"/>
    <display:column property="date" sortable="true" titleKey="reservation.date" escapeXml="true"/>
    <display:column property="startTime" sortable="true" titleKey="reservation.startTime" escapeXml="true"/>
    <display:column property="endTime" sortable="true" titleKey="reservation.endTime" escapeXml="true"/>
    <display:column property="user.fullName" sortable="true" titleKey="user.fullName" escapeXml="true"/>
</display:table>

<script type="text/javascript">highlightTableRows("room");</script>
