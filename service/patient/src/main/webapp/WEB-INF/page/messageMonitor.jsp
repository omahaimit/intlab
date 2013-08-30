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
	$(function() {
		$("#lefnav li").mouseup(function(){
			$("#lefnav li").removeClass("active");
			$(this).addClass("active");
		});
		
		$.post("<c:url value='/admin/message/ajax/count'/>",{date:"<c:out value='${today}'/>"}, function(result){
			var countList = $.parseJSON(result);
			$("#allSampleCount").html(countList.all);
			$("#msgCount").html(countList.msg);
			$("#respondCount").html(countList.respond);
		});
		
		$("#smsSearchBtn").click(function(){
			var date = $("#smsSearchDate").val();
			jQuery("#smsList").jqGrid("setGridParam",{url:"<c:url value='/admin/ajax/smsGrid'/>?date=" + date}).trigger("reloadGrid");
		});
		
		$("#smsSearchCountBtn").click(function(){
			var date = $("#smsSearchDateCount").val();
			$.post("<c:url value='/admin/message/ajax/count'/>",{date:date}, function(result){
				var countList = $.parseJSON(result);
				$("#allSampleCount").html(countList.all);
				$("#msgCount").html(countList.msg);
				$("#respondCount").html(countList.respond);
			});
		});
		
		jQuery("#smsList").jqGrid({ 
	    	url:"<c:url value='/admin/ajax/smsGrid'/>?date=<c:out value='${today}'/>", 
	    	datatype: "json", 
	    	width: 720, 
	    	colNames:['<fmt:message key="column.sampleNo" />', '<fmt:message key="column.patientId" />', '<fmt:message key="column.telephone" />','<fmt:message key="column.sendTime" />','<fmt:message key="column.response" />'], 
	    	colModel:[ 
	    		{name:'sampleNo',index:'sampleNo', width:140, sortable:false},
	    		{name:'patientId',index:'patientId', width:170, sortable:false},
	    		{name:'telephone',index:'telephone', width:120, sortable:false},
	    		{name:'sendTime',index:'sendTime', width:160, sortable:false},
	    		{name:'response',index:'response', width:100, sortable:false, align:"center",formatter:'checkbox'}],
	    	rowNum:10,
	    	height: '100%',
	    	rownumbers: true,
	    	jsonReader : {repeatitems : false},
	    	mtype: "GET", 
	    	pager: '#smsPager',
	    	onSelectRow: function(id) {
	    	},
	    	loadComplete: function() {
	    	}
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
						<li class="active"><a href="#"><fmt:message key="admin.nav.message" /></a></li>
						<li><a href="<c:url value='/admin/patient'/>"><fmt:message key="admin.nav.patient" /></a></li>
						<li><a href="<c:url value='/admin/system'/>"><fmt:message key="admin.nav.systemControl" /></a></li>
						<li><a href="<c:url value='/admin/request'/>"><fmt:message key="admin.nav.requestControl" /></a></li>
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
					<li class="active"><a href="#SMS-statistics"><i class="icon-chevron-right"></i> 短信统计</a></li>
					<li><a href="#sent-SMS"><i class="icon-chevron-right"></i> 已发短信</a></li>
				</ul>
			</div>
			<div class="span9" style="margin-top:-40px;">

				<section id="SMS-statistics">
					<div class="page-header">
						<h1>1. 短信统计</h1>
					</div>
					<div class="row-fluid">
						<input id="smsSearchDateCount" type="text" class="input-medium search-query" value="${today}">
  						<button id="smsSearchCountBtn" type="button" class="btn btn-success">Search</button>
					</div>
					<hr>
					<div class="row-fluid">
						<div class="span4 panel">
							<h2 id="allSampleCount">0</h2>
							<p>
								<fmt:message key="all.patientinfo.today" />
							</p>
						</div>
						<div class="span4 panel">
							<h2 id="msgCount">0</h2>
							<p>
								<fmt:message key="send.message.today" />
							</p>
						</div>
						<div class="span4 panel">
							<h2 id="respondCount">0</h2>
							<p>
								<fmt:message key="message.respond.today" />
							</p>
						</div>
					</div>
				</section>

				<section id="sent-SMS">
					<div class="page-header">
						<h1>2. 已发短信</h1>
					</div>
					<div class="row-fluid">
						<input id="smsSearchDate" type="text" class="input-medium search-query" value="${today}">
  						<button id="smsSearchBtn" type="button" class="btn btn-success">Search</button>
					</div>
					<hr>
					<div class="row-fluid">
						<table id="smsList" class="table table-bordered"></table>
						<div id="smsPager" style="margin-bottom：30px;"></div>
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

