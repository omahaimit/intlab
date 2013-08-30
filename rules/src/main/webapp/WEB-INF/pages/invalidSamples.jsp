<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="invalidSamplesList.title" /></title>
<meta name="heading"
	content="<fmt:message key='invalidSamplesList.heading'/>" />
<meta name="menu" content="InvalidSamplesMenu" />

<script type="text/javascript">
	function check() {
		var a = /^\d{4}[-]\d{1,2}[-]\d{1,2}$/
		if ((!a.test(document.select.fromDate.value))
				|| (!a.test(document.select.toDate.value))) {
			alert("\u65e5\u671f\u683c\u5f0f\u4e0d\u6b63\u786e!");
			return false;
		} else
			return true;
	}

	$(function() {
		$("#fromDate").datepicker({
			dateFormat : "yy-mm-dd"
		});
		$("#toDate").datepicker({
			dateFormat : "yy-mm-dd"
		});
	});
</script>

</head>

<form class="form-inline" id="select" method="post"
	action="invalidSamples" name="select" onSubmit="return check()">
	<div style="float: left;" class="input-prepend input-append">
		<span class="add-on"><fmt:message key='invalidSamples.fromDate' /></span>
		<input id="fromDate" class="span2" name="fromDate" type="text" /> <span
			class="add-on"><fmt:message key='invalidSamples.toDate' /></span> <input
			id="toDate" class="span2" name="toDate" type="text" />

	</div>

	<div style="float:left; margin-left:80px;">
		<fmt:message key="patients.labDepartment" />
		<select id="labDepartment" name="labDepartment" class="span3">
			<option value="0000000">
				<fmt:message key="labDepartment.0000000" />
			</option>
			<option value="3120800">
				<fmt:message key="labDepartment.3120800" />
			</option>
			<option value="1300200">
				<fmt:message key="labDepartment.1300200" />
			</option>
			<option value="1300106">
				<fmt:message key="labDepartment.1300106" />
			</option>
			<option value="1300202">
				<fmt:message key="labDepartment.1300202" />
			</option>
			<option value="1300105">
				<fmt:message key="labDepartment.1300105" />
			</option>
			<option value="1310000">
				<fmt:message key="labDepartment.1310000" />
			</option>
			<option value="3120400">
				<fmt:message key="labDepartment.3120400" />
			</option>
			<option value="1300300">
				<fmt:message key="labDepartment.1300300" />
			</option>
			<option value="1300107">
				<fmt:message key="labDepartment.1300107" />
			</option>
			<option value="1300102">
				<fmt:message key="labDepartment.1300102" />
			</option>
			<option value="1300101">
				<fmt:message key="labDepartment.1300101" />
			</option>
			<option value="1300700">
				<fmt:message key="labDepartment.1300700" />
			</option>
			<option value="1030703">
				<fmt:message key="labDepartment.1030703" />
			</option>
			<option value="1130103">
				<fmt:message key="labDepartment.1130103" />
			</option>
			<option value="1040102">
				<fmt:message key="labDepartment.1040102" />
			</option>
			<option value="1041008">
				<fmt:message key="labDepartment.1041008" />
			</option>
			<option value="1300600">
				<fmt:message key="labDepartment.1300600" />
			</option>
			<option value="3090101">
				<fmt:message key="labDepartment.3090101" />
			</option>
			<option value="1300602">
				<fmt:message key="labDepartment.1300602" />
			</option>
			<option value="1530100">
				<fmt:message key="labDepartment.1530100" />
			</option>
			<option value="3120401">
				<fmt:message key="labDepartment.3120401" />
			</option>
			<option value="3020300">
				<fmt:message key="labDepartment.3020300" />
			</option>
			<option value="3020100">
				<fmt:message key="labDepartment.3020100" />
			</option>
			<option value="1300000">
				<fmt:message key="labDepartment.1300000" />
			</option>
		</select>
	</div>
	<script type="text/javascript">
		var lab =<%=request.getAttribute("labDepartment")%>;
		if (lab != null) {
			document.getElementById("labDepartment").value = lab;
		}
		if (lab == "0") {
			document.getElementById("labDepartment").value = "0000000";
		}
	</script>

	<button style="margin-left:120px;" type="submit" class="btn btn-info">
		<fmt:message key='button.submit' />
	</button>

</form>

<button type="button" class="btn"
	onclick="location.href='<c:url value="/invalidSamplesform"/>'">
	<fmt:message key='button.add' />
</button>


<display:table name="invalidSamplesList" class="table" requestURI=""
	id="invalidSamplesList" export="true" pagesize="25"
	style="margin-top:10px;">
	<display:column property="barCode" sortable="true"
		href="invalidSamplesform" media="html" paramId="barCode"
		paramProperty="barCode" titleKey="invalidSamples.barCode" />
	<display:column property="barCode" media="csv excel xml pdf"
		titleKey="invalidSamples.barCode" />
	<display:column property="patient.sampleTypeString" sortable="true"
		titleKey="patients.sampleType" />
	<display:column property="patient.section" sortable="true"
		titleKey="patients.section" />
	<display:column property="patient.labDepartmentStr" sortable="true"
		titleKey="patients.labDepartment" />
	<display:column property="rejectTime" sortable="true"
		titleKey="invalidSamples.rejectTime" />
	<display:column property="containerTypeString" sortable="true"
		titleKey="invalidSamples.containerType" />
	<display:column property="labelTypeString" sortable="true"
		titleKey="invalidSamples.labelType" />
	<display:column property="requestionTypeString" sortable="true"
		titleKey="invalidSamples.requestionType" />
	<display:column property="rejectSampleReasonString" sortable="true"
		titleKey="invalidSamples.rejectSampleReason" />
	<display:column property="measureTakenString" sortable="true"
		titleKey="invalidSamples.measureTaken" />
	<display:column property="notes" sortable="true"
		titleKey="invalidSamples.notes" />
	<display:column property="rejectPerson" sortable="true"
		titleKey="invalidSamples.rejectPerson" />



	<display:setProperty name="paging.banner.item_name">
		<fmt:message key="invalidSamplesList.invalidSample" />
	</display:setProperty>

	<display:setProperty name="export.excel.filename">invalidSample.xls</display:setProperty>
	<display:setProperty name="export.csv.filename">invalidSample.csv</display:setProperty>
	<display:setProperty name="export.pdf.filename">invalidSample.pdf</display:setProperty>
</display:table>

<script type="text/javascript">
	var size= '<c:out value="${size}" />';
	if(size=='0'){
		jQuery("#invalidSamplesList thead").css({
			"display":"none"
		});
	}
</script>
