<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="auditView.title" /></title>
<%-- <meta name="heading" content="<fmt:message key='auditView.heading'/>"/> --%>
<meta name="menu" content="IntelAuditAndExplain" />
<script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.tablednd_0_5.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.json-2.3.min.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/ext-all.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.form.js'/> "></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery.bgiframe-2.1.2.js'/> "></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/styles/ui.jqgrid.css'/> " />
<link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ruleLib.css'/>" /> 
<style>
.ui-progressbar .ui-progressbar-value { background-image: url(<c:url value='/images/pbar-ani.gif'/>); }
button.btn {
	font-family: Microsoft YaHei;
}
#btnMenu h3 {
	color:#ee0000;
	font-weight:bold;
}
.ui-jqgrid .ui-jqgrid-htable th div {
    height: 20px;
}

#rowed3 input {
	padding-top:1px;
	padding-bottom:1px;
	width:90%;
}

#patient-info .pLabel {
}
#addTestList {
	margin-top:10px;
}
.c_td {
	display: inline-block;
	padding-left:18px;
}
#addTestList .testName {
	display:block; 
	float:left; 
	text-align:right;
	margin-right:10px;
	margin-top:5px;
}

#addTestList .testValue {
	height:16px;
	margin-bottom:6px;
}

#codeSetDiv div {
	height:30px;
	font-size:15px;
}

#selectNoteDiv .selectLabel {
	cursor:pointer;
}

#patient-info .pText {
	margin-right:20px;
}

#patient-info2 .pText {
	margin-right:15px;
}

#rowed3 {
	font-family: Microsoft YaHei;
}

select {
	padding: 0px;
}

#labSelect {
	width:120px;
	padding: 4px 6px;
	margin-bottom:0px;
}

#profileList {
	padding: 4px 6px;
	margin-bottom:0px;
	width:240px;
}

/* textarea, input[type="text"]{
	padding: 1px;
}
 */
#addTestForm tr {
	height:30px;
}

#gview_s3list .ui-jqgrid-hdiv {
	overflow-y:hidden;
}

#gview_rowed3 .ui-jqgrid-bdiv {
	overflow-y:hidden;s
}

#addTestForm td {
	padding-left:20px;
}

#btnMenu button {
	padding:2px;
}

#reasonBtn button {
	padding:2px;
}

h2 {
	color: #B4C24B;
	font-size: 2.0em;
	letter-spacing: -1px;
	margin: 0 0 0.5em;
	padding: 0;
	display: inline;
}

.pagelinks {
	line-height: 17px;
}
.ui-jqgrid-titlebar {
	height:20px;
	font-size:16px;
	line-height:19px;
}

.ui-th-column {
	line-height:20px;
}

#gview_s3list .ui-search-toolbar th {
	border-top-width:0px !important;
}

#gs_sample {
	height: 88%;
	font-size:12px;
	font-weight:700;
	margin:0px;
	color:#000000;
}

#gs_mark {
	height: 100%;
}

#gs_status {
	height: 100%;
}

#gview_s3list .ui-jqgrid-htable th {
	padding:0px;
}

#s3list {
	font-size:12px;
}
.ui-jqgrid-labels {
	height:25px;
}
#auditDialog .proId {
	display:none;
}
#auditDialog .proStart {
	float:left;
}
#auditDialog .proEnd {
	float:right;
}
#auditDialog .proValue {
	width:260px;
	height:22px;
}
#auditDialog .proStatus {
	width:40px;
}

input {
	width:20px;
}

.status_icon {
   	width : 30px;
   	height : 30px;
   	margin-top : -5px;
   	margin-bottom : -5px;
   	margin-left : -2px;
}
.diff_td {
	height:21px;
	line-height:21px;
	background-color:#FFFF00;
}
.ratio_td {
	height:21px;
	line-height:21px;
	background-color:#00c0bF;
}
.dan_td {
	height:21px;
	line-height:21px;
	background-color:#ff7070;
}
.re_td {
	height:21px;
	line-height:21px;
	background-color:#ffbbff;
}
.al2_td {
	height:21px;
	line-height:21px;
	background-color:#00FF00;
}
.al3_td {
	height:21px;
	line-height:21px;
	background-color:#F4A460;
}
.ex_td {
	height:21px;
	line-height:21px;
	background-color:#EE4000;
}
.result_span {
	display:block;
	float:left;
	width:50px;
	font-weight:700;
	margin-left:2px;
}
.last_span {
	display:block;
	float:left;
	width:40px;
	font-weight:500;
	margin-left:2px;
}
#need_writeback_table .wb_checker {
	display:none;
}
#need_writeback_table .wb_lab {
	display:none;
}
.ui-tabs-vertical { width: 580; }
.ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
.ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: 0 -1px .2em 0; }
.ui-tabs-vertical .ui-tabs-nav li a { display:block; }
.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-active { padding-bottom: 0; padding-right: .1em; border-right-width: 1px; border-right-width: 1px; }
.ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: right; width: 370px;}
#audit_information td {white-space:normal;}
#addResultForm .controls {
	margin-left:80px;
}
#addResultForm .control-label {
	width:60px;
}
.compare_td {
	height:21px;
	line-height:21px;
	background-color:#EEE685;
}
.add-on {
    background-color: #EEEEEE;
    border: 1px solid #CCCCCC;
    display: inline-block;
    font-weight: normal;
    height: 20px;
    line-height: 20px;
    min-width: 16px;
    padding: 4px 5px;
    text-align: center;
    text-shadow: 0 1px 0 #FFFFFF;
    width: auto;
}
.lisPass-info {
    background-color: #CEFFCE;
    border-color: #BCE8F1;
    color: #3C3C3C;
}
</style>

<script type="text/javascript">
	var isFirstTrace = true;
	function getAuditHistory() {
		var sample = $("#hiddenSampleNo").val();
		if(isFirstTrace){
			jQuery("#audit_trace_information").jqGrid({
				url:"<c:url value='/explain/audit/trace'/>?sample="+sample,
				datatype: "json",
				jsonReader : {repeatitems : false}, 
				colNames:['<fmt:message key="audit.sample.number"/>','<fmt:message key="tat.auditor"/>','<fmt:message key="tat.audit"/>'
				          ,'<fmt:message key="audit.status"/>','<fmt:message key="audit.type"/>'],
			   	colModel:[{name:'sampleno',index:'sampleno',width:120,sortable:false},
			   		{name:'checker',index:'checker',width:60,sortable:false},
			   		{name:'checktime',index:'checktime',width:160,sortable:false},
			   		{name:'status',index:'status',width:60,sortable:false},
			   		{name:'type',index:'type',width:80,sortable:false}],
			   	height: '100%'
			});
			isFirstTrace=false;
		}else{
			jQuery("#audit_trace_information").jqGrid("setGridParam",{
				url:"<c:url value='/explain/audit/trace'/>?sample="+sample
			}).trigger("reloadGrid"); 
		} 
		$("#auditTraceDialog").dialog("open");
		
	}

	function getCompareTable(text){
		jQuery("#sample_compare_information").jqGrid({
			url:"<c:url value='/explain/audit/sampleCompare'/>?sampleNo="+text,
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['<fmt:message key="project"/>','COLOR','<fmt:message key="resultstatus"/>','<fmt:message key="z1_resultstatus"/>','<fmt:message key="result"/>','<fmt:message key="z1_result"/>'
			          ,'<fmt:message key="reflo"/>','<fmt:message key="z1_reflo"/>','<fmt:message key="refhi"/>','<fmt:message key="z1_refhi"/>',
			          '<fmt:message key="resultflag"/>','<fmt:message key="z1_resultflag"/>'],
		   	colModel:[{name:'test',index:'test',width:80,sortable:false},
		   		{name:'color',index:'color',hidden:true,sortable:false},
		   	    {name:'resultstatus',index:'resultstatus',width:20,sortable:false},
		   		{name:'z1_resultstatus',index:'z1_resultstatus',width:20,sortable:false},
		   		{name:'result',index:'result',width:40,sortable:false},
		   		{name:'z1_result',index:'z1_result',width:40,sortable:false},
		   		{name:'reflo',index:'reflo',width:40,sortable:false},
		   		{name:'z1_reflo',index:'z1_reflo',width:40,sortable:false},
		   		{name:'refhi',index:'refhi',width:40,sortable:false},
		   		{name:'z1_refhi',index:'z1_refhi',width:40,sortable:false},
		   		{name:'resultflag',index:'resultflag',width:60,sortable:false},
		   		{name:'z1_resultflag',index:'z1_resultflag',width:60,sortable:false}],
		   	height: '100%',
		   	loadComplete: function() {
		   		$.each(jQuery('#sample_compare_information').jqGrid('getCol','test', false), function(k,v) {
        			var ret = jQuery("#sample_compare_information").jqGrid('getRowData',k+1);
        			if(ret.color==1){
        				jQuery("#sample_compare_information").jqGrid('setRowData', k+1, {
        					test:"<div class='compare_td'><span class='result_span'>"+v+"</span></div>"});       			
        			}
        		}); 
		   	}
		});
	}
	
	function getTestModifyTable(sample) {
		jQuery("#test_modify_information").jqGrid({
			url:"<c:url value='/explain/audit/testModify'/>?sampleNo="+sample,
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['<fmt:message key="modify.name"/>','<fmt:message key="modify.type"/>','<fmt:message key="modify.oldvalue"/>'
			          ,'<fmt:message key="modify.newvalue"/>','<fmt:message key="modify.time"/>','<fmt:message key="modify.user"/>'],
		   	colModel:[{name:'test',index:'test',width:80,sortable:false},
		   		{name:'type',index:'type',width:60,sortable:false},
		   		{name:'oldValue',index:'oldValue',width:40,sortable:false},
		   		{name:'newValue',index:'newValue',width:40,sortable:false},
		   		{name:'modifyTime',index:'modifyTime',sortable:false},
		   		{name:'modifyUser',index:'modifyUser',width:60,sortable:false}],
		   	height: '100%'
		});
	}
	
	function labChange(select) {
		$("#lastDepLib").val(select.value);
		jQuery("#s3list").jqGrid("setGridParam",{
			url:"<c:url value='/explain/audit/data'/>?lab="+select.value+"&text="+${strToday}
		}).trigger("reloadGrid"); 
		$.ajax({
			  type: 'POST',
			  url: "<c:url value='/explain/audit/labChange'/>?lab="+select.value
		});
		selectNoteAdd = true;
	}
	
	function closeResultDialog(){
		$("#addResultDialog").dialog("close");
	}

	function closeTestResultDialog(){
		$("#addTestResultDialog").dialog("close");
	}
	
	function validate(formData, jqForm, options) {
		
		for (var i=0; i < formData.length; i++) {
	        if (!formData[i].value) {
	            return false;  
	        }
		}
		return true;
	}
	
 	function getPatient(ret) {

 		var docNo = ret.id;
		$.get("<c:url value='/explain/audit/patient'/>",{id:docNo},function(data){
			if (data.isOverTime) {
				$('#tatDialog').dialog("option","title", "TAT\u8d85\u65f6");
				$("#tatBtn").html("<b style='color: #FF4500;'>TAT\u8d85\u65f6</b>");
			} else {
				$('#tatDialog').dialog("option","title", "TAT");
				$("#tatBtn").html("<b>TAT</b>");
			}
			/* if (data.isLisPass) {
				$("#sampleLisPass").css('display','block');
			} else {
				$("#sampleLisPass").css('display','none');
			} */
    		if(data.size > 30 && $("#oneColumnShowBtn").attr("checked") != "checked") {
    			$("#patientRow").css('display','none');
    			$("#twosampleTable").css('display','block');
    			if (isFirstTime) {
    				getSample(ret.sample);
        			getSample0(ret.sample);
        			getSample1(ret.sample);
        			isFirstTime = false;
        		} else {
        			jQuery("#sample0").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample0'/>?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
        			jQuery("#sample1").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample1'/>?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
        		}
			} else {
        		$("#twosampleTable").css('display','none');
        		$("#patientRow").css('display','block');
        		if (isFirstTime) {
        			getSample(ret.sample);
        			getSample0(ret.sample);
        			getSample1(ret.sample);
    				isFirstTime = false;
        		} else {
        			jQuery("#rowed3").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample'/>?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
        		}
			}
    		
    		if ($("#historyTabs").tabs('option', 'selected') == 0) {
    			getChart(ret.id);
    		} else if ($("#historyTabs").tabs('option', 'selected') == 1) {
   				jQuery("#audit_information").jqGrid("setGridParam",{
   					url:"<c:url value='/explain/audit/explain'/>?id="+ret.id,
   					editurl: "<c:url value='/explain/audit/explain/edit'/>?docNo=" + ret.id
   				}).trigger("reloadGrid");
    		} else {
				jQuery("#rowed3").setGridParam().showCol("last2");
				jQuery("#rowed3").setGridParam().showCol("last3");
				jQuery("#rowed3").setGridParam().showCol("last4");
				jQuery("#rowed3").setGridParam().showCol("device");
				jQuery("#rowed3").setGridParam().showCol("checktime");
    		}
    		$("#historyTabs").css('display','block');
			
			$("#midContent").css('display','block');
			if(data.mode == 1) {
				$("#sampleTitle").html("<font color='red'><fmt:message key='jizhen'/></font>" + $("#hiddenSampleNo").val() + "  " + data.examinaim);
				$("#sample0").jqGrid("setCaption", "<font color='red'>*</font>&nbsp" + $("#hiddenSampleNo").val() + " (\u5171" + data.size + "\u9879)");        	
			} else {
        		$("#sampleTitle").html($("#hiddenSampleNo").val() + "  " + data.examinaim);
        		$("#sample0").jqGrid("setCaption", $("#hiddenSampleNo").val() + " (\u5171" + data.size + "\u9879)");
        	}
			$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
			$("#sample1").jqGrid("setCaption", data.examinaim);
        	$("#audit_reason").html(data.reason);
        	$("#pName").html("<a href='../patientList?patientId=" + data.patientId + "'  target='_blank'>" + data.name + "</a>");
        	$("#pAge").html(data.age);
        	$("#blh").html(data.blh);
        	$("#pId").html(data.id);
        	$("#pSex").html(data.sex);
        	$("#pSection").html(data.section);
        	$("#pType").html(data.type);
        	if(data.diagnosticKnow == "") {
        		$("#diagnostic").html(data.diagnostic);
        	} else {
        		$("#diagnostic").html("<a href='javascript:show_knowledge(\""+data.diagnosticKnow+"\")'>"+data.diagnostic+"</a>");
        	}
        	
        	
        	var reason = data.passReason;
        	$("#passreason").html(reason);
        	if (reason != null) {
        		$("#passLabel").css('display','block');
        	} else {
        		$("#passLabel").css('display','none');
        	}
        	
        	$("#pName2").html(data.name);
        	$("#pAge2").html(data.age);
        	$("#blh2").html(data.blh);
        	$("#pId2").html(data.id);
        	$("#pSex2").html(data.sex);
        	$("#pSection2").html(data.section);
        	$("#pType2").html(data.type);
        	$("#diagnostic2").html(data.diagnostic);
        	
        	if(data.bed == null){
        		$("#pBedText").css('display','none');
        		$("#pBedLabel").css('display','none');
        		
        		$("#pBedText2").css('display','none');
        		$("#pBedLabel2").css('display','none');
        	}else{
        		$("#pBed").html(data.bed);
        		$("#pBedText").css('display','inline');
        		$("#pBedLabel").css('display','inline');
        		
        		$("#pBed2").html(data.bed);
        		$("#pBedText2").css('display','inline');
        		$("#pBedLabel2").css('display','inline');
        	}
        	
        	$("#hiddenDocId").val(docNo);
        	
        	$("#critical_alert").removeClass('alert-error');
        	$("#critical_title").html("");
    		$("#critical_info").html("");
    		$("#critical_time").html("");

        	if (data.mark == 6 && data.dgFlag == 0) {
        		$("#critical_div").css('display','block');
        		$("#critical_alert").addClass('alert-error');
        		$("#critical_title").html("\u8be5\u5371\u673a\u503c\u672a\u5904\u7406");
        	} else if (data.mark == 6 && data.dgFlag == 1) {
        		$("#critical_div").css('display','block');
        		$("#critical_info").html(data.dgInfo);
        		$("#critical_time").html(data.dgTime);
        		$("#critical_title").html("\u8be5\u5371\u673a\u503c\u5df2\u5904\u7406");
        	} else {
        		$("#critical_div").css('display','none');
        	}
        	
			/* if ($("#unaudit_reason").css('display') != 'none') {
				var h = 390 - $("#unaudit_reason").height();
				$("#gview_rowed3").find(".ui-jqgrid-bdiv").css('height',h);
			} else {
				$("#gview_rowed3").find(".ui-jqgrid-bdiv").css('height',390);
			}  */
			
        }, "json");
	}
 	
 	function getChart(docNo) {
 		
 		$.get("<c:url value='/explain/audit/chart'/>",{id:docNo},function(data){
 			$("#chartPanel").empty();
	    	if (data != "") {
	    		var length = data.length;
	    		for (var i=0; i<length; i++) {
	    			$("#chartPanel").append("<div id=\"chart" + i + "\" style=\"height:180px;width:180px;margin-bottom:15px;\"></div>")
	    			var item = data[i];
	    			$.jqplot('chart'+i, [item.array,item.array1,item.array2], {
	    				title:item.name,
	    				series:[{},{linePattern: 'dashed',markerOptions: { size:1 }},{linePattern: 'dashed',markerOptions: { size:1 }}],
						axes:{
					       xaxis:{
					         renderer:$.jqplot.DateAxisRenderer,
					         tickOptions:{
					           formatString:'%m/%d'
					         } 
					       },
					       yaxis:{
					         tickOptions:{
					           formatString:'%.2f'
					           }
					       }
					     },
					     highlighter: {
					       show: true,
					       sizeAdjust: 5.0
					     },
					     cursor: {
					       show: false
					     }
	        		});
	    		}
			}
 		});
 	}

 	function dataProcess(data){
		var title="<ul>";
		var array = data.split("<div class=\"tabbedSection\">");
		var data = array[1];
		array = data.split("<\/body>");
		data = "<div> "+array[0];
		var dataArray = data.split("<div class=\"tab-");
		for(var i=0; i<dataArray.length;i++){
			var str = dataArray[i].replace("\">","!@#$%^&*");
			if(i!=0){
				var arr = str.split("!@#$%^&*");
				
				title = title+"<li><a href=\"#tabs-"+i+"\">"+arr[0]+"<\/a><\/li>";
				dataArray[i] = "<div id=\"tabs-"+i+"\">"+arr[1]; 
			}
			if(i==dataArray.length-1){
				dataArray[i] = dataArray[i].replace("<\/div>","");
				dataArray[i] = dataArray[i].replace("<\/div>","");
			}
		}
		title = title + "<\/ul>";
		var result = "<div id=\"tabs\">"+title;
		for(var j=0;j<dataArray.length;j++){
			if(j!=0){
				result = result + dataArray[j]; 
			}
		}
		result = result + "<\/div>";
		return result;
	}
 	
 	function show_knowledge(item) {
 		jQuery.ajax({
	  		type:'GET',
			url: '/item.jsp?page='+item,
	  		dataType: 'html',
	  		success: function(data) {
				var data2=dataProcess(data);
	  	    	document.getElementById("dialog").innerHTML = data2;
	  	    	$( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
				$( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
				$( "#dialog" ).dialog( "open" );
	  	  	}
	  	});
 	}
 	
 	var isTwoColumnFirstTime = true;
	function getSample(sampleNo) {

        var lastsel;
        var cl = "";
        var isEdit = false;
		jQuery("#rowed3").jqGrid({
		   	url:"<c:url value='/explain/audit/sample'/>?id="+sampleNo,
			datatype: "json",
			jsonReader : {repeatitems : false, userdata : "userdata"},  
		   	colNames:['ID','Color','<fmt:message key="project"/>', '<fmt:message key="result"/>', '\u5386\u53f2', '\u5386\u53f2', '\u5386\u53f2', '\u5386\u53f2', '\u5386\u53f2', '\u5206\u6790\u65f6\u95f4', '\u673a\u5668\u53f7', '<fmt:message key="scope"/>', '<fmt:message key="unit"/>','KNOWLEDGE','EDITMARK'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'color',index:'color', hidden:true},
		   		{name:'name',index:'name',width:135,sortable:false},
		   		{name:'result',index:'result',width:75, sortable:false, editable:true},
		   		{name:'last',index:'last',width:50, sortable:false},
		   		{name:'last1',index:'last1',width:50, sortable:false},
		   		{name:'last2',index:'last2',width:50, hidden:true, sortable:false},
		   		{name:'last3',index:'last3',width:50, hidden:true, sortable:false},
		   		{name:'last4',index:'last4',width:50, hidden:true, sortable:false},
		   		{name:'checktime',index:'checktime',width:50, hidden:true, sortable:false},
		   		{name:'device',index:'device',width:50, hidden:true, sortable:false},
		   		{name:'scope',index:'scope',width:80,sortable:false},
		   		{name:'unit', sortable:false, width:65, index:'unit'},
		   		{name:'knowledgeName',index:'knowledgeName', hidden:true},
		   		{name:'editMark',index:'editMark',hidden:true}
		   	],
		   	height: "100%",
		   	rowNum: 100,
		   	rownumbers: true,
		    caption: "",
			onSelectRow: function(id) {
				
				if($("#needEdit").val() == "true") {
					if (lastsel) {
						if (lastsel == id) {
							if (!isEdit) {
								isEdit = true;
								var ret = jQuery("#rowed3").jqGrid('getRowData',id);
								var pre = "<div class='"+$(ret.result).attr('class')+"'>";
								cl = pre + $(ret.result).html() + "</div>";
								lastval = $(ret.result).find(":eq(0)").html();
								jQuery("#rowed3").jqGrid('setRowData', id, {result:lastval});
								jQuery("#rowed3").jqGrid('editRow',id, {
									keys:true,
									aftersavefunc:function() {
										var newVal = jQuery("#rowed3").jqGrid('getRowData',id);
										var hl = newVal.scope.split("-");
					        			var h = parseFloat(hl[1]);
					        			var l = parseFloat(hl[0]);
					        			var va = parseFloat(newVal.result);
					        			var res = "";
					        			
					        			if (!isNaN(h) && !isNaN(l)) {
					        				if (!isNaN(va)) {
					        					if (va < l) {
						        					res = "<font color='red'>\u2193</font>";
						        				} else if (va > h) {
						        					res = "<font color='red'>\u2191</font>";
						        				}
					        				}
					        			}
										jQuery("#rowed3").jqGrid('setRowData', id, {
											result:pre + "<span class='result_span'>" + newVal.result + "</span>"+res+"</div>"
										});
										isEdit = false;
									}				
								});
							}
						} else {
							jQuery('#rowed3').jqGrid('restoreRow', lastsel);
							if (isEdit) {
								jQuery("#rowed3").jqGrid('setRowData', lastsel, {result:cl});
							}
							isEdit = false;
						}
					}
					lastsel=id;
				}
			},
			onRightClickRow: function(id) {
				var sample=$("#hiddenSampleNo").val();
				$.get("<c:url value='/explain/audit/singleChart'/>",{id:id, sample:sample},function(data){
					$("#chartDialog").dialog("open");
					$("#singleChartPanel").empty();
	    			$("#singleChartPanel").append("<div id=\"chart" + id + "\" style=\"height:480px;width:600px;\"></div>")
	    			var plot1 = $.jqplot('chart'+id, [data.array,data.array1,data.array2], {
	    				title:data.name,
	    				series:[{},{linePattern: 'dashed',markerOptions: { size:1 }},{linePattern: 'dashed',markerOptions: { size:1 }}],
						axes:{
					       xaxis:{
					         renderer:$.jqplot.DateAxisRenderer,
					         tickOptions:{
					           formatString:'%m/%d'
					         } 
					       },
					       yaxis:{
					         tickOptions:{
					           formatString:'%.4f'
					           }
					       }
					     },
					     highlighter: {
					       show: true,
					       sizeAdjust: 5.0
					     },
					     cursor:{ 
		    			   show: true,
		    			   zoom:true, 
		    			   showTooltip:false
		    			 }
	        		});
	    			$('.button-reset').click(function() { plot1.resetZoom() });
		 		});
			},
			loadComplete: function() {
				if ($("#sampleTitle").html() == "") {
					$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
				}
				var hisDate = jQuery("#rowed3").jqGrid("getGridParam", "userData").hisDate;
				var sameSample = jQuery("#rowed3").jqGrid("getGridParam", "userData").sameSample;
				$("#jqgh_rowed3_last").html("\u5386\u53f2");
				$("#jqgh_rowed3_last1").html("\u5386\u53f2");
				$("#jqgh_rowed3_last2").html("\u5386\u53f2");
				$("#jqgh_rowed3_last3").html("\u5386\u53f2");
				$("#jqgh_rowed3_last4").html("\u5386\u53f2");
				if (hisDate != null && hisDate != "") {
					var his = hisDate.split(",");
					if (his.length == 1) {
						$("#jqgh_rowed3_last").html(his[0]);
					}else if (his.length == 2) {
						$("#jqgh_rowed3_last").html(his[0]);
						$("#jqgh_rowed3_last1").html(his[1]);
					}else if (his.length == 3) {
						$("#jqgh_rowed3_last").html(his[0]);
						$("#jqgh_rowed3_last1").html(his[1]);
						$("#jqgh_rowed3_last2").html(his[2]);
					} else if (his.length == 4) {
						$("#jqgh_rowed3_last").html(his[0]);
						$("#jqgh_rowed3_last1").html(his[1]);
						$("#jqgh_rowed3_last2").html(his[2]);
						$("#jqgh_rowed3_last3").html(his[3]);
					} else {
						$("#jqgh_rowed3_last").html(his[0]);
						$("#jqgh_rowed3_last1").html(his[1]);
						$("#jqgh_rowed3_last2").html(his[2]);
						$("#jqgh_rowed3_last3").html(his[3]);
						$("#jqgh_rowed3_last4").html(his[4]);
					}
				}
				if ($("#sampleTitle").html() == "") {
					$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
				}
				//alert($("#rowed3").jqGrid("getCaption"));
				$.each(jQuery('#rowed3').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#rowed3").jqGrid('getRowData',v);
        			if (ret.last != null && ret.last != "")
        				$("#hisLastResult").val(1);
        			else
        				$("#hisLastResult").val(0);
        			var hl = ret.scope.split("-");
        			var h = parseFloat(hl[1]);
        			var l = parseFloat(hl[0]);
        			var color = "<div class='";
        			if (ret.color == 1) {
        				color += "diff_td'>";
        			} else if (ret.color == 2) {
        				color += "ratio_td'>";
        			} else if (ret.color == 3) {
        				color += "dan_td'>";
        			} else if (ret.color == 4) {
        				color += "re_td'>";
        			} else if (ret.color == 5) {
        				color += "al2_td'>";
        			} else if (ret.color == 6) {
        				color += "al3_td'>";
        			} else if (ret.color == 7) {
        				color += "ex_td'>";
        			} else {
        				color += "'>";
        			}
        			
        			if (hl.length != 2) {
        				jQuery("#rowed3").jqGrid('setRowData', v, {
        					name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a>",
        					result:color+"<span class='result_span'>"+ret.result+"</span></div>"
        				});
        				return true;
        			}
        			
        			var va = parseFloat(ret.result);
        			var la = parseFloat(ret.last);
        			var la1 = parseFloat(ret.last1);
        			var la2 = parseFloat(ret.last2);
        			var la3 = parseFloat(ret.last3);
        			var la4 = parseFloat(ret.last4);
        			var res = "";
        			var res1 = "";
        			var res2 = "";
        			var res3 = "";
        			var res4 = "";
        			var res5 = "";
        			
        			if (!isNaN(h) && !isNaN(l)) {
        				if (!isNaN(va)) {
        					if (va < l) {
	        					res = "<font color='red'>\u2193</font>";
	        				} else if (va > h) {
	        					res = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la)) {
        					if (la < l) {
	        					res1 = "<font color='red'>\u2193</font>";
	        				} else if (la > h) {
	        					res1 = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la1)) {
        					if (la1 < l) {
	        					res2 = "<font color='red'>\u2193</font>";
	        				} else if (la1 > h) {
	        					res2 = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la2)) {
        					if (la2 < l) {
	        					res3 = "<font color='red'>\u2193</font>";
	        				} else if (la2 > h) {
	        					res3 = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la3)) {
        					if (la3 < l) {
	        					res4 = "<font color='red'>\u2193</font>";
	        				} else if (la3 > h) {
	        					res4 = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la4)) {
        					if (la4 < l) {
	        					res5 = "<font color='red'>\u2193</font>";
	        				} else if (la4 > h) {
	        					res5 = "<font color='red'>\u2191</font>";
	        				}
        				}
        			}
        			
					if (ret.editMark != 0 && ret.editMark % 33 == 0) {
        				jQuery("#rowed3").jqGrid('setRowData', v, {
        					name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a><font color='red'>*</font>",
        					result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>",
        					last:"<span class='last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='last_span'>" + ret.last1 + "</span>"+res2,
        					last2:"<span class='last_span'>" + ret.last2 + "</span>"+res3,
        					last3:"<span class='last_span'>" + ret.last3 + "</span>"+res4,
        					last4:"<span class='last_span'>" + ret.last4 + "</span>"+res5
        				});
					} else {
						jQuery("#rowed3").jqGrid('setRowData', v, {
							name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a>",
							result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>",
							last:"<span class='last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='last_span'>" + ret.last1 + "</span>"+res2,
        					last2:"<span class='last_span'>" + ret.last2 + "</span>"+res3,
        					last3:"<span class='last_span'>" + ret.last3 + "</span>"+res4,
        					last4:"<span class='last_span'>" + ret.last4 + "</span>"+res5
						});
					}
        		}); 
				if ($("#lastDepLib").val() == '1300600' && sameSample != null && sameSample != "") {
					alert(sameSample);
				}
			},
			editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + sampleNo
		});
	}
	
	function getSample0(sampleNo) {

        var lastsel;
        var cl = "";
        var isEdit = false;
		jQuery("#sample0").jqGrid({
		   	url:"<c:url value='/explain/audit/sample0'/>?id="+sampleNo,
			datatype: "json",
			jsonReader : {repeatitems : false, userdata : "userdata"},  
		   	colNames:['ID','Color','<fmt:message key="project"/>', '<fmt:message key="result"/>', '\u5386\u53f2', '\u5386\u53f2', '\u673a\u5668\u53f7', '<fmt:message key="scope"/>', '<fmt:message key="unit"/>','KNOWLEDGE','EDITMARK'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'color',index:'color', hidden:true},
		   		{name:'name',index:'name',width:85,sortable:false},
		   		{name:'result',index:'result',width:60, sortable:false, editable:true},
		   		{name:'last',index:'last',width:40, sortable:false},
		   		{name:'last1',index:'last1',width:40, sortable:false},
		   		{name:'device',index:'device',width:50, hidden:true, sortable:false},
		   		{name:'scope',index:'scope',width:65,sortable:false},
		   		{name:'unit', sortable:false, width:40, index:'unit'},
		   		{name:'knowledgeName',index:'knowledgeName', hidden:true},
		   		{name:'editMark',index:'editMark',hidden:true}
		   	],
		   	height: "100%",
		   	rowNum: 50,
		    caption: "",
			onSelectRow: function(id) {
				
				if($("#needEdit").val() == "true") {
					if (lastsel) {
						if (lastsel == id) {
							if (!isEdit) {
								isEdit = true;
								var ret = jQuery("#sample0").jqGrid('getRowData',id);
								var pre = "<div class='"+$(ret.result).attr('class')+"'>";
								cl = pre + $(ret.result).html() + "</div>";
								lastval = $(ret.result).find(":eq(0)").html();
								jQuery("#sample0").jqGrid('setRowData', id, {result:lastval});
								jQuery("#sample0").jqGrid('editRow',id, {
									keys:true,
									aftersavefunc:function() {
										var newVal = jQuery("#sample0").jqGrid('getRowData',id);
										var hl = newVal.scope.split("-");
					        			var h = parseFloat(hl[1]);
					        			var l = parseFloat(hl[0]);
					        			var va = parseFloat(newVal.result);
					        			var res = "";
					        			
					        			if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
					        				if (va < l) {
					        					res = "<font color='red'>\u2193</font>";
					        				} else if (va > h) {
					        					res = "<font color='red'>\u2191</font>";
					        				}
					        			}
										jQuery("#sample0").jqGrid('setRowData', id, {result:pre + "<span class='result_span'>" + newVal.result + "</span>"+res+"</div>"});
										isEdit = false;
									}				
								});
							}
						} else {
							jQuery('#sample0').jqGrid('restoreRow', lastsel);
							if (isEdit) {
								jQuery("#sample0").jqGrid('setRowData', lastsel, {result:cl});
							}
							isEdit = false;
						}
					}
					lastsel=id;
				}
				
				var sample1_selected = jQuery("#sample1").jqGrid('getGridParam','selrow');
				if(sample1_selected!=null) {
					jQuery("#sample1").jqGrid("resetSelection", sample1_selected);
				}
			},
			loadComplete: function() {
				if ($("#sampleTitle").html() == "") {
					$("#sample0").jqGrid("setCaption", $("#sampleTitle").html());
				}
				var hisDate = jQuery("#sample0").jqGrid("getGridParam", "userData").hisDate;
				var sameSample = jQuery("#sample0").jqGrid("getGridParam", "userData").sameSample;
				$("#jqgh_sample0_last").html("\u5386\u53f2");
				$("#jqgh_sample0_last1").html("\u5386\u53f2");
				if (hisDate != null && hisDate != "") {
					var his = hisDate.split(",");
					if (his.length == 1) {
						$("#jqgh_sample0_last").html(his[0]);
					}else if (his.length == 2) {
						$("#jqgh_sample0_last").html(his[0]);
						$("#jqgh_sample0_last1").html(his[1]);
					}else {
						$("#jqgh_sample0_last").html(his[0]);
						$("#jqgh_sample0_last1").html(his[1]);
						$("#jqgh_sample0_last2").html(his[2]);
					}
				}
				if ($("#sampleTitle").html() == "") {
					$("#sample0").jqGrid("setCaption", $("#sampleTitle").html());
				}
				$.each(jQuery('#sample0').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#sample0").jqGrid('getRowData',v);
        			if (ret.last != null && ret.last != "")
        				$("#hisLastResult").val(1);
        			else
        				$("#hisLastResult").val(0);
        			var hl = ret.scope.split("-");
        			var h = parseFloat(hl[1]);
        			var l = parseFloat(hl[0]);
        			var color = "<div class='";
        			if (ret.color == 1) {
        				color += "diff_td'>";
        			} else if (ret.color == 2) {
        				color += "ratio_td'>";
        			} else if (ret.color == 3) {
        				color += "dan_td'>";
        			} else if (ret.color == 4) {
        				color += "re_td'>";
        			} else if (ret.color == 5) {
        				color += "al2_td'>";
        			} else if (ret.color == 6) {
        				color += "al3_td'>";
        			} else if (ret.color == 7) {
        				color += "ex_td'>";
        			} else {
        				color += "'>";
        			}
        			
        			if (hl.length != 2) {
        				jQuery("#sample0").jqGrid('setRowData', v, {
        					name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a>",
        					result:color+"<span class='result_span'>"+ret.result+"</span></div>"
        				});
        				return true;
        			}
        			
        			var va = parseFloat(ret.result);
        			var la = parseFloat(ret.last);
        			var la1 = parseFloat(ret.last1);
        			var res = "";
        			var res1 = "";
        			var res2 = "";
        			
        			if (!isNaN(h) && !isNaN(l)) {
        				if (!isNaN(va)) {
        					if (va < l) {
	        					res = "<font color='red'>\u2193</font>";
	        				} else if (va > h) {
	        					res = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la)) {
        					if (la < l) {
	        					res1 = "<font color='red'>\u2193</font>";
	        				} else if (la > h) {
	        					res1 = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la1)) {
        					if (la1 < l) {
	        					res2 = "<font color='red'>\u2193</font>";
	        				} else if (la1 > h) {
	        					res2 = "<font color='red'>\u2191</font>";
	        				}
        				}
        			}
        			
					if (ret.editMark != 0 && ret.editMark % 33 == 0) {
        				jQuery("#sample0").jqGrid('setRowData', v, {
        					name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a><font color='red'>*</font>",
        					result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>",
        					last:"<span class='last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='last_span'>" + ret.last1 + "</span>"+res2
        				});
					} else {
						jQuery("#sample0").jqGrid('setRowData', v, {
							name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a>",
							result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>",
							last:"<span class='last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='last_span'>" + ret.last1 + "</span>"+res2
						});
					}
        		}); 
				if ($("#lastDepLib").val() == '1300600' && sameSample != null && sameSample != "") {
					alert(sameSample);
				}
			},
			editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + sampleNo
		});
		
		$("#gbox_sample0").css('float','left');
	}
	
	function getSample1(sampleNo) {

        var lastsel;
        var cl = "";
        var isEdit = false;
		jQuery("#sample1").jqGrid({
		   	url:"<c:url value='/explain/audit/sample1'/>?id="+sampleNo,
			datatype: "json",
			jsonReader : {repeatitems : false, userdata : "userdata"},  
		   	colNames:['ID','Color','<fmt:message key="project"/>', '<fmt:message key="result"/>', '\u5386\u53f2', '\u5386\u53f2', '\u673a\u5668\u53f7', '<fmt:message key="scope"/>', '<fmt:message key="unit"/>','KNOWLEDGE','EDITMARK'],
		   	colModel:[
		   	   	{name:'id',index:'id', hidden:true},
		   		{name:'color',index:'color', hidden:true},
		   		{name:'name',index:'name',width:85,sortable:false},
		   		{name:'result',index:'result',width:60, sortable:false, editable:true},
		   		{name:'last',index:'last',width:40, sortable:false},
		   		{name:'last1',index:'last1',width:40, sortable:false},
		   		{name:'device',index:'device',width:50, hidden:true, sortable:false},
		   		{name:'scope',index:'scope',width:65,sortable:false},
		   		{name:'unit', sortable:false, width:40, index:'unit'},
		   		{name:'knowledgeName',index:'knowledgeName', hidden:true},
		   		{name:'editMark',index:'editMark',hidden:true}
		   	],
		   	height: "100%",
		   	rowNum: 50,
		    caption: "",
			onSelectRow: function(id) {
				
				if($("#needEdit").val() == "true") {
					if (lastsel) {
						if (lastsel == id) {
							if (!isEdit) {
								isEdit = true;
								var ret = jQuery("#sample1").jqGrid('getRowData',id);
								var pre = "<div class='"+$(ret.result).attr('class')+"'>";
								cl = pre + $(ret.result).html() + "</div>";
								lastval = $(ret.result).find(":eq(0)").html();
								jQuery("#sample1").jqGrid('setRowData', id, {result:lastval});
								jQuery("#sample1").jqGrid('editRow',id, {
									keys:true,
									aftersavefunc:function() {
										var newVal = jQuery("#sample1").jqGrid('getRowData',id);
										var hl = newVal.scope.split("-");
					        			var h = parseFloat(hl[1]);
					        			var l = parseFloat(hl[0]);
					        			var va = parseFloat(newVal.result);
					        			var res = "";
					        			
					        			if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
					        				if (va < l) {
					        					res = "<font color='red'>\u2193</font>";
					        				} else if (va > h) {
					        					res = "<font color='red'>\u2191</font>";
					        				}
					        			}
										jQuery("#sample1").jqGrid('setRowData', id, {result:pre + "<span class='result_span'>" + newVal.result + "</span>"+res+"</div>"});
										isEdit = false;
									}				
								});
							}
						} else {
							jQuery('#sample1').jqGrid('restoreRow', lastsel);
							if (isEdit) {
								jQuery("#sample1").jqGrid('setRowData', lastsel, {result:cl});
							}
							isEdit = false;
						}
					}
					lastsel=id;
				}
				
				var sample0_selected = jQuery("#sample0").jqGrid('getGridParam','selrow');
				if(sample0_selected!=null) {
					jQuery("#sample0").jqGrid("resetSelection", sample0_selected);
				}
			},
			loadComplete: function() {

				var hisDate = jQuery("#sample1").jqGrid("getGridParam", "userData").hisDate;
				$("#jqgh_sample1_last").html("\u5386\u53f2");
				$("#jqgh_sample1_last1").html("\u5386\u53f2");
				if (hisDate != null && hisDate != "") {
					var his = hisDate.split(",");
					if (his.length == 1) {
						$("#jqgh_sample1_last").html(his[0]);
					}else if (his.length == 2) {
						$("#jqgh_sample1_last").html(his[0]);
						$("#jqgh_sample1_last1").html(his[1]);
					}else {
						$("#jqgh_sample1_last").html(his[0]);
						$("#jqgh_sample1_last1").html(his[1]);
						$("#jqgh_sample1_last2").html(his[2]);
					}
				}
				$.each(jQuery('#sample1').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#sample1").jqGrid('getRowData',v);
        			if (ret.last != null && ret.last != "")
        				$("#hisLastResult").val(1);
        			else
        				$("#hisLastResult").val(0);
        			var hl = ret.scope.split("-");
        			var h = parseFloat(hl[1]);
        			var l = parseFloat(hl[0]);
        			var color = "<div class='";
        			if (ret.color == 1) {
        				color += "diff_td'>";
        			} else if (ret.color == 2) {
        				color += "ratio_td'>";
        			} else if (ret.color == 3) {
        				color += "dan_td'>";
        			} else if (ret.color == 4) {
        				color += "re_td'>";
        			} else if (ret.color == 5) {
        				color += "al2_td'>";
        			} else if (ret.color == 6) {
        				color += "al3_td'>";
        			} else if (ret.color == 7) {
        				color += "ex_td'>";
        			} else {
        				color += "'>";
        			}
        			
        			if (hl.length != 2) {
        				jQuery("#sample1").jqGrid('setRowData', v, {
        					name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a>",
        					result:color+"<span class='result_span'>"+ret.result+"</span></div>"
        				});
        				return true;
        			}
        			
        			var va = parseFloat(ret.result);
        			var la = parseFloat(ret.last);
        			var la1 = parseFloat(ret.last1);
        			var res = "";
        			var res1 = "";
        			var res2 = "";
        			
        			if (!isNaN(h) && !isNaN(l)) {
        				if (!isNaN(va)) {
        					if (va < l) {
	        					res = "<font color='red'>\u2193</font>";
	        				} else if (va > h) {
	        					res = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la)) {
        					if (la < l) {
	        					res1 = "<font color='red'>\u2193</font>";
	        				} else if (la > h) {
	        					res1 = "<font color='red'>\u2191</font>";
	        				}
        				}
        				
        				if (!isNaN(la1)) {
        					if (la1 < l) {
	        					res2 = "<font color='red'>\u2193</font>";
	        				} else if (la1 > h) {
	        					res2 = "<font color='red'>\u2191</font>";
	        				}
        				}
        			}
        			
					if (ret.editMark != 0 && ret.editMark % 33 == 0) {
        				jQuery("#sample1").jqGrid('setRowData', v, {
        					name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a><font color='red'>*</font>",
        					result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>",
        					last:"<span class='last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='last_span'>" + ret.last1 + "</span>"+res2
        				});
					} else {
						jQuery("#sample1").jqGrid('setRowData', v, {
							name:"<a href='javascript:show_knowledge(\""+ret.knowledgeName+"\")'>"+ret.name+"</a>",
							result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>",
							last:"<span class='last_span'>" + ret.last + "</span>"+res1,
        					last1:"<span class='last_span'>" + ret.last1 + "</span>"+res2
						});
					}
        		}); 
			},
			editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + sampleNo
		});
		
		$("#gbox_sample1").css('float','left');
		$("#gbox_sample1").css('margin-left','5px');
	}
	
	function getTwoColumn(sampleNo) {

		jQuery("#twocol").jqGrid({
		   	url:"<c:url value='/explain/audit/twoColumn'/>?id="+sampleNo,
			datatype: "json",
			jsonReader : {repeatitems : false, userdata : "userdata"},  
		   	colNames:['ID','Color','','<fmt:message key="project"/>', '<fmt:message key="result"/>', '\u5386\u53f2', '\u5386\u53f2', '\u5386\u53f2', '\u673a\u5668\u53f7', '<fmt:message key="scope"/>', '<fmt:message key="unit"/>','KNOWLEDGE', 'ID','Color','','<fmt:message key="project"/>', '<fmt:message key="result"/>', '\u5386\u53f2', '\u5386\u53f2', '\u5386\u53f2', '\u673a\u5668\u53f7', '<fmt:message key="scope"/>', '<fmt:message key="unit"/>','KNOWLEDGE'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'color',index:'color', hidden:true},
		   		{name:'num',index:'num', width:20},
		   		{name:'name',index:'name',width:100,sortable:false},
		   		{name:'result',index:'result',width:65, sortable:false, editable:true},
		   		{name:'last',index:'last',width:40, sortable:false},
		   		{name:'last1',index:'last1',width:40, sortable:false},
		   		{name:'last2',index:'last2',width:40, sortable:false},
		   		{name:'device',index:'device',width:30, sortable:false},
		   		{name:'scope',index:'scope',width:80,sortable:false},
		   		{name:'unit', index:'unit', sortable:false, width:60},
		   		{name:'knowledgeName',index:'knowledgeName', hidden:true},
		   		{name:'_id',index:'_id', hidden:true},
		   		{name:'_color',index:'_color', hidden:true},
		   		{name:'_num',index:'_num', width:20},
		   		{name:'_name',index:'_name',width:100,sortable:false},
		   		{name:'_result',index:'_result',width:65, sortable:false, editable:true},
		   		{name:'_last',index:'_last',width:40, sortable:false},
		   		{name:'_last1',index:'_last1',width:40, sortable:false},
		   		{name:'_last2',index:'_last2',width:40, sortable:false},
		   		{name:'_device',index:'_device',width:30, sortable:false},
		   		{name:'_scope',index:'_scope',width:80,sortable:false},
		   		{name:'_unit', index:'_unit', sortable:false, width:60},
		   		{name:'_knowledgeName',index:'_knowledgeName', hidden:true}
		   	],
		   	height: "100%",
		   	rowNum: 100,
			loadComplete: function() {

				var hisDate = jQuery("#twocol").jqGrid("getGridParam", "userData").hisDate;
				$("#jqgh_twocol_last").html("\u5386\u53f2");
				$("#jqgh_twocol_last1").html("\u5386\u53f2");
				$("#jqgh_twocol_last2").html("\u5386\u53f2");
				$("#jqgh_twocol__last").html("\u5386\u53f2");
				$("#jqgh_twocol__last1").html("\u5386\u53f2");
				$("#jqgh_twocol__last2").html("\u5386\u53f2");
				if (hisDate != null && hisDate != "") {
					var his = hisDate.split(",");
					if (his.length == 1) {
						$("#jqgh_twocol_last").html(his[0]);
						$("#jqgh_twocol__last").html(his[0]);
					} else if (his.length == 2) {
						$("#jqgh_twocol_last").html(his[0]);
						$("#jqgh_twocol_last1").html(his[1]);
						$("#jqgh_twocol__last").html(his[0]);
						$("#jqgh_twocol__last1").html(his[1]);
					} else {
						$("#jqgh_twocol_last").html(his[0]);
						$("#jqgh_twocol_last1").html(his[1]);
						$("#jqgh_twocol_last2").html(his[2]);
						$("#jqgh_twocol__last").html(his[0]);
						$("#jqgh_twocol__last1").html(his[1]);
						$("#jqgh_twocol__last2").html(his[2]);
					}
				}
				
				$.each(jQuery('#twocol').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#twocol").jqGrid('getRowData',v);
        			var hl = ret.scope.split("-");
        			var _hl = ret._scope.split("-");
        			if (hl.length == 2) {
	        			var h = parseFloat(hl[1]);
	        			var l = parseFloat(hl[0]);
	        			var va = parseFloat(ret.result);
	        			var res = "";
	        			var color = "<div class='";
	        			if (ret.color == 1) {
	        				color += "diff_td'>";
	        			} else if (ret.color == 2) {
	        				color += "ratio_td'>";
	        			} else if (ret.color == 3) {
	        				color += "dan_td'>";
	        			} else if (ret.color == 4) {
	        				color += "re_td'>";
	        			} else if (ret.color == 5) {
	        				color += "al2_td'>";
	        			} else if (ret.color == 6) {
	        				color += "al3_td'>";
	        			} else if (ret.color == 7) {
	        				color += "ex_td'>";
	        			} else {
	        				color += "'>";
	        			}
	        			
	        			if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
	        				if (va < l) {
	        					res = "<font color='red'>\u2193</font>";
	        				} else if (va > h) {
	        					res = "<font color='red'>\u2191</font>";
	        				}
	        			}
	        			jQuery("#twocol").jqGrid('setRowData', v, {result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>"});
        			}
        			if (_hl.length == 2) {
	        			var h = parseFloat(_hl[1]);
	        			var l = parseFloat(_hl[0]);
	        			var va = parseFloat(ret._result);
	        			var res = "";
	        			var color = "<div class='";
	        			if (ret._color == 1) {
	        				color += "diff_td'>";
	        			} else if (ret._color == 2) {
	        				color += "ratio_td'>";
	        			} else if (ret._color == 3) {
	        				color += "dan_td'>";
	        			} else if (ret._color == 4) {
	        				color += "re_td'>";
	        			} else if (ret._color == 5) {
	        				color += "al2_td'>";
	        			} else if (ret._color == 6) {
	        				color += "al3_td'>";
	        			} else if (ret._color == 7) {
	        				color += "ex_td'>";
	        			} else {
	        				color += "'>";
	        			}
	        			
	        			if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
	        				if (va < l) {
	        					res = "<font color='red'>\u2193</font>";
	        				} else if (va > h) {
	        					res = "<font color='red'>\u2191</font>";
	        				}
	        			}
	        			jQuery("#twocol").jqGrid('setRowData', v, {_result:color+"<span class='result_span'>"+ret._result+"</span>"+res+"</div>"});
        			}

        			
        		}); 
			}
		});
	}
	
	function getExplain(docNo){
		var lastsel;
        jQuery("#audit_information").tableDnD({
	        onDrop:function(table){
	        	var rows = $("tr",table);
	        	var content = rows[1].id;
	        	for(var i=2;i<rows.length;i++){
	        		content = content + "," + rows[i].id;
	        	}
	        	$.post("<c:url value='/explain/audit/drag'/>",{docNo:$("#hiddenDocId").val(), content:content},function(data) {
				});
	        } 
	    });
		jQuery("#audit_information").jqGrid({
			url:"<c:url value='/explain/audit/explain'/>?id="+docNo,
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['ID','OLDRESULT','<fmt:message key="addResult.result"/>','<fmt:message key="content"/>','RANK'],
		   	colModel:[{name:'id',index:'id',sortable:false,hidden:true},
		   		{name:'oldResult',index:'oldResult',sortable:false,hidden:true,editable:true},
		   		{name:'result',index:'result',width:190,sortable:false,editable:true},
		   		{name:'content',index:'content',width:190,sortable:false,hidden:true,editable:true},
		   		{name:'rank',index:'rank',sortable:false,hidden:true,editable:false}],
		    gridComplete: function() {
		    	jQuery("#audit_information").tableDnDUpdate();
		    }, 
		   	onSelectRow: function(id){
		   		if(id && id!==lastsel){
					jQuery('#audit_information').jqGrid('restoreRow',lastsel);
					jQuery('#audit_information').jqGrid('editRow',id,true);
					lastsel=id;
				}
			},
			editurl: "<c:url value='/explain/audit/explain/edit'/>?docNo=" + docNo,
		   	height: '100%'
		});
		
	}
	var isFirstTime = true;
	
	function getList(text, lab) {
		
		var mygrid = jQuery("#s3list").jqGrid({ 
        	url:"<c:url value='/explain/audit/data'/>?sample="+text+"&lab="+lab, 
        	datatype: "json", 
        	width: 230, 
        	colNames:['ID','<fmt:message key="audit.status" />', '<fmt:message key="audit.mark" />', '', '<fmt:message key="audit.sample.number" />','FLAG','SIZE'], 
        	colModel:[ 
        		{name:'id',index:'id', hidden:true}, 
        		{name:'status',index:'status', width:50, sortable:false, stype:'select',editoptions:{value:"-3:\u6240\u6709;-1:\u65e0\u7ed3\u679c;0:<fmt:message key='audit.unaudit'/>;1:<fmt:message key='audit.pass'/>;2:<fmt:message key='audit.unpass'/>;3:<fmt:message key='audit.modified'/>;4:<fmt:message key='audit.unchecked'/>"}},
        		{name:'mark',index:'mark', width:40, sortable:false, stype:'select',editoptions:{value:"0:\u6240\u6709;1:\u81ea\u52a8;2:\u5dee\u503c;3:\u6bd4\u503c;4:\u5c11\u505a;5:\u590d\u68c0;6:\u5371\u6025;7:\u8b66\u62121;8:\u8b66\u62122;9:\u6781\u503c;10:Bayes"}}, 
        		{name:'lisPass',index:'lisPass', width:10, sortable:false},
        		{name:'sample',index:'sample', width:120, sortable:false},
        		{name:'flag',index:'flag', hidden:true},
        		{name:'size',index:'size', hidden:true}], 
        	rowNum:25,
        	height: '100%',
        	jsonReader : {repeatitems : false},
        	mtype: "GET", 
        	pager: '#s3pager',
        	multiselect: true,
        	multiboxonly:true,
        	onSelectRow: function(id) {
        		var ret = jQuery("#s3list").jqGrid('getRowData',id);
        		getPatient(ret);
        		$("#testAdd").css('display','inline');
    			$("#testDelete").css('display','inline');
    			$("#auditUnpassBtn").css('display','inline');
    			$("#auditPassBtn").css('display','inline');
    			$("#auditPrintBtn").css('display','none');
    			
    			$("#needEdit").val(true);
        		if(ret.status == "<fmt:message key='audit.unaudit'/>") {
        			$("#unaudit_reason_btn").hide();	
        		} else if(ret.status == "<fmt:message key='audit.pass'/>") {
        			$("#unaudit_reason_btn").hide();
        			$("#testAdd").css('display','none');
        			$("#testDelete").css('display','none');
        			$("#auditPrintBtn").css('display','inline');
        			$("#auditPassBtn").css('display','none');
        			$("#needEdit").val(false);
        		} else if (ret.status == "\u65e0\u7ed3\u679c"){
        			$("#unaudit_reason_btn").hide();
        			$("#testAdd").css('display','none');
        			$("#testDelete").css('display','none');
        			$("#auditPrintBtn").css('display','none');
        			$("#auditUnpassBtn").css('display','none');
        			$("#auditPassBtn").css('display','none');
        		} else {
        			$("#unaudit_reason_btn").show();
        			$("#auditUnpassBtn").css('display','none');
        		}
        		
        		$("#hiddenSampleNo").val(ret.sample);
        		$("#sampleTitle").html("");
        		$("#hisLastResult").val(null);
        		$("#span_sampleNo").html(ret.sample);
        		$("#test_sampleNo").val(ret.sample);

        		
        		
        		if(ret.flag==0){
        			$("#modifyBtn").css('display','none');
        		}else{
        			$("#modifyBtn").css('display','inline');
        		}
        		

        	},
        	loadComplete: function() {
        		jQuery("#s3list").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/data'/>?lab=" + $("#labSelect").val()});
        		var lg = $("#gs_sample").val().length;
        		
        		$("#s3list_cb").css('width',23);
        		$("#s3list_status").css('width',48);
        		if (lg > 14 && $("#gs_mark").children('option:selected').val() == 0) {
        			$("#jqgh_s3list_mark").css('display','none');
    				$("#s3list_mark").css('width',0);
            		$("#s3list_sample").css('width',153);
    			} else {
    				$("#jqgh_s3list_mark").css('display','block');
    				$("#s3list_mark").css('width',38);
            		$("#s3list_sample").css('width',115);
    			}
        		var firstDocId, firstSampleNo;
        		var length = jQuery('#s3list').jqGrid('getCol','id', false).length;
        		if(length==0){
        			$("#midContent").css("display","none");
        		}
        		$.each(jQuery('#s3list').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#s3list").jqGrid('getRowData',v);
        			if (k == 0) {
        				firstDocId = ret.id;
        				firstSampleNo = ret.sample;
        			}
            		if (ret.status == "<fmt:message key='audit.unpass'/>") {
            			jQuery("#s3list").jqGrid('setRowData', v, {status:"<font color='red'>"+ret.status+"</font>"});
            	 	}
            		if (ret.mark == "\u5371\u6025") {
        				jQuery("#s3list").jqGrid('setRowData', v, {mark:"<div class='dan_td'>"+ret.mark+"</div>"});
            		} else if (ret.mark == "\u5dee\u503c") {
            			jQuery("#s3list").jqGrid('setRowData', v, {mark:"<div class='diff_td'>"+ret.mark+"</div>"});
            		} else if (ret.mark == "\u6bd4\u503c") {
            			jQuery("#s3list").jqGrid('setRowData', v, {mark:"<div class='ratio_td'>"+ret.mark+"</div>"});
            		} else if (ret.mark == "\u590d\u68c0") {
            			jQuery("#s3list").jqGrid('setRowData', v, {mark:"<div class='re_td'>"+ret.mark+"</div>"});
            		} else if (ret.mark == "\u8b66\u62121") {
            			jQuery("#s3list").jqGrid('setRowData', v, {mark:"<div class='al2_td'>"+ret.mark+"</div>"});
            		} else if (ret.mark == "\u8b66\u62122") {
            			jQuery("#s3list").jqGrid('setRowData', v, {mark:"<div class='al3_td'>"+ret.mark+"</div>"});
            		} else if (ret.mark == "\u6781\u503c") {
            			jQuery("#s3list").jqGrid('setRowData', v, {mark:"<div class='ex_td'>"+ret.mark+"</div>"});
            		}
        		}); 
        		$("#s3list").setSelection(firstDocId, true);
        		
        		if ($("#gs_status").children('option:selected').val() != 1 && $("#gs_status").children('option:selected').val() != 2) {
    				$("#gs_mark").css('display','none');
    			}
        		$("#gs_status").change(function(){

        			if ($(this).children('option:selected').val() == 1 || $(this).children('option:selected').val() == 2) {
        				$("#gs_mark").css('display','block');
        			} else {
        				$("#gs_mark").css('display','none');
        			}
        		});
        		
        		$("#gs_sample").keyup(function(){
        			
        			var lg = $("#gs_sample").val().length;
            		
        			/* if (lg > 14 && $("#gs_mark").children('option:selected').val() == 0) { */
        			if (lg > 14) {
        				$("#jqgh_s3list_mark").css('display','none');
        				$("#s3list_mark").css('width',0);
                		$("#s3list_sample").css('width',153);
        			} else {
        				$("#jqgh_s3list_mark").css('display','block');
        				$("#s3list_mark").css('width',38);
                		$("#s3list_sample").css('width',115);
        			}
        		});
        	}
        }).trigger("reloadGrid"); 
        jQuery("#s3list").jqGrid('navGrid','#s3pager',{edit:false,add:false,del:false,search:false,refresh:false});
        jQuery("#s3list").jqGrid('filterToolbar');
        jQuery("#s3list").jqGrid('navButtonAdd',"#s3pager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
	}
	
	function getProValue() {
    	$.get("<c:url value='/task/ajax/audit'/>",{},function(data){

    		var ids = {
     			put : function(key,value){this[key] = value},
     			get : function(key){return this[key]},
     			contains : function(key){return this[key] == null?false:true},
     			remove : function(key){delete this[key]}
     		}
    		
    		$("#auditDialog div.proId").each(function(index,self) {
    			ids.put($(self).html(),$(self).html())
    		});
    		
    		var map = {
   				put : function(key,value){this[key] = value},
   				get : function(key){return this[key]},
   				contains : function(key){return this[key] == null?false:true},
   				remove : function(key){delete this[key]}
   			}
    		var array = jQuery.parseJSON(data);
    		for (var i=0 ; i < array.length ; i++) {
    			map.put(array[i].id,i);
    			if (!ids.contains(array[i].id)) {
    				var text = array[i].text;
    				if (text.length > 11) {
    					text=text.slice(0,11)+"...";
    				}
    				var content = "<div><table style='margin:0px;'><tr><td><div class='proRatio'></div></td><td><div class='proId'>"
    				+ array[i].id
    				+ "</div></td><td><div style='width:260px;'><div class='proStart'>"
    				+ "</div><div class='proEnd'></div></div></td><td></td></tr><tr><td style='width:100px;display:block;'><span>"
    				+ text
    				+ "</span></td><td>"
    				+ "<div class='proStatus'></div><input class='hiddenValue' type='hidden' value='100' ></td><td><div class='proValue'>"
    				+ "</div></td><td><input type='button' class='stopAudit btn' value='\u505c\u6b62'/></td></tr>"
    				+ "</table><div style='border-top: 1px solid #E1E1E1;margin-bottom:10px;'></div></div>";
    				//$("#auditDialog").prepend(content);
    				$(content).insertAfter($("#searchPanel"));
    			}
    		}
    		
   			$("#auditDialog table").each(function(index,self) {
       			var id = $(self).find("div.proId").html();
       			var da = array[map.get(id)];
       			if (da == null) return true; //continue;
       			$(self).find("div.proStart").html(da.start);
       			$(self).find("div.proEnd").html(da.end);
       			$(self).find("div.proRatio").html(da.ratio);
       			$(self).find("div.proValue").progressbar({value:da.value});
       			if (da.status == "1") {
       				$(self).find("div.proStatus").html("<img src='<c:url value='/images/status.run.png'/>' class='status_icon'/>");
       				$(self).find(".stopAudit").removeAttr("disabled");
       				$(self).find(".stopAudit").click(function(){
       					$.get("<c:url value='/task/ajax/cancel'/>",{id : id}, function() {});
       				});
        		} else if (da.status =="2") {
        			var value = $(self).find("input.hiddenValue").val();
        			$(self).find("div.proStatus").html("<img src='<c:url value='/images/status.finished.png'/>' class='status_icon'/>");
        			$(self).find(".stopAudit").attr('disabled',"true");
        			if (value != 100 && da.value != 0) {
        				jQuery("#s3list").trigger("reloadGrid");
        			}
        		} else if (da.status == "3") {
        			$(self).find("div.proStatus").html("<img src='<c:url value='/images/status.cancel.png'/>' class='status_icon'/>");
        			$(self).find(".stopAudit").attr('disabled',"true");
        		} else if (da.status == "0") {
        			$(self).find("div.proStatus").html("<img src='<c:url value='/images/status.wait.gif'/>' style='width:20px;height:20px;margin-left:1px;' class='status_icon'/>");
        			$(self).find(".stopAudit").removeAttr("disabled");
       				$(self).find(".stopAudit").click(function(){
       					$.get("<c:url value='/task/ajax/cancel'/>",{id : id}, function() {});
       				});
        		}
       			$(self).find("input.hiddenValue").val(da.value);
       		});
   			
   			if ($("#isContinued").html() == "1") {
   				//getProValue(1000);
   				setTimeout(getProValue,1000);
   			}
    	});
    }

	$(function() {
		var library = "<c:out value='${library}'/>";
		//alert(library);
		if(library != null || library != ""){
			$("#labSelect").val(library);
		}

		$.ajaxSetup({cache:false});
		
		$("#historyTabs").tabs({
			selected: 2,
			select: function(event, ui) { 
				if(ui.index == 2) {
					/* jQuery("#rowed3").setGridParam().showCol("last1"); */
					jQuery("#rowed3").setGridParam().showCol("last2");
					jQuery("#rowed3").setGridParam().showCol("last3");
					jQuery("#rowed3").setGridParam().showCol("last4");
					jQuery("#rowed3").setGridParam().showCol("device");
					jQuery("#rowed3").setGridParam().showCol("checktime");
				} else {
					/* jQuery("#rowed3").setGridParam().hideCol("last1"); */
					jQuery("#rowed3").setGridParam().hideCol("last2");
					jQuery("#rowed3").setGridParam().hideCol("last3");
					jQuery("#rowed3").setGridParam().hideCol("last4");
					jQuery("#rowed3").setGridParam().hideCol("device");
					jQuery("#rowed3").setGridParam().hideCol("checktime");
					var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
					if (ui.index == 1) {
						getExplain(s);
					} else if (ui.index == 0) {
	        			getChart(s);
					}
				}
			}
		});
		
		$("#auditDialog").dialog({
			autoOpen: false,
		    resizable: false,
		    width: 500,
		    height: 320,
		    close: function(event, ui) {
		    	$("#isContinued").html("0");
		    }
		});
		$("#auditPrint").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 700,
		    height: 500
		});
		$("#chartDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 640,
		    height: 600
		});
		$("#samplePrint").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 700,
		    height: 500
		});
		
		$( "#dialog" ).dialog({
            autoOpen: false,
            modal:true,
            width: 680,
			height: 360
        });
		
		$("#addTestResultDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 450,
		    height: 400,
		    buttons:{
		    	"<fmt:message key='button.add'/>":function() {
		    		var result = false;
		    		$("#addTestList .testValue").each(function(index,self){
    		    		if ($(self).val() != "") {
    		    			result = true;
    		    		}
    		    	});
		    		if (!result) {
		    			//alert("<fmt:message key='alert.input.testresult'/>");
		    		} else {
		    			var postStr = "";
		    			var sample = $("#hiddenSampleNo").val();
		    			$("#addTestList div").each(function(index,self){
	    		    		var id = $(self).find(".testID").val();
	    		    		var value = $(self).find(".testValue").val();
	    		    		if (value != null && value != "") {
		    		    		if (postStr != "") postStr+=";";
		    		    		postStr += id + ":" + value;
	    		    		}
	    		    	});
		    			//alert(postStr);
		    			if (postStr != "") {
			    			$.post("<c:url value='/explain/audit/add'/>",{test:postStr,sample:sample},function(data){
			    				if (data) {
			    					$("#addTestResultDialog").dialog("close");
			    					var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
			    					var ret = jQuery("#s3list").jqGrid('getRowData',s);
			    					
			    					if(ret.size > 30) {
			    						jQuery("#sample0").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample0'/>?id="+sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + sample}).trigger("reloadGrid");
				    					jQuery("#sample1").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample1'/>?id="+sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + sample}).trigger("reloadGrid");
				    					
			    	    			} else {
			    	    				jQuery("#rowed3").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample'/>?id="+sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + sample}).trigger("reloadGrid");
			    	    			}
			    					
			    				} else {
			    					alert("Failed!");
			    				}
			    			});
		    			}
		    		}
		    	},
		    	"<fmt:message key='button.cancel'/>":function() {
		    		$(this).dialog("close");
		    	}
		    }
		});	
		
		$(".ui-dialog-buttonset button").each(function(index,self){
    		$(self).addClass('btn');
    	});

		$("#addResultDialog").dialog({
			autoOpen: false,
			resizable: false,
		    width: 340,
		    height: 300
		});
		
		$("#tatDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true
		});
		
		$("#twoColumnDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true
		});
		
		$("#opStatusDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 340,
		    height: 250
		});
		
		$("#codeSetDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 340,
		    height: 250
		});
		
		$("#writeBackPartDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 550,
		    height: 100
		});
		
		$("#testModifyDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 480,
		    height: 320
		});
		
		$("#sampleCompareDialog").dialog({
			autoOpen: false,
		    width: 600,
		    height: 500
		});
		
		$("#auditTraceDialog").dialog({
			autoOpen: false,
		    width: 600,
		    height: 500
		});
		
		$("#allNeedWriteBackDialog").dialog({
			autoOpen: false,
			resizable: false,
			modal:true,
		    width: 580,
		    height: 320
		});
		
		$("#statisticDialog").dialog({
			autoOpen: false,
		    width: 600,
		    height: 500
		});
		
		$("#tatBtn").click(function() {
			
			$("#tat_request").html("");
			$("#tat_execute").html("");
			$("#tat_receive").html("");
			$("#tat_audit").html("");
			$("#tat_result").html("");
			$("#tat_send").html("");
			$("#tat_ksreceive").html("");
			$("#audit_tat").html("");
			$("#tatDialog").dialog("open");
			var doc = $("#hiddenDocId").val();
			$.get("<c:url value='/explain/audit/tat'/>",{id:doc},function(data){
				data = jQuery.parseJSON(data);
				$("#tat_request").html(data.request);
				$("#tat_execute").html(data.execute);
				$("#tat_receive").html(data.receive);
				$("#tat_audit").html("<a href='javascript:void(0);' class='btn btn-info' onclick='getAuditHistory()'>" + data.audit + "</a>");
				$("#tat_auditor").html(data.auditor);
				$("#tat_result").html(data.result);
				$("#tat_send").html(data.send);
				$("#tat_ksreceive").html(data.ksreceive);
				var time = parseInt(data.tat);
				var tStr = "";
				if (time >= 1440) {
					var day = Math.floor(time / 1440);
					tStr += day.toString();
					tStr += "<fmt:message key='day'/>";
					time = time - day * 1440;
				}
				
				if (time >= 60) {
					var hour = Math.floor(time / 60);
					tStr += hour.toString();
					tStr += "<fmt:message key='hour'/>";
					time = time - hour * 60;
				}
				tStr += time.toString();
				tStr += "<fmt:message key='minute'/>";
				
				$("#audit_tat").html(tStr);
			});
		});
		
		$("#opConfirrm").click(function() {
			var sample = $("#hiddenSampleNo").val();
			var id = $("#hiddenDocId").val();
			
			if ($("#hiddenIsPass").val() == "true") {
				var note = $("#selectNoteDiv input[name='passReason']:checked").parent().find(".selectLabel").html();
				$.post("<c:url value='/explain/audit/manual'/>",{sample:sample, operate:"pass", note:note},function(data) {
					if (data == true) {
						var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
						jQuery("#s3list").jqGrid('setRowData', s, {status:"<fmt:message key='audit.pass'/>"});
						
						/* var next = $("#"+s).next("tr").attr("id");
						if (next != null) {
							$("#s3list").setSelection(s, false);
							$("#s3list").setSelection(next, true);
						} */
						$("#testAdd").css('display','none');
		    			$("#testDelete").css('display','none');
		    			$("#auditUnpassBtn").css('display','inline');
		    			$("#auditPassBtn").css('display','none');
		    			$("#auditPrintBtn").css('display','inline');
		    			$("#opStatusDialog").dialog("close");
		    			$("#twoColumnDialog").dialog("close");
					}
				});
			} else {
				$.post("<c:url value='/explain/audit/manual'/>",{sample:sample, operate:"unpass", note:""},function(data) {
					if (data == true) {
						var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
						jQuery("#s3list").jqGrid('setRowData', s, {status:"<font color='red'><fmt:message key='audit.unpass'/></font>"});
						$("#testAdd").css('display','inline');
		    			$("#testDelete").css('display','inline');
		    			$("#auditUnpassBtn").css('display','none');
		    			$("#auditPassBtn").css('display','inline');
		    			$("#auditPrintBtn").css('display','none');
		    			$("#opStatusDialog").dialog("close");
		    			$("#twoColumnDialog").dialog("close");
					}
				});
			}
		});
		
		$(document).keydown(function(e){
			if(e.keyCode == 40)
			{
				var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
				var next = $("#"+s).next("tr").attr("id");
				
				if (next != null) {
					$("#s3list").setSelection(s, false);
					$("#s3list").setSelection(next, true);
				} else {
					var page = parseInt(jQuery("#s3list").jqGrid('getGridParam','page'));
					page = page + 1;
					var records = parseInt(jQuery("#s3list").jqGrid('getGridParam','records'));
					var total = (records - records % 25) / 25 + 1;
					if (page <= total) {
						$("#s3list").setGridParam({page:page}).trigger("reloadGrid");
					}
				}
				e.preventDefault();
			} else if (e.keyCode == 38) {
				var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
				var prev = $("#"+s).prev("tr").attr("id");
				
				if (prev != null) {
					$("#s3list").setSelection(s, false);
					$("#s3list").setSelection(prev, true);
				}
				e.preventDefault();
			}
		});
		
		$("#opCancel").click(function() {
			$("#opStatusDialog").dialog("close");
		});
		
		var selectNoteAdd = true;
		$("#auditPassBtn").click(function() {
			$("#hiddenIsPass").val(true);
			$("#passNotes").html("<fmt:message key='pass.notes' />");
			
			if (selectNoteAdd) {
				if ($("#lastDepLib").val() == '1300101' || $("#lastDepLib").val() == '1300200' || $("#lastDepLib").val() == '1300100' || $("#lastDepLib").val() == '1300201') {
					$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason' checked><span class='label selectLabel'>\u6807\u672c\u91cd\u65b0\u68c0\u6d4b</span></label>");
					$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u63a8\u7247\u663e\u5fae\u955c\u68c0\u67e5</span></label>");
					$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u624b\u5de5\u5206\u7c7b</span></label>");
					$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u7b26\u5408\u903b\u8f91\u89c4\u5219</span></label>");
					$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u5176\u5b83</span></label>");
				} else if ($("#lastDepLib").val() == '1300600') {
					$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u91cd\u65b0\u68c0\u6d4b</span></label>");
					$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u7a00\u91ca\u6216\u52a0\u91cf\u68c0\u6d4b</span></label>");
					$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u67e5\u770b\u6807\u672c</span></label>");
					$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u7b26\u5408\u903b\u8f91\u89c4\u5219</span></label>");
					$("#selectNoteDiv").append("<label class='radio span'><input type='radio' name='passReason'><span class='label selectLabel'>\u5176\u5b83</span></label>");
				}
				selectNoteAdd = false;
			}
			$("#opStatusDialog").dialog("open");
		});
		$("#auditUnpassBtn").click(function(){
			$("#hiddenIsPass").val(false);
			$("#passNotes").html("<fmt:message key='unpass.notes' />");
			$("#opStatusDialog").dialog("open");
		});
		
		$("#tcPassBtn").click(function() {
			$("#hiddenIsPass").val(true);
			$("#passNotes").html("<fmt:message key='pass.notes' />");
			$("#opStatusDialog").dialog("open");
		});
		$("#tcUnpassBtn").click(function(){
			$("#hiddenIsPass").val(false);
			$("#passNotes").html("<fmt:message key='unpass.notes' />");
			$("#opStatusDialog").dialog("open");
		});
		$("#tcCloseBtn").click(function(){
			$("#twoColumnDialog").dialog("close");
		});
		
		$("#auditBtn2").click(function(){
			var text = $("#auditText2").val();
			if (text != "") {
				var reaudit = false;
				if (!!$("#auditAllbtn").attr("checked")) {
					reaudit = true;
				}
				$.get("<c:url value='/explain/audit/result'/>",{sample:text,reaudit:reaudit},function() {});
			}
		});
		$("#auditBtn").click(function(){
			$("#auditDialog").dialog("open");
			//$("#auditDialog").modal('show');
			$("#isContinued").html("1");
			getProValue();
		});
		
		var isFirstCompare = true;
		$("#compareBtn").click(function(){
			var text = $("#sample_compare").val();
			if(isFirstCompare){
				getCompareTable(text);
				isFirstCompare=false;
			}else{
				jQuery("#sample_compare_information").jqGrid("setGridParam",{
					url:"<c:url value='/explain/audit/sampleCompare'/>?sampleNo="+text
				}).trigger("reloadGrid"); 
			} 
			$("#sampleCompareDialog").dialog("open");
		});
		
		$("#auditPrintBtn").click(function() {
			$('#printFrame').empty();
			var id = $("#hiddenDocId").val();
			var sample = $("#hiddenSampleNo").val();
			if ($("#hisLastResult").val() == 1) {
				$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' src=\"<c:url value='/explain/audit/print'/>?docId="+id+"&sampleNo="+sample+"&last=1\" />")
			} else {
				$("#printFrame").append("<iframe id='iframe_print' name='iframe_print' frameborder=0 style='background-color:transparent' width='99%' src=\"<c:url value='/explain/audit/print'/>?docId="+id+"&sampleNo="+sample+"&last=0\" />")
			}
			$("#auditPrint").dialog("open");
			var height1 = $("#midContent").height();
			var height2 = $("#chartPanel").height()/195/3 * 195 + 195;
			
			$("#iframe_print").height(height1 + height2 + $("#rowed3 tr").length * 5);
			//alert($("#chartPanel").height());
		});
		
		$("#samplePrintBtn").click(function(){
			var text = $("#gs_sample").val();
			$('#samplePrintFrame').empty();
			$("#samplePrintFrame").append("<iframe id='iframe_sample' name='iframe_sample' frameborder=0 style='background-color:transparent' width='99%' height='99%' src=\"<c:url value='/explain/audit/samplePrint'/>?text="+text+"\" />");
			$("#samplePrint").dialog("open");
			
		});
		
		$("#manualAuditPassBtn").click(function() {
			var s; 
			s = jQuery("#s3list").jqGrid('getGridParam','selarrrow'); 
			var ids = "";
			for (var c in s) {
				if (ids != "") {
					ids += ",";
				}
				ids += s[c];
			}
			if (s.length != 0 && confirm("\u786e\u5b9a\u901a\u8fc7\u8fd9 "+s.length+" \u4e2a\u6837\u672c\uff1f")) {
				$.post("<c:url value='/explain/audit/batch'/>",{ids:ids,op:"pass"},function(data) {
					if (data == true) {
						for (var c in s) {
							jQuery("#s3list").jqGrid('setRowData', s[c], {status:"<fmt:message key='audit.pass'/>"});	
						}
					}
				});
			}
		});
		$("#manualAuditUnPassBtn").click(function() {
			var s; 
			s = jQuery("#s3list").jqGrid('getGridParam','selarrrow'); 
			var ids = "";
			for (var c in s) {
				if (ids != "") {
					ids += ",";
				}
				ids += s[c];
			}
			if (s.length != 0 && confirm("\u786e\u5b9a\u4e0d\u901a\u8fc7\u8fd9 "+s.length+" \u4e2a\u6837\u672c\uff1f")) {
				$.post("<c:url value='/explain/audit/batch'/>",{ids:ids,op:"unpass"},function(data) {

					if (data == true) {
						for (var c in s) {
							jQuery("#s3list").jqGrid('setRowData', s[c], {status:"<font color='red'><fmt:message key='audit.unpass'/></font>"});	
						}
					}
				});
			}
		});
		
		$("#statisticDialogBtn").click(function() {
			$("#statisticDialog").dialog("open");
		});
		
		$("#ylxhBtn").click(function() {
			window.open("<c:url value='/ylxh'/>?lab=" + $("#lastDepLib").val());
		});
		
		var isFirstStatistic = true;
		$("#statisticBtn").click(function() {
			var code = $("#statistic_code").val();
			var from = $("#statistic_from").val();
			var to = $("#statistic_to").val();
			
			if (isFirstStatistic) {
				jQuery("#statistic_table").jqGrid({
					url:"<c:url value='/explain/audit/statistic'/>?code="+code+"&from="+from+"&to="+to,
					datatype: "json",
					jsonReader : {repeatitems : false}, 
					colNames:['ID','<fmt:message key="project"/>','<fmt:message key="num"/>','<fmt:message key="average"/>','<fmt:message key="max"/>','<fmt:message key="min"/>','<fmt:message key="standardDeviation"/>','<fmt:message key="coefficientOfVariation"/>'],
				   	colModel:[{name:'id',index:'id',hidden:true,sortable:false},
				   	    {name:'name',index:'name',width:120,sortable:false},
				   	 	{name:'num',index:'num',width:40,sortable:false},
				   	 	{name:'average',index:'average',width:80,sortable:false},
				   	 	{name:'max',index:'max',width:60,sortable:false},
				   	 	{name:'min',index:'min',width:60,sortable:false},
				   	 	{name:'standardDeviation',index:'standardDeviation',width:80,sortable:false},
				   	 	{name:'coefficientOfVariation',index:'coefficientOfVariation',width:80,sortable:false}],
				   	rowNum: 80,
				   	height:'100%'
				});
				isFirstStatistic = false;
			} else {
				jQuery("#statistic_table").jqGrid("setGridParam",{
					url:"<c:url value='/explain/audit/statistic'/>?code="+code+"&from="+from+"&to="+to
				}).trigger("reloadGrid"); 
			}
		});
		
		
		$("#div_dangerous").click(function() {
			window.open("<c:url value='/critical/undeal'/>");
		});
		
		$("#reason_block").click(function() {
			$("#reason_none").css('display','inline');
			$("#reason_block").css('display','none');
			jQuery("#audit_information").setGridParam().showCol("content");
			jQuery("#audit_information").setGridWidth(190,true);
			
		});
		
		$("#reason_none").click(function() {
			$("#reason_block").css('display','inline');
			$("#reason_none").css('display','none');
			jQuery("#audit_information").setGridParam().hideCol("content");
			jQuery("#audit_information").setGridWidth(190,true);
		});
		
		$("#testAdd").click(function() {
			$("#profileList").empty();
			$("#addTestList").html("");
			$("#labSelect").val()
			var lastProfile = "<c:out value='${lastProfile}' />";
    		$.get("<c:url value='/explain/audit/ajax/profileList'/>",{lab:$("#labSelect").val()},function(data){
    			//alert(data);
    			var array = jQuery.parseJSON(data);
    			for (var i=0 ; i < array.length ; i++) {
    				var html = array[i].describe+","+array[i].device;
    				if(lastProfile == array[i].test) {
    					$("#profileList").append("<option value='"+array[i].test+"' selected>"+html+"</option>");
    				} else {
    					$("#profileList").append("<option value='"+array[i].test+"'>"+html+"</option>");
    				}
    			}
    		 });
			$("#addTestResultDialog").dialog("open");
			
			if (lastProfile != "") {
				$.post("<c:url value='/explain/audit/ajax/profileTest'/>",{test:lastProfile,sample:$("#hiddenSampleNo").val()},function(data) {
	     			var array = jQuery.parseJSON(data);
	    			for (var i=0 ; i < array.length ; i++) {
	    				var result = true;
	    				$("#addTestList .testID").each(function(index,self){
	    		    		if ($(self).val() == array[i].test)
	    		    			result = false;
	    		    	});
	    				if (result) {
	    					$("#addTestList").append("<div><input type='hidden' class='testID' value='"+array[i].test+"'/><span class='testName span2'>"+array[i].name+"</span><input type='text' class='testValue span2'/></div>")
	    				}
	    			}
	     		});
			}
		});
		$("#resultAdd").click(function() {
			var docNo = $("#hiddenDocId").val();
			$("#span_docNo").val(docNo);
			$("#result_docNo").val(docNo);
			$("#addResultDialog").dialog("open");
		});
		$("#testDelete").click(function() {
			var ii = jQuery("#rowed3").jqGrid('getGridParam','selrow');
			if (ii != null) {
				$.post("<c:url value='/explain/audit/delete'/>",{sampleNo:$("#hiddenSampleNo").val(),id:ii}, function(data) {
					if (data == true) {
						var s = jQuery("#rowed3").jqGrid('getGridParam','selrow');
						var next = $("#"+s).next("tr").attr("id");
						$("#rowed3").jqGrid('delRowData',ii);
						if (next != null) {
							$("#rowed3").setSelection(next, true);
						}
					} else {
						alert("Fail!!!");
					}
				});
			}
			
			var ii0 = jQuery("#sample0").jqGrid('getGridParam','selrow');
			if (ii0 != null) {
				$.post("<c:url value='/explain/audit/delete'/>",{sampleNo:$("#hiddenSampleNo").val(),id:ii0}, function(data) {
					if (data == true) {
						var s = jQuery("#sample0").jqGrid('getGridParam','selrow');
						var next = $("#"+s).next("tr").attr("id");
						$("#sample0").jqGrid('delRowData',ii0);
						if (next != null) {
							$("#sample0").setSelection(next, true);
						}
					} else {
						alert("Fail!!!");
					}
				});
			}
			
			var ii1 = jQuery("#sample1").jqGrid('getGridParam','selrow');
			if (ii1 != null) {
				$.post("<c:url value='/explain/audit/delete'/>",{sampleNo:$("#hiddenSampleNo").val(),id:ii1}, function(data) {
					if (data == true) {
						var s = jQuery("#sample1").jqGrid('getGridParam','selrow');
						var next = $("#"+s).next("tr").attr("id");
						$("#sample1").jqGrid('delRowData',ii1);
						if (next != null) {
							$("#sample1").setSelection(next, true);
						}
					} else {
						alert("Fail!!!");
					}
				});
			}
		});

		$("#resultDelete").click(function() {
			var ii = jQuery("#audit_information").jqGrid('getGridParam','selrow');
			if (ii != null) {
				$.post("<c:url value='/explain/audit/deleteResult'/>",{docNo:$("#hiddenDocId").val(),id:ii}, function(data) {
					if (data == true) {
						$("#audit_information").jqGrid('delRowData',ii);
					} else {
						alert("Fail!!!")
					}
				});
			} 
		});
		
		$("#searchProject").autocomplete({
	        source: function( request, response ) {
	            $.ajax({
	            	url: "<c:url value='/index/ajax/searchTest'/>",
	                dataType: "json",
	                data: {
	                    name : request.term
	                },
	                success: function( data ) {
	  					
	                	response( $.map( data, function( result ) {
	                        return {
	                            label: result.id + " : " + result.ab + " : " + result.name,
	                            value: result.name,
	                            id : result.id
	                        }
	                    }));

	                    $("#searchResult").removeClass("ui-autocomplete-loading");
	                }
	            });
	        },
	        minLength: 1,
	        select : function(event, ui) {
	        	var result = true;
				$("#addTestList .testID").each(function(index,self){
		    		if ($(self).val() == ui.item.id)
		    			result = false;
		    	});
				if(result){
					$("#addTestList").append("<div><input type='hidden' class='testID' value='"+ui.item.id+"'/><span class='testName span2'>"+ui.item.value+"</span><input type='text' class='testValue span2'/></div>")
				}else{
					alert("<fmt:message key='alert.add.single.error' />");
				}
	        	
	        }
		});

		$('#addTestForm').ajaxForm({
			beforeSubmit: validate,
			success: function(data) {
				if (data == true) {
					$("#addTestResultDialog").dialog("close");
					jQuery("#rowed3").trigger("reloadGrid");
				} else {
					alert("Fail!!!")
				}
		    }
		});  
		
		$('#addResultForm').ajaxForm({
			beforeSubmit: validate,
			success: function(data) {
				if (data == true) {
					$("#addResultDialog").dialog("close");
					jQuery("#audit_information").trigger("reloadGrid");
				} else {
					alert("Fail!!!")
				}
		    }
		});
		
		var isTestModifyFirst = true;
		$("#modifyBtn").click(function() {
			if (isTestModifyFirst) {
				getTestModifyTable($("#hiddenSampleNo").val());
				isTestModifyFirst = false;
			} else {
				jQuery("#test_modify_information").jqGrid("setGridParam",{
					url:"<c:url value='/explain/audit/testModify'/>?sampleNo="+$("#hiddenSampleNo").val()
				}).trigger("reloadGrid");
			}
			$("#testModifyDialog").dialog("open");
		});
		
		getList("<c:out value='${strToday}'/>",$("#labSelect").val());
		$("#gs_sample").val("<c:out value='${strToday}'/>");
		
		$.get("<c:url value='/explain/audit/count'/>",{}, function(data) {
 			$("#today_info_unaudit").html(data.todayunaudit);
 			$("#today_info_unpass").html(data.todayunpass);
 			$("#need_write_back").html(data.needwriteback);
 			$("#info_dangerous_undeal").html(data.dangerous);
 			if (data.dangerous != 0) {
 				$("#div_dangerous").removeClass('alert-success');
 				$("#div_dangerous").addClass('alert-error');
 			} else {
 				$("#div_dangerous").removeClass('alert-error');
 				$("#div_dangerous").addClass('alert-success');
 			}
 		},'json');
		
     	setInterval(function() {
     		
     		$.get("<c:url value='/explain/audit/count'/>",{}, function(data) {
     			
     			/* $("#info_total").html(data.total); */
     			//$("#info_unaudit").html(data.unaudit);
     			//$("#info_unpass").html(data.unpass);
     			$("#today_info_unaudit").html(data.todayunaudit);
     			$("#today_info_unpass").html(data.todayunpass);
     			$("#need_write_back").html(data.needwriteback);
     			/* $("#info_unpass").html(data.pass); */
     			$("#info_dangerous_undeal").html(data.dangerous);
     			if (data.dangerous != 0) {
     				$("#div_dangerous").removeClass('alert-success');
     				$("#div_dangerous").addClass('alert-error');
     			} else {
     				$("#div_dangerous").removeClass('alert-error');
     				$("#div_dangerous").addClass('alert-success');
     			}

     			$("#audit_status_info").html("");
     			if (data.status == 1) {
     				$("#audit_status_info").html("<fmt:message key='auto.audit'/>\u4e2d...");
     			} else if (data.status == 2) {
     				if ($("#hiddenAuditConfirm").val() == 'true') {
     					$.get("<c:url value='/explain/audit/result'/>",{sample:"<c:out value='${strToday}'/>", auto:"true"},function() {});
	     				$("#audit_status_info").html("<fmt:message key='auto.audit'/>\u4e2d...");
     				}
     			}
     		},'json');
     	}, 15000);
     	
     	 $("#fullScreen").click(function(){
     		if ($("#isfulltag").val() == 0) {
     			$("#header").css("display","none");
         		$("#content").css("margin-top","3px");
         		$("#fullScreen .ui-icon").removeClass("ui-icon-arrow-4-diag");
         		$("#fullScreen .ui-icon").addClass("ui-icon-minusthick");
         		$("#isfulltag").val(1);
     		} else {
     			$("#header").css("display","block");
     			$("#fullScreen .ui-icon").removeClass("ui-icon-minusthick");
         		$("#fullScreen .ui-icon").addClass("ui-icon-arrow-4-diag");    		
         		$("#isfulltag").val(0);
     		}
     	 });
     	 
     	 function writeBackOnce(code, lab, user) {
     		
   			 $.getJSON("${catcherUrl}ajax/writeBack/once.htm?callback=?",{code:code, lib:lab, operator:user},function(data){
   				 if (data.result == 0) {
   					alert("\u6b63\u5728\u5199\u56de\u4e2d...");
   				 } else if (data.result == 1) {
   					$.get("<c:url value='/explain/audit/count'/>",{}, function(data) {
   			 			$("#today_info_unaudit").html(data.todayunaudit);
   			 			$("#today_info_unpass").html(data.todayunpass);
   			 			$("#need_write_back").html(data.needwriteback);
   			 			$("#info_dangerous_undeal").html(data.dangerous);
   			 			if (data.dangerous != 0) {
   			 				$("#div_dangerous").removeClass('alert-success');
   			 				$("#div_dangerous").addClass('alert-error');
   			 			} else {
   			 				$("#div_dangerous").removeClass('alert-error');
   			 				$("#div_dangerous").addClass('alert-success');
   			 			}
   			 		},'json');
   					alert("\u5199\u56de\u6210\u529f");
   				 } else {
   					alert("\u5199\u56de\u5931\u8d25");
   				 }
   			 });
     	 }
     	 
     	function writeBackPart(code, lab, user, from, to) {
     		
  			 $.getJSON("${catcherUrl}ajax/writeBack/part.htm?callback=?",{code:code, lib:lab, operator:user, from:from, to:to},function(data){
  				 if (data.result == 0) {
  					alert("\u6b63\u5728\u5199\u56de\u4e2d...");
  				 } else if (data.result == 1) {
  					$.get("<c:url value='/explain/audit/count'/>",{}, function(data) {
  			 			$("#today_info_unaudit").html(data.todayunaudit);
  			 			$("#today_info_unpass").html(data.todayunpass);
  			 			$("#need_write_back").html(data.needwriteback);
  			 			$("#info_dangerous_undeal").html(data.dangerous);
  			 			if (data.dangerous != 0) {
  			 				$("#div_dangerous").removeClass('alert-success');
  			 				$("#div_dangerous").addClass('alert-error');
  			 			} else {
  			 				$("#div_dangerous").removeClass('alert-error');
  			 				$("#div_dangerous").addClass('alert-success');
  			 			}
  			 		},'json');
  					alert("\u5199\u56de\u6210\u529f");
  				 } else {
  					alert("\u5199\u56de\u5931\u8d25");
  				 }
  			 });
    	 }
     	 
     	 $("#writeBackBtn").click(function() {
     		 if ($("#need_write_back").html() != "0") {
     			if (confirm("\u786e\u8ba4\u5199\u56de"+$("#need_write_back").html()+"\u6761\u6837\u672c")) {
     				writeBackOnce("${userCode}", $("#lastDepLib").val(), "${checkOperator}");
     			}
     		 }
     	 });
     	 
     	$("#writeBackPartBtn").click(function() {
    		if ($("#need_write_back").html() != "0") {
    			 $("#writeBackPartDialog").dialog("open");
    		}
    	});
     	
     	$("#writePartBtn").click(function() {
			if ($("#writeBack_code").val() != "" && $("#writeBack_from").val() != "" && $("#writeBack_to").val() != "") {
				writeBackPart($("#writeBack_code").val(), $("#lastDepLib").val(), "${checkOperator}", $("#writeBack_from").val(), $("#writeBack_to").val());
	   		}
	   	});
     	
     	 $("#sampleListRefreshBtn").click(function() {
     		jQuery("#s3list").trigger("reloadGrid"); 
     		$.getJSON("${catcherUrl}ajax/remoteSyncOnce.htm?callback=?", {
				start : "<c:out value='${date}'/>",
				end : "<c:out value='${date}'/>"
			}, function(data) {
			});
     	 });
     	 
     	$("#pageRefreshBtn").click(function() {
     		window.location.reload();
     	 });
     	 
     	 $("#addProfileBtn").click(function() {
     		var testIds = $("#profileList").val();
     		$.post("<c:url value='/explain/audit/ajax/profileTest'/>",{test:testIds,sample:$("#hiddenSampleNo").val()},function(data) {
     			var array = jQuery.parseJSON(data);
    			for (var i=0 ; i < array.length ; i++) {
    				var result = true;
    				$("#addTestList .testID").each(function(index,self){
    		    		if ($(self).val() == array[i].test)
    		    			result = false;
    		    	});
    				if (result) {
    					$("#addTestList").append("<div><input type='hidden' class='testID' value='"+array[i].test+"'/><span class='testName span2'>"+array[i].name+"</span><input type='text' class='testValue span2'/></div>")
    				}
    			}
    			//alert("<fmt:message key='alert.add.profile.finished' />");
     		});
     	 });
     	 
     	 $("#deleteAllTest").click(function(){
     		$("#addTestList").html("");
     	 });
     	 
     	$("#AuditCodeSetting").click(function(){
    		$("#codeSetDialog").dialog("open");
    	});
     	
     	$("#need_write_back_div").click(function() {
     		$("#need_writeback_table").html("");
     		$.get("<c:url value='/explain/audit/ajax/writeBack'/>",{},function(data){
     			var array = jQuery.parseJSON(data);
     			for(var i=0; i<array.length; i++) {
     				var trLine = "<tr>";
     				trLine += "<td class='wb_checker'>" + array[i].checker + "</rd>";
     				trLine += "<td>" + array[i].name + "<div class='sample_list_div' style='display:none;'>"+array[i].list+"</div></rd>";
     				trLine += "<td class='wb_code'>" + array[i].code + "</rd>";
     				trLine += "<td class='wb_lab'>" + array[i].lab + "</rd>";
     				trLine += "<td class='wb_count'>" + array[i].count + "</rd>";
     				trLine += "<td><button class='btn wb_detail'>Detail</button><button class='btn btn-info wb_button'><fmt:message key='writeback' /></button></rd>";
     				$("#need_writeback_table").append(trLine);
     			}
     			$("#need_writeback_table .wb_button").click(function(){
     				var checker = $(this).parent().parent().find('.wb_checker').html();
     				var count = $(this).parent().parent().find('.wb_count').html();
     				var code = $(this).parent().parent().find('.wb_code').html();
     				var lab = $(this).parent().parent().find('.wb_lab').html();
     				if (confirm("\u786e\u8ba4\u5199\u56de"+count+"\u6761\u6837\u672c")) {
         				writeBackOnce(code, lab, checker);
         			}
     			});
     			$("#need_writeback_table .wb_detail").click(function(){
     				var sampleDiv = $(this).parent().parent().find('.sample_list_div');
     				if (sampleDiv.css('display') == 'none')
     					sampleDiv.css('display','block');
     				else
     					sampleDiv.css('display','none');
     			});
     		});
     		
     		$("#allNeedWriteBackDialog").dialog("open");
     	});
     	
     	$("#codeSetDiv .codeCheck").click(function(){
     		var code = $(this).parent().find(".codeText").html();
  			//alert(code);
     		if ($(this).attr("checked") == "checked"){
     			$(this).parent().parent().parent().find(".scopeDiv").css('display','block');
     			$.post("<c:url value='/explain/audit/activeCode'/>",{code:code,active:true},function() {}); 		
            } else {
            	$(this).parent().parent().parent().find(".scopeDiv").css('display','none');
            	$.post("<c:url value='/explain/audit/activeCode'/>",{code:code,active:false},function() {});
            }
     	});
     	
     	$("#unaudit_reason_btn").hover(function(){
     		$("#unaudit_reason_btn").popover({title:"<fmt:message key='unpass.reason' />",html:true,content:$("#audit_reason").html(),trigger:'manual'});
     		$("#unaudit_reason_btn").popover("show");
     		
     		
     		/* var display = $("#unaudit_reason").css('display');
     		if (display == "none") {
     			$("#unaudit_reason").css('display','block');
     		} else {
     			$("#unaudit_reason").css('display','none');
     		} */
     	},function(){
     		$("#unaudit_reason_btn").popover("destroy");
     	});
     	
     	
     	$("#controlAuditBtn").click(function() {
     		var btnText = $("#controlAuditBtn").html().trim();
     		var status = 0;
     		if (btnText == "<fmt:message key='audit.start'/>") {
     			status = 1;
     		}
     		var flag = true;
			var codeScope = "";
			if (status == 1) {
	     		$("#codeSetDiv .codeItem").each(function(index,self) {
	    			if ($(self).find(".codeCheck").attr("checked") == "checked"){
	    				var code = $(self).find(".codeText").html();
	    				var lo = $(self).find(".val-lo").val();
	        			var hi = $(self).find(".val-hi").val();
	        			//alert(lo);
	        			//alert(hi);
	        			if (codeScope != "") codeScope += ";";
	        			if (lo.length == 0 && hi.length == 0) {
	        			} else if (lo.length == 3 && hi.length == 3) {
	        				codeScope += code + ":" + lo + "-" + hi;
	        			} else {
	        				flag = false;
	        			}
	    			}
	    		});
			}
     		if (flag) {
				$.get("<c:url value='/explain/audit/autoAudit'/>",{status:status, scope:codeScope},function(data){
	     			
	     			if (data) {
	     				if (status == 1) {
	     					$("#controlAuditBtn").html("<fmt:message key='audit.stop'/>");
	     					$("#hiddenAuditConfirm").val(true);
	     					$("#codeSetDiv .input-ctl").attr('disabled', 'disabled');
	     				} else {
	     					$("#controlAuditBtn").html("<fmt:message key='audit.start'/>");
	     					$("#hiddenAuditConfirm").val(false);
	     					$("#codeSetDiv .input-ctl").removeAttr('disabled');
	     				}
	     			}
	     		});
			} else {
				alert("\u8f93\u5165\u9519\u8bef");
			}	
     	});
     	
     	$.post("<c:url value='/explain/audit/ajax/profileJYZ'/>",{}, function(data) {
     		if(data != "") {
     			alert("\u4ee3\u7801  " + data + "  \u8fd8\u672a\u6307\u5b9a\u68c0\u9a8c\u8005\uff0c\u8bf7\u5148\u5236\u5b9a\u68c0\u9a8c\u8005\u518d\u8fdb\u884c\u5ba1\u6838\uff0c\u5426\u5219chkoper2\u5c06\u4e3a\u7a7a\u503c!");
     		}
     	});
     	
  		if ("${activeAuto}" == "true") {
			$("#hiddenAuditConfirm").val(true);
			$("#codeSetDiv .input-ctl").attr('disabled', 'disabled');
  		} else {
			$("#hiddenAuditConfirm").val(false);
			$("#codeSetDiv .input-ctl").removeAttr('disabled');
  		}
	
  		$("#colorHelp").append("<span class='c_td diff_td'> </span>\u5dee\u503c <span class='c_td ratio_td'> </span>\u6bd4\u503c <span class='c_td re_td'> </span>\u590d\u68c0<span class='c_td dan_td'></span>\u5371\u6025 <span class='c_td al2_td'> </span>\u8b66\u62121 <span class='c_td al3_td'> </span>\u8b66\u62122 <span class='c_td ex_td'> </span>\u6781\u503c");
  		$("#color2Help").append("<span class='c_td diff_td'> </span>\u5dee\u503c <span class='c_td ratio_td'> </span>\u6bd4\u503c <span class='c_td re_td'> </span>\u590d\u68c0<span class='c_td dan_td'></span>\u5371\u6025 <span class='c_td al2_td'> </span>\u8b66\u62121 <span class='c_td al3_td'> </span>\u8b66\u62122 <span class='c_td ex_td'> </span>\u6781\u503c");
  		
     	$("#autoAuditNote").html("\u53c2\u8003\u8303\u56f4\u4e3a<strong class='text-warning'>3\u4f4d</strong>\u6570\u5b57,\u4e0d\u8f93\u5ba1\u6838<strong class='text-warning'>\u6574\u4e2a\u6bb5</strong>");
		
     	$("#oneColumnShowBtn").click(function(){

     		if ($("#oneColumnShowBtn").attr("checked") == "checked") {
	     		var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
				var ret = jQuery("#s3list").jqGrid('getRowData',s);
	       		$("#twosampleTable").css('display','none');
	       		$("#patientRow").css('display','block');
	       		if (isFirstTime) {
	       			getSample(ret.sample);
	       			getSample0(ret.sample);
	       			getSample1(ret.sample);
	    			isFirstTime = false;
	       		} else {
	       			jQuery("#rowed3").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample'/>?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
	       		}
     		} else {
     			var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
				var ret = jQuery("#s3list").jqGrid('getRowData',s);
     			$("#patientRow").css('display','none');
    			$("#twosampleTable").css('display','block');
    			if (isFirstTime) {
    				getSample(ret.sample);
        			getSample0(ret.sample);
        			getSample1(ret.sample);
        			isFirstTime = false;
        		} else {
        			jQuery("#sample0").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample0'/>?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
        			jQuery("#sample1").jqGrid("setGridParam",{url:"<c:url value='/explain/audit/sample1'/>?id="+ret.sample,editurl: "<c:url value='/explain/audit/edit'/>?sampleNo=" + ret.sample}).trigger("reloadGrid");
        		}
     		}
     	});
	});
	
</script>

</head>
<div style="font-size: 14px; margin-bottom: 2px; line-height: 25px; letter-spacing: 0px;">

	<div class="alert alert-success clearfix" style="margin-bottom:7px;padding:0px;padding-left:10px;">
		<%-- <div style="float: left;margin-right: 30px;">
			<b><fmt:message key="department.code" /> : </b>
			<c:out value="${department}" />
		</div>
		<div style="float: left;margin-right: 30px;">
			<b><fmt:message key="lab.code" /> : </b>
			<c:out value="${labCode}" />
		</div>
		<div style="float: left;margin-right: 20px;">
			<b><fmt:message key="audit.info.all.unaudit" /> : </b><a id="a_info_unaudit"><span id="info_unaudit"><c:out
						value="${info_unaudit}" /></span></a>
		</div>
		<div style="float: left;margin-right: 20px;">
			<b><fmt:message key="audit.info.all.unpass" /> : </b><a id="a_info_unpass"><span id="info_unpass"><c:out
						value="${info_unpass}" /></span></a>
		</div> --%>
		<div style="float: left;margin-right: 20px;">
			<b ><fmt:message key="audit.info.today.unaudit" /> : </b><a id="t_info_unaudit"><span id="today_info_unaudit"><c:out
						value="${today_info_unaudit}" /></span></a>
		</div>
		<div style="float: left;margin-right: 20px;">
			<b><fmt:message key="audit.info.today.unpass" /> : </b><a id="t_info_unpass"><span id="today_info_unpass"><c:out
						value="${today_info_unpass}" /></span></a>
		</div>
		<div id="need_write_back_div" style="float: left;padding:0px 10px 0px 10px;margin:0px;margin-right: 20px;cursor:pointer;" class="alert alert-info">
			<b><fmt:message key="audit.info.need.writeback" /> : </b><span id="need_write_back"><c:out value="${need_write_back}" /></span>
		</div>
		
		<div style="float: left;padding:0px 10px 0px 10px;margin:0px;margin-right: 20px;cursor:pointer;" class="alert <c:if test="${dangerous_undeal != 0}">alert-error</c:if>" id="div_dangerous" >
			<b><fmt:message key="audit.info.dangerous" /> : </b>
			<span id="info_dangerous_undeal"><c:out value="${dangerous_undeal}" /></span>
		</div>
		<div style="float: left;margin-right: 10px;">
			<span style="color: #f03010;"
				id="audit_status_info"></span>
		</div>
		<div style="float: right; margin-right:10px;margin-bottom:0px;">
			<div class="span" style="margin-right:10px;">
				<i class="icon-user" style="margin:4px;"></i><b style="font-size:14px;color:#000000;line-height:30px;"><c:out value="${operator}"/></b>
			</div>
			<button id="writeBackPartBtn" class="btn" style="width:100px;height:28px;"><fmt:message key="writebackpart" /></button>
			<button id="writeBackBtn" class="btn" style="width:55px;height:28px;"><fmt:message key="writeback" /></button>
			<button id="pageRefreshBtn" class="btn" style="width:30px;height:28px;padding:4px;">
				<span class="icon-refresh" style="margin:3px;"></span>
			</button>
			<button id="fullScreen" class="btn" style="width:30px;height:28px;padding:5px;">
			<span class="ui-icon ui-icon-arrow-4-diag"></span></button>
			<input type="hidden" value="0" id="isfulltag">
			<input type="hidden" id="lastDepLib" value="${library}">
		</div>
	</div>
</div>
<div >
	<div id="searchHeader" style="float: left; width: 225px;">
		<div class="form-inline" style="margin-bottom: 5px;display:none;">
			<input type="text" style="height:20px;width:150px;" id="sample_compare"></input>
			<input id="compareBtn" type="button" class="btn" style="margin-left:5px;height:30px;width:80px;" value='<fmt:message key='audit.compare.sample'/>' />
		</div>
		<div style="height: 35px;">
			<div style="margin-bottom: 10px;">
				<div class="btn-group">
					<a class="btn dropdown-toggle" style="height:20px;" data-toggle="dropdown" href="#"><fmt:message key='batch.deal'/><span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="manualAuditPassBtn" href="#"><fmt:message key='batch.pass'/></a></li>
						<li><a id="manualAuditUnPassBtn" href="#"><fmt:message key='batch.unpass'/></a></li>
						<li><a id="statisticDialogBtn" href="#"><fmt:message key='batch.statistic'/></a></li>
						<li><a id="ylxhBtn" href="#"><fmt:message key='ylxh.title'/></a></li>
					</ul>
				</div>
				<input id="samplePrintBtn" type="button" class="btn" style="height:30px;width:45px;padding:4px;" value='<fmt:message key="audit.print" />' />
				<input id="auditBtn" type="button" class="btn" style="height:30px;width:45px;padding:4px;" value='<fmt:message key='audit'/>' />
				<button id="sampleListRefreshBtn" class="btn btn-info" style="width:30px;height:30px;padding:4px;">
				<span class="icon-refresh" style="margin:3px;"></span></button>
				</div>
		</div>
		<div id="sampleListPanel">
			<table id="s3list"></table>
			<div id="s3pager"></div>
		</div>
	</div>
	<div id="midContent"
		style="float: left; width: 520px; margin-left: 10px; display: none;">
		<div class="clearfix">
			<h2 style="display:none;" id="sampleTitle"></h2>
			<%-- <div id='sampleLisPass' class="alert lisPass-info" style="margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
				<b><fmt:message key="sample.lisPass" /></b>
			</div> --%>
			<div id='passLabel' class="alert lisPass-info" style="display:none;margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
				<b><fmt:message key="passreason"/>&nbsp;</b>
				<b id="passreason"></b>
			</div>
			<div id="patient-info" class="alert alert-info" style="margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.patientName" /></span>
					<span class="pText"><b id="pName"></b></span>
				<!-- </div>
				<div class="pItem"> -->
					<span class="pLabel"><fmt:message key="patient.patientSex" /></span>
					<span class="pText"><b id="pSex"></b></span>
				<!-- </div>
				<div class="pItem"> -->
					<span class="pLabel"><fmt:message key="patient.age" /></span>
					<span class="pText"><b id="pAge"></b></span>
				<!-- </div>
				<div class="pItem"> -->
					<span class="pLabel"><fmt:message key="patient.sampleType" /></span>
					<span class="pText"><b id="pType"></b></span>
				</div>
				
				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.blh" /></span>
					<span class="pText"><b id="blh"></b></span>
					<span class="pLabel"><fmt:message key="patient.doctadviseno" /></span>
					<span class="pText"><b id="pId"></b></span>
				<!-- </div>
				<div class="pItem"> -->
					
				</div>

				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.section"/>&nbsp;</span>
					<span class="pText"><b id="pSection"></b></span>
					<span id="pBedLabel" class="pLabel"><fmt:message key="patient.departbed"/>&nbsp;</span>
					<span id="pBedText" class="pText"><b id="pBed"></b></span>
				<!-- </div>
				<div class="pItem"> -->
					<span class="pLabel"><fmt:message key="diagnostic"/>&nbsp;</span>
					<span class="pText"><b id="diagnostic"></b></span>
				</div>
			</div>
			<div style="display:none;" class="clearfix" id="unaudit_reason">
				<div style="float:left;width:80px;margin:0px;padding:2px;padding-left:10px;margin-right:10px;" class="alert alert-error"><b><fmt:message key="unpass.reason" /></b></div>
				<div style="width: 350px;float:left;"><span id="audit_reason"></span></div>
			</div>
		</div>
		<div style="height:35px;" id="btnMenu">
			<div style="margin-top:4px;float:left;">
				<button id="unaudit_reason_btn" class="btn btn-danger" style="font-size:12px;width:80px;"><b><fmt:message key="unpass.reason" /></b></button>
				<button id="testAdd" class="btn" style="font-size:12px;"><b><fmt:message key="button.add" /></b></button>
				<button id="testDelete" class="btn" style="font-size:12px;"><b><fmt:message key="button.delete" /></b></button>
				<button id="tatBtn" class="btn" style="font-size:12px;"><b>TAT</b></button>
				<button id="modifyBtn" class="btn" style="font-size:12px;"><b><fmt:message key="button.testModify" /></b></button>
			</div>
			<div style="margin-top:4px;float:right;">	
				<button id="auditPrintBtn" class="btn" style="font-size:12px;">
					<b><fmt:message key="audit.print" /></b>
				</button>
				<button id="auditUnpassBtn" class="btn" style="font-size:12px;">
					<b><fmt:message key="unpass.button" /></b>
				</button>
				<button id="auditPassBtn" class="btn btn-success" style="font-size:12px;">
					<b><fmt:message key="pass.button" /></b>
				</button>
			</div>
		</div>
		<div id="patientRow" >
			<table id="rowed3" style="font-size: 14px;"></table>
		</div>
		<div id="twosampleTable" style="font-size: 14px;float:left;display: none; width:740px;">
			<table id="sample0" style="font-size: 14px;"></table>
			<table id="sample1" style="font-size: 14px;"></table>
		</div>
		<div style="font-size: 13px; display:none;margin-top: 10px;">
			<div style="margin-left:60px;">
				<input type="hidden" id="hiddenDocId"/>
				<input type="hidden" id="hiddenSampleNo"/>
				<input type="hidden" id="hisLastResult"/>
				<input type="hidden" id="needEdit" /> 
				<input type="hidden" id="hiddenIsPass"/>
				<input type="hidden" id="hiddenAuditConfirm" value="${activeAuto}"/>
			</div>
		</div>
		<div id="colorHelp" style="float:left;margin-top:3px;">
		</div>
		<div id="critical_div" style="float:left;margin-top:10px;">
			<div id="critical_alert" class="alert">
				<b id="critical_title" style="font-size:18px;margin-right:20px;"></b>
				<span id="critical_time"></span>
				<div id="critical_info"></div>
			</div>
		</div>
	</div>
	<div style="position:absolute;right:0px; width: 220px;">
		<div>
			<%-- <span style="font-size:16px;line-height:30px;"><fmt:message key="patient.section"/></span>
			 --%>
			 <span ><select id="labSelect" onchange="labChange(this)">
				<c:forEach var="depart" items="${departList}">
					<option value='<c:out value="${depart.key}" />'><c:out value="${depart.value}" /></option>
				</c:forEach>
			</select></span>
			<button class="btn" id="AuditCodeSetting" style="width:84px;"><fmt:message key="code.setting"/></button>
		</div>
		<div>
			<div id="historyTabs" style="display:none;">
				<ul>
					<li><a href="#tabs-0"><fmt:message key="history.plot"/></a></li>
					<li><a href="#tabs-1"><fmt:message key="addResult.result"/></a></li>
					<li><a href="#tabs-2"><fmt:message key="history.table"/></a></li>
				</ul>
				<div id="tabs-0" style="padding:5px;">
					<div style="margin: 10px;">
						<span class="label label-info"><fmt:message key="history.graphs" /></span>
					</div>
					<div id="chartPanel" style="height:560px;overflow-y:auto;background-color:#f0f0f0;"></div>
				</div>
				<div id="tabs-1" style="padding:5px;">
					<div style="margin: 10px;">
						<span class="label label-info"><fmt:message key="audit.infomation" /></span>
						<div style="margin-right:5px;float:right;" id="reasonBtn">	
							<button id="reason_block" class="btn" style="font-size:12px;">
								<fmt:message key="reason.block" />
							</button>
							<button id="reason_none" class="btn" style="font-size:12px;display:none;">
								<fmt:message key="reason.none" />
							</button>
						</div>
					</div>
					<div style="height:560px;overflow-y:auto;background-color:#f0f0f0;">
						
						<div style="margin-top:10px;">
							<div id="explainRow" style="margin-top: 4px; font-size: 13px;">
								<table id="audit_information"></table>
							</div>
						</div>
						<div style="margin-top:5px;">
							<button id="resultAdd" class="btn"><fmt:message key="button.add" /></button>
							<button id="resultDelete" class="btn"><fmt:message key="button.delete" /></button>
						</div>
					</div>
				</div>
				<div id="tabs-2" style="padding:5px;background-color:#f0f0f0;">
					<div style="margin: 1px;">
						<span class="label label-info"><fmt:message key="show.three.history" /></span>
						<input type="checkbox" id="oneColumnShowBtn" style="margin-bottom:5px;">
						<label class="label label-warning" for="oneColumnShowBtn" style="cursor:pointer;"><fmt:message key="one.column.show" /></label>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="dialogs" style="display:none;">
<div id="auditDialog" style="text-align:left;" title="<fmt:message key="manual.audit"/>">
	<div id="searchPanel" align="left">
		<div style="display:none;" id="isContinued">0</div>
		<div class="form-inline" style="margin-bottom:5px;">
			<input id="auditText2" type="text" class="span3" style="margin-left:0px;" placeholder="20120829SGB001-999"/>
			<button id="auditBtn2" class="btn" style="width:60px;margin-right:15px;"><fmt:message key='audit'/></button>
			<input type="checkbox" id="auditAllbtn" style="margin-top:-2px;margin-right:10px;"><fmt:message key='reaudit'/>
		</div>
		
		<div style="border-top:1px solid #E1E1E1;margin-bottom:10px;margin-top:10px;"></div>
	</div>
</div>

<div id="auditPrint" align="left" title='<fmt:message key="audit.preview" />'>
	<button class="btn btn-success" onclick="javascript:document.getElementById('iframe_print').contentWindow.print();;"><fmt:message key="audit.print" /></button>
	<div id="printFrame">
	</div>
	<button class="btn btn-success" onclick="javascript:document.getElementById('iframe_print').contentWindow.print();;"><fmt:message key="audit.print" /></button>
</div>

<div id="samplePrint" align="left" title='<fmt:message key="audit.preview" />'>
	<button class="btn btn-success" onclick="javascript:document.getElementById('iframe_sample').contentWindow.print();;"><fmt:message key="audit.print" /></button>
	<div id="samplePrintFrame" style="height:360px;">
	</div>
	<button class="btn btn-success" onclick="javascript:document.getElementById('iframe_sample').contentWindow.print();;"><fmt:message key="audit.print" /></button>
</div>


<div id="dialog" align="left" title="<fmt:message key='knowledge.info' />">
</div>

<div id="addTestResultDialog" style="text-align:left;" title="<fmt:message key='add.test.result'/>">
	<div>
		<span style=""><fmt:message key='add.single'/></span>
		<input class="span4" id="searchProject" type="text" placeholder="<fmt:message key='add.single'/>">
	</div>
	<div>
		<select id="profileList"></select>
		<button id="addProfileBtn" class="btn btn-info"><fmt:message key="button.add.profile" /></button>
		<button id="deleteAllTest" class="btn btn-danger"><fmt:message key="button.empty" /></button>
	</div>
	<div id="addTestList">
	</div>
</div>

<div id="addResultDialog" style="text-align:left;" title="<fmt:message key='addReuslt'/>">
	<form id="addResultForm" class="form-horizontal" action="<c:url value='/explain/audit/addResult'/>" method="post">
			<div class="control-group">
				<label class="control-label" ><fmt:message key="addResult.number" /></label>
				<div class="controls">
					<input type="text" class="span3" id="span_docNo" disabled></input>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><fmt:message key="addResult.result" /></label>
				<div class="controls">
					<input id="result_result" type="text" class="span3" name="result"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"><fmt:message key="addResult.content" /></label>
				<div class="controls">
					<textarea id="result_content" class="span3" name="content"></textarea>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<input id="result_docNo" name="docNo" type="hidden"></input>
					<button type="submit" class="btn" ><fmt:message key='button.submit' /></button>
				</div>
			</div>
		</form>
	</div>

	<div id="tatDialog" title="TAT"  style="text-align:left;" >
		<table class="table">
			<tbody>
			<tr><th><fmt:message key='tat.request' /></th><td><span id="tat_request"></span></td></tr>
			<tr><th><fmt:message key='tat.execute' /></th><td><span id="tat_execute"></span></td></tr>
			<tr><th><fmt:message key='tat.send' /></th><td><span id="tat_send"></span></td></tr>
			<tr><th><fmt:message key='tat.ksreceive' /></th><td><span id="tat_ksreceive"></span></td></tr>
			<tr><th><fmt:message key='tat.receive' /></th><td><span id="tat_receive"></span></td></tr>
			<tr><th><fmt:message key='tat.audit' /></th><td><span id="tat_audit"></span></td></tr>
			<tr><th><fmt:message key='tat.auditor' /></th><td><span id="tat_auditor"></span></td></tr>
			<tr><th><fmt:message key='tat.result' /></th><td><span id="tat_result"></span></td></tr>
			<tr><th><fmt:message key='tat.audit.tat' /></th><td><span id="audit_tat"></span></td></tr>
			</tbody>
		</table>
	</div>
	
	<div id="twoColumnDialog" title="" >
		<div class="clearfix">
			<h2 style="display:none;" id="sampleTitle"></h2>
			<div id="patient-info2" class="alert alert-info" style="margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.patientName" /></span>
					<span class="pText"><b id="pName2"></b></span>
					<span class="pLabel"><fmt:message key="patient.patientSex" /></span>
					<span class="pText"><b id="pSex2"></b></span>
					<span class="pLabel"><fmt:message key="patient.age" /></span>
					<span class="pText"><b id="pAge2"></b></span>
					<span class="pLabel"><fmt:message key="patient.sampleType" /></span>
					<span class="pText"><b id="pType2"></b></span>
					<span class="pLabel"><fmt:message key="patient.blh" /></span>
					<span class="pText"><b id="blh2"></b></span>
					<span class="pLabel"><fmt:message key="patient.doctadviseno" /></span>
					<span class="pText"><b id="pId2"></b></span>
					<span class="pLabel"><fmt:message key="patient.section"/>&nbsp;</span>
					<span class="pText"><b id="pSection2"></b></span>
					<span id="pBedLabel2" class="pLabel"><fmt:message key="patient.departbed"/>&nbsp;</span>
					<span id="pBedText2" class="pText"><b id="pBed2"></b></span>
					<span class="pLabel"><fmt:message key="diagnostic"/>&nbsp;</span>
					<span class="pText"><b id="diagnostic2"></b></span>
				</div>
			</div>
		</div>
	
		<div style="text-align:left;margin:5px;">
			<button id="tcPassBtn" class="btn btn-success" style="font-size:14px;">
				<b><fmt:message key="pass.button" /></b>
			</button>
			<button id="tcUnpassBtn" class="btn" style="font-size:14px;">
				<b><fmt:message key="unpass.button" /></b>
			</button>
			<button id="tcCloseBtn" class="btn" style="font-size:14px;">
				<b><fmt:message key="button.undeal" /></b>
			</button>
		</div>
		<div style="font-size: 13px;">
			<table id="twocol"></table>
		</div>
		<div id="color2Help" style="margin-top:3px;">
		</div>
	</div>
	
	<div id="opStatusDialog" title="<fmt:message key='pass.button' />/<fmt:message key='unpass.button' />" style="text-align:left;" >
		<h5 id="passNotes"></h5>
		<!-- <textarea id="noteText" rows="1" style="width:280px;"></textarea> -->
		<div id="selectNoteDiv" class="clearfix"></div>
		<div style="margin-top:20px;margin-left:20px;">
			<button id="opConfirrm" class="btn btn-info" ><fmt:message key='button.confirm' /></button>
			<button id="opCancel" class="btn" ><fmt:message key='button.cancel' /></button>
		</div>
	</div>
	
	<div id="codeSetDialog" title="<fmt:message key='audit.code.setting' />" style="text-align:left;" >
		<div style="margin:10px;margin-left:25px;">
			<span><fmt:message key='auto.audit'/></span>
			<button id="controlAuditBtn" class="btn btn-info">
			<c:choose>
				<c:when test="${activeAuto}">
					<fmt:message key='audit.stop'/>
				</c:when>
				<c:otherwise>
					<fmt:message key='audit.start'/>
				</c:otherwise>
			</c:choose>
			</button>
			
		</div>
		<div>
			<blockquote class="" style="margin-left:20px;margin-bottom:8px;">
				<small id="autoAuditNote" ></small>
			</blockquote>
		</div>
		<div id="codeSetDiv" >
			<c:forEach var="code" items="${codeList}">
			<div class="codeItem" style="margin-bottom:5px;">
				<div class="span">
					<label class="checkbox inline">
						<input type="checkbox" class="codeCheck input-ctl" <c:if test="${code.active == true}">checked</c:if>>
						<span class="codeText"><c:out value="${code.labCode}" /></span>
					</label>
				</div>
				<div class="span scopeDiv" <c:if test="${code.active == false}">style="display:none;"</c:if>>
					<input class="span1 input-ctl val-lo" style="color:#000000;" type="text" value="${code.lo}" /> 
					<input style="width:17px;" type="text" value=" -" disabled/> 
					<input class="span1 input-ctl val-hi" style="color:#000000;" type="text" value="${code.hi}" /> 
				</div>
			</div>
			</c:forEach>
		</div>

	</div>
	
	<div id="testModifyDialog" title="<fmt:message key='audit.testModify' />" style="text-align:left;" >
		<table id="test_modify_information"></table>
	</div>
	
	<div id="sampleCompareDialog" title="<fmt:message key='audit.compare.sample' />" style="text-align:left;" >
		<table id="sample_compare_information"></table>
	</div>
	
	<div id="allNeedWriteBackDialog" title="<fmt:message key='audit.info.need.writeback' />" style="text-align:left;" >
		<table id="need_writeback_table" class="table">
			
		</table>
	</div>
	
	<div id="statisticDialog" title="<fmt:message key='statistic.title' />" style="text-align:left;" >
		<div class='form-inline'>
			<input id="statistic_code" class="span2" type="text" placeholder="<fmt:message key='placeholder.code'/>"/>
			<input id="statistic_from" class="span2" type="text" placeholder="<fmt:message key='placeholder.from'/>"/>
			<input id="statistic_to" class="span2" type="text" placeholder="<fmt:message key='placeholder.to'/>"/>
			<button id="statisticBtn" class="btn btn-info"><fmt:message key='button.statistic'/></button>
		</div>
		
		<table id="statistic_table" style="font-size: 14px;"></table>
	</div>
	
	<div id="writeBackPartDialog" title="<fmt:message key='writebackpart' />" style="text-align:left;" >
		<div class='form-inline'>
			<input id="writeBack_code" class="span2" type="text" placeholder="<fmt:message key='placeholder.code'/>"/>
			<input id="writeBack_from" class="span2" type="text" placeholder="<fmt:message key='placeholder.from'/>"/>
			<input id="writeBack_to" class="span2" type="text" placeholder="<fmt:message key='placeholder.to'/>"/>
			<button id="writePartBtn" class="btn btn-info"><fmt:message key='writeback'/></button>
		</div>
	</div>
	
	
	<div id="auditTraceDialog" title="<fmt:message key='audit.trace' />" style="text-align:left;" >
		<table id="audit_trace_information"></table>
	</div>
	
	<div id="chartDialog" title="<fmt:message key='history.plot' />" style="text-align:left;" >
		<div id="singleChartPanel"></div>
	</div>
</div>