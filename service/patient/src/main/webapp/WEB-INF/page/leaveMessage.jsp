<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">
<head>
<title>浙江大学附属第一医院检验报告查阅系统</title>
<meta name="menu" content="Login" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="<c:url value='/css/themes/default/jquery.mobile-1.3.1.min.css'/>" />
<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.mobile-1.3.1.js'/>"></script>
<link href="<c:url value='/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
</head>
<body>
	<div style="max-width: 300px;padding: 19px 29px 29px;margin: 0 auto 20px;">
		<div><h4>留言</h4></div>
		<div><input type="text" id="titleText" Placeholder="标题" /></div>
		<div>
			<textarea id="contentText" Placeholder="内容"></textarea>
		</div>
		<div>
			<button class="btn" id="submitMessage">提交</button>
		</div>
		<script type="text/javascript">
			$("#submitMessage").click(function(){
				$.post("<c:url value='/leave/ajax/submit'/>", {
					title : $("#titleText").val(),
					content : $("#contentText").val()
				}, function(data){
					if (data == 1){
						alert("提交成功！");
						location.href = "<c:url value='/login' />";
					} else {
						alert("提交失败！")
					}
				});
			});
		</script>
	</div>
</body>