<%@ include file="/WEB-INF/jsp/html.jsp" %>
<html>
<head><title><fmt:message key="list.usersToDelete"/></title></head>
<body id="deleteuser"">
<h2><fmt:message key="list.usersToDelete"/></h2>

<%@ include file="/WEB-INF/jsp/messages.jsp" %>

<c:choose>
  <c:when test="${empty users}"><p><fmt:message key="list.empty"/></p></c:when>
  <c:otherwise>
    <div class="users">
      <c:forEach var="user" items="${users}">
      <dl>
        <dt class="image"><img src="<c:url value='/images/personal.png'/>" width="32" height="32" alt=""/></dt>
        <dt class="login"><strong>${user.login}</strong> <c:if test="${not empty user.name}"> (<em>${user.name}</em>)</c:if></dt>
        <dd class="desc">
          <fmt:message key="user.role">
          <fmt:param>
          <c:choose>
            <c:when test="${user.admin}"><fmt:message key="user.admin"/></c:when>
            <c:otherwise><fmt:message key="user.user"/></c:otherwise>
          </c:choose>
          </fmt:param>
          </fmt:message>
        </dd>
        <dd class="desc"><fmt:message key="user.taskCount"><fmt:param value="${fn:length(user.tasks)}"/></fmt:message></dd>
        <dd class="actions">
          <ul>
          <li>
          <img src="<c:url value='/images/trashcan_full.png'/>" alt=""/>
          <a class="actionUserDelete" href="<c:url value='/deleteuser.html'><c:param name='login' value='${user.login}'/></c:url>">
            <fmt:message key='user.delete'/>
          </a>
          </li>
          </ul>
        </dd>
      </dl>
      </c:forEach>
    </div>
  </c:otherwise>
</c:choose>

</body>
</html>
