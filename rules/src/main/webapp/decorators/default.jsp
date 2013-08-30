<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <%@ include file="/common/meta.jsp" %>
        <title><decorator:title/> | <fmt:message key="webapp.name"/></title>

        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/theme.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value='/styles/jquery.jqplot.css'/>" />
		<%-- <link rel="stylesheet" type="text/css" href="<c:url value='/styles/excite-bike/jquery-ui-1.9.1.custom.min.css'/>" />
         --%><%-- <script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/scriptaculous.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script> --%>
        <script type="text/javascript" src="<c:url value='/scripts/jquery-1.8.2.min.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/jquery-ui-1.9.1.custom.min.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/jquery.jqplot.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/jqplot.dateAxisRenderer.min.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/jqplot.json2.min.js'/>"></script>
        
        <link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap/css/bootstrap.css'/>" />
		<!--[if lte IE 6]>
  		<link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap/css/bootstrap-ie6.css'/>" />
  		<![endif]-->
  		<!--[if lte IE 7]>
  		<link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap/css/ie.css'/>" />
  		<![endif]-->

		<link rel="stylesheet" type="text/css" href="<c:url value='/styles/custom-theme/jquery-ui-1.10.0.custom.css'/>" />
        <style>
        h1 {
        	font-size:28px;
        }
        *{
			font-family:Microsoft YaHei; 
		}
        </style>
        <decorator:head/>
    </head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>

    <div id="page">
        <div id="header" class="clearfix" align="left" style="margin-bottom:50px;">
            <jsp:include page="/common/header.jsp"/>
            <c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>
			<div style="position:absolute;width:990px;z-index:1000;font-weight:700;">
			<jsp:include page="/common/menu.jsp"/>
			</div>
        </div>

        <div id="content" class="clearfix">
            <div id="main">
                <%@ include file="/common/messages.jsp" %>
                <h1><decorator:getProperty property="meta.heading"/></h1>
                <decorator:body/>
            </div>

        </div>

        <div id="footer" class="clearfix">
            <jsp:include page="/common/footer.jsp"/>
        </div>
    </div>
    <!--[if lte IE 6]>
	<script type="text/javascript">
  	$(".btn").each(function(){
  		$(this).removeClass("btn");
  	});
  	$(".btn-primary").each(function(){
  		$(this).removeClass("btn-primary");
  	});
  	$(".btn-success").each(function(){
  		$(this).removeClass("btn-success");
  	});
  	</script>
  	<![endif]-->
    <script type="text/javascript" src="<c:url value='/styles/bootstrap/js/bootstrap.js'/>"></script>
    
</body>
</html>
