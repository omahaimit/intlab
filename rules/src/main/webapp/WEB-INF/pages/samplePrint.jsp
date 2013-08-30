<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<%@ include file="/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/styles/jquery.jqplot.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/jquery-1.8.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-ui-1.9.1.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.json-2.3.min.js'/> "></script>
<%-- <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " /> --%>

<link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap/css/bootstrap.css'/>" />
<!--[if lte IE 6]>
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap/css/bootstrap-ie6.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap/css/ie.css'/>" />
<![endif]-->

<script type="text/javascript">
	function getSampleData(text) {
		jQuery("#rowed3").jqGrid({
		   	url:"<c:url value='/explain/audit/samplePrintData'/>?text="+text,
			datatype: "json",
			jsonReader : {repeatitems : false},  
		   	colNames:['<fmt:message key="audit.sample.number"/>','<fmt:message key="audit.status"/>', '<fmt:message key="audit.mark"/>', '<fmt:message key="testValue"/>','<fmt:message key="content"/>'],
		   	colModel:[
		   		{name:'sample',index:'sample',width:80},
		   		{name:'status',index:'status',width:60,sortable:false},
		   		{name:'mark',index:'mark',width:40, sortable:false},
		   		{name:'value',index:'value',width:200, sortable:false},
		   		{name:'reason',index:'reason',width:240,sortable:false},
		   	],
		   	height: '100%',
		   	rowNum: 500,
		   	rownumbers: true
		});
	}
	
	$(function() {
		var text = '<c:out value="${text}" />';
		getSampleData(text);
		jQuery("#rowed3_sample").css({
			"width":"150px",
			"text-align":"center"
		});
		jQuery("#rowed3_status").css({
			"width":"90px",
			"text-align":"center"
		});
		jQuery("#rowed3_mark").css({
			"width":"70px",
			"text-align":"center"
		});
		jQuery("#rowed3_reason").css({
			"width":"300px",
			"text-align":"center"
		});
	});
	
</script>
<style type="text/css">

#jqgh_rowed3_sample {
	margin-left:50px;
}
#jqgh_rowed3_status {
	margin-left:40px;
}
#jqgh_rowed3_mark {
	margin-left:10px;
}
.ui-state-default {
	width:20px;
}
</style>
</head>
<body>
	<h3 id="sampleTitle"><fmt:message key="audit.samplePrint"/></h3>
	<div id="midContent">
		<table class="table" id="rowed3"></table>
	</div>
</body>
</html>