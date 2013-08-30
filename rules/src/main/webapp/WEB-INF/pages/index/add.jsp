<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="addIndex.title"/></title>
    <meta name="heading" content="<fmt:message key='addIndex.heading'/>"/>
    <meta name="menu" content="RulesManage"/>
</head>

<body id="addIndex"/>

<spring:bind path="index.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">    
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon" />
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

<div class="separator"></div>

<form:form commandName="index" method="post" action="add" onsubmit="return true" id="addIndexForm">
<ul>
	<li>
        <appfuse:label styleClass="desc" key="index.indexId"/>
        <form:input path="indexId" id="indexId" cssClass="text large" cssErrorClass="text large error"/>
        <form:errors path="indexId" cssClass="fieldError"></form:errors>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="index.name"/>
        <form:input path="name" id="name" cssClass="text large" cssErrorClass="text large error"/>
        <form:errors path="name" cssClass="fieldError"></form:errors>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="index.sampleFrom"/>
        <form:select path="sampleFrom" id="sampleFrom" cssClass="selects large">
        	<form:options items="${sampleList}" cssClass="text medium" />
        </form:select>
        <form:errors path="sampleFrom" cssClass="fieldError"></form:errors>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="index.algorithm"/>
        <form:select path="diffAlgo" id="diffAlgo" cssClass="selects large">
        	<form:options items="${algorithmList}" cssClass="text medium" />
        </form:select>
        <form:errors path="diffAlgo" cssClass="fieldError"></form:errors>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="index.unit"/>
        <form:input path="unit" id="unit" cssClass="text large" cssErrorClass="text large error"/>
        <form:errors path="unit" cssClass="fieldError"></form:errors>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="index.type"/>
        <form:select path="type" id="type" cssClass="selects large">
        	<form:options items="${typeList}" cssClass="text medium" />
        </form:select>
        <form:errors path="type" cssClass="fieldError"></form:errors>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="index.description"/>
        <form:input path="description" id="description" cssClass="text large" cssErrorClass="text large error"/>
        <form:errors path="description" cssClass="fieldError"></form:errors>
    </li>
    <li class="buttonBar bottom">
        <input type="submit" class="btn" name="add" value="<fmt:message key="button.add"/>"/>
        <input type="button" class="btn" name="cancel" onclick="location.href='<c:url value="/index/list"/>'" value="<fmt:message key="button.cancel"/>"/>
    </li>
</ul>
</form:form>
