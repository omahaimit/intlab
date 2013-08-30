
<%@ include file="/common/taglibs.jsp" %>
 
<head>
    <title><fmt:message key="invalidSamplesDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='invalidSamplesDetail.heading'/>"/>
    
    <script type="text/javascript">
	    function getData(obj,event) {
	    	var e=e||event;
	    	var key=event.keyCode;;
	    	if(navigator.appName=="Netscape"){
	    		key=e.which;
	    	}else{
	    		key=event.keyCode;
	    	}
	    	switch(key){
	    		case 13 : window.location.href="invalidSamplesform.jsp?barCode="+obj.value;
	    	}
	    }
    	function button(obj){
    		document.getElementById("buttonresult").value=obj;
    	}
    </script>
</head>

<form:form commandName="invalidSamples" method="post" action="invalidSamplesform" name="invalidSamplesForm">
<form:errors path="*" cssClass="error" element="div"/>
<form:hidden path="patient.result"/>
<form:hidden path="buttonresult"/>

<div class="clearfix">
	<div id="patient-info" class="alert alert-info" style="margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
		<div class="pItem">
			<span class="pLabel"><fmt:message key="invalidSamples.barCode"/></span>
			<form:errors path="barCode" cssClass="fieldError"/>
			<form:input path="barCode" id="barCode" cssClass="text medium" 
				cssErrorClass="text medium error" maxlength="50"
					onkeypress="getData(this,event);" title=""/>
		
			<span class="pLabel" style="margin-left:20px;"><fmt:message key="patients.section"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.section}"/></b></span>

			<span class="pLabel" style="margin-left:20px;"><fmt:message key="patients.patientName"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.patientName}"/></b></span>
			
			<span class="pLabel" style="margin-left:20px;"><fmt:message key="patients.sex"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.sexStr}"/></b></span>

			<span class="pLabel" style="margin-left:20px;"><fmt:message key="patients.age"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.age}"/></b></span>
			
			<span class="pLabel" style="margin-left:20px;"><fmt:message key="patients.treatmentType"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.treatmentTypeString}"/></b></span>
		</div>

		<div class="pItem">
			<span class="pLabel"><fmt:message key="patients.examinaim"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.examinaim}"/></b></span>
			
			<span class="pLabel" style="margin-left:20px;"><fmt:message key="patients.sampleNo"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.sampleNo}"/></b></span>
			
			<span class="pLabel" style="margin-left:20px;"><fmt:message key="invalidSamples.rejectPerson"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.rejectPerson}"/></b></span>

			<span class="pLabel" style="margin-left:20px;"><fmt:message key="patients.receivePerson"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.receivePerson}"/></b></span>

			<span class="pLabel" style="margin-left:20px;"><fmt:message key="patients.sampleType"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.sampleTypeString}"/></b></span>

			<span class="pLabel" style="margin-left:20px;"><fmt:message key="patients.labDepartment"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.labDepartmentStr}"/></b></span>
		</div>
		
		<div class="pItem">
			<span class="pLabel"><fmt:message key="patients.receiveTime"/></span>
			<span class="pText"><b><c:out value="${invalidSamples.patient.receiveTime}"/></b></span>
			
			<span class="pLabel" style="margin-left:20px;"><fmt:message key="invalidSamples.rejectTime"/></span>
		    <form:errors path="rejectTime" cssClass="fieldError"/>
		    <form:input path="rejectTime" id="rejectTime" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
		</div>
	</div>
</div>

