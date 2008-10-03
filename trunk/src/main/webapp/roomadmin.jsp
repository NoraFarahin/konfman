<%@ include file="/taglibs.jsp"%>
<div id="intro">
    <h2>Welcome ${user.fullName}, to Room Administration!</h2>

   	You are the administrator of the following rooms:

	<display:table name="user.administeredRooms" class="table" requestURI="" id="rooms" export="true" pagesize="10">
	    <display:column property="name" sortable="true" titleKey="room.name" escapeXml="true"/>
	    <display:column property="title" sortable="true" titleKey="room.title" escapeXml="true"/>
	    <display:column href="roomform.html" paramId="id" paramProperty="id">Edit</display:column>
	    <display:column href="reservations.html" paramId="roomId" paramProperty="id">Reservations</display:column>
	    <display:column href="cal-month.html" paramId="roomId" paramProperty="id">Calendar</display:column>
	</display:table>
</div>