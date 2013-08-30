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

.spanc {
	margin-left:0px;
	width:180px;
}

</style>
<script type="text/javascript">
$(function() {
	$("#operationDlg").dialog({
		autoOpen: false,
		resizable: false,
		modal:true,
	    width: 500,
	    height: 400
	});
	
	$("#dealBtn").html("\u5904\u7406");
	$("#dealBtn").click(function() {
		var doc = $("#doc").val();
		// post info
		$.post("<c:url value='/critical/operate'/>",{
			docId : doc,
			target : $("#targetText").val(),
			result : $("#resultText").val(),
			role : $("input[name='role']:checked").val(),
			success : $("input[name='success']:checked").val()
		},function(data) {
			if (data == true) {
				$("#ctl tbody tr").each(function() {
					var docId = $(this).find(".docId").val();
					if (docId == doc) {
						$(this).remove();
						return false;
					}
				});
			}
			$("#operationDlg").dialog("close");
		});
	});
	
	$("#deleteBtn").click(function() {
		var doc = $("#doc").val();
		// delete info
		if (confirm("\u786e\u8ba4\u5220\u9664\u8be5\u5371\u6025\u503c ?")) {
			$.post("<c:url value='/critical/delete'/>",{
				docId : doc
			},function(data) {
				if (data == true) {
					$("#ctl tbody tr").each(function() {
						var docId = $(this).find(".docId").val();
						if (docId == doc) {
							$(this).remove();
							return false;
						}
					});
				}
				$("#operationDlg").dialog("close");
			});
		}
	});

	$("#ctl tbody tr").each(function() {
		var id = $(this).find(".reqId").html();
		$(this).find(".reqName").html("Name"+id);
		$(this).find(".reqPhone").html("Phone"+id);
		var doc = $(this).find(".operation").html();
		$(this).find(".operation").html("<input type='button' class='span1 btn' value='\u5904\u7406'/><input type='hidden' class='docId' value='"+doc+"'/>");
		$(this).find('.btn').click(function() {
			$("#doc").val(doc);
			$("#historyText").html("");
			$("#historyInfo").css('display','none');
			$("#patientPhoneText").html("");
			$("#patientAddressText").html("");
			$("#requesterNameText").html("");
			$("#requesterSectionText").html("");
			$("#requesterPhoneText").html("");
			$("#targetText").val(id);
			$("#resultText").val("");
			$("#failed-radio").click();
			$("#role-radio").click();
			
			$("#operationDlg").dialog("open");
			// get info
			$.get("<c:url value='/critical/ajax/info'/>",{
				docId : doc
			},function(data) {
				data = jQuery.parseJSON(data);
				if (data.history != "") {
					$("#historyText").html(data.history);
					$("#historyInfo").css('display','block');
				}
				$("#patientPhoneText").html(data.patientPhone);
				$("#patientAddressText").html(data.patientAddress);
				$("#requesterNameText").html(data.requesterName);
				$("#requesterSectionText").html(data.requesterSection);
				$("#requesterPhoneText").html(data.requesterPhone);

			});
		});
	});
});
</script>
</head>

<div>
	<div class="">
	<div class="row clearfix">
		<div class="span9" style="padding-top:4px;">
		<ul class="nav nav-pills">
			<li class="active"><a href="<c:url value='/critical/undeal'/>"><span style="font-size:18px;"><fmt:message key='audit.info.dangerous.undeal' /></span></a></li>
			<li><a href="<c:url value='/critical/dealed' />"><span style="font-size:18px;"><fmt:message key='audit.info.dangerous.dealed' /></span></a></li>
		</ul>
		</div>
	</div>
	</div>
	<display:table name="criticals" id="ctl" cellspacing="0" cellpadding="0" class="table table-hover" pagesize="10" requestURI="">
    	<display:column property="id" titleKey="" sortable="false" escapeXml="true"/>
    	<display:column property="sampleNo" titleKey="patientInfo.sampleNo" sortable="false" escapeXml="true"/>
    	<display:column property="patientName" titleKey="patient.patient.name" sortable="false" style="width:60px;" escapeXml="true" url="/explain/patientList" paramId="patientId" paramProperty="patientId"/>
    	<display:column property="blh" titleKey="patient.blh" sortable="false" style="width:60px;" escapeXml="true"/>
    	<display:column property="section" titleKey="patient.section" sortable="false" escapeXml="true"/>
    	<display:column property="infoValue" titleKey="audit.info.dangerous" sortable="false" escapeXml="true"/>
    	<display:column property="requester" titleKey="requester" sortable="false" class="reqId" escapeXml="true"/>
    	<display:column property="docId" titleKey="operation" sortable="false" class="operation" escapeXml="true"/>
	</display:table>
</div>

<div id="operationDlg" align="left" title='<fmt:message key='audit.info.dangerous' /><fmt:message key="operation" />'>
	<input id="doc" type="hidden" />
	<input id="index" type="hidden">
	<div id="historyInfo" class="alert alert-info" style="margin-bottom:5px;padding-right:8px;">
		<div id="historyText"></div>
	</div>
	<div class="row">
		<label class="spanb"><fmt:message key="patient.info" /> : </label>
		<label class="spanb" id="patientPhoneText"></label>
		<label class="span3" style="width:270px;" id="patientAddressText"></label>
	</div>
	<div class="row">
		<label class="spanb"><fmt:message key="requester.name" /> : </label>
		<label class="spanb" id="requesterNameText"></label>
		<label class="spanb"><fmt:message key="requester.phone" /> : </label>
		<label class="spanb" id="requesterPhoneText"></label>
	</div>
	<div class="row">
		<label class="spanb"><fmt:message key="requester.section" /> : </label>
		<label class="spanc" id="requesterSectionText"></label>
	</div>
	
	<div class="row">
		<label class="spanb"><fmt:message key="role" /> : </label>
		<label class="radio span1" id="role-radio" style="margin-left:0px;margin-bottom:0px;">
			<input type="radio" name="role" value="0" checked><fmt:message key="doctor" />
		</label>
		<label class="radio span1" style="margin-left:0px;margin-bottom:0px;">
			<input type="radio" name="role" value="1"><fmt:message key="nurse" />
		</label>
		<label class="radio span1" style="margin-left:0px;margin-bottom:0px;">
			<input type="radio" name="role" value="2"><fmt:message key="patient" />
		</label>
	</div>
	<div class="row">
		<label class="spanb" style="margin-top:5px;"><fmt:message key="number" /> : </label>
		<input type="text" id="targetText" class="span4">
	</div>
	<div class="row">
		<label class="spanb"><fmt:message key="contact" /> : </label>
		<label class="radio span1" style="margin-left:0px;margin-bottom:0px;">
			<input type="radio" name="success" value="1" ><fmt:message key="success" />
		</label>
		<label class="radio span1" style="margin-left:0px;margin-bottom:0px;">
			<input type="radio" id="failed-radio" name="success" value="0" checked><fmt:message key="failed" />
		</label>
	</div>
	<div class="row">
		<label class="spanb" style="margin-top:5px;"><fmt:message key="contact.info" /> : </label>
		<input type="text" id="resultText" class="span4">
	</div>
	<div class="row">
		<label class="spanb"></label>
		<button id="dealBtn" class="btn btn-info span2" style="margin-left:0px;"></button>
		<button id="deleteBtn" class="btn btn-danger span2" style="margin-left:10px;"><fmt:message key="button.delete" /></button>
	</div>
	
</div>