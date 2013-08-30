<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="patientView.title"/></title>
    <meta name="heading" content="<fmt:message key='patientView.heading'/>"/>
    <meta name="menu" content="IntelAuditAndExplain"/>
    <script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/plugins/jqplot.highlighter.min.js'/>"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/>" />
    <style>
    #searchHeader div {
    	margin-bottom:4px;
    }
    #patient_info tr td.pLabel {
    	padding:8px 10px 8px 0px;
    }
    #patient_info tr td.pText {
    	padding-right:10px;
    }
    #patient_info {
    	font-size:14px;
    }
    #rowed3 {
    	font-size:12px;
    	font-family:Microsoft YaHei; 
    }
    h2 {
	    color: #B4C24B;
	    font-size: 2.0em;
	    letter-spacing: -1px;
	    margin: 0 0 0.5em;
	    padding: 0;
	    display:inline;
	}
	.pagelinks {
		line-height:17px;
	}
    </style>
    <SCRIPT>
    function printsetup(){  
		wb.execwb(8,1);  
	}
	function printpreview(){
		wb.execwb(7,1);
	}
	function printit(){
		if(confirm("dsa")){
			wb.execwb(6,6)
		}
	}
	</SCRIPT>
<script>
	    $(function() {
	        $( "#fromDate" ).datepicker({ 
	        	dateFormat: "yy-mm-dd",
	        	dayNamesMin: ['\u65e5', '\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d'],
	        	monthNames:['\u4e00\u6708','\u4e8c\u6708','\u4e09\u6708','\u56db\u6708','\u4e94\u6708','\u516d\u6708','\u4e03\u6708','\u516b\u6708','\u4e5d\u6708','\u5341\u6708','\u5341\u4e00\u6708','\u5341\u4e8c\u6708']
	        });
	        $( "#toDate" ).datepicker({ 
	        	dateFormat: "yy-mm-dd",
	        	dayNamesMin: ['\u65e5', '\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d'],
	        	monthNames:['\u4e00\u6708','\u4e8c\u6708','\u4e09\u6708','\u56db\u6708','\u4e94\u6708','\u516d\u6708','\u4e03\u6708','\u516b\u6708','\u4e5d\u6708','\u5341\u6708','\u5341\u4e00\u6708','\u5341\u4e8c\u6708']
	        });
	        
	        var docNo = $("#sampleList tbody td:first").html();
	        var sampleNo = $("#sampleList tbody td:eq(1)").html();
	        
	        if (docNo != null && docNo != "") {
				$.get("<c:url value='/explain/doctor/patient'/>",{id:docNo},function(data){
					$("#chartPanel").empty();
					$("#intelExplain").empty();
	            	if (data.history != "") {
	            		var length = data.history.length;
	            		for (var i=0; i<length; i++) {
	            			$("#chartPanel").append("<div id=\"chart" + i + "\" style=\"height:200px;width:250px;margin-bottom:15px;\"></div>")
	            			var item = data.history[i];
	            			$.jqplot('chart'+i, [item.array,item.array1,item.array2],
	                			    {
	            						title:item.name,
	            						series:[
	                							{
	                								
	                							},
	                							{
	                								markerOptions: { size:1 }
	                							},
	                							{
	                								markerOptions: { size:1 }
	                							}
	                					],
	                			    	axes:{
	                			    		xaxis:{
	                			    			renderer:$.jqplot.DateAxisRenderer,
	                			    			tickOptions:{formatString:'%m/%d'}
	                			    			}
	            						},
	            						highlighter: {
	            					        show: true,
	            					        sizeAdjust: 7.5
	            					    }
	                			    });
	            		}
					}
		        	$("#sampleTitle").html(data.examinaim);
		        	$("#pName").html(data.name);
		        	$("#pAge").html(data.age);
		        	$("#pId").html(data.id);
		        	$("#pSex").html(data.sex);
		        	$("#pSection").html(data.section);
		        	$("#pType").html(data.type);
		        	$("#intelExplain").html(data.intelExplain);
		        	$("#midContent").css('display','block');
		        }, "json");
	        }

	        if (sampleNo != null && sampleNo != "") {
		        $("#patientRow").empty();
		        $("#patientRow").html("<table id=\"rowed3\"></table>");
		        var lastsel;
				jQuery("#rowed3").jqGrid({
				   	url:"<c:url value='/explain/doctor/sample'/>?id="+sampleNo,
					datatype: "json",
					jsonReader : {repeatitems : false},  
				   	colNames:['ID','<fmt:message key="project"/>','<fmt:message key="result"/>', '<fmt:message key="scope"/>', '<fmt:message key="unit"/>'],
				   	colModel:[
				   		{name:'id',index:'id', hidden:true},
				   		{name:'name',index:'name',width:160,sortable:false},
				   		{name:'result',index:'result',width:80, sortable:false},
				   		{name:'scope',index:'scope',width:110,sortable:false},
				   		{name:'unit', sortable:false, width:80, index:'unit'}
				   	],
				   	height: '100%',
				   	rowNum: 50
				});
				

	        }
	        
			$("#sampleList thead th:first").css('display','none');
			$("#sampleList thead th:eq(1)").css('display','none');
    	});
	    
	    function jump(){
	    	var fd=document.getElementById("fromDate");
	    	var td=document.getElementById("toDate");
	    	
	    	
	    	url="<c:url value='/explain/patient'/>?fromDate="+fd.value+"&toDate="+td.value;
	    	window.location.href=url; 
	    }
	</script>
</head>


<table>
	<tr>
		<td width="500px">From<input type="text" id="fromDate"/>To<input type="text" id="toDate" /></td>
		<td><input type="text" id="patientname"/></td>
		<td width="100px"/>
		<td><input type="button" name="search" value="<fmt:message key="button.save"/>"/></td>
	</tr>
</table>
<script type="text/javascript">
	var now = new Date();
	var LSTR_Year=now.getFullYear(); 
	var LSTR_Month=now.getMonth(); 
	var LSTR_Date=now.getDate(); 
	var uom = new Date(LSTR_Year,LSTR_Month,LSTR_Date); 
	uom.setDate(uom.getDate()-7);
	
	var from="${fromDate}";
	if(from!=null){
		document.getElementById("fromDate").value=from;
	}else{
		document.getElementById("fromDate").value=uom.getFullYear()+ "-"+ (uom.getMonth()+1)+"-"+uom.getDate();
	}
	var to="${toDate}";
	if(to!=null){
		document.getElementById("toDate").value=to;
	}else{
		document.getElementById("toDate").value=now.getFullYear() + "-"+ (now.getMonth()+1)+"-"+now.getDate();
	}
</script>

<hr color="#d7e9f5" size=5 align=center style="display:block;"/>
<div style="float:left;width:230px;">
<display:table name="patientList" class="table" requestURI="" id="patientList" pagesize="5">
    <display:column property="patientId" sortable="true" href="#"
        paramId="patientId" paramProperty="patientId" titleKey="patientInfo.patientId"/>
	<display:column property="patientName" sortable="true" titleKey="patientInfo.patientName"/>
</display:table>
<display:table name="sampleList" class="table" requestURI="" id="sampleList"  pagesize="15">
	<display:column property="id" style="display:none;"></display:column>
	<display:column property="sampleNo" style="display:none;"></display:column>
	<display:column property="examinaim" sortable="true" titleKey="patientInfo.examinaim" style="width: 56%"/>
    <display:column property="checkStatus" sortable="true" titleKey="patientInfo.resultStatus"/>
    <display:column property="receivetime" sortable="true" titleKey="patientInfo.receivetime"/>
</display:table>
</div>

<div id="midContent" style="float:left;width:450px;margin-left:20px;display:none;">
	<div style="height:95px;">
		<h2 id="sampleTitle"></h2>
		<table id="patient_info">
			<tr>
				<td class="pLabel"><b><fmt:message key="patient.patientName"/></b></td>
				<td class="pText"><span id="pName"></span></td>
				<td class="pLabel"><b><fmt:message key="patient.patientSex"/></b></td>
				<td class="pText"><span id="pSex"></span></td>
				<td class="pLabel"><b><fmt:message key="patient.patientId"/></b></td>
				<td><span id="pId"></span></td>
			</tr>
			<tr>
				<td class="pLabel"><b><fmt:message key="patient.age"/></b></td>
				<td class="pText"><span id="pAge"></span></td>
				<td class="pLabel"><b><fmt:message key="patient.section"/></b></td>
				<td class="pText"><span id="pSection"></span></td>
				<td class="pLabel"><b><fmt:message key="patient.sampleType"/></b></td>
				<td><span id="pType"></span></td>
			</tr>
		</table>
	</div>
	<div style="font-size:13px;margin-top:15px;">
		<div style="margin-bottom:10px;"><b><fmt:message key="audit.infomation" />:</b></div>
		<div id="intelExplain" style="width:440px;"></div>
	</div>
	<div id="patientRow" style="margin-top:4px;font-size:13px;">
		<table id="rowed3"></table>
	</div>
