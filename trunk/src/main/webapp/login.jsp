<%@ include file="/taglibs.jsp" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Welcome</title>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/deliciouslyblue/theme.css" title="default" />
    <link rel="alternate stylesheet" type="text/css" href="${ctx}/styles/deliciouslygreen/theme.css" title="green" />
    <script type="text/javascript" src="${ctx}/scripts/stylesheetswitcher.js"></script>
</head>
<body>
	<a name="top"></a>
	<div id="page">
	
    <div id="header" class="clearfix">

        <h1 style="cursor: pointer" onclick="location.href='${ctx}/'">Konfman</h1>

        <div id="branding">
            <a href="http://konfman.sourceforge.net" title="Konfman - Conference room scheduler">
                <img src="${ctx}/images/powered-by-appfuse.gif" width="203" height="75" alt="Konfman"/></a>
        </div>

        <p>Konfman</p>
    </div>

    <div id="content">

        <div id="main">
			<h1>Login</h1>
			<c:if test="${not empty param.login_error}">
			   <font color="red">
			     Your login attempt was not successful, try again.<br/><br/>
			   </font>
			 </c:if>
			
			<form action="j_spring_security_check">
				<table>
					<tr>
						<td><label for="j_username">Username</label></td>
						<td><input type="text" name="j_username" id="j_username"/></td>
					</tr>
					<tr>
						<td><label for="j_password">Password</label></td>
						<td><input type="password" name="j_password" id="j_password"/></td>
					</tr>
					<tr>
						<td align="right"><input type='checkbox' name='_spring_security_remember_me'/></td>
						<td> Remember me on this computer.</td>
					</tr>
					<tr>
						<td><button type="submit">Login</button></td>
					</tr>
				</table>			
			</form>
        </div>
	
        <div id="nav">
            <div class="wrapper">
                <h2 class="accessibility">Navigation</h2>
                <ul class="clearfix">
                    <li><a href="${ctx}/" title="Home"><span>Home</span></a></li>
                    <li><a href="${ctx}/myreservations.html" title="My Reservations"><span>My Reservations</span></a></li>
                    <li><a href="${ctx}/myprofile.html?me" title="My Profile"><span>My Profile</span></a></li>
                    <li><a href="${ctx}/reservationform.html?dest=myreservations.html" title="Create a new reservation"><span>New Reservation</span></a></li>
                    <li><a href="${ctx}/logoff.jsp" title="Log Off"><span>Log Off</span></a></li>
                </ul>
            </div>
        </div><!-- end nav -->
    </div><!-- end content -->

    <div id="footer">
        <p>
            <a href="http://validator.w3.org/check?uri=referer">Valid XHTML 1.0</a> |
            <a href="http://www.oswd.org/design/preview/id/2634">Deliciously Blue</a> from <a href="http://www.oswd.org/">OSWD</a> |
            Design by <a href="http://www.oswd.org/user/profile/id/8377">super j man</a>
        </p>
    </div>
</div>
</body>
</html>
	