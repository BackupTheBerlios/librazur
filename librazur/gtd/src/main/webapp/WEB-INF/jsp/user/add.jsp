<%@ include file="/WEB-INF/jsp/html.jsp" %>
<html>
<head><title><fmt:message key="menu.addUser"/></title></head>
<body id="adduser">
<h2><fmt:message key="menu.addUser"/></h2>

<%@ include file="/WEB-INF/jsp/messages.jsp" %>

<div class="funform">
<html:form action="/adduser" focus="login" styleId="userForm" acceptCharset="ISO-8859-1">
<fieldset>
<label for="login"><fmt:message key="user.login"/></label><br class="nobr"/>
<html:text property="login" styleId="login" maxlength="32" styleClass="textfield"/>
<br/>
<label for="password"><fmt:message key="user.password"/></label><br class="nobr"/>
<html:password property="password" styleId="password" maxlength="200" styleClass="textfield"/>
<br/>
<label for="name"><fmt:message key="user.name"/></label><br class="nobr"/>
<html:text property="name" styleId="name" maxlength="200" styleClass="textfield"/>
<br/>
<html:checkbox property="admin" styleId="admin" styleClass="checkbox"/>
<label for="admin" class="checkboxlabel"><fmt:message key="user.admin"/></label>
<br/>
<div class="buttons">
<html:submit><fmt:message key="action.add"/></html:submit>
</div>
</fieldset>
</html:form>
</div>

<div class="info"><ul>
<li><fmt:message key="info.password.spaces"/></li>
</ul></div>

</body>
</html>
