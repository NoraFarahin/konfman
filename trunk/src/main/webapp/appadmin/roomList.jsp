<%@ include file="/taglibs.jsp"%>
<jsp:directive.page import="com.jmw.konfman.model.Floor"/>
<c:out value="<a href='floors.html?buildingId=${floor.building.id}'>Return to floor list</a>" escapeXml="false"/>
<title>Rooms on floor: <c:out value="${floor.name}"/> - <c:out value="${floor.title}"/></title>
<button onclick="location.href='./roomform.html?floorId=<%=request.getParameter("floorId")%>'"style="float: right; margin-top: -30px; width: 100px">Add Room</button>
<display:table name="floor.rooms" class="table" requestURI="" id="floor" export="true" pagesize="10">
	<display:setProperty name="export.pdf.filename" value="rooms.pdf"/>
    <display:column href="./roomform.html" media="html" paramId="id" paramProperty="id">Edit</display:column>
    <display:column property="id" media="csv excel xml pdf" titleKey="room.id"/>
    <display:column property="name" sortable="true" titleKey="room.name" escapeXml="true"/>
    <display:column property="title" sortable="true" titleKey="room.title" escapeXml="true"/>
    <display:column property="active" sortable="true" titleKey="room.active" escapeXml="true"/>
	<display:column href="./reservations.html" paramId="roomId" paramProperty="id">Reservations</display:column>
</display:table>

