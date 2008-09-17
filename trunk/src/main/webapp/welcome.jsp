<%@ include file="/taglibs.jsp"%>
<div id="intro">
    <h2>Welcome to Konfman <c:out value="${me}"/>!</h2>
    Your upcoming schedule is:
		<display:table name="reservations" class="table" requestURI="" id="user" pagesize="10">
		    <display:column href="reservationform.html?dest=myreservations.html" media="html"
		        paramId="id" paramProperty="id" titleKey="reservation.id">
		        Edit
		    </display:column>
		    <display:column property="comment" sortable="true" titleKey="reservation.comment" escapeXml="true"/>
		    <display:column property="date" sortable="true" titleKey="reservation.date" escapeXml="true"/>
		    <display:column property="startTime" sortable="true" titleKey="reservation.startTime" escapeXml="true"/>
		    <display:column property="endTime" sortable="true" titleKey="reservation.endTime" escapeXml="true"/>
		    <display:column property="room.name" sortable="true" titleKey="room.name" escapeXml="true"/>
		</display:table>    
</div>
<!--  script type="text/javascript">
function readMore() {
    var main = document.getElementById("intro");
    var more = document.getElementById("readmore");
    if (main.style.display == "") {
        main.style.display = "none";
        more.style.display = "";
    } else {
        more.style.display = "none";
        main.style.display = "";
    }
}
</script -->
