<%@ include file="/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><decorator:title default="Welcome"/> | <fmt:message key="webapp.name"/></title>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link rel="shortcut icon" href="${ctx}/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/deliciouslygreen/theme.css" title="default" />
    <link rel="alternate stylesheet" type="text/css" href="${ctx}/styles/deliciouslyred/theme.css" title="alt" />
    <script type="text/javascript" src="${ctx}/scripts/prototype.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/scriptaculous.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/stylesheetswitcher.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/global.js"></script>
    <decorator:head/>
</head>
<body>
<a name="top"></a>
<div id="page">

    <div id="header" class="clearfix">

        <h1 style="cursor: pointer" onclick="location.href='${ctx}/'">Konfman</h1>

        <div id="branding">
            <a href="http://konfman.sourceforge.net" title="Konfman - Conference room scheduler">
                <img src="${ctx}/images/logo.jpg" width="203" height="75" alt="Konfman"/></a>
        </div>

        <p><fmt:message key="webapp.tagline"/></p>
    </div>

    <div id="content">

        <div id="main">
            <h1><decorator:title/></h1>
            <%@ include file="/messages.jsp"%>
            <decorator:body/>

            <div id="underground"><decorator:getProperty property="page.underground"/></div>
        </div>

        <!-- TODO put this into a tag which detemines users rights -->
        <div id="sub">
            <h3>Administration Functions</h3>

             <ul class="glassList">
                <li><a href="./userlist.html">Administer Users</a></li>
                <li><a href="./buildings.html">Administer Facilities</a></li>
            </ul>

            <img src="${ctx}/images/image.gif" alt="Click to Change Theme" width="150" height="112" class="right" style="margin: 10px 0 20px 0"
                 onclick="StyleSheetSwitcher.setActive((StyleSheetSwitcher.getActive() == 'default') ? 'alt' : 'default')"/>
        </div>

        <div id="nav">
            <div class="wrapper">
                <h2 class="accessibility">Navigation</h2>
                <ul class="clearfix">
                    <li><a href="${ctx}/" title="Home"><span>Home</span></a></li>
                    <li><a href="${ctx}/myreservations.html" title="My Reservations"><span>My Reservations</span></a></li>
                    <li><a href="${ctx}/myprofile.html" title="My Profile"><span>My Profile</span></a></li>
                    <li><a href="${ctx}/reservationform.html?dest=myreservations.html" title="Create a new reservation"><span>New Reservation</span></a></li>
                   	<li><a href="${ctx}/appadmin/" title="Application Administration"><span>Administration</span></a></li>
		            <sec:authorize ifAllGranted="ROLE_ROOM-ADMIN">
                    	<li><a href="${ctx}/roomadmin.html" title="Room Administration"><span>Room Admin</span></a></li>
                    </sec:authorize>
                    <li><a href="${ctx}<c:url value='/j_spring_security_logout'/>" title="Log Off"><span>Log Off</span></a></li>
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
