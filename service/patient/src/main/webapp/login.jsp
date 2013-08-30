<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">
<head>
    <title>浙江大学附属第一医院检验报告查阅系统</title>
    <meta name="menu" content="Login"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<c:url value='/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/bootstrap/css/bootstrap-responsive.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/bootstrap/css/doc.css'/>" rel="stylesheet">
        <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
</head>
<body>
	<form method="post" class="form-signin" action="<c:url value='j_spring_security_check'/>">
		<h3 style="text-align:center">检验报告查阅</h3>
		<label for="j_username" class="required">就诊卡号</label> 
		<input type="text" class="text" name="j_username" tabindex="1" value="<c:out value='${param.id}' />"/>
		<label for="j_password" class="required">本人身份证后六位</label>
		<input type="password" class="text" name="j_password" tabindex="2" />
		<div>
		<button type="submit" class="btn btn-info btn-large" style="width:225px;" name="login" tabindex="4">
			登录
		</button>
		<div style="padding-top:10px;">
			<a href="<c:url value='/leave/message'/>" >如有任何问题请留言</a>
		</div>
		</div>
	</form>
</body>