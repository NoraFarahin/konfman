<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="userForm.title"/></title>
</head>

<p>Please fill in user's information below:</p>

<form:form commandName="user" method="post" action="userform.html" onsubmit="return validateUser(this)" id="userForm">
<form:errors path="*" cssClass="error"/>
<form:hidden path="id"/>
<table class="detail">
<tr>
    <th><label for="username" class="required">* <fmt:message key="user.username"/>:</label></th>
    <td>
        <form:input path="username" id="username"/>
        <form:errors path="username" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="firstName" class="required">* <fmt:message key="user.firstName"/>:</label></th>
    <td>
        <form:input path="firstName" id="firstName"/>
        <form:errors path="firstName" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="lastName" class="required">* <fmt:message key="user.lastName"/>:</label></th>
    <td>
        <form:input path="lastName" id="lastName"/>
        <form:errors path="lastName" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="phone" class="required">* <fmt:message key="user.phone"/>:</label></th>
    <td>
        <form:input path="phone" id="phone"/>
        <form:errors path="phone" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="email" class="required">* <fmt:message key="user.email"/>:</label></th>
    <td>
        <form:input path="email" id="email"/>
        <form:errors path="email" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="password" class="required">* <fmt:message key="user.password"/>:</label></th>
    <td>
        <form:password path="password" id="password" showPassword="true"/>
        <form:errors path="password" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="verifyPassword" class="required">* <fmt:message key="user.verifyPassword"/>:</label></th>
    <td>
        <form:password path="verifyPassword" id="verifyPassword" showPassword="true"/>
        <form:errors path="verifyPassword" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="defaultFloor"> <fmt:message key="user.defaultFloor"/>:</label></th>
    <td>
    	<c:if test="${not empty user.defaultFloor}">
	    	<input value="${user.defaultFloor.name}" readOnly="true"/>
	    </c:if>
    	<c:if test="${empty user.defaultFloor}">
	    	<input value="No floor selected" readOnly="true"/>
	    </c:if>
        <input type="submit" class="button" name="_target1" value="Select/Change Default Floor"/>
    </td>
</tr>
<tr>
    <td></td>
    <td>
        <input type="submit" class="button" name="_finish" value="Save"/>
      <c:if test="${not empty param.id}">
        <input type="submit" class="button" name="_finish" value="Delete" onclick="bCancel=true"/>
      </c:if>
      	<input type="submit" class="button" name="_cancel" value="Cancel" onclick="bCancel=true"/>
    </td>
</tr>
</table>

<h2>Authorities of ${user.fullName}</h2>
<button type="submit" name="_target3" value="true" style="float: right; margin-top: -30px; width: 100px">Add Roll</button>
<display:table name="user.roles" class="table" requestURI="" id="roles" export="false" pagesize="10">
    <display:column property="role" sortable="true" titleKey="user.roll" escapeXml="true"/>
    <display:column sortable="true" href="userform.html" media="html"
        paramId="removeAuthorityId" paramProperty="id">Remove</display:column>
</display:table>

<h2>Rooms Administered by ${user.fullName}</h2>
<button type="submit" name="_target2" value="true" style="float: right; margin-top: -30px; width: 100px">Add Room</button>
</form:form>
<display:table name="user.administeredRooms" class="table" requestURI="" id="rooms" export="true" pagesize="10">
    <display:column property="name" sortable="true" titleKey="room.name" escapeXml="true"/>
    <display:column property="title" sortable="true" titleKey="room.title" escapeXml="true"/>
    <display:column sortable="true" href="userform.html" media="html"
        paramId="removeRoomId" paramProperty="id">Remove</display:column>
</display:table>


<v:javascript formName="user" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
