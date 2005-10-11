<%@ page session="false" %>
<%@ include file="/WEB-INF/jsp/html.jsp" %>
<html>
<head><title><fmt:message key="title"/></title></head>
<body id="home">
<h2><fmt:message key="welcome"/></h2>

<c:set var="clickhere"><a href="<c:url value='/list.html'/>"><fmt:message key="welcome.clickhere"/></a></c:set>

<p><fmt:message key="welcome.p1"/></p>
<p><fmt:message key="welcome.p2"><fmt:param value="${clickhere}"/></fmt:message></p>

</body>
</html>