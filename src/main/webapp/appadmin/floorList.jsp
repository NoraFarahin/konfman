<%@ include file="/taglibs.jsp"%>
<title>Floors in building: <c:out value="${building.name}"/> - <c:out value="${building.title}"/></title>
<a href="buildings.html">Return to building list</a>
<button onclick="location.href='floorform.html?buildingId=<%=request.getParameter("buildingId")%>'"style="float: right; margin-top: -30px; width: 100px">Add Floor</button>
<display:table name="building.floors" class="table" requestURI="" id="building" export="true" pagesize="10">
	<display:setProperty name="export.pdf.filename" value="floors.pdf"/>
    <display:column href="floorform.html" media="html"paramId="id" paramProperty="id">Edit</display:column>
    <display:column property="id" media="csv excel xml pdf" titleKey="floor.id"/>
    <display:column property="name" sortable="true" titleKey="floor.name" escapeXml="true"/>
    <display:column property="title" sortable="true" titleKey="floor.title" escapeXml="true"/>
    <display:column property="active" sortable="true" titleKey="floor.active" escapeXml="true"/>
	<display:column href="rooms.html" paramId="floorId" paramProperty="id">Rooms</display:column>
</display:table>

