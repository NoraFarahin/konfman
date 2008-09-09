<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="reservationForm.title"/></title>
    <link  href="${ctx}/styles/calendar.css"  type="text/css"  rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/scripts/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/lang/calendar-en.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/timepicker.js"></script>
</head>
<p>Please fill in reservation's information below:</p>
<form:form commandName="reservation" method="post" action="reservationform.html?roomId=1" onsubmit="return validateUser(this)" id="reservationForm">
<form:errors path="*" cssClass="error"/>
<form:hidden path="id"/>
<table class="detail">
<tr>
    <th><label for="date"  class="required">*<fmt:message key="reservation.date"/>:</label></th>
    <td>
        <form:input path="date" id="date" size="11"/>
        <button id="dateCal" type="button" class="button"> ... </button> [<fmt:message key="date.format"/>]
        <form:errors path="date" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="startTime"  class="required">*<fmt:message key="reservation.startTime"/>:</label></th>
    <td>
        <form:input path="startTime" id="startTime" size="11"/>
        <button id="startTimePick" type="button" class="button" alt="Pick a Time!" onClick="selectTime(this,startTime)"> ... </button>
        <form:errors path="startTime" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="endTime"  class="required">*<fmt:message key="reservation.endTime"/>:</label></th>
    <td>
        <form:input path="endTime" id="endTime" size="11"/>
        <button id="endTimePick" type="button" class="button" onClick="selectTime(this,endTime)"> ... </button>
        <form:errors path="endTime" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="comment" class="required">* <fmt:message key="reservation.comment"/>:</label></th>
    <td>
        <form:input path="comment" id="comment"/>
        <form:errors path="comment" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="room" >*<fmt:message key="reservation.room.name"/>:</label></th>
    <td>
    	<c:if test="${not empty reservation.room}">
        	<input name="roomName" value="${reservation.room.floor.name}-${reservation.room.title}" readonly="true"/> 
		</c:if>
    	<c:if test="${empty reservation.room}">
        	<input name="roomName" value="No Room Selected" readonly="true"/> 
		</c:if>
        <input type="submit" class="button" name="_target2" value="Select/Change Room"/>
        <form:errors path="room" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="user" >*<fmt:message key="user.fullName"/>:</label></th>
    <td>
        <input name="userName" value="${reservation.user.fullName}" readonly="true"/>
        <form:errors path="user" cssClass="fieldError"/>
        <sec:authorize ifAnyGranted="ROLE_APP-ADMIN,ROLE_ROOM-ADMIN"> 
        	<input type="submit" class="button" name="_target1" value="Select/Change User"/>
        </sec:authorize>
    </td>
</tr>
<tr>
    <td></td>
    <td>
      <input type="submit" class="button" name="_finish" value="Save"/>
      <!-- TODO block this button for someone other than an admin or the user himself -->
      <c:if test="${not empty param.id}">
        <input type="submit" class="button" name="_finish" value="Delete" onclick="bCancel=true"/>
      </c:if>
      	<input type="submit" class="button" name="_cancel" value="Cancel" onclick="_Cancel=true"/>
    </td>
</tr>
</table>
</form:form>


<v:javascript formName="reservation" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
<script type="text/javascript">
    Form.focusFirstElement($('reservationForm'));
    Calendar.setup(
    {
        inputField  : "date",      // id of the input field
        ifFormat    : "%m/%d/%Y",      // the date format
        button      : "dateCal"    // id of the button

    }
    );
</script>
