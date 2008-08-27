<%@ include file="/taglibs.jsp"%>

<title><fmt:message key="authorityList.title"/></title>

Select a roll to add to user <c:out value="${reservation.comment}"/><br/>

<display:table name="authorityList" class="table" requestURI="" id="authority" export="false" pagesize="20">
    <display:column property="id" sortable="true" titleKey="user.roll" escapeXml="true"/>
    <display:column property="role" sortable="true" titleKey="user.roll" escapeXml="true"/>
    <display:column href="${submitform}.html" paramId="addAuthorityId" paramProperty="id">
    	Select
    </display:column>
</display:table>
