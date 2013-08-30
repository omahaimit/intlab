<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="bagList.title"/></title>
    <meta name="heading" content="<fmt:message key='bagList.heading'/>"/>
    <meta name="menu" content="AdminMenu"/>
</head>

<display:table name="bagList" cellspacing="0" cellpadding="0" requestURI="" id="bags" pagesize="20" class="table">
	<display:column property="id" titleKey="ID" />
	<display:column property="name" sortable="true" titleKey="Name" escapeXml="false"
    	url="/admin/download/export" paramId="id" paramProperty="id" />
</display:table>