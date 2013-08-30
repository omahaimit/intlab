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
		
		$("#submitSmsFilterBtn").click(function(){
			var smsStatus = $('input[name="smsStatus"]:checked').val();
			var fromAge = $("#ageFrom").val();
			var toAge = $("#ageTo").val();
			var sex = $('input[name="sexFilter"]:checked').val();
			var max = $("#maxSMSCount").val();
			var result = new Array();
			var nativeUrl = $("#nativeUrl").val();
			$('input[name="statusFilter"]:checked').each(function(){
				result.push($(this).val());
			});
			var enble = result.join(",");
			
			$.post("<c:url value='/admin/ajax/control/filter'/>", {
				sms : smsStatus,
				from : fromAge,
				to : toAge,
				sex : sex,
				max : max,
				enble : enble,
				native : nativeUrl
			}, function(result){
				if (result == 1) {
					alert("更新完成!")
				} else {
					
				}
			});
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

input, button, select, textarea {
	font-family: "Microsoft Yahei",Helvetica,Arial,sans-serif;
}

#SMS-filterControl small {
	margin-bottom:5px;
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
						<li><a href="<c:url value='/admin/patient'/>"><fmt:message key="admin.nav.patient" /></a></li>
						<li  class="active"><a href="#"><fmt:message key="admin.nav.systemControl" /></a></li>
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
					<!-- <li class="active"><a href="#SMS-startAndStop"><i class="icon-chevron-right"></i> 启动与关闭</a></li> -->
					<li class="active"><a href="#SMS-filterControl"><i class="icon-chevron-right"></i> 短信过滤控制</a></li>
				</ul>
			</div>
			<div class="span9" style="margin-top:-40px;">

				<section id="SMS-filterControl">
					<div class="page-header">
						<h1>1. 短信过滤控制</h1>
					</div>
					<div class="row-fluid">
						<blockquote>
							<small>是否开启短信发送功能</small>
							<div class="controls">
								<label class="radio inline">
									<input type="radio" name="smsStatus" value="0" <c:if test="${smsStatus == false}">checked</c:if>>关闭
								</label>
								<label class="radio inline">
									<input type="radio" name="smsStatus" value="1" <c:if test="${smsStatus == true}">checked</c:if>>启动
								</label>
							</div>
						</blockquote>
					</div>
					<div class="row-fluid">
						<blockquote>
							<small>年龄段筛选, 对所选年龄段进行信息发送</small>
							<div class="controls">
								<input type="text" id="ageFrom" value="${fromAge}">
								<input type="text" class="span1" value="至" disabled style="width:30px;">
								<input type="text" id="ageTo" value="${toAge}">
							</div>
						</blockquote>
					</div>
					<div class="row-fluid">
						<blockquote>
							<small>性别筛选, 对所选性别进行信息发送</small>
							<label class="radio inline">
								<input type="radio" name="sexFilter" value="0" <c:if test="${sex == 0}">checked</c:if>>所有
							</label>
							<label class="radio inline">
								<input type="radio" name="sexFilter" value="1" <c:if test="${sex == 1}">checked</c:if>>男性
							</label>
							<label class="radio inline">
								<input type="radio" name="sexFilter" value="2" <c:if test="${sex == 2}">checked</c:if>>女性
							</label>
						</blockquote>
					</div>
					<div class="row-fluid">
						<blockquote>
							<small>一日短信最大发送量, -1表示无限制</small>
							<input type="text" id="maxSMSCount" value="${maxCount}">
						</blockquote>
					</div>
					<div class="row-fluid">
						<blockquote>
							<small>患者标记状态, 仅对以下选中状态的患者进行短信推送</small>
							<label class="checkbox">
  								<input type="checkbox" name="statusFilter" <c:if test="${enble0}">checked</c:if> value="0"> 未发送过短信的患者
							</label>
							<label class="checkbox">
  								<input type="checkbox" name="statusFilter" <c:if test="${enble1}">checked</c:if> value="1"> 已发送过短信但未登录
							</label>
							<label class="checkbox">
  								<input type="checkbox" name="statusFilter" <c:if test="${enble2}">checked</c:if> value="2"> 登录过但未定制短信服务
							</label>
							<label class="checkbox">
  								<input type="checkbox" name="statusFilter" <c:if test="${enble3}">checked</c:if> value="3"> 定制了短信服务
							</label>
							<label class="checkbox">
  								<input type="checkbox" name="statusFilter" <c:if test="${enble4}">checked</c:if> value="4"> 安装app后，开启了消息推送功能
							</label>
						</blockquote>
					</div>
					<div class="row-fluid">
						<blockquote>
							<small>当前Patient所部属的URL</small>
							<input type="text" id="nativeUrl" class="span7" value="${nativeUrl}">
						</blockquote>
					</div>
					<div class="row-fluid" style="margin-bottom:100px;">
						<button id="submitSmsFilterBtn" class="btn btn-primary" type="button">提交过滤控制的变动</button>
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

