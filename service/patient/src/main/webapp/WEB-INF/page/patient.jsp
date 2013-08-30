<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>浙江大学附属第一医院检验报告查阅系统</title>
<link rel="stylesheet"
	href="<c:url value='/css/themes/default/jquery.mobile-1.3.1.min.css'/>" />
<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.mobile-1.3.1.js'/>"></script>
<link href="<c:url value='/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
<style type="text/css">
#header-title {
	font-size:18px;
	margin:9px;
	line-height:38px;
	color:#eee;
}
.ui-table-columntoggle-btn {
	display:none;
}
#movie-table .result_span {
	display:block;
	width:40px;
	margin-left:0px;
}
.nav-tabs {
	text-shadow: none;
}
</style>
<script type="text/javascript">
$(function(){
	$("#startSms").click(function(){
		var op = "stop";
		if ($("#startSms").is(":checked")) {
			op = "start";
        }
		$.post("<c:url value='/ajax/notification'/>",{op:op},function(result){
			if (result != 1) {
				alert("失败！");
			}
		});
	});
});
</script>
</head>
<body>
	<div data-role="page" id="page1">
		<div data-role="header" data-position="fixed">
			<!-- <a href="#" data-icon="arrow-l">返回</a> -->
			<div style="float:left;width:100px;"><span id="header-title">浙大一院</span></div>
			<ul class="nav nav-tabs" style="margin-bottom:0px;margin-left:100px;">
				<li class="active"><a href="<c:url value='/patient'/>" >检验报告</a></li>
				<li><a href="<c:url value='/request'/>" >申请解释</a></li>
				<%-- <li><a href="#page1" ><fmt:message key="nav.other" /></a></li> --%>
			</ul>
		</div>
		<div data-role="content">
			
			<p style="font-size:15px;line-height:25px;">
				<strong style="margin-right:18px;">姓名 : <c:out value="${patientName}" /></strong>
				<strong style="margin-right:18px;">性别 : <c:out value="${patientSex}" /></strong>
				<strong style="margin-right:18px;">年龄 : <c:out value="${patientAge}" /></strong>
				<strong>病历号 : <c:out value="${patientBlh}" /></strong>
			</p>
			<label class="checkbox">
				<input type="checkbox" id="startSms" <c:if test="${hasRequireSms}">checked</c:if> >开启检验报告的短信通知
			</label>
			
			<ul data-role="listview" data-inset="true" data-divider-theme="d">
			<c:forEach var="dateInfo" items="${dateInfoMap}">
				<li data-role="list-divider"><c:out value="${dateInfo.key}" /></li>
			    <c:forEach var="info" items="${dateInfo.value}">
			    	<li><a href="<c:url value='/record?id=${info.id}'/>"><c:out value="${info.examinaim}" /></a></li>
			    </c:forEach>
			</c:forEach>
			</ul>
		</div>
	</div>
	
	
</body>
</html>