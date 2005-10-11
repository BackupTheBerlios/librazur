<logic:messagesPresent>
<div id="errors">
<ul>
<html:messages id="error"><li>${error}</li></html:messages>
</ul></div>
</logic:messagesPresent>

<logic:messagesPresent message="true">
<div id="messages"><ul>
<html:messages id="message" message="true"><li>${message}</li>
</html:messages>
</ul></div>
</logic:messagesPresent>
