<%@ include file="/WEB-INF/jsp/html.jsp" %><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html xhtml="true" lang="true">
<head>

<fmt:message key="title" var="defaultTitle"/>
<title><decorator:title default="${defaultTitle}"/></title>

<meta http-equiv="Content-type" content="text/html; charset=ISO-8859-1"></meta>
<meta http-equiv="imagetoolbar" content="false"></meta>
<meta name="MSSmartTagsPreventParsing" content="true"></meta>

<link rel="author" href="<fmt:message key='author'/> - <fmt:message key='homepage'/>"></link>
<link rel="home" title="home" href="<c:url value='/'/>"></link>
<link rel="shortcut icon" href="<c:url value='/favicon.ico'/>" type="image/x-icon" />

<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/css/styles.css'/>"/>

<!--[if IE]>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/css/ie-styles.css'/>"/>
<script type="text/javascript" src="<c:url value='/js/pngfix.js'/>"></script>
<![endif]-->

<script type="text/javascript" src="<c:url value='/js/gtd.js'/>"></script>

<decorator:head/>

</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.onload" writeEntireProperty="true"/>>
<div id="page">

<div id="header"><h1><a href="<c:url value='/'/>">
<img src="<c:url value='/images/gtd.png'/>" alt="<fmt:message key="title"/>"/></a></h1></div>

<c:set var="login"><authz:authentication operation="username"/></c:set>
<c:if test="${not empty login && login != 'anonymousUser'}">
<%-- displayed when an user is authenticated --%>
<div id="loginbox"><p><fmt:message key="connected.as"><fmt:param value="${login}"/></fmt:message></p>
<p><a href="<c:url value='/logoff.html'/>"><fmt:message key="logoff"/></a></p></div>

<div id="actions">
<h2><fmt:message key="menu"/></h2>
<ul>
<li><a id="menu_list" href="<c:url value='/list.html'/>"><fmt:message key="menu.showMyTasks"/></a></li>
<li><a id="menu_profile" href="<c:url value='/editprofile.html'/>"><fmt:message key="menu.editMyProfile"/></a></li>
<authz:authorize ifAnyGranted="ROLE_ADMIN">
<li><a id="menu_adduser" href="<c:url value='/newuser.html'/>"><fmt:message key="menu.addUser"/></a></li>
<li><a id="menu_deleteuser" href="<c:url value='/listuserstodelete.html'/>"><fmt:message key="menu.deleteUser"/></a></li>
</authz:authorize>
</ul>
</div>
</c:if>

<div id="content">
<decorator:body/>
</div>

<div id="footer">
<p>Copyright &copy; 2005 <a href="<fmt:message key='homepage'/>"><fmt:message key="author"/></a></p>
</div>

</div>
</body>
</html:html>
