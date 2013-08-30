<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="activeUsers.title" /></title>
<meta name="menu" content="AdminMenu" />
</head>
<body id="pushData">

	<div class="span10">
		<h2>Control Push Data</h2>
		<div class="row-fluid">
			<blockquote>
				<small>Middle URL</small>
				<input type="text" id="patientUrl" class="span7" value="${patientUrl}">
			</blockquote>
		</div>
		<div class="row-fluid input-prepend input-append">
			<span class="add-on">Push Data Interval</span> <input id="inputInterval" style="width: 80px;" type="text"
				value="${interval}" <c:if test="${systemStatus}">disabled</c:if> /> <span class="add-on">millsecond</span>

			<input id="syncStartBtn" type="button" class="btn btn-info" 
				<c:if test="${systemStatus == true}">value="stop"</c:if>
				<c:if test="${systemStatus == false}">value="start"</c:if>>
		</div>
	</div>
	<script type="text/javascript">
	$(function(){
		$("#syncStartBtn").click(function(){
			var btnVal = $(this).val();
			var interval = $("#inputInterval").val();
			var patientUrl = $("#patientUrl").val();
			$.post("<c:url value='/admin/push/ajax/switch'/>", {operate:btnVal,interval:interval,patientUrl:patientUrl}, function(result) {
				if (result == 1) {
					if (btnVal == "start") {
						$("#inputInterval").attr("disabled", "disabled");
						$("#syncStartBtn").val("stop");
					} else if (btnVal == "stop") {
						$("#inputInterval").removeAttr("disabled");
						$("#syncStartBtn").val("start");
					}
				} else {
					alert("Failed!!!")
				}
			});
		});
	});
	</script>
</body>