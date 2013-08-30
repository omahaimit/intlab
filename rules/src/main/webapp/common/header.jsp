<%@ include file="/common/taglibs.jsp"%>

<%-- <c:if test="${pageContext.request.locale.language ne 'en'}">
    <div id="switchLocale"><a href="<c:url value='/?locale=en'/>"><fmt:message key="webapp.name"/> in English</a></div>
</c:if> --%>

<div style="position: absolute;">
	<h1 style="font-size:24px;margin:0px;padding:0px;margin-left:50px;"><a href="<c:url value='/'/>"><fmt:message key="webapp.name"/></a></h1>
</div>

<%-- Put constants into request scope --%>
<appfuse:constants scope="request"/>