<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="login.title"/></title><%-- 
    <meta name="heading" content="<fmt:message key='login.heading'/>"/> --%>
    <meta name="menu" content="Login"/>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/layout-1col.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap/css/bootstrap.min.css'/>" />
    <style type="text/css">
    .mini-layout {
	    border: 2px solid #DDDDDD;
	    border-radius: 10px 10px 10px 10px;
	    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.075);
	}
    </style>
</head>
<body id="login" >
<form method="post" id="loginForm" style="margin:30px;" action="<c:url value='/j_security_check'/>"
    onsubmit="saveUsername(this);return validateForm(this)">
<fieldset style="padding-top:20px;margin-bottom:100px;margin-top:30px;" class="mini-layout">
<ul>
<c:if test="${param.error != null}">
    <li class="error">
        <img src="${ctx}/images/iconWarning.gif" alt="<fmt:message key='icon.warning'/>" class="icon"/>
        <fmt:message key="errors.password.mismatch"/>
        <%--${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}--%>
    </li>
</c:if>
    <li>
       <label for="j_username" class="required" style="font-size:15px;margin-bottom:3px;">
            <fmt:message key="label.username"/> 
        </label>
        <input type="text" class="text" Placeholder="UserName" name="j_username" style="width:210px;" id="j_username" tabindex="1" />
    </li>

    <li>
        <label for="j_password" class="required" style="font-size:15px;margin-bottom:3px;">
            <fmt:message key="label.password"/> 
        </label>
        <input type="password" class="text"  Placeholder="Password" name="j_password" style="width:210px;" id="j_password" tabindex="2" />
    </li>

<c:if test="${appConfig['rememberMeEnabled']}">
    <li style="height:30px;line-height:30px;margin-top:2px;margin-bottom:2px;">
        <input type="checkbox"  style="float:left;margin-right:14px; width:20px;margin-left:3px;" name="_spring_security_remember_me" id="rememberMe" tabindex="3"/>
        <label for="rememberMe" class="muted"><fmt:message key="login.rememberMe"/></label>
    </li>
</c:if>
    <li>
        <button type="submit" class="button btn btn-info" style="width:224px;margin-bottom:20px;" name="login" tabindex="4" ><b><fmt:message key='button.login'/></b></button>
        <%-- <p style="font-size:12px;">
            <fmt:message key="login.signup">
                <fmt:param><c:url value="/signup"/></fmt:param>
            </fmt:message>
        </p> --%>
    </li>
</ul>
</fieldset>
</form>
<%@ include file="/scripts/login.js"%>

<script type="text/javascript" src="<c:url value='/styles/bootstrap/js/bootstrap.min.js'/>"></script>
</body>