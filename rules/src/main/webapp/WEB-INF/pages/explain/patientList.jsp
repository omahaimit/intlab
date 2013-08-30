<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="patientView.title" /></title>
	<meta name="heading" content="<fmt:message key='auditView.heading'/>"/> 
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

#patient-info .pLabel {
}

#patient-info .pText {
	margin-right:20px;
}

#rowed3 {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

select {
	padding: 0px;
}

#gview_s3list .ui-jqgrid-hdiv {
	overflow-y:hidden;
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

#gview_s3list .ui-jqgrid-htable th {
	padding:0px;
}

#s3list {
	font-size:12px;
}
.ui-jqgrid-labels {
	height:25px;
}

input {
	width:20px;
}

.result_span {
	display:block;
	float:left;
	width:50px;
	margin-left:2px;
}
.ui-tabs-vertical { width: 580; }
.ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
.ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: 0 -1px .2em 0; }
.ui-tabs-vertical .ui-tabs-nav li a { display:block; }
.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-active { padding-bottom: 0; padding-right: .1em; border-right-width: 1px; border-right-width: 1px; }
.ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: right; width: 370px;}
#audit_information td {white-space:normal;}
#s3list td {white-space:normal;}
</style>
<script type="text/javascript">
 	function getPatient(docNo) {
		$.get("<c:url value='/explain/patientList/patient'/>",{id:docNo},function(data){
			$("#midContent").css('display','block');
			$("#sampleTitle").html(data.examinaim);
			$("#rowed3").jqGrid("setCaption", data.examinaim);
        	$("#audit_reason").html(data.reason);
        	$("#pName").html(data.name);
        	$("#pAge").html(data.age);
        	$("#blh").html(data.blh);
        	$("#pId").html(data.id);
        	$("#pSex").html(data.sex);
        	$("#pSection").html(data.section);
        	$("#pType").html(data.type);
        	$("#diagnostic").html(data.diagnostic);
        	
        	$("#hiddenDocId").val(docNo);
        }, "json");
	}
 	
 	function getChart(docNo) {
 		
 		$.get("<c:url value='/explain/patientList/chart'/>",{id:docNo},function(data){
 			
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
	        					tickOptions:{formatString:'%m/%d'}
	        				}
	    				}
	        		});
	    		}
			}
 		});
 	}

	function getSample(sampleNo) {
        var cl = "";
		jQuery("#rowed3").jqGrid({
		   	url:"<c:url value='/explain/patientList/sample'/>?id="+sampleNo,
			datatype: "json",
			jsonReader : {repeatitems : false},  
		   	colNames:['ID','<fmt:message key="project"/>', '<fmt:message key="result"/>', '\u5386\u53f21', '\u5386\u53f22', '\u5386\u53f23', '<fmt:message key="scope"/>', '<fmt:message key="unit"/>'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name',width:135,sortable:false},
		   		{name:'result',index:'result',width:75, sortable:false},
		   		{name:'last',index:'last',width:50, sortable:false},
		   		{name:'last1',index:'last1',width:50, hidden:true, sortable:false},
		   		{name:'last2',index:'last2',width:50, hidden:true, sortable:false},
		   		{name:'scope',index:'scope',width:90,sortable:false},
		   		{name:'unit', sortable:false, width:70, index:'unit'},
		   	],
		   	height: '404',
		   	rowNum: 100,
		   	rownumbers: true,
		    caption: " ",
			loadComplete: function() {
				$("#rowed3").jqGrid("setCaption", $("#sampleTitle").html());
				$.each(jQuery('#rowed3').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#rowed3").jqGrid('getRowData',v);
        			if (ret.last != null && ret.last != "")
        				$("#hisLastResult").val(1);
        			else
        				$("#hisLastResult").val(0);
        			var hl = ret.scope.split("-");
        			var h = parseFloat(hl[1]);
        			var l = parseFloat(hl[0]);
        			var va = parseFloat(ret.result);
        			var res = "";
        			var color = "<div>";
        			
        			if (!isNaN(h) && !isNaN(l) && !isNaN(va)) {
        				if (va < l) {
        					res = "<font color='red'>\u2193</font>";
        				} else if (va > h) {
        					res = "<font color='red'>\u2191</font>";
        				}
        			}
        			jQuery("#rowed3").jqGrid('setRowData', v, {result:color+"<span class='result_span'>"+ret.result+"</span>"+res+"</div>"});
        		}); 
			}
		});
	}
	
	function getExplain(docNo){
		jQuery("#audit_information").jqGrid({
			url:"<c:url value='/explain/patientList/explain'/>?id="+docNo,
			datatype: "json",
			jsonReader : {repeatitems : false}, 
			colNames:['ID','OLDRESULT','<fmt:message key="addResult.result"/>','<fmt:message key="content"/>','RANK'],
		   	colModel:[{name:'id',index:'id',sortable:false,hidden:true},
		   		{name:'oldResult',index:'oldResult',sortable:false,hidden:true},
		   		{name:'result',index:'result',width:90,sortable:false},
		   		{name:'content',index:'content',width:100,sortable:false},
		   		{name:'rank',index:'rank',sortable:false,hidden:true}], 
		   	height: '100%'
		});
	}
	
	function getList(patientId) {
		
		var isFirstTime = true;
		var isFirstTimeForResult = true;
		var mygrid = jQuery("#s3list").jqGrid({
        	url:"<c:url value='/explain/patientList/data'/>?patientId="+patientId, 
        	datatype: "json", 
        	width: 250, 
        	colNames:['ID', '<fmt:message key="audit.sample.number" />', '<fmt:message key="patientInfo.examinaim"/>', '<fmt:message key="resultstatus"/>'], 
        	colModel:[ 
        		{name:'id',index:'id', hidden:true},
        		{name:'sample',index:'sample',width:110, sortable:false},
        		{name:'examinaim',index:'examinaim',width:100, sortable:false}, 
        		{name:'type',index:'type',width:40, sortable:false}], 
        	rowNum:12,
        	height: '100%',
        	jsonReader : {repeatitems : false},
        	mtype: "GET", 
        	pager: '#s3pager',
        	onSelectRow: function(id) {    
        		var ret = jQuery("#s3list").jqGrid('getRowData',id);
        		
        		getPatient(ret.id);
        		
        		$("#hiddenSampleNo").val(ret.sample);
        		$("#hisLastResult").val(null);
        		if (isFirstTime) {
    				getSample(ret.sample);
    				isFirstTime = false;
        		} else {
        			jQuery("#rowed3").jqGrid("setGridParam",{url:"<c:url value='/explain/patientList/sample'/>?id="+ret.sample}).trigger("reloadGrid");
        		}
        		
        		if ($("#historyTabs").tabs('option', 'selected') == 0) {
        			getChart(ret.id);
        		} else if ($("#historyTabs").tabs('option', 'selected') == 1) {
       				jQuery("#audit_information").jqGrid("setGridParam",{
       					url:"<c:url value='/explain/patientList/explain'/>?id="+ret.id
       				}).trigger("reloadGrid");
        		} else {
        			jQuery("#rowed3").setGridParam().showCol("last1");
					jQuery("#rowed3").setGridParam().showCol("last2");
        		}
        		$("#historyTabs").css('display','block');
        	},
        	loadComplete: function() {
        		var firstDocId, firstSampleNo;
        		$.each(jQuery('#s3list').jqGrid('getCol','id', false), function(k,v) {
        			var ret = jQuery("#s3list").jqGrid('getRowData',v);
        			if (k == 0) {
        				firstDocId = ret.id;
        				firstSampleNo = ret.sample;
        			}
            		
        		}); 
        		$("#s3list").setSelection(firstDocId, true);
        	}
        }).trigger("reloadGrid"); 
		jQuery("#s3list").jqGrid('navGrid','#s3pager',{edit:false,add:false,del:false,search:false,refresh:false});
        jQuery("#s3list").jqGrid('navButtonAdd',"#s3pager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
	}
	
	$(function() {
		$.ajaxSetup({cache:false});
		
		$( "#from" ).datepicker({
	      defaultDate: "-1m",
	      changeMonth: true,
	      dateFormat:"yy-mm-dd",
	  	  monthNamesShort: ['1\u6708','2\u6708','3\u6708','4\u6708','5\u6708','6\u6708','7\u6708','8\u6708','9\u6708','10\u6708','11\u6708','12\u6708'],
	      dayNamesMin: ['\u65e5','\u4e00','\u4e8c','\u4e09','\u56db','\u4e94','\u516d'],
	      onClose: function( selectedDate ) {
	        $( "#to" ).datepicker( "option", "minDate", selectedDate );
	      }
	    });
	    $( "#to" ).datepicker({
	      changeMonth: true,
	      dateFormat:"yy-mm-dd",
	      monthNamesShort: ['1\u6708','2\u6708','3\u6708','4\u6708','5\u6708','6\u6708','7\u6708','8\u6708','9\u6708','10\u6708','11\u6708','12\u6708'],
	      dayNamesMin: ['\u65e5','\u4e00','\u4e8c','\u4e09','\u56db','\u4e94','\u516d'],
	      onClose: function( selectedDate ) {
	        $( "#from" ).datepicker( "option", "maxDate", selectedDate );
	      }
	    });
	    
		$("#searchBtn").click(function() {
			
			var from = $("#from").val();
			var to = $("#to").val();
			var pName = $("#patientName").val();
			var doct = $("#doctadviseno").val();
			
			
			if (from != "" && to != "" && pName != "") {
				jQuery("#s3list").jqGrid("setGridParam",{
					url:"<c:url value='/explain/patientList/data'/>?from="+from+"&to="+to+"&pName="+pName
				}).trigger("reloadGrid"); 
			} else if (doct != "") {
				jQuery("#s3list").jqGrid("setGridParam",{
					url:"<c:url value='/explain/patientList/data'/>?doct="+doct
				}).trigger("reloadGrid"); 
			}
			
		});
		
		$("#historyTabs").tabs({
			select: function(event, ui) { 
				if(ui.index == 2) {
					jQuery("#rowed3").setGridParam().showCol("last1");
					jQuery("#rowed3").setGridParam().showCol("last2");
				} else {
					jQuery("#rowed3").setGridParam().hideCol("last1");
					jQuery("#rowed3").setGridParam().hideCol("last2");
					var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
					if (ui.index == 1) {
						getExplain(s);
					} else if (ui.index == 0) {
	        			getChart(s);
					}
				}
			}
		});
		
		getList("<c:out value='${patientId}'/>");
	});
	
</script>

</head>

<div class="form-inline">
	<label for="from" style="margin-left: 20px;"><b><fmt:message key="patient.searchfrom" /></b></label>
	<input type="text" id="from" name="from" class="span2"/>
	<label for="to"><b><fmt:message key="patient.searchto" /></b></label>
	<input type="text" id="to" name="to" class="span2"/>
	
	<label for="patientName" style="margin-left: 40px;"><b><fmt:message key="patientInfo.patientName" /></b></label>
	<input type="text" id="patientName" name="patientName" class="span2"/>
	
	<label for="doctadviseno" style="margin-left: 40px;"><b><fmt:message key="patient.doctadviseno" /></b></label>
	<input type="text" id="doctadviseno" name="doctadviseno" class="span2"/>
	
	<button id="searchBtn" class="btn btn-info" style="margin-left: 40px;"><fmt:message key='search'/></button>
</div>

<div style="margin-top: 10px;">
	<div id="searchHeader" style="float: left; width: 250px;">
		<div id="sampleListPanel">
			<table id="s3list"></table>
			<div id="s3pager"></div>
		</div>
	</div>
	<div id="midContent"
		style="float: left; width: 470px; margin-left: 20px; display: none;">
		<div class="clearfix">
			<h2 style="display:none;" id="sampleTitle"></h2>
			<div id="patient-info" class="alert alert-info" style="margin-bottom:2px;padding:0px;padding-left:10px;padding-bottom:4px;">
				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.patientName" /></span>
					<span class="pText"><b id="pName"></b></span>
					<span class="pLabel"><fmt:message key="patient.patientSex" /></span>
					<span class="pText"><b id="pSex"></b></span>
					<span class="pLabel"><fmt:message key="patient.age" /></span>
					<span class="pText"><b id="pAge"></b></span>
					<span class="pLabel"><fmt:message key="patient.sampleType" /></span>
					<span class="pText"><b id="pType"></b></span>
				</div>
				
				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.blh" /></span>
					<span class="pText"><b id="blh"></b></span>
					<span class="pLabel"><fmt:message key="patient.patientId" /></span>
					<span class="pText"><b id="pId"></b></span>
				</div>

				<div class="pItem">
					<span class="pLabel"><fmt:message key="patient.section"/>&nbsp;</span>
					<span class="pText"><b id="pSection"></b></span>
					<span class="pLabel"><fmt:message key="diagnostic"/>&nbsp;</span>
					<span class="pText"><b id="diagnostic"></b></span>
				</div>
			</div>
		</div>
		<div style="height:20px"></div>
		<div id="patientRow" style="font-size: 13px;">
			<table id="rowed3"></table>
		</div>
		<div style="font-size: 13px; display:none;margin-top: 10px;">
			<div style="margin-left:60px;">
				<input type="hidden" id="hiddenDocId"/>
				<input type="hidden" id="hiddenSampleNo"/>
				<input type="hidden" id="hisLastResult"/>
			</div>
		</div>
	</div>
	<div style="position:absolute;right:0px; width: 220px;">
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
				<div id="chartPanel" style="height:465px;overflow-y:auto;"></div>
			</div>
			<div id="tabs-1" style="padding:5px;">
				<div style="margin: 10px;">
					<span class="label label-info"><fmt:message key="audit.infomation" /></span>
				</div>
				<div style="height:465px;overflow-y:auto;">
					<div id="explainRow" style="margin-top: 4px; font-size: 13px;">
						<table id="audit_information"></table>
					</div>
				</div>
			</div>
			<div id="tabs-2" style="padding:5px;">
				<div style="margin: 10px;">
					<span class="label label-info"><fmt:message key="show.three.history" /></span>
				</div>
			</div>
		</div>
	</div>
</div>
