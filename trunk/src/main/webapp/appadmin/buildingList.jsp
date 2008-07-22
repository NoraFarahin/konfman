<%@ include file="/taglibs.jsp"%>

<title><fmt:message key="buildingList.title"/></title>

<button onclick="location.href='buildingform.html'"style="float: right; margin-top: -30px; width: 100px">Add Building</button>

<display:table name="buildingList" class="table" requestURI="" id="buildingList" export="true" pagesize="10">
    <display:setProperty name="export.pdf.filename" value="buildings.pdf"/>
    <display:column property="id" sortable="true" href="buildingform.html" media="html"
        paramId="id" paramProperty="id" titleKey="building.id"/>
    <display:column property="id" media="csv excel xml pdf" titleKey="building.id"/>
    <display:column property="name" sortable="true" titleKey="building.name" escapeXml="true"/>
    <display:column property="title" sortable="true" titleKey="building.title" escapeXml="true"/>
    <display:column property="active" sortable="true" titleKey="building.active" escapeXml="true"/>
	<display:column href="floors.html" paramId="buildingId" paramProperty="id">Floors</display:column>
</display:table>

<!--  script type="text/javascript">highlightTableRows("buildingList");</script -->