</div>

<div style="float:right;width:250px;">
	<h2><fmt:message key="history.data.infomation" /></h2>
	<div id="chartPanel"  style="margin-top:20px;"></div>
</div>

<script type="text/javascript">
function highlightTableRows1(tableId) {
    var previousClass = null;
    var table = document.getElementById(tableId); 
    var startRow = 0;
    // workaround for Tapestry not using thead
    if (!table.getElementsByTagName("thead")[0]) {
	    startRow = 1;
    }
    var tbody = table.getElementsByTagName("tbody")[0];
    var rows = tbody.getElementsByTagName("tr");
    // add event handlers so rows light up and are clickable
    for (i=startRow; i < rows.length; i++) {
        rows[i].onmouseover = function() { previousClass=this.className;this.className+=' over' };
        rows[i].onmouseout = function() { this.className=previousClass };
        rows[i].onclick = function() {
            var docNo = this.getElementsByTagName("td")[0].innerHTML;
            var sampleNo = this.getElementsByTagName("td")[1].innerHTML;

            $.get("<c:url value='/explain/doctor/patient'/>",{id:docNo},function(data){
				$("#chartPanel").empty();
            	if (data.history != "") {
            		var length = data.history.length;
            		for (var i=0; i<length; i++) {
            			$("#chartPanel").append("<div id=\"chart" + i + "\" style=\"height:200px;width:250px;margin-bottom:15px;\"></div>")
            			var item = data.history[i];
            			$.jqplot('chart'+i, [item.array,item.array1,item.array2],
                			    {
            						title:item.name,
            						series:[
            							{
            								
            							},
            							{
            								markerOptions: { size:1 }
            							},
            							{
            								markerOptions: { size:1 }
            							}
            						],
                			    	axes:{
                			    		xaxis:{
                			    			renderer:$.jqplot.DateAxisRenderer,
                			    			tickOptions:{formatString:'%m/%d'}
                			    		}
            						},
            						highlighter: {
            					        show: true,
            					        sizeAdjust: 7.5
            					    }
                			    });
            		}
				}
	        	$("#sampleTitle").html(data.examinaim);
	        	$("#pName").html(data.name);
	        	$("#pAge").html(data.age);
	        	$("#pId").html(data.id);
	        	$("#pSex").html(data.sex);
	        	$("#pSection").html(data.section);
	        	$("#pType").html(data.type);
	       		$("#intelExplain").html(data.intelExplain);
	        	$("#midContent").css('display','block');
	        }, "json");
            
            $("#patientRow").empty();
            $("#patientRow").html("<table id=\"rowed3\"></table>");
            var lastsel;
			jQuery("#rowed3").jqGrid({
			   	url:'<c:url value='/explain/doctor/sample'/>?id='+sampleNo,
				datatype: "json",
				jsonReader : {repeatitems : false},  
			   	colNames:['ID','<fmt:message key="project"/>','<fmt:message key="result"/>', '<fmt:message key="scope"/>', '<fmt:message key="unit"/>'],
			   	colModel:[
			   		{name:'id',index:'id', hidden:true},
			   		{name:'name',index:'name',width:160,sortable:false},
			   		{name:'result',index:'result',width:80, sortable:false},
			   		{name:'scope',index:'scope',width:110,sortable:false},
			   		{name:'unit', sortable:false, width:80, index:'unit'}
			   	],
			   	height: '100%',
			   	rowNum: 50
			});
            return false;
        }
    }
}
highlightTableRows1("sampleList");
</script>

