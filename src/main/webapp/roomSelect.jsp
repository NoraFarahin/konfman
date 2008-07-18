<%@ include file="/taglibs.jsp"%>
<title>Select a room</title>
<c:forEach items="${buildingList}" var="building" varStatus="status">
	<h3>
		<c:out value="${building.name} : ${building.title}"/>
	</h3>
	<button onClick='toggleVisibilty("building${status.count}")' style="float: right; margin-top: -30px; width: 100px">Show/Hide</button>
	<div id="building${status.count}" style="display:none">
		<c:forEach items="${building.floors}" var="floor" varStatus="fstatus">
			<p><c:out value="${floor.name} : ${floor.title}"/></p>
			<button onClick='toggleVisibilty("floor${fstatus.count}")' style="float: right; margin-top: -30px; width: 100px">Show/Hide</button>
			<div id="floor${fstatus.count}" style="display:none">
				<display:table name="${floor.rooms}" class="table" requestURI="" id="${floor.name}" pagesize="10">
				    <display:column property="name" sortable="true" titleKey="room.name" escapeXml="true"/>
				    <display:column property="title" sortable="false" titleKey="room.title" escapeXml="true"/>
				    <display:column sortable="false" titleKey="room.title" href="${submitform}.html?" paramId="roomId" paramProperty="id">
				    	Select
				    </display:column> 
				</display:table>
			</div>
		</c:forEach>
		<c:if test="${empty building.floors}">
			<p>There are no available floors in this building</p>
		</c:if>
	</div>	
</c:forEach>

<script type="text/javascript">
function toggleVisibilty(id) {
    var main = document.getElementById(id);
    if (main.style.display == "") {
        main.style.display = "none";
    } else {
        main.style.display = "";
    }
}
</script>
