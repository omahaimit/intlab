<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>hello</title>
<link rel="stylesheet"
	href="<c:url value='/css/themes/default/jquery.mobile-1.3.1.min.css'/>" />
<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.mobile-1.3.1.js'/>"></script>

</head>
<body>
	<div data-role="page" id="page1">
		<div data-role="navbar" data-iconpos="right">
			<ul>
				<li><a href="#page1" data-transition="fade" data-theme="c"
					data-icon="" class="ui-btn-active"><fmt:message key="nav.report" /></a></li>
				<li><a href="#page1" data-transition="fade" data-theme="c"
					data-icon=""><fmt:message key="nav.advice" /></a></li>
				<li><a href="#page1" data-transition="slidefade"
					data-theme="c" data-icon=""><fmt:message key="nav.other" /></a></li>
			</ul>
		</div>
		<div data-role="content">
			<div>
				<span><fmt:message key="patient.name"/>:<c:out value="${patientName}" /></span>
				<span><fmt:message key="patient.examine"/>:<c:out value="${patientExamine}" /></span>
				<span><fmt:message key="patient.date"/>:<c:out value="${patientDate}" /></span>
			</div>
			<table data-role="table" id="movie-table" data-mode="columntoggle"
				class="ui-responsive table-stroke">
				<thead>
					<tr>
						<th data-priority="persist">Name</th>
						<th data-priority="persist">Result</th>
						<th data-priority="3">Scope</th>
						<th data-priority="4">Unit</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${testResult}" var="test">
					<tr>
						<td><a href="#"><c:out value="${test.name}" /></a></td>
						<td><c:out value="${test.testResult}" /></td>
						<td><c:out value="${test.refLo}" />-<c:out value="${test.refHi}" /></td>
						<td><c:out value="${test.unit}" /></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>