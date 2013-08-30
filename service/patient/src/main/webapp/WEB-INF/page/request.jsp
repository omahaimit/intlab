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
</head>
<body>
	<div data-role="page" id="page1">
		<div data-role="header" data-position="fixed">
			<!-- <a href="#" id="returnBtn" data-icon="arrow-l" style="text-decoration: none;">返回</a> -->
			<div style="float:left;width:100px;"><span id="header-title">浙大一院</span></div>
			<ul class="nav nav-tabs" style="margin-bottom:0px;">
				<li><a href="<c:url value='/patient'/>" >检验报告</a></li>
				<li class="active"><a href="<c:url value='/request'/>" >申请解释</a></li>
			</ul>
		</div>
		<div data-role="content">
			<div>
				<p style="color:#ff6666;">本功能尚处于测试阶段，如果有医生回复，将以短信形式通知。</p>
			</div>
			<div>
				<h4>留言</h4>
				<textarea id="reqestText" rows="5"></textarea>
				<button class="btn" id="submitRequestBtn" >提交请求</button>
				<script type="text/javascript">
					$("#submitRequestBtn").click(function() {
						$.post("<c:url value='/ajax/request' />", {content : $("#reqestText").val()}, function(data){
							if (data == 1) {
								alert("申请完成");
								location.href = "<c:url value='/request' />";
							} else {
								alert("失败");
							}
						});
					});
				</script>
			</div>
			<div>
				<h4>申请记录</h4>
				<c:forEach var="request" items="${requests}">
		    	<div class="row-fluid">
					<blockquote>
						<small><c:out value="${request.requestContent}" /> - <fmt:formatDate value="${request.requestTime}" pattern="yyyy-MM-dd HH:mm" /></small>
						<div class="controls">
							<c:out value="${request.respondContent}" />
							<c:if test="${request.respondMethod == 0}">
								<span style="color:#ff2222;">还未答复</span>
							</c:if>
						</div>
					</blockquote>
				</div>
				</c:forEach>
			</div>
		</div>
		
	</div>
</body>
</html>