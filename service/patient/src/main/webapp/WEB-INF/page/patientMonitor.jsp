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
		
		$.post("<c:url value='/admin/patient/ajax/count'/>",{date:"<c:out value='${today}'/>"}, function(result){
			var countList = $.parseJSON(result);
			$("#sampleTotalCount").html(countList.allSample);
			$("#msgTotalCount").html(countList.allMsg);
			$("#msgRespondCount").html(countList.msgRespond);
			$("#patientTotalCount").html(countList.allPatient);
			$("#loginRecordCount").html(countList.loginRecord);
			$("#loginPatientCount").html(countList.loginPatient);
		});
		
		$("#recordSearchBtn").click(function(){
			var date = $("#recordSearchDate").val();
			jQuery("#smsList").jqGrid("setGridParam",{url:"<c:url value='/admin/ajax/patientGrid'/>?date=" + date}).trigger("reloadGrid");
		});
		
		$("#recordSearchCountBtn").click(function(){
			var date = $("#recordSearchDateCount").val();
			$.post("<c:url value='/admin/patient/ajax/count'/>",{date:date}, function(result){
				var countList = $.parseJSON(result);
				$("#sampleTotalCount").html(countList.allSample);
				$("#msgTotalCount").html(countList.allMsg);
				$("#msgRespondCount").html(countList.msgRespond);
				$("#patientTotalCount").html(countList.allPatient);
				$("#loginRecordCount").html(countList.loginRecord);
				$("#loginPatientCount").html(countList.loginPatient);
			});
		});
		
		jQuery("#smsList").jqGrid({ 
	    	url:"<c:url value='/admin/ajax/patientGrid'/>?date=<c:out value='${today}'/>", 
	    	datatype: "json", 
	    	width: 620, 
	    	colNames:['<fmt:message key="column.patientId" />', '<fmt:message key="column.device" />','<fmt:message key="column.loginTime" />','<fmt:message key="column.age" />','<fmt:message key="column.sex" />'], 
	    	colModel:[ 
	    		{name:'patientId',index:'patientId', width:170, sortable:false},
	    		{name:'device',index:'device', width:100, sortable:false},
	    		{name:'loginTime',index:'loginTime', width:160, sortable:false},
	    		{name:'age',index:'age', width:80, sortable:false, align:"center"},
	    		{name:'sex',index:'sex', width:80, sortable:false, align:"center"}],
	    	rowNum:10,
	    	height: '100%',
	    	rownumbers: true,
	    	jsonReader : {repeatitems : false},
	    	mtype: "GET", 
	    	pager: '#smsPager'
	    });
	});
</script>
<link href="<c:url value='/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/bootstrap/css/bootstrap-responsive.min.css'/>" rel="stylesheet">
<link href="<c:url value='/bootstrap/css/doc.css'/>" rel="stylesheet">
<link href="<c:url value='/css/ui-lightness/jquery-ui-icon.css'/>" rel="stylesheet">

<style type="text/css">
body {
	padding-bottom: 40px;
	overflow-x: hidden;
	-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
	font-family: 'Microsoft Yahei', simsun, serif;
}

@media ( max-width : 980px) {
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
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
						<li class="active"><a href="#"><fmt:message key="admin.nav.patient" /></a></li>
						<li><a href="<c:url value='/admin/system'/>"><fmt:message key="admin.nav.systemControl" /></a></li>
						<li><a href="<c:url value='/admin/request'/>"><fmt:message key="admin.nav.requestControl" /></a></li>
						<li><a href="<c:url value='/logout'/>">退出</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<ul class="nav nav-list bs-docs-sidenav affix" id="lefnav">
					<li class="active"><a href="#patient-statistics"><i class="icon-chevron-right"></i> 患者统计</a></li>
					<li><a href="#login-record"><i class="icon-chevron-right"></i> 患者登录记录</a></li>
				</ul>
			</div>
			<div class="span9" style="margin-top:-40px;">

				<section id="patient-statistics">
					<div class="page-header">
						<h1>1. 患者统计</h1>
					</div>
					<div class="row-fluid">
						<input id="recordSearchDateCount" type="text" class="input-medium search-query" value="${today}">
  						<button id="recordSearchCountBtn" type="button" class="btn btn-success">Search</button>
					</div>
					<hr>
					<div class="row-fluid">
						<div class="span4 panel">
							<h2 id="sampleTotalCount">0</h2>
							<p>
								<fmt:message key="all.patientinfo.today" />
							</p>
						</div>
						<div class="span4 panel">
							<h2 id="msgTotalCount">0</h2>
							<p>
								<fmt:message key="send.message.today" />
							</p>
						</div>
						<div class="span4 panel">
							<h2 id="msgRespondCount">0</h2>
							<p>
								<fmt:message key="message.respond.today" />
							</p>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span4 panel">
							<h2 id="patientTotalCount">0</h2>
							<p>
								<fmt:message key="all.patient.today" />
							</p>
						</div>
						<div class="span4 panel">
							<h2 id="loginRecordCount">0</h2>
							<p>
								<fmt:message key="login.record.today" />
							</p>
						</div>
						<div class="span4 panel">
							<h2 id="loginPatientCount">0</h2>
							<p>
								<fmt:message key="login.patient.today" />
							</p>
						</div>
					</div>
				</section>

				<section id="login-record">
					<div class="page-header">
						<h1>2. 患者登录记录</h1>
					</div>
					<div class="row-fluid">
						<input id="recordSearchDate" type="text" class="input-medium search-query" value="${today}">
  						<button id="recordSearchBtn" type="button" class="btn btn-success">Search</button>
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

