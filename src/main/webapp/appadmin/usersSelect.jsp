<%@ include file="/taglibs.jsp"%>

<title><fmt:message key="userList.title"/></title>

<p>
Select user for reservation <c:out value="${reservation.comment}"/>
</p>
<form:form commandName="reservation" method="post" action="reservationform.html?roomId=1" onsubmit="return validateUser(this)" id="reservationForm">

<display:table name="userList" class="table" requestURI="" id="userList" export="false" pagesize="20">
    <display:column property="fullName" sortable="true" titleKey="user.firstName" escapeXml="true"/>
    <display:column property="phone" sortable="true" titleKey="user.phone" escapeXml="true"/>
    <display:column property="email" sortable="true" titleKey="user.email" escapeXml="true"/>
    <display:column href="${submitform}.html?${submittarget}" paramId="userId" paramProperty="id">
    	Select
    </display:column>
</display:table>
</form:form>