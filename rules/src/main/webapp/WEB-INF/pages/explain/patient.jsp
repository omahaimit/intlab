<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="patientView.title"/></title>
    <meta name="heading" content="<fmt:message key='patientView.heading'/>"/>
    <meta name="menu" content="IntelAuditAndExplain"/>
    <script type="text/javascript" src="<c:url value='/scripts/grid.locale-cn.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.jqGrid.min.js'/> "></script>
    <script type="text/javascript" src="<c:url value='/scripts/plugins/jqplot.highlighter.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.jqprint-0.3.js'/>"></script>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/styles/ui.jqgrid.css'/> " />
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
	table{border-collapse:collapse;border-spacing:0;empty-cells:show;}
	td .wikitable{margin:.5em .25em;}
	.wikitable tr{vertical-align:top;}
	.wikitable * th{border:1px solid #ddd;background-color:#eee;vertical-align:top;}
	.wikitable * td{border:1px solid #ddd;vertical-align:top;text-align:left;}
	.zebra-table tr.odd td{background-color:#eee;}
	.wikitable * td.split{border-top:1px solid #d9d9d9;}
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
	.ui-jqgrid-titlebar {
		height:20px;
		font-size:16px;
		line-height:19px;
	}
	
    </style>
    <script>
    function getPatient(docNo) {
		$.get("<c:url value='/explain/patient/patient'/>",{id:docNo},function(data){
			$("#sampleTitle").html(data.examinaim);
			$("#rowed3").jqGrid("setCaption", data.examinaim);
        	$("#current_sample_status").html(data.status);
        	$("#audit_infomation").html(data.infomation);
        	$("#audit_reason").val(data.reason);
        	$("#pName").html(data.name);
        	$("#pAge").html(data.age);
        	$("#pId").html(data.id);
        	$("#pSex").html(data.sex);
        	$("#pSection").html(data.section);
        	$("#pType").html(data.type);
        	$("#midContent").css('display','block');
        	$("#hiddenDocId").val(docNo);
        }, "json");
	}
 	
 	function getChart(docNo) {
 		
 		$.get("<c:url value='/explain/patient/chart'/>",{id:docNo},function(data){
 			
 			$("#chartPanel").empty();
	    	if (data != "") {
	    		var length = data.length;
	    		for (var i=0; i<length; i++) {
	    			$("#chartPanel").append("<div id=\"chart" + i + "\" style=\"height:180px;width:200px;margin-bottom:15px;\"></div>")
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
 	
 	function getExplain(docNo) {
 		jQuery.ajax({
	  		type:'GET',
			url: "<c:url value='/explain/patient/result'/>?id="+docNo,
	  		dataType: 'json',
	  		success: function(data) {
	  			var intelExplain="";
	  			for(var i=0; i<data.length; i++){
	  				intelExplain=intelExplain+data[i]+"</br>";
	  			}
	  			$("#intelExplain").html(intelExplain);
	  	  	}
	  	}); 
	}
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
	        
	        $( "#dialog" ).dialog({
	            autoOpen: false,
	            show: "blind",
	            hide: "explode",
	            width: "600px",
				height: "360"
	        });	        
	        
	        var docNo = $("#sampleList tbody td:first").html();
	        var sampleNo = $("#sampleList tbody td:eq(1)").html();
	        var plot=new Array();
	        
	        if (docNo != null && docNo != "") {
	        	getPatient(docNo);
	        	getChart(docNo);
	        	getExplain(docNo);
	        }

	        if (sampleNo != null && sampleNo != "") {
		        $("#patientRow").empty();
		        $("#patientRow").html("<table id=\"rowed3\"></table>");
		        var lastsel;
				jQuery("#rowed3").jqGrid({
				   	url:"<c:url value='/explain/patient/sample'/>?id="+sampleNo,
					datatype: "json",
					jsonReader : {repeatitems : false},  
				   	colNames:['ID','KNOWLEDGE','<fmt:message key="project"/>','<fmt:message key="result"/>', '<fmt:message key="scope"/>', '<fmt:message key="unit"/>'],
				   	colModel:[
				   		{name:'id',index:'id', hidden:true},
				   		{name:'knowledgeName',index:'knowledgeName', hidden:true},
				   		{name:'name',index:'name',width:160,sortable:false},
				   		{name:'result',index:'result',width:80, sortable:false},
				   		{name:'scope',index:'scope',width:110,sortable:false},
				   		{name:'unit', sortable:false, width:80, index:'unit'}
				   	],
				   	height: '100%',
				   	rowNum: 50,
				   	rownumbers: true,
					onCellSelect : function(rowid, index, contents, event){
		                var data = $("#"+rowid +">td"); 
		                showDialog(data[2].innerHTML);
		            }
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
	    
	    function showDialog(item){
	   		getData(item);
	   		$( "#dialog" ).dialog( "open" );
	   	}
	    
	    function getData(item){
			jQuery.ajax({
		  		type:'GET',
				url: '/item.jsp?page='+item,
		  		dataType: 'html',
		  		success: function(data) {
					var data2=dataProcess(data);
		  	    	document.getElementById("dialog").innerHTML = data2;
		  	    	$( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
					$( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
		  	  	}
		  	}); 
		}
	    
		function dataProcess(data){
			var knowledge="";
			$.ajax({
    			type: "get",
    			url: "<c:url value='/usercustom/data'/>",
    			async:false,    			
    			success: function(data){
    				knowledge = data[0];
    			}
    		});
			var title="<ul>";
			var title2="<ul>";
			var array = data.split("<div class=\"tabbedSection\">");
			var data = array[1];
			array = data.split("<\/body>");
			data = "<div> "+array[0];
			var dataArray = data.split("<div class=\"tab-");
			var tabContent="";
			var k=0;
			if(knowledge.indexOf("\u68c0\u9a8c\u9879\u76ee\u7b80\u4ecb") != -1){
				var str = dataArray[1].replace("\">","!@#$%^&*");
				var arr = str.split("!@#$%^&*");
				title2 = title2+"<li><a href=\"#tabs-"+k+"\">"+arr[0]+"<\/a><\/li>";
				tabContent = tabContent + "<div id=\"tabs-"+k+"\">"+arr[1];
				k=k+1;
			}
			for(var i=0; i<dataArray.length;i++){
			
				var str = dataArray[i].replace("\">","!@#$%^&*");
				if(i!=0){
					var arr = str.split("!@#$%^&*");
					if(knowledge.indexOf(arr[0]) != -1){
						title2 = title2+"<li><a href=\"#tabs-"+k+"\">"+arr[0]+"<\/a><\/li>";
						if(i==dataArray.length-1){
							alert(arr[1]);
						}
						tabContent = tabContent + "<div id=\"tabs-"+k+"\">"+arr[1];
						k=k+1;
						
					}
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
			
			title2 = title2 + "<\/ul>";
			var result2 = "<div id=\"tabs\" style='text-align:left;'>"+ title2;
			result2 = result2 + tabContent +"<\/div>";
			return result2;
		}
	</script>
    <style>
    .ui-tabs-vertical { width: 580; }
    .ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
    .ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: 0 -1px .2em 0; }
    .ui-tabs-vertical .ui-tabs-nav li a { display:block; }
    .ui-tabs-vertical .ui-tabs-nav li.ui-tabs-active { padding-bottom: 0; padding-right: .1em; border-right-width: 1px; border-right-width: 1px; }
    .ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: right; width: 370px;}
    </style>
</head>

<table>
	<tr>
		<td width="500px">From<input type="text" id="fromDate"/>To<input type="text" id="toDate" /></td>
		<td width="300px"/>
		<td><input type="button" name="search" value="<fmt:message key="button.search"/>" onclick="jump();"/></td>
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

<div style="float:left;width:230px;">
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
		<h2 id="sampleTitle" style="text-align:left;"></h2>
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
	<div style="font-size:13px;margin-top:15px;text-align:left;">
		<div style="margin-bottom:10px;"><b><fmt:message key="audit.infomation" />:</b></div>
		<div id="intelExplain" style="width:440px;"></div>
	</div>
	<div id="patientRow" style="margin-top:4px;font-size:13px;">
		<table id="rowed3"></table>
	</div>
</div>

<div id="historyChart" style="float:right;width:250px;">
	<h2><fmt:message key="history.data.infomation" /></h2>
	<div id="chartPanel"  style="margin-top:20px;"></div>
</div>

<div id="dialog" title="KnowLedgeInfo">
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
    var plot=new Array();
    // add event handlers so rows light up and are clickable
    for (i=startRow; i < rows.length; i++) {
        rows[i].onmouseover = function() { previousClass=this.className;this.className+=' over' };
        rows[i].onmouseout = function() { this.className=previousClass };
        rows[i].onclick = function() {
            var docNo = this.getElementsByTagName("td")[0].innerHTML;
            var sampleNo = this.getElementsByTagName("td")[1].innerHTML;

            getPatient(docNo);
        	getChart(docNo);
        	getExplain(docNo);
            
            $("#patientRow").empty();
            $("#patientRow").html("<table id=\"rowed3\"></table>");
            var lastsel;
			jQuery("#rowed3").jqGrid({
			   	url:"<c:url value='/explain/patient/sample'/>?id="+sampleNo,
				datatype: "json",
				jsonReader : {repeatitems : false},  
				colNames:['ID','KNOWLEDGE','<fmt:message key="project"/>','<fmt:message key="result"/>', '<fmt:message key="scope"/>', '<fmt:message key="unit"/>'],
			   	colModel:[
			   		{name:'id',index:'id', hidden:true},
			   		{name:'knowledgeName',index:'knowledgeName', hidden:true},
			   		{name:'name',index:'name',width:160,sortable:false},
			   		{name:'result',index:'result',width:80, sortable:false},
			   		{name:'scope',index:'scope',width:110,sortable:false},
			   		{name:'unit', sortable:false, width:80, index:'unit'}
			   	],
			   	height: '100%',
			   	rowNum: 50,
			   	rownumbers: true,
				onCellSelect : function(rowid, index, contents, event){
		            var data = $("#"+rowid +">td"); 
		            showDialog(data[2].innerHTML);
		        }
			});
            return false;
        }
    }
}
highlightTableRows1("sampleList");
</script>