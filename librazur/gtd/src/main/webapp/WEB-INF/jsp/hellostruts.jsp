<%@ page session="false" %>
<%@ include file="/WEB-INF/jsp/html.jsp" %>
<html>
<head><title>${text}</title></head>
<body id="hellostruts">
<h2><fmt:message key="hellostruts"><fmt:param value="${text}"/></fmt:message></h2>
</body>
</html>
