<%@ include file="/WEB-INF/jsp/html.jsp" %>

<c:set var="userName"><c:out value="${user.name}" default="${user.login}"/></c:set>

<fmt:message key="list.task" var="title">
<fmt:param value="${userName}"/>
</fmt:message>

<html>
<head><title>${title}</title></head>
<body id="list">
<h2>${title}</h2>

<%@ include file="/WEB-INF/jsp/messages.jsp" %>

<div class="funform">
<html:form action="/add" focus="category" styleId="taskForm" acceptCharset="ISO-8859-1">
<fieldset>
<label for="category"><fmt:message key="task.category"/></label><br class="nobr"/>
<html:text property="category" styleId="category" maxlength="200" styleClass="textfield"/>
<br/>
<label for="title"><fmt:message key="task.title"/></label><br class="nobr"/>
<html:text property="title" styleId="title" maxlength="200" styleClass="textfield"/>
<br/>
<div class="buttons">
<html:submit><fmt:message key="action.add"/></html:submit>
</div>
</fieldset>
</html:form>
</div>

<c:choose>
  <c:when test="${empty tasks}"><p><fmt:message key="list.empty.task"/></p></c:when>
  <c:otherwise>
    <div class="tasks">
      <c:forEach var="task" items="${tasks}">
      <dl>
        <dt class="icon">
        <c:choose>
          <c:when test="${task.done}">
            <img src="<c:url value='/images/button_ok.png'/>" alt=""/>
          </c:when>
          <c:otherwise>
            <img src="<c:url value='/images/stop.png'/>" alt=""/>
          </c:otherwise>
        </c:choose>
        </dt>
        <dt class="category"><em>${task.category.name}</em></dt>
        <dt class="title"><strong>${task.title}</strong></dt>
        <dd class="desc">
          <fmt:message key="task.created"><fmt:param value="${task.created}"/></fmt:message>
        </dd>
        <dd class="desc">
          <c:choose>
            <c:when test="${task.done}">
              <fmt:message key="task.finished"><fmt:param value="${task.finished}"/></fmt:message>
            </c:when>
            <c:otherwise>
              <fmt:message key="task.finished.not"/>
            </c:otherwise>
          </c:choose>
        </dd>
        <dd class="actions">
          <ul>
          <li>
            <img src="<c:url value='/images/rebuild.png'/>" alt=""/>
            <c:choose>
              <c:when test="${task.done}">
                <a href="<c:url value='/setundone.html'><c:param name='id' value='${task.id}'/></c:url>">
                  <fmt:message key="task.setUndone"/>
                </a>
              </c:when>
              <c:otherwise>
                <a href="<c:url value='/setdone.html'><c:param name='id' value='${task.id}'/></c:url>">
                  <fmt:message key="task.setDone"/>
                </a>
              </c:otherwise>
            </c:choose>
          </li>
          <li>
            <img src="<c:url value='/images/trashcan_full.png'/>" alt=""/>
            <a href="<c:url value='/delete.html'><c:param name='id' value='${task.id}'/></c:url>">
              <fmt:message key="task.delete"/>
            </a>
          </li>
        </dd>
      </dl>
      </c:forEach>
    </div>
  </c:otherwise>
</c:choose>

</body>
</html>
