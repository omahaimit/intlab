<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="ylxh.title" /></title>
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
.ui-pg-input {
	width:20px;
}
</style>
<script type="text/javascript">
	function getList(lab) {
		var isFirstTime = true;
		var mygrid = jQuery("#s3list").jqGrid({
	    	url:"<c:url value='/ylxh/data'/>?lab="+lab, 
	    	datatype: "json", 
	    	width: 300, 
	    	colNames:['ID', '<fmt:message key="patientInfo.examinaim" />','PTEST'], 
	    	colModel:[ 
	    		{name:'id',index:'id', hidden:true},
	    		{name:'ylmc',index:'ylmc',width:300, sortable:false},
	    		{name:'ptest',index:'ptest', hidden:true}], 
	    	rowNum:20,
	    	height: '100%',
	    	jsonReader : {repeatitems : false},
	    	mtype: "GET", 
	    	pager: '#s3pager',
	    	onSelectRow: function(id) {    
	    		var ret = jQuery("#s3list").jqGrid('getRowData',id);
	    		
	    		getTest(ret.id);
	    		
	    		$("#hiddenSampleNo").val(ret.sample);
	    		$("#hisLastResult").val(null);
	    		if (isFirstTime) {
	    			getTest(ret.id);
					isFirstTime = false;
	    		} else {
	    			jQuery("#tlist").jqGrid("setGridParam",{url:"<c:url value='/ylxh/test'/>?ylxh="+ret.id}).trigger("reloadGrid");
	    		}
	    	},
	    	loadComplete: function() {
	    		var firstId;
	    		$.each(jQuery('#s3list').jqGrid('getCol','id', false), function(k,v) {
	    			var ret = jQuery("#s3list").jqGrid('getRowData',v);
	    			if (k == 0) {
	    				firstId = ret.id;
	    			}
	    		}); 
	    		$("#s3list").setSelection(firstId, true);
	    	}
	    }).trigger("reloadGrid"); 
		jQuery("#s3list").jqGrid('navGrid','#s3pager',{edit:false,add:false,del:false,search:false,refresh:false});
	    jQuery("#s3list").jqGrid('navButtonAdd',"#s3pager",{caption:"",title:"", buttonicon :'ui-icon-pin-s', onClickButton:function(){ mygrid[0].toggleToolbar() } });
	}
	
	function getTest(ylxh) {
		jQuery("#tlist").jqGrid({
		   	url:"<c:url value='/ylxh/test'/>?ylxh="+ylxh,
			datatype: "json",
			jsonReader : {repeatitems : false},  
		   	colNames:['ID','<fmt:message key="project"/>'],
		   	colModel:[
		   		{name:'id',index:'id', hidden:true},
		   		{name:'name',index:'name',width:320,sortable:false},
		   	],
		   	height: '444',
		   	rowNum: 100,
		   	rownumbers: true
		});
	}

	$(function() {
		$("#addTestDialog").dialog({
			autoOpen: false,
		    width: 320,
		    height: 120
		});
		
		$("#testAdd").click(function() {
			$("#addTestDialog").dialog("open");
		});
		
		$("#testDelete").click(function() {
			var id = jQuery("#s3list").jqGrid('getGridParam','selrow');
			var del = jQuery("#tlist").jqGrid('getGridParam','selrow');
			if (del != null) {
				$.post("<c:url value='/ylxh/delete'/>",{del:del,id:id}, function(data) {
					if (data == true) {
						var s = jQuery("#tlist").jqGrid('getGridParam','selrow');
						var next = $("#"+s).next("tr").attr("id");
						$("#tlist").jqGrid('delRowData',del);
						if (next != null) {
							$("#tlist").setSelection(next, true);
						}
					} else {
						alert("Fail!!!");
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
	        	var s = jQuery("#s3list").jqGrid('getGridParam','selrow');
				var ret = jQuery("#s3list").jqGrid('getRowData',s);
				var ptest;
				if(ret.ptest != "") {
					ptest = ret.ptest;
				} else {
					ptest = ret.id
				}
	        	
				if (ptest.indexOf(ui.item.id)>0) {
					alert("\u9879\u76ee\u5df2\u5b58\u5728\uff01")
				} else {
					$.post("<c:url value='/ylxh/addTest'/>",{add:ui.item.id,id:ret.id}, function(data) {
						if (data == true) {
							$("#addTestDialog").dialog("close");
							jQuery("#tlist").trigger("reloadGrid");
						} else {
							alert("Fail!!!")
						}
					});
				}
	        }
		});
		getList("<c:out value='${lab}'/>");
	});
	
</script>

</head>

<div id="searchHeader" style="margin-left: 100px; float: left; width: 320px;">
	<div id="sampleListPanel">
		<table id="s3list"></table>
		<div id="s3pager"></div>
	</div>
</div>

<div id="testlist" style="float: left; margin-left: 100px; width: 320px;">
	<div style="float:left;">
		<button id="testAdd" class="btn" style="font-size:12px;"><b><fmt:message key="button.add" /></b></button>
		<button id="testDelete" class="btn" style="font-size:12px;"><b><fmt:message key="button.delete" /></b></button>
	</div>
	<div style="margin-top:40px;">
		<table id="tlist"></table>
	</div>
</div>



<div id="addTestDialog" style="text-align:left;" title="<fmt:message key='add.test.result'/>">
	<div>
		<input class="span4" id="searchProject" type="text" placeholder="<fmt:message key='add.single'/>">
	</div>
</div>

