<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="scripts/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#startOrstop").click(function() {
			if ($("#cFlag").html() == "false") {
				$.post("ajax/operate.htm", {
					op : "start",
					date : $("#date").val(),
					interval : $("#interval").val()
				}, function(data) {
					if (data == "true") {
						$("#startOrstop").val("关闭适配器");
						$("#cFlag").html("true");
					} else {
						alert("error!");
					}
				});
			} else {
				$.post("ajax/operate.htm", {
					op : "stop"
				}, function(data) {
					if (data == "true") {
						$("#startOrstop").val("启动适配器");
						$("#cFlag").html("false");
					}
				});
			}
		});
		
		$("#mStartOrstop").click(function() {
			if ($("#mFlag").html() == "false") {
				$.post("ajax/measure.htm", {
					op : "start",
					interval : $("#measureInterval").val()
				}, function(data) {
					if (data == "true") {
						$("#mStartOrstop").val("关闭适配器");
						$("#mFlag").html("true");
					} else {
						alert("error!");
					}
				});
			} else {
				$.post("ajax/measure.htm", {
					op : "stop"
				}, function(data) {
					if (data == "true") {
						$("#mStartOrstop").val("启动适配器");
						$("#mFlag").html("false");
					}
				});
			}
		});
		
		$("#writeBackBtn").click(function() {
			if ($("#wFlag").html() == "false") {
				$.post("ajax/writeBack.htm", {
					op : "start",
					date : $("#writeBackDate").val(),
					interval : $("#writeBackInterval").val()
				}, function(data) {
					if (data == "true") {
						$("#writeBackBtn").val("关闭适配器");
						$("#wFlag").html("true");
					} else {
						alert("error!");
					}
				});
			} else {
				$.post("ajax/writeBack.htm", {
					op : "stop"
				}, function(data) {
					if (data == "true") {
						$("#writeBackBtn").val("启动适配器");
						$("#wFlag").html("false");
					}
				});
			}
		});
		
		$("#startOnce").click(function() {
			$.post("ajax/syncOnce.htm", {
				start : $("#startDate").val(),
				end : $("#endDate").val()
			}, function(data) {
				alert("已启动");
			});

		});
		
		$("#syncUser").click(function(){
			$.get("ajax/userinfo.htm", function(data) {
				alert("新增用户" + data + "个！");
			});
		});
	});
</script>
</head>
<body>
<div id="row1" class="row">
	<div class="span4">
		<h4>循环同步</h4>
		<div class="input-prepend">
			<span class="add-on">同步日期</span> <input class="span3" id="date" type="text" value="${date}" placeholder="${date}">
		</div>
		<div class="input-prepend input-append">
			<span class="add-on">时间间隔</span> 
			<input class="span3" id="interval" type="text" value="${interval}" placeholder="${interval}">
			<span class="add-on">秒</span>
		</div>

		<input id="startOrstop" class="btn btn-success offset1" type="button" value="${syncStart}适配器" />
		<div style="display: none;" id="cFlag">${currentFlag}</div>
	</div>
	
	<div class="offset1 span3">
		<h4>单次同步</h4>
		<div class="input-prepend">
			<span class="add-on">起始日期</span> <input class="span3" id="startDate" type="text" value="${date}" placeholder="${date}">
		</div>
		<div class="input-prepend">
			<span class="add-on">结束日期</span> <input class="span3" id="endDate" type="text" value="${date}" placeholder="${date}">
		</div>
		<input id="startOnce" class="btn btn-info offset1" type="button" value="单次启动" />
	</div>
</div>
<div id="row2" class="row">
	<div class="span4">
		<h4>数据写回</h4>
		<div class="input-prepend">
			<span class="add-on">同步日期</span> <input class="span3" id="writeBackDate" type="text" value="${date}" placeholder="${date}">
		</div>
		<div class="input-prepend input-append">
			<span class="add-on">时间间隔</span> 
			<input class="span3" id="writeBackInterval" type="text" value="${writeInterval}" placeholder="${writeInterval}">
			<span class="add-on">秒</span>
		</div>

		<input id="writeBackBtn" class="btn btn-warning offset1" type="button" value="${writeStart}适配器" />
		<div style="display: none;" id="wFlag">${writeBackFlag}</div>
	</div>
	
	<div class="offset1 span3">
		<h4>MeasureTime同步</h4>
		<div class="input-prepend input-append">
			<span class="add-on">时间间隔</span> 
			<input class="span2" id="measureInterval" type="text" value="${measureInterval}" placeholder="${measureInterval}">
			<span class="add-on">秒</span>
		</div>
		<input id="mStartOrstop" class="btn btn-success offset1" type="button" value="${mSyncStart}适配器" />
		<div style="display: none;" id="mFlag">${measureFlag}</div>
	</div>
</div>
<div class="row">
	<input id="syncUser" type="button" class="btn" value="同步用户" />
</div>
</body>
</html>

