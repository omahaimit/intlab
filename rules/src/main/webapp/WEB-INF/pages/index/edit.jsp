<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="editIndex.title"/></title>
    <meta name="heading" content="<fmt:message key='editIndex.heading'/>"/>
    <meta name="menu" content="RulesManage"/>
    <script type="text/javascript">
    $(function() {
    	$("#currentHospId").val($("#currentHosp").eq(0).val());
    });
    function HispitalSelected(obj) {
    	for (var i=0;i<obj.length;i++) {
    		if (obj.options[i].selected) {
    			$("#currentHospId").val(obj.options[i].value);
    		}
    	}
    }
    </script>
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

<form:form class="form-horizontal" style="margin-left:-100px;" commandName="index" method="post" action="edit" onsubmit="return true" id="editIndexForm">
	<div class="control-group">
        <appfuse:label styleClass="control-label" key="index.indexId"/>
        <div class="controls">
	        <form:hidden path="id"/>
	        <form:input path="indexId" id="indexId" cssClass="text"/>
        </div>
	</div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="index.name"/>
        <div class="controls">
        	<form:input path="name" id="name" cssClass="text" />
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="index.hospital"/>
        <div class="controls">
        	<form:select path="currentHosp" id="currentHosp" cssClass="selects" onchange="return HispitalSelected(this);" >
       			<form:options items="${hospIdMap}"  />
       		</form:select>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="index.hospitalId"/>
        <div class="controls">
        	<form:input path="currentHospId" id="currentHospId" cssClass="text" />
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="index.sampleFrom"/>
        <div class="controls">
        	<form:select path="sampleFrom" id="sampleFrom" cssClass="selects">
	        	<form:options items="${sampleList}" />
	        </form:select>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="index.algorithm"/>
        <div class="controls">
			<form:select path="diffAlgo" id="diffAlgo" cssClass="selects">
				<form:options items="${algorithmList}" />
	        </form:select>
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="index.unit"/>
        <div class="controls">
        	<form:input path="unit" id="unit" cssClass="text" />
        </div>
    </div>
    <div class="control-group">
        <appfuse:label styleClass="control-label" key="index.type"/>
        <div class="controls">
        	<form:select path="type" id="type" cssClass="selects">
	        	<form:options items="${typeList}"  />
	        </form:select>
        </div>
    </div>
	<div class="control-group">
        <appfuse:label styleClass="control-label" key="index.description"/>
        <div class="controls">
        	<form:input path="description" id="description" cssClass="text"/>
        </div>
    </div>

    <div class="control-group">
    	<label class="control-label" ></label>
 		<div class="controls">
        	<input type="submit" class="btn" style="width:80px;" name="save"  value="<fmt:message key="button.save"/>"/>
        	<input type="button" class="btn" style="width:80px;" name="cancel" onclick="location.href='<c:url value="/index/view"/>?id=${index.id}'" value="<fmt:message key="button.cancel"/>"/>
   		</div>
    </div>

</form:form>