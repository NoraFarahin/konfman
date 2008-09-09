<%@ include file="/taglibs.jsp"%>

<title><fmt:message key="userList.title"/></title>

<button onclick="location.href='./userform.html'"style="float: right; margin-top: -30px; width: 100px">Add User</button>

<display:table name="userList" class="table" requestURI="" id="userList" export="true" pagesize="10">
    <display:setProperty name="export.pdf.filename" value="users.pdf"/>
    <display:column href="./userform.html" media="html" paramId="id" paramProperty="id" titleKey="user.id">Edit</display:column>
    <display:column property="id" media="csv excel xml pdf" titleKey="user.id"/>
    <display:column property="firstName" sortable="true" titleKey="user.firstName" escapeXml="true"/>
    <display:column property="lastName" sortable="true" titleKey="user.lastName" escapeXml="true"/>
    <display:column property="phone" sortable="true" titleKey="user.phone" escapeXml="true"/>
    <display:column property="email" sortable="true" titleKey="user.email" escapeXml="true"/>
    <display:column property="defaultFloor.name" sortable="true" titleKey="user.defaultFloor" escapeXml="true"/>
	<display:column href="./userreservations.html" paramId="userId" paramProperty="id">Reservations</display:column>
</display:table>

<!-- script type="text/javascript">highlightTableRows("userList");</script -->
