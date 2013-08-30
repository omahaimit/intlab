<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="auditView.title" /></title>
<meta name="menu" content="IntelAuditAndExplain" />
<script type="text/javascript" src="<c:url value='/scripts/jquery.json-2.3.min.js'/> "></script>
<style type="text/css">
.docId {
	display:none;
}
#operationDlg .row {
	margin-left:6px;
	margin-top:10px;
}

.spanb {
	margin-left:0px;
	width:80px;
}

</style>
<script type="text/javascript">
$(function() {
	$("#date").datepicker({
		dateFormat : "yy-mm-dd"
	});
	
	$("#query").click(function(){
		var date = $("#date").val();
		location.href="<c:url value='/critical/dealed' />?date="+date;
	});
});
</script>
</head>

<div>
	<div class="">
	<div class="row clearfix">
		<div class="span9" style="padding-top:4px;">
		<ul class="nav nav-pills">
			<li><a href="<c:url value='/critical/undeal'/>"><span style="font-size:18px;"><fmt:message key='audit.info.dangerous.undeal' /></span></a></li>
			<li class="active"><a href="<c:url value='/critical/dealed' />"><span style="font-size:18px;"><fmt:message key='audit.info.dangerous.dealed' /></span></a></li>
		</ul>
		</div>
	</div>
	</div>
	<div class="row input-prepend input-append" style="margin-left:10px;">
		<span class="add-on"><fmt:message key='current.dateTime' /></span>
		<input id="date" type="text" value="${date}" class="span2">
		<input class="btn add-on" id="query" value="<fmt:message key='search' />" style="width:50px;"/>
	</div>
	<display:table name="criticals" id="ctl" cellspacing="0" cellpadding="0" class="table table-hover" pagesize="10" requestURI="">
    	<display:column property="id" titleKey="" sortable="false" escapeXml="true"/>
    	<display:column property="sampleNo" titleKey="patientInfo.sampleNo" sortable="false" escapeXml="true"/>
    	<display:column property="patientName" titleKey="patient.patient.name" sortable="false" style="width:60px;" escapeXml="true"/>
    	<display:column property="blh" titleKey="patient.blh" sortable="false" style="width:60px;" escapeXml="true"/>
    	<display:column property="section" titleKey="patient.section" sortable="false" escapeXml="true"/>
    	<display:column property="infoValue" titleKey="audit.info.dangerous" sortable="false" escapeXml="true"/>
    	<display:column property="requester" titleKey="requester" sortable="false" escapeXml="true"/>
    	<display:column property="dealPerson" titleKey="deal.person" sortable="false" escapeXml="true"/>
    	<display:column property="dealTime" titleKey="deal.time" format="{0,date,MM-dd hh:mm}" sortable="false"/>
	</display:table>
</div>