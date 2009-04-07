<%-- SiteMesh has a bug where error pages aren't decorated - hence the full HTML --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        
<%@ include file="/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>Page Not Found</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <link rel="shortcut icon" href="${ctx}/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/deliciouslyblue/theme.css" title="default" />
    <link rel="alternate stylesheet" type="text/css" href="${ctx}/styles/deliciouslygreen/theme.css" title="green" />
    <script type="text/javascript" src="${ctx}/scripts/prototype.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/scriptaculous.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/stylesheetswitcher.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/global.js"></script>
</head>
<body>

<a name="top"></a>
<div id="page">

    <div id="header" class="clearfix">

        <h1 style="cursor: pointer" onclick="location.href='/'">Konfman</h1>

        <div id="branding">
            <a href="http://konfman.sourceforge.net" title="Konfman - Conference room scheduler">
                <img src="/images/logo.jpg" width="203" height="75" alt="Konfman"/></a>
        </div>

        <p>Web based conference room scheduler</p>
    </div>

    <div id="content">

        <div id="main">
            <h1>Page Not Found</h1>
            <p>The page you requested was not found.  You might try returning to the 
            <a href="<c:url value="/"/>">welcome page</a>. While you're here, how 
            about a pretty picture to cheer you up? 
            </p>

            <p style="text-align: center; margin-top: 20px">
                <img style="border: 0" src="<c:url value="/images/404.jpg"/>" alt="Emerald Lake - Western Canada" />
            </p>
        </div>
        
        <div id="sub">
            <h3>User Services</h3>
		
            <ul class="glassList">
				<li><a href="/cal-month.html" title="My Calendar">My Calendar</a></li>
				<li><a href="/myprofile.html?me" title="My Profile">My Profile</a></li>
                <li><a href="/myreservations.html" title="My Reservations">My Reservations</a></li>
                <li><a href="/reservationform.html?dest=myreservations.html" title="Create a new reservation">New Reservation</a></li>

			</ul>

            <img src="/images/image.gif" alt="Click to Change Theme" width="150" height="112" class="right" style="margin: 10px 0 20px 0"
                 onclick="StyleSheetSwitcher.setActive((StyleSheetSwitcher.getActive() == 'default') ? 'green' : 'default')"/>
        </div>

        <div id="nav">
            <div class="wrapper">
                <h2 class="accessibility">Navigation</h2>
                <ul class="clearfix">

                    <li><a href="/" title="Home"><span>Home</span></a></li>
                    <li><a href="/myreservations.html" title="My Reservations"><span>My Reservations</span></a></li>
                    <li><a href="/myprofile.html?me" title="My Profile"><span>My Profile</span></a></li>
		            
                    	<li><a href="/appadmin/" title="Application Administration"><span>Administration</span></a></li>
                    
		            
                    	<li><a href="/roomadmin.html" title="Room Administration"><span>Room Admin</span></a></li>
                    
                    <li><a href="/j_spring_security_logout" title="Log Off"><span>Log Off</span></a></li>

                    <!--  li><a href="/logoff.jsp" title="Log Off"><span>Log Off</span></a></li -->
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
