<%@ page session="false" language="java" isErrorPage="true" %>
<%@ include file="/WEB-INF/jsp/html.jsp" %>
<html>
<head><title><fmt:message key="error.title"/></title>
</head>
<body id="error">
<div id="screen">
<div id="content">
<h2><fmt:message key="error.heading"/></h2>

<%@ include file="/WEB-INF/jsp/messages.jsp" %>

<%-- the following is taken from Appfuse: http://appfuse.dev.java.net --%>

<% if (exception != null) { %>
     <pre><% exception.printStackTrace(new java.io.PrintWriter(out)); %></pre>
<% } else if ((Exception)request.getAttribute("javax.servlet.error.exception") != null) { %>
     <pre><% ((Exception)request.getAttribute("javax.servlet.error.exception"))
                             .printStackTrace(new java.io.PrintWriter(out)); %></pre>
<% } else if (pageContext.findAttribute("org.apache.struts.action.EXCEPTION") != null) { %>
     <bean:define id="exception2" name="org.apache.struts.action.EXCEPTION"
       type="java.lang.Exception"/>
     <c:if test="${exception2 != null}">
       <pre><% exception2.printStackTrace(new java.io.PrintWriter(out));%></pre>
     </c:if>
     <c:if test="${exception2 == null}"><fmt:message key="error.none"/></c:if>
<% } %>
</div>
</body>
</html>
