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
	
	function loadResultList(sampleNo) {
		jQuery("#resultList").jqGrid({ 
	    	url:"<c:url value='/admin/ajax/resultList'/>?sampleNo=" + sampleNo, 
	    	datatype: "json", 
	    	width: 670, 
	    	colNames:['testId', '项目', '结果','参考范围', '单位'], 
	    	colModel:[ 
	    		{name:'testId',index:'testId', hidden:true},
	    		{name:'name',index:'name', width:120, sortable:false},
	    		{name:'result',index:'result', width:80, sortable:false},
	    		{name:'scope',index:'scope', width:100, sortable:false},
	    		{name:'unit',index:'unit', width:60, sortable:false}],
	    	rowNum:100,
	    	height: '100%',
	    	rownumbers: true,
	    	jsonReader : {repeatitems : false},
	    	mtype: "GET"
	    });
	}

	function loadsampleNoList(patientId) {
		jQuery("#sampleNoList").jqGrid({ 
	    	url:"<c:url value='/admin/ajax/sampleNoList'/>?patientId=" + patientId, 
	    	datatype: "json", 
	    	width: 460, 
	    	colNames:['样本号', '名称'], 
	    	colModel:[ 
	    		{name:'sampleNo',index:'sampleNo', width:140, sortable:false},
	    		{name:'examinaim',index:'examinaim', width:300, sortable:false}],
	    	rowNum:100,
	    	height: '100%',
	    	rownumbers: true,
	    	jsonReader : {repeatitems : false},
	    	mtype: "GET", 
	    	loadComplete: function() {
	    		
	    	},
			onSelectRow: function(id) {
				var ret = jQuery("#sampleNoList").jqGrid('getRowData',id);
				var sampleNo = ret.sampleNo;
				loadResultList(sampleNo);
			}
	    });
	}

	$(function() {
		$("#queryPatientSampleBtn").click(function(){
			var patientId = $("#patientIdText").val();
			if (patientId != "") {
				loadsampleNoList(patientId);
			}
		});
		//loadsampleNoList("6217211202003452308");
		var patientId = "<c:out value='${patientId}'/>";
		
		if (patientId != "") {
			$("#patientIdText").val(patientId);
			loadsampleNoList(patientId);
		}
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
#sampleNoList {
	overflow:hidden;
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
		<div class="row" style="padding-top: 30px;">
			<div class="span5">
				<p>
					患者历次化验查询
				</p>
				<div class="input-append">
					<input class="span3" id="patientIdText" type="text" PlaceHolder="输入就诊卡号">
				  	<button class="btn btn-primary" id="queryPatientSampleBtn" type="button">查询</button>
				</div>
				<table id="sampleNoList" class="table table-bordered"></table>
			</div>
			<div class="span5">
				<table id="resultList" class="table table-bordered"></table>
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