<table class="table">
	<tr height="60">
    	<td><appfuse:label key="invalidSamples.rejectSampleReason"/>
    	<form:select path="rejectSampleReason">
    		<form:option value="1"><fmt:message key="sampleReason.1"/></form:option>
    		<form:option value="2"><fmt:message key="sampleReason.2"/></form:option>
    		<form:option value="3"><fmt:message key="sampleReason.3"/></form:option>
    		<form:option value="4"><fmt:message key="sampleReason.4"/></form:option>
    		<form:option value="5"><fmt:message key="sampleReason.5"/></form:option>
    		<form:option value="6"><fmt:message key="sampleReason.6"/></form:option>
    		<form:option value="7"><fmt:message key="sampleReason.7"/></form:option>
    		<form:option value="8"><fmt:message key="sampleReason.8"/></form:option>
    		<form:option value="9"><fmt:message key="sampleReason.9"/></form:option>
    		<form:option value="10"><fmt:message key="sampleReason.10"/></form:option>
    		<form:option value="11"><fmt:message key="sampleReason.11"/></form:option>
    		<form:option value="12"><fmt:message key="sampleReason.12"/></form:option>
    		<form:option value="13"><fmt:message key="sampleReason.13"/></form:option>
    		<form:option value="14"><fmt:message key="sampleReason.14"/></form:option>
    		<form:option value="15"><fmt:message key="sampleReason.15"/></form:option>
    		<form:option value="16"><fmt:message key="sampleReason.16"/></form:option>
    		<form:option value="17"><fmt:message key="sampleReason.17"/></form:option>
    		<form:option value="18"><fmt:message key="sampleReason.18"/></form:option>
    		<form:option value="19"><fmt:message key="sampleReason.19"/></form:option>
    		
		</form:select></td>
    	<td><appfuse:label key="invalidSamples.measureTaken"/>
    	<form:select path="measureTaken">
  			<form:option value="1"><fmt:message key="measureTaken.1"/></form:option>
  			<form:option value="2"><fmt:message key="measureTaken.2"/></form:option>
  			<form:option value="3"><fmt:message key="measureTaken.3"/></form:option>
  			<form:option value="4"><fmt:message key="measureTaken.4"/></form:option>
  			<form:option value="5"><fmt:message key="measureTaken.5"/></form:option>
		</form:select></td>
    </tr>    
	<tr>
		<td><fieldset>
			<appfuse:label key="invalidSamples.requestionType"/>
            <%-- <legend><fmt:message key="invalidSamples.requestionType"/></legend> --%>
            <form:radiobutton path="requestionType" value="1" checked="true"/><fmt:message key="requestionType.1"/>
            <form:radiobutton path="requestionType" value="2"/><fmt:message key="requestionType.2"/>
            <form:radiobutton path="requestionType" value="3"/><fmt:message key="requestionType.3"/>	
        </fieldset></td>
        <td><fieldset>
        	<appfuse:label key="invalidSamples.labelType"/>
            <%-- <legend><fmt:message key="invalidSamples.labelType"/></legend> --%>
            <form:radiobutton path="labelType" value="1" checked="true"/><fmt:message key="labelType.1"/>
            <form:radiobutton path="labelType" value="2"/><fmt:message key="labelType.2"/>
            <form:radiobutton path="labelType" value="3"/><fmt:message key="labelType.3"/>
            <form:radiobutton path="labelType" value="4"/><fmt:message key="labelType.4"/>
        </fieldset></td>
        <td><fieldset>
        	<appfuse:label key="invalidSamples.containerType"/>
            <%-- <legend><fmt:message key="invalidSamples.containerType"/></legend> --%>
	        <form:radiobutton path="containerType" value="1" checked="true"/><fmt:message key='containerType.1'/>
		    <form:radiobutton path="containerType" value="2"/><fmt:message key="containerType.2"/>
        </fieldset></td>
    </tr>
	<tr>
		<td>
			<appfuse:label styleClass="desc2" key="invalidSamples.notes"/>
	        <form:errors path="notes" cssClass="fieldError"/>
	        <form:textarea path="notes" id="notes"  rows="4" cols="40" cssStyle="overflow:auto"/>
		</td>
	</tr>
</table>
<ul>
    <li>
        <input type="button" class="btn" name="save" value="<fmt:message key="button.save"/>" onclick="button('s');submit()"/>
        <c:if test="${not empty invalidSamples.barCode}">
        <input type="button" class="btn" name="delete" onclick="confirmDelete('\u6837\u672c');button('d');submit()"
            value="<fmt:message key="button.delete"/>" />
        </c:if>
        <input type="button" class="btn" name="cancel" value="<fmt:message key="button.cancel"/>" onclick="button('c');submit()"/>
    </li>
</ul>
</form:form>

<script type="text/javascript">
    Form.focusFirstElement($('invalidSamplesForm'));
</script>