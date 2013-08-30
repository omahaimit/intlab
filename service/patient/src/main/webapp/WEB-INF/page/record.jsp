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
<script type="text/javascript" src="<c:url value='/js/jquery.mobile-1.3.1.min.js'/>"></script>
<link href="<c:url value='/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
<style type="text/css">
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
#header-title {
	font-size:18px;
	margin:9px;
	line-height:38px;
	color : #eee;
}
</style>
</head>
<body>
	<div data-role="page" id="page1">
		<div data-role="header" data-position="fixed">
			<!-- <a href="#" id="returnBtn" data-icon="arrow-l" style="text-decoration: none;">返回</a> -->
			<div style="float:left;width:100px;"><span id="header-title">浙大一院</span></div>
			<ul class="nav nav-tabs" style="margin-bottom:0px;">
				<li class="active"><a href="<c:url value='/patient'/>" >检验报告</a></li>
				<li><a href="<c:url value='/request'/>" >申请解释</a></li>
				<%-- <li><a href="#page1" ><fmt:message key="nav.advice" /></a></li>
				<li><a href="#page1" ><fmt:message key="nav.other" /></a></li> --%>
			</ul>
		</div>
		<div data-role="content" style="padding:5px;">
			
			<div style="padding:5px;">
				<p style="margin-bottom:5px;">
					<strong><c:out value="${patientExamine}" /></strong>
					<strong style="margin-right:10px;"><c:out value="${patientDate}" /></strong>
				</p>
				<div>
					<div style="float:left;">病历号<c:out value="${patientBlh}" /></div>
					<div style="float:right;">No:<c:out value="${sampleNo}" /></div>
				</div>
				<div style="margin-top:28px;padding-top:4px;padding-bottom:4px;border-top: 1px solid #ddd;border-bottom: 1px solid #ddd;">
					<span>病人姓名 </span>
					<strong style="margin-right:24px;"><c:out value="${patientName}" /></strong>
					<span style="margin-right:24px;">性别 <c:out value="${patientSex}" /></span>
					<span style="margin-right:24px;">年龄 <c:out value="${patientAge}" /></span>
					<span style="margin-right:13px;">样本 <c:out value="${sampleType}" /></span>
					<span style="margin-right:13px;">诊断 <c:out value="${diagnostic}" /></span>
					<span>备注 </span>
				</div>
			</div>
			<table data-role="table" id="movie-table" data-mode="columntoggle"
				class="ui-responsive table-stroke">
				<thead>
					<tr>
						<th data-priority="persist"><fmt:message key="test.project"/></th>
						<th data-priority="persist"><fmt:message key="test.result"/></th>
						<th data-priority="persist"><fmt:message key="test.scope"/></th>
						<th data-priority="2"><fmt:message key="test.unit"/></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${testResult}" var="test">
					<tr>
						<td><a href="#"><c:out value="${test.name}" /></a></td>
						<td style="width:50px;">
							<div>
								<span class="result_span"><c:out value="${test.testResult}" /></span>
								<font color="red"><c:out value="${test.hasHigh}" /></font>
							</div>
						</td>
						<td style="width:80px;"><c:out value="${test.refLo}" />-<c:out value="${test.refHi}" /></td>
						<td><c:out value="${test.unit}" /></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>