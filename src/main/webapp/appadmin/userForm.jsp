<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="userForm.title"/></title>
    <%-- Calendar Setup - put in decorator if needed in multiple pages --%>
    <link  href="${ctx}/styles/calendar.css"  type="text/css"  rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/scripts/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/lang/calendar-en.js"></script>
</head>

<p>Please fill in user's information below:</p>

<form:form commandName="user" method="post" action="userform.html" onsubmit="return validateUser(this)" id="userForm">
<form:errors path="*" cssClass="error"/>
<form:hidden path="id"/>
<table class="detail">
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
        <form:input path="password" id="password"/>
        <form:errors path="password" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="adminStatus" class="required">* <fmt:message key="user.adminStatus"/>:</label></th>
    <td>
        <form:input path="adminStatus" id="adminStatus"/>
        <form:errors path="adminStatus" cssClass="fieldError"/>
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
    <th><label for="birthday"><fmt:message key="user.birthday"/>:</label></th>
    <td>
        <form:input path="birthday" id="birthday" size="11"/>
        <button id="birthdayCal" type="button" class="button"> ... </button> [<fmt:message key="date.format"/>]
        <form:errors path="birthday" cssClass="fieldError"/>
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

<h2>Rooms Administered by ${user.fullName}</h2>
<button type="submit" name="_target2" value="true" style="float: right; margin-top: -30px; width: 100px">Add Room</button>
</form:form>
<display:table name="user.administeredRooms" class="table" requestURI="" id="rooms" export="true" pagesize="10">
	<display:setProperty name="export.pdf.filename" value="rooms.pdf"/>
    <display:column property="id" sortable="true" href="roomform.html" media="html"
        paramId="id" paramProperty="id" titleKey="room.id"/>
    <display:column property="name" sortable="true" titleKey="room.name" escapeXml="true"/>
    <display:column property="title" sortable="true" titleKey="room.title" escapeXml="true"/>
    <display:column sortable="true" href="userform.html" media="html"
        paramId="removeRoomId" paramProperty="id">Remove</display:column>
</display:table>


<script type="text/javascript">
    Form.focusFirstElement($('userForm'));
    Calendar.setup(
    {
        inputField  : "birthday",      // id of the input field
        ifFormat    : "%m/%d/%Y",      // the date format
        button      : "birthdayCal"    // id of the button
    }
    );
</script>

<v:javascript formName="user" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
<script type="text/javascript">
function readMore() {
    var main = document.getElementById("defaultFloor");
    var more = document.getElementById("selectFloor");
    if (main.style.display == "") {
        main.style.display = "none";
        more.style.display = "";
    } else {
        more.style.display = "none";
        main.style.display = "";
    }
}
</script>