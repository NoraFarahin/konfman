<%@ include file="/taglibs.jsp"%>
<title>Select a floor</title>
<c:forEach items="${buildingList}" var="building" varStatus="status">
	<h1>
		<c:out value="${building.name} : ${building.title}"/>
	</h1>
	<div id="building${status.count}" style="display:none">
		<display:table name="${building.floors}" class="table" requestURI="" id="${building.name}" pagesize="10">
		    <display:column property="name" sortable="true" titleKey="floor.name" escapeXml="true"/>
		    <display:column property="title" sortable="false" titleKey="floor.title" escapeXml="true"/>
		    <display:column property="id" sortable="false" titleKey="floor.title" href="userform.html?_finish=true" paramId="floorId" paramProperty="id"/> 
		</display:table>
	</div>
	<button onClick='toggleVisibilty("building${status.count}")'>Show/Hide</button>
	
	
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
