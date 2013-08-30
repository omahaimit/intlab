<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">
<head>
<meta charset="utf-8">
<title><fmt:message key="project.name" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script src="<c:url value='/js/jquery-1.9.1.min.js' />"></script>
<script src="<c:url value='/js/jquery-migrate-1.1.0.min.js' />"></script>
<script src="<c:url value='/js/jquery.jqGrid.min.js' />"></script>
<script src="<c:url value='/js/grid.locale-cn.js' />"></script>
<script src="<c:url value='/js/jquery.json-2.3.min.js' />"></script>
<script type="text/javascript">
	
	function loadUndealGrid(date) {
		jQuery("#smsList").jqGrid({ 
	    	url:"<c:url value='/admin/ajax/requestGrid'/>?date=" + date + "&status=0", 
	    	datatype: "json", 
	    	width: 720, 
	    	colNames:['id', '<fmt:message key="column.patientId" />', '<fmt:message key="column.requestTime" />','<fmt:message key="column.respond" />',''], 
	    	colModel:[ 
	    		{name:'id',index:'id', hidden:true},
	    		{name:'patientId',index:'patientId', width:170, sortable:false},
	    		{name:'requestTime',index:'requestTime', width:150, sortable:false},
	    		{name:'status',index:'status', width:60, sortable:false},
	    		{name:'content',index:'content', hidden:true}],
	    	rowNum:10,
	    	height: '100%',
	    	rownumbers: true,
	    	jsonReader : {repeatitems : false},
	    	mtype: "GET", 
	    	pager: '#smsPager',
	    	loadComplete: function() {
	    		$("#smsList tr").each(function(){
	    			var td = $(this).find("td:eq(4)");
	    			var id = $(this).find("td:eq(1)").html();
	    			var content = $(this).find("td:eq(5)").html();
	    			if (td.html() == "0") {
	    				td.html("<a class='dealLink btn btn-link' href='#myModal' role='button' data-toggle='modal'>答复</a>")	;
	    				$(this).find(".dealLink").click(function(){
	    					$("#requestId").html(id);
	    					$("#requestContent").html(content);
	    					$("#adviceContent").val("");
	    				});
	    			}
	    		});
	    	}
	    });
	}
	
	function loadDealedGrid(date) {
		jQuery("#smsList2").jqGrid({ 
	    	url:"<c:url value='/admin/ajax/requestGrid'/>?date=" + date + "&status=1", 
	    	datatype: "json", 
	    	width: 870, 
	    	colNames:['id', '<fmt:message key="column.patientId" />', '<fmt:message key="column.requestTime" />','<fmt:message key="column.respondTime" />','问题', '答复'], 
	    	colModel:[ 
	    		{name:'id',index:'id', hidden:true},
	    		{name:'patientId',index:'patientId', width:170, sortable:false},
	    		{name:'requestTime',index:'requestTime', width:180, sortable:false},
	    		{name:'respondTime',index:'respondTime', width:180, sortable:false},
	    		{name:'content',index:'content', width:200, sortable:false},
	    		{name:'respond',index:'respond', width:200, sortable:false}],
	    	rowNum:10,
	    	height: '100%',
	    	rownumbers: true,
	    	jsonReader : {repeatitems : false},
	    	mtype: "GET", 
	    	pager: '#smsPager2'
	    });
	}

	$(function() {
		$("#lefnav li").mouseup(function(){
			$("#lefnav li").removeClass("active");
			$(this).addClass("active");
		});
		
		$.post("<c:url value='/admin/request/ajax/count'/>",{date:"<c:out value='${today}'/>"}, function(result){
			var countList = $.parseJSON(result);
			$("#allRequestCount").html(countList.all);
			$("#unRespondCount").html(countList.unRespond);
		});
		
		var date = "<c:out value='${today}' />";
		loadUndealGrid(date);
		loadDealedGrid(date);

		$("#finishBtn").click(function(){
			var id = $("#requestId").html();
			var content = $("#adviceContent").val();
			alert(id + content);
			$.post("<c:url value='/admin/ajax/dealRequest'/>",{id:id,content:content},function(result){
				if (result == 0) {
					alert("请求处理失败!");
				} else {
					var id = $("#requestId").html();
					$("#smsList tr").each(function(){
		    			var idTd = $(this).find("td:eq(1)").html();
		    			if (id == idTd) {
		    				$(this).find("td:eq(4)").html("<span style='padding-left:10px;'>已答复</span>")
		    			}
		    		});
				}
			});
		});
		
		$("#searchUndeal").click(function(){
			var date = $("#UndealText").val();
			jQuery("#smsList").jqGrid("setGridParam",{url:"<c:url value='/admin/ajax/requestGrid'/>?date=" + date + "&status=0"}).trigger("reloadGrid");
		});
		
		$("#SearchCountBtn").click(function(){
			var date = $("#SearchDateCount").val();
			$.post("<c:url value='/admin/request/ajax/count'/>",{date:date}, function(result){
				var countList = $.parseJSON(result);
				$("#allRequestCount").html(countList.all);
				$("#unRespondCount").html(countList.unRespond);
			});
		});
		
		$("#searchDealed").click(function(){
			var date = $("#DealedText").val();
			jQuery("#smsList2").jqGrid("setGridParam",{url:"<c:url value='/admin/ajax/requestGrid'/>?date=" + date + "&status=1"}).trigger("reloadGrid");
		});
		
		$("#viewSampleBtn").click(function(){
			
		});
		
	});
</script>
<link href="<c:url value='/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/bootstrap/css/bootstrap-responsive.min.css'/>" rel="stylesheet">
<link href="<c:url value='/bootstrap/css/doc.css'/>" rel="stylesheet">
<%-- <link href="<c:url value='/css/ui.jqgrid.css'/>" rel="stylesheet">
<link href="<c:url value='/css/ui-lightness/jquery-ui-1.9.2.custom.min.css'/>" rel="stylesheet"> --%>
<link href="<c:url value='/css/ui-lightness/jquery-ui-icon.css'/>" rel="stylesheet">

<style type="text/css">
body {
	padding-bottom: 40px;
	/* background: #e5e5e5 url(images/mooning.png); */
	overflow-x: hidden;
	-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
	font-family: 'Microsoft Yahei', simsun, serif;
}
#smsList .dealLink {
	padding:0px;
	padding-left:10px;
}
</style>
<body>

	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="brand" href="#"><fmt:message key="project.name" /></a>
				<div class="nav-collapse collapse">
					<ul class="nav" style="font-size:15px;">
						<li><a href="#">Home</a></li>
						<li><a href="<c:url value='/admin/message'/>"><fmt:message key="admin.nav.message" /></a></li>
						<li><a href="<c:url value='/admin/patient'/>"><fmt:message key="admin.nav.patient" /></a></li>
						<li><a href="<c:url value='/admin/system'/>"><fmt:message key="admin.nav.systemControl" /></a></li>
						<li class="active"><a href="<c:url value='/admin/request'/>"><fmt:message key="admin.nav.requestControl" /></a></li>
						<li><a href="<c:url value='/logout'/>">退出</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<ul class="nav nav-list bs-docs-sidenav affix" id="lefnav">
					<li class="active"><a href="#request-statistics"><i class="icon-chevron-right"></i> 请求统计</a></li>
					<li><a href="#request-undeal"><i class="icon-chevron-right"></i> 未处理的请求</a></li>
					<li><a href="#request-dealed"><i class="icon-chevron-right"></i> 已处理的请求</a></li>
				</ul>
			</div>
			<div class="span9" style="margin-top:-40px;">

				<section id="request-statistics">
					<div class="page-header">
						<h1>1. 请求统计</h1>
					</div>
					<div class="row-fluid">
						<input id="SearchDateCount" type="text" class="input-medium search-query" value="${today}">
  						<button id="SearchCountBtn" type="button" class="btn btn-success">Search</button>
					</div>
					<hr>
					<div class="row-fluid">
						<div class="span4 panel">
							<h2 id="allRequestCount">0</h2>
							<p>今日请求数</p>
						</div>
						<div class="span4 panel">
							<h2 id="unRespondCount">0</h2>
							<p>未处理请求数</p>
						</div>
					</div>
				</section>

				<section id="request-undeal">
					<div class="page-header">
						<h1>2. 未处理的请求</h1>
					</div>
					<div class="row-fluid">
						<input id="UndealText" type="text" placeholder="yyyy-MM-dd" value="<c:out value='${today}' />" class="input-medium search-query">
  						<button id="searchUndeal" class="btn btn-success">Search</button>
					</div>
					<hr>
					<div class="row-fluid">
						<table id="smsList" class="table table-bordered"></table>
						<div id="smsPager" style="margin-bottom：30px;"></div>
					</div>
					<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
							<h3 id="myModalLabel">患者请求处理</h3>
						</div>
						<div>
							<button class="btn btn-primary" id="viewSampleBtn">查看该患者的样本数据</button>
						</div>
						<div class="modal-body">
							<div style="display:none;" id="requestId"></div>
							<div><span>问题:</span><span id="requestContent"></span></div>
							<textarea id="adviceContent" rows="4" style="width:450px;"></textarea>
						</div>
						<div class="modal-footer">
							<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
							<button id="finishBtn" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">完成</button>
						</div>
					</div>
				</section>
				
				<section id="request-dealed">
					<div class="page-header">
						<h1>3. 已处理的请求</h1>
					</div>
					<div class="row-fluid">
						<input id="DealedText" type="text" placeholder="yyyy-MM-dd" value="<c:out value='${today}' />" class="input-medium search-query">
  						<button id="searchDealed" class="btn btn-success">Search</button>
					</div>
					<hr>
					<div class="row-fluid">
						<table id="smsList2" class="table table-bordered"></table>
						<div id="smsPager2" style="margin-bottom：30px;"></div>
					</div>
				</section>
			</div>
		</div>

		<hr>

		<footer>
			<p>&copy; Company 2013</p>
		</footer>

	</div>

	<!-- Placed at the end of the document so the pages load faster -->
	<script src="<c:url value='/bootstrap/js/bootstrap.min.js' />"></script>
	
</body>
</html>

