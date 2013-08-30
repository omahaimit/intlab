<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css" />
<decorator:head />
</head>
<body style="background-color: transparent;font-family:Microsoft YaHei;" <decorator:getProperty property="body.id" writeEntireProperty="true"/>
	<decorator:getProperty property="body.class" writeEntireProperty="true"/>>

	<div id="page" >
		<!-- <div class="navbar">
			<div class="navbar-inner">
				<a class="brand" href="#">智能检验数据适配器</a>
				<ul class="nav">
				</ul>
			</div>
		</div> -->
		
		<div id="content" >
			<div id="main" class="container">
				<decorator:body />
			</div>
		</div>
		<div id="footer">
		</div>
	</div>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
