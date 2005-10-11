<%@ page session="false" %>
<%@ include file="/WEB-INF/jsp/html.jsp" %>
<html>
<head>
<title><fmt:message key="logon"/></title>
</head>
<body id="login" onload="init_logon()">
<h2><fmt:message key="logon"/></h2>

<c:if test="${error}">
<div id="errors"><ul>
<li><fmt:message key="logon.error"/></li>
</ul></div>
</c:if>

<div class="funform">
<form action="<c:url value='/j_acegi_security_check'/>" method="post">
<fieldset>
<label for="j_username"><fmt:message key="user.login"/></label><br class="nobr"/>
<input type="text" name="j_username" id="j_username" maxlength="32" class="textfield"/>
<br/>
<label for="j_password"><fmt:message key="user.password"/></label><br class="nobr"/>
<input type="password" name="j_password" id="j_password" maxlength="200" class="textfield"/>
<br/>
<div class="buttons">
<input type="submit" value="<fmt:message key='action.logon'/>"/>
</div>
</fieldset>
</form>
</div>

</body>
</html>
