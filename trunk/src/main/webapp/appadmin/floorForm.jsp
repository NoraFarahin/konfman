<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="floorForm.title"/></title>
</head>
<p>Please fill in floor's information below:</p>
<form:form commandName="floor" method="post" action="floorform.html?buildingId=1" onsubmit="return validateUser(this)" id="floorForm">
<form:errors path="*" cssClass="error"/>
<form:hidden path="id"/>
<form:hidden path="building.id"/>
<table class="detail">
<tr>
    <th><label for="name" class="required">* <fmt:message key="floor.name"/>:</label></th>
    <td>
        <form:input path="name" id="name"/>
        <form:errors path="name" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="title" class="required">* <fmt:message key="floor.title"/>:</label></th>
    <td>
        <form:input path="title" id="title"/>
        <form:errors path="title" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="comment" class="required">* <fmt:message key="floor.comment"/>:</label></th>
    <td>
        <form:input path="comment" id="comment"/>
        <form:errors path="comment" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="active" class="required">* <fmt:message key="floor.active"/>:</label></th>
    <td>
        <form:checkbox path="active" id="active"/>
        <form:errors path="active" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <td></td>
    <td>
        <input type="submit" class="button" name="save" value="Save"/>
      <c:if test="${not empty param.id}">
        <input type="submit" class="button" name="delete" value="Delete" onclick="bCancel=true"/>
      </c:if>
      	<input type="submit" class="button" name="cancel" value="Cancel" onclick="bCancel=true"/>
    </td>
</tr>
</table>
</form:form>


<v:javascript formName="floor" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
