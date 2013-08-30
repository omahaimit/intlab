<!DOCTYPE html>

<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
	<head>
        <%@ include file="/common/meta.jsp" %>
        <title><decorator:title/> | <fmt:message key="webapp.name"/></title>

        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/theme.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>" />
        <link rel="stylesheet" type="text/css" href="<c:url value='/styles/jquery.jqplot.css'/>" />
		<script type="text/javascript" src="<c:url value='/scripts/jquery-1.8.2.min.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/jquery.jqplot.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/excanvas.min.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/jqplot.dateAxisRenderer.min.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/jqplot.cursor.min.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/jqplot.highlighter.min.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/jqplot.json2.min.js'/>"></script>
    	<script type="text/javascript" src="<c:url value='/scripts/jquery-ui-1.9.1.custom.min.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/jquery.bgiframe-2.1.2.js'/> "></script>
			
		<link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap/css/bootstrap.css'/>" />
		<!--[if lte IE 6]>
  		<link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap/css/bootstrap-ie6.css'/>" />
  		<link rel="stylesheet" type="text/css" href="<c:url value='/styles/bootstrap/css/ie.css'/>" />
  		<![endif]-->

		<link rel="stylesheet" type="text/css" href="<c:url value='/styles/custom-theme/jquery-ui-1.10.0.custom.css'/>" />
        <decorator:head/>
   </head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>

    <div id="page">
        <div id="header" class="clearfix" align="left" style="margin-bottom:48px;">
            <jsp:include page="/common/header.jsp"/>
            <c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>
			<div style="position:absolute;width:990px;z-index:1000;font-weight:700;">
			<jsp:include page="/common/menu.jsp"/>
			</div>
        </div>
        
        <div id="content" class="clearfix">
            <div id="main">
                <%@ include file="/common/messages.jsp" %>
                <%-- <h1><decorator:getProperty property="meta.heading"/></h1> --%>
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