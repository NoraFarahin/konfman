<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="roomForm.title"/></title>
</head>
<p>Please fill in room's information below:</p>
<form:form commandName="room" method="post" action="roomform.html?floorId=1" onsubmit="return validateUser(this)" id="roomForm">
<form:errors path="*" cssClass="error"/>
<form:hidden path="id"/>
<form:hidden path="floor.id"/>
<table class="detail">
<tr>
    <th><label for="name" class="required">* <fmt:message key="room.name"/>:</label></th>
    <td>
        <form:input path="name" id="name"/>
        <form:errors path="name" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="title" class="required">* <fmt:message key="room.title"/>:</label></th>
    <td>
        <form:input path="title" id="title"/>
        <form:errors path="title" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="comment" class="required">* <fmt:message key="room.comment"/>:</label></th>
    <td>
        <form:input path="comment" id="comment"/>
        <form:errors path="comment" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="active" class="required">* <fmt:message key="room.active"/>:</label></th>
    <td>
        <form:checkbox path="active" id="active"/>
        <form:errors path="active" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <td></td>
    <td>
        <button type="submit" class="button" name="_finish" value="Save">Save</button>
		<sec:authorize ifAllGranted="ROLE_APP-ADMIN">
			<c:if test="${not empty param.id}">
				<button type="submit" class="button" name="_finish" value="Delete" onclick="bCancel=true">Delete</button>
			</c:if>
		</sec:authorize>
      	<button type="submit" class="button" name="_cancel" value="Cancel" onclick="bCancel=true">Cancel</button>
    </td>
</tr>
</table>

<sec:authorize ifAllGranted="ROLE_APP-ADMIN">
	<h2>Administrators for ${room.name}</h2>
	
	<button type="submit" name="_target1" value="true" style="float: right; margin-top: -30px; width: 100px">Add Admin</button>
	<display:table name="room.administrators" class="table" requestURI="" id="userList" pagesize="10">
	    <display:column property="fullName" sortable="true" titleKey="user.firstName" escapeXml="true"/>
	    <display:column property="phone" sortable="true" titleKey="user.phone" escapeXml="true"/>
	    <display:column property="email" sortable="true" titleKey="user.email" escapeXml="true"/>
	    <display:column sortable="true" href="roomform.html" media="html"
	        paramId="removeUserId" paramProperty="id">Remove</display:column>
	</display:table>
</sec:authorize>
</form:form>
<v:javascript formName="room" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
