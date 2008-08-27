<%@ include file="/taglibs.jsp"%>

<%@ page import="org.springframework.security.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.Authentication" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<html>
  <head>
    <title>Log Off</title>
  </head>

  <body>
    <c:if test="${not empty param.login_error}">
      <font color="red">
        Your 'Exit User' attempt was not successful, try again.<BR><BR>
        Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
      </font>
    </c:if>

    <form action="<c:url value='j_spring_security_logout'/>" method="POST">
        <button name="exit" type="submit">Exit</button>

    </form>

  </body>
</html>
