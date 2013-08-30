<%@ include file="/common/taglibs.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TongJi</title>

<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.tablednd_0_5.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.json-2.3.min.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.form.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.bgiframe-2.1.2.js'/> "></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" /> 

<script type="text/javascript">
	function tongji() {
		var time = $("#time").val();
		var code = $("#code").val();
		var from = $("#from").val();
		var to = $("#to").val();
		
		jQuery("#rowed3").jqGrid({
			url:"<c:url value='/tongji/sample'/>?time="+time+"&code="+code+"&from="+from+"&to="+to,
			datatype: "json",
			jsonReader : {repeatitems : false, userdata : "userdata"}, 
			colNames:['ID','NAME','TOTAL','PASS','UNPASS','DIFF','RATIO','RETEST','DANGER'],
		   	colModel:[{name:'id',index:'id',hidden:true,sortable:false},
		   	    {name:'name',index:'name',sortable:false},
		   	 	{name:'totalNo',index:'totalNo',width:60,sortable:false},
		   	 	{name:'passNo',index:'passNo',width:60,sortable:false},
		   	 	{name:'totalUnpassNo',index:'totalUnpassNo',width:60,sortable:false},
		   	 	{name:'diffUnpassNo',index:'diffUnpassNo',width:60,sortable:false},
		   	 	{name:'ratioUnpassNo',index:'ratioUnpassNo',width:60,sortable:false},
		   		{name:'reTestUnpassNo',index:'reTestUnpassNo',width:60,sortable:false},
		   	 	{name:'dangerUnpassNo',index:'dangerUnpassNo',width:60,sortable:false}],
		   	rowNum: 80,
		   	height:'100%',
		   	loadComplete: function() {
				var totalSample = jQuery("#rowed3").jqGrid("getGridParam", "userData").totalSample;
				var totalUnpassSample = jQuery("#rowed3").jqGrid("getGridParam", "userData").totalUnpassSample;
				$("#totalSample").html(totalSample);
				$("#totalUnpassSample").html(totalUnpassSample);
				$("#sample").css('display','block');
		   	}
		});
	}

</script>
</head>

<div class='form-inline'>
	<input id="time" class="span2" type="text" />
	<input id="code" class="span2" type="text" />
	<input id="from" class="span2" type="text" />
	<input id="to" class="span2" type="text" />
	<button id="submit" class="btn btn-info" onclick="tongji()">Submit</button>
</div>

<div id="sample" style="display:none;">
	<b><fmt:message key="tongji.total"/></b><a id="totalSample"></a>
	<b><fmt:message key="tongji.unpass"/></b><a id='totalUnpassSample'></a>
</div>
<div id="patientRow">
	<table id="rowed3" style="font-size: 14px;"></table>
</div>