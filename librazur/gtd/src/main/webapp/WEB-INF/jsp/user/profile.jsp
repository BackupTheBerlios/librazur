<%@ include file="/WEB-INF/jsp/html.jsp" %>
<html>
<head><title><fmt:message key="menu.editMyProfile"/></title></head>
<body id="profile">
<h2><fmt:message key="menu.editMyProfile"/></h2>

<%@ include file="/WEB-INF/jsp/messages.jsp" %>

<div class="funform">
<html:form action="/updateuser" focus="oldPassword" styleId="userForm" acceptCharset="ISO-8859-1">
<fieldset>
<html:hidden property="login"/>
<label for="login"><fmt:message key="user.login"/></label><br class="nobr"/>
<html:text property="login" styleId="login" maxlength="32" styleClass="textfield" readonly="true" disabled="true"/>
<br/>
<label for="oldPassword"><fmt:message key="user.oldPassword"/></label><br class="nobr"/>
<html:password property="oldPassword" styleId="oldPassword" maxlength="200" styleClass="textfield"/>
<br/>
<label for="newPassword"><fmt:message key="user.newPassword"/></label><br class="nobr"/>
<html:password property="password" styleId="newPassword" maxlength="200" styleClass="textfield"/>
<br/>
<label for="name"><fmt:message key="user.name"/></label><br class="nobr"/>
<html:text property="name" styleId="name" maxlength="200" styleClass="textfield"/>
<br/>

<authz:authorize ifAnyGranted="ROLE_ADMIN">
<html:checkbox property="admin" styleId="admin" styleClass="checkbox"/>
<label for="admin" class="checkboxlabel"><fmt:message key="user.admin"/></label>
<br/>
</authz:authorize>

<div class="buttons">
<html:submit><fmt:message key="action.update"/></html:submit>
</div>
</fieldset>
</html:form>

<div class="info"><ul>
<li><fmt:message key="info.password.updating"/></li>
<li><fmt:message key="info.password.spaces"/></li>
</ul></div>

</div>

</body>
</html>
