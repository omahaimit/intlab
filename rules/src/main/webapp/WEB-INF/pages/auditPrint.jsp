<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<%@ include file="/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/styles/jquery.jqplot.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/jquery-1.8.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-ui-1.9.1.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.jqplot.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jqplot.dateAxisRenderer.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jqplot.json2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.json-2.3.min.js'/> "></script>
<%-- <link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " /> --%>
<script type="text/javascript">
	function getPatient(docNo) {
		$.get("<c:url value='/explain/audit/patient'/>",{id:docNo},function(data){
			$("#sampleTitle").html(data.examinaim);	    	
	    	$("#pName").html(data.name);
	    	$("#pAge").html(data.age);
	    	$("#pId").html(data.id);
	    	$("#pSex").html(data.sex);
	    	$("#pSection").html(data.section);
	    	$("#pType").html(data.type);
	    	if (data.infomation != null)
	    		$("#audit_infomation").html(data.infomation.replace(/<br>/g,","));
	    }, "json");
	}
	
	function getChart(docNo) {
		
		$.get("<c:url value='/explain/audit/chart'/>",{id:docNo},function(data){

		$("#chartPanel").empty();
    	if (data != "") {
    		var length = data.length;
   			var exsitHeight = $("#headDiv").height() + $("#patientRow").height() + 40;
   			var flagPrint = true;
    		for (var i=0; i<length; i++) {
    			if (i % 3 == 0) {
    				if (i / 3 * 190 + exsitHeight > 800 && flagPrint) {
    					$("#chartPanel").append("<tr class='pageEnd'></tr>");
    					flagPrint = false;
    				}
    				$("#chartPanel").append("<tr><td id=\"chartTd"+i+"\"></td><td id=\"chartTd"+(i+1)+"\"></td><td id=\"chartTd"+(i+2)+"\"></td></tr>")
    			}
    			$("#chartTd"+i).append("<div id=\"chart" + i + "\" class=\"clearfix\" style=\"float:left;height:180px;width:200px;margin-bottom:10px;\"></div>")
    			var item = data[i];
    			$.jqplot('chart'+i, [item.array,item.array1,item.array2], {
    				title:item.name,
    				series:[{},{linePattern: 'dashed', markerOptions: { size:1 }},{linePattern: 'dashed', markerOptions: { size:1 }}],
        			axes:{
        				xaxis:{
        					renderer:$.jqplot.DateAxisRenderer,
        					tickOptions:{formatString:'%m/%d'}
        				}
    				}
        		});
    		}
		}
    	$(parent.document).find("#iframe_print").height($("#midContent").height()+30);
		});
	}
	function getSample(sampleNo, docId) {
		jQuery("#rowed3").jqGrid({
		   	url:"<c:url value='/explain/audit/sample'/>?id="+sampleNo,
			datatype: "json",
			jsonReader : {repeatitems : false},  
		   	colNames:['ID','<fmt:message key="project"/>','<fmt:message key="result"/>', <c:if test="${showLast == 1}">'<fmt:message key="result.last"/>', </c:if>'<fmt:message key="scope"/>', '<fmt:message key="unit"/>'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name',sortable:false},
		   		{name:'result',index:'result',width:90, sortable:false},
		   		<c:if test="${showLast == 1}">
		   		{name:'last',index:'last',width:90, sortable:false},
		   		</c:if>
		   		{name:'scope',index:'scope',width:100,sortable:false},
		   		{name:'unit', sortable:false, width:80, index:'unit'}
		   	],
		   	height: '100%',
		   	rowNum: 100,
		   	rownumbers: true,
		   	loadComplete: function() {
		   		$(parent.document).find("#iframe_print").height($("#midContent").height()+30);
		   		getChart(docId);
		   		
		   		$.each(jQuery('#rowed3').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#rowed3").jqGrid('getRowData',v);
        			var hl = ret.scope.split("-");
        			var h = parseFloat(hl[1]);
        			var l = parseFloat(hl[0]);
        			var va = parseFloat(ret.result);
        			var res = "";
        			if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
        				if (va < l) {
        					res = "<font color='red'>\u2193</font>";
        				} else if (va > h) {
        					res = "<font color='red'>\u2191</font>";
        				}
        			}

        			jQuery("#rowed3").jqGrid('setRowData', v, {result:"<span class='result_span'>"+ret.result+"</span>"+res});
        		});
		   	}
		});
	}
	
	$(function() {
		var docId = '<c:out value="${docId}" />';
		var sampleNo = '<c:out value="${sampleNo}" />';
		getPatient(docId);
		getSample(sampleNo, docId);
		
		//alert($(parent.document).find("#iframe_print").height($("#midContent").height()));
	});
	
</script>
<style type="text/css">
.pageEnd {
	page-break-after:always
}
#rowed3_name {
	text-align:left;
}
.result_span {
	display:block;
	float:left;
	width:40px;
	margin-left:2px;
}
#rowed3_result {
	text-align:left;
}
#rowed3_last {
	text-align:left;
}
#rowed3_scope {
	text-align:left;
}
#rowed3_unit {
	text-align:left;
}
.ui-jqgrid-hdiv {
	font-size:15px;
	margin-bottom:6px;
	padding-bottom:6px;
	border-bottom:solid 1px #555555;
}
#patient_info {
	font-size:14px;
}
.pline {
	border-bottom:solid 1px #555555;
	margin-top:5px;
	margin-bottom:5px;
}
#rowed3 .jqgrow {
	height:22px;
	font-size:14px;s
}
#rowed3 .jqgrid-rownum {
	width:40px;
	text-align:left;
}
#jqgh_rowed3_name {
	margin-left:15px;
}
#patient_info .pText span {
	font-weight:600;
}
#patient_info tr {
	height:24px;
}
.jqplot-title {
	font-size:14px;
}
</style>
</head>
<body>
	<div id="midContent">
		<div id="headDiv">
			<h3 id="sampleTitle"></h3>
			<div class="pline"></div>
			<table id="patient_info">
				<tr>
					<td class="pLabel"><fmt:message key="patient.patientName" /></td>
					<td class="pText" style="width:100px;"><span id="pName"></span></td>
					<td class="pLabel"><fmt:message key="patient.patientSex" /></td>
					<td class="pText" style="width:100px;"><span id="pSex"></span></td>
					<td class="pLabel"><fmt:message key="patient.patientId" /></td>
					<td class="pText"><span id="pId"></span></td>
				</tr>
				<tr>
					<td class="pLabel"><fmt:message key="patient.age" /></td>
					<td class="pText"><span id="pAge"></span></td>
					<td class="pLabel"><fmt:message key="patient.section" /></td>
					<td class="pText"><span id="pSection"></span></td>
					<td class="pLabel"><fmt:message key="patient.sampleType" /></td>
					<td class="pText"><span id="pType"></span></td>
				</tr>
			</table>
			<%-- <table >
				<tr>
				<td valign="top" style="width:100px;font-size:14px;"><b><fmt:message key="audit.infomation" /></b></td>
				<td><div id="audit_infomation" style="font-size:13px;line-height:20px;width:400px;"></div></td>
				</tr>
			</table> --%>
			<div class="pline"></div>
		</div>
		
		<div id="patientRow" style="font-size: 13px;">
			<table id="rowed3"></table>
		</div>
		
		<div>
			<div>
				<b><fmt:message key="history.data.infomation" /></b>
			</div>
			
			<table id="chartPanel" style="margin-top: 20px;"></table>
		</div>
	</div>
</body>
</html>