<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="editResult.title"/></title>
    <meta name="heading" content="<fmt:message key='editResult.heading'/>"/>
    <meta name="menu" content="RulesManage"/>
    <script type="text/javascript">
	    function button(obj){
			document.getElementById("buttonresult").value=obj;
		}
	</script>
</head>


<form:form commandName="result" method="post" action="edit" onsubmit="return onFormSubmit(this)" id="editresult">
<form:hidden path="id"/>

<ul>
	<li>
        <appfuse:label styleClass="desc" key="result.content"/>
        <form:errors path="content" cssClass="fieldError"/>
        <form:input path="content" id="content" cssClass="text large" cssErrorClass="text large error"/>
    </li>

	<li style="display:none;">
        <appfuse:label styleClass="desc" key="result.level"/>
        <form:select path="level">
    		<form:option value="1"></form:option>
    		<form:option value="2"></form:option>
    		<form:option value="3"></form:option>
    		<form:option value="4"></form:option>
    		<form:option value="5"></form:option>
    		<form:option value="6"></form:option>
    		<form:option value="7"></form:option>
    		<form:option value="8"></form:option>
    		<form:option value="9"></form:option>
    		<form:option value="10"></form:option>
		</form:select>
    </li>
    
    <li style="display:none;">
        <appfuse:label styleClass="desc" key="result.reject"/>
        <form:errors path="reject" cssClass="fieldError"/>
        <form:input path="reject" id="reject" cssClass="text large" cssErrorClass="text large error"/>
    </li>
    
    <li>
        <appfuse:label styleClass="desc" key="result.category"/>
        <form:errors path="category" cssClass="fieldError"/>
        <form:input path="category" id="category" cssClass="text large" cssErrorClass="text large error"/>
    </li>
    
    <li>
        <appfuse:label styleClass="desc" key="result.percent"/>
        <form:errors path="percent" cssClass="fieldError"/>
        <form:input path="percent" id="percent" cssClass="text large" cssErrorClass="text large error"/>
    </li>
</ul>

<ul>
    <li class="buttonBar bottom">
		<input type="submit" class="button" name="save" onclick="bCancel=false" value="<fmt:message key="button.save"/>"/>
		<input type="submit" class="button" name="cancel" onclick="bCancel=true" value="<fmt:message key="button.cancel"/>"/>
    </li>
</ul>

</form:form>