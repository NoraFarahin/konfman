<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="userForm.title"/></title>
</head>

<p>Please fill in user's information below:</p>

<form:form commandName="user" method="post" action="myprofile.html" onsubmit="return validateUser(this)" id="userForm">
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
	    <th><label for="firstName" class="required"><fmt:message key="user.firstName"/>:</label></th>
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
	    <th><label for="phone"> <fmt:message key="user.phone"/>:</label></th>
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
	        <form:input path="password" id="password"/>
	        <form:errors path="password" cssClass="fieldError"/>
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
	      	<input type="submit" class="button" name="_cancel" value="Cancel" onclick="bCancel=true"/>
	    </td>
	</tr>
	</table>
</form:form>