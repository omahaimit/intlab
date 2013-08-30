<%@ include file="/common/taglibs.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><fmt:message key="statistic.title" /></title>

<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.tablednd_0_5.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.json-2.3.min.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.form.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.bgiframe-2.1.2.js'/> "></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" /> 

<script type="text/javascript">
	function statistic() {
		var time = $("#time").val();
		var code = $("#code").val();
		var from = $("#from").val();
		var to = $("#to").val();
		
		jQuery("#statistic_table").jqGrid({
			url:"<c:url value='/statistic/sample'/>?code="+code+"&from="+from+"&to="+to+"&time="+time,
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['ID','<fmt:message key="project"/>','<fmt:message key="num"/>','<fmt:message key="average"/>','<fmt:message key="max"/>','<fmt:message key="min"/>','<fmt:message key="standardDeviation"/>','<fmt:message key="coefficientOfVariation"/>'],
		   	colModel:[{name:'id',index:'id',hidden:true,sortable:false},
		   	    {name:'name',index:'name',width:120,sortable:false},
		   	 	{name:'num',index:'num',width:60,sortable:false},
		   	 	{name:'average',index:'average',width:100,sortable:false},
		   	 	{name:'max',index:'max',width:60,sortable:false},
		   	 	{name:'min',index:'min',width:60,sortable:false},
		   	 	{name:'standardDeviation',index:'standardDeviation',width:100,sortable:false},
		   	 	{name:'coefficientOfVariation',index:'coefficientOfVariation',width:100,sortable:false}],
		   	rowNum: 80,
		   	height:'100%'
		});
	}

</script>
</head>

<div class='form-inline'>
	<input id="time" class="span2" type="text" placeholder="20130101"/>
	<input id="code" class="span2" type="text" placeholder="CBC"/>
	<input id="from" class="span2" type="text" placeholder="001"/>
	<input id="to" class="span2" type="text" placeholder="100"/>
	<button id="submit" class="btn btn-info" onclick="statistic()"><fmt:message key='button.statistic'/></button>
</div>

<div id="patientRow">
	<table id="statistic_table" style="font-size: 14px;"></table>
</div>