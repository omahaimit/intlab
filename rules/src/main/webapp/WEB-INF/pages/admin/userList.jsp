<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userList.title"/></title>
    <meta name="heading" content="<fmt:message key='userList.heading'/>"/>
    <meta name="menu" content="AdminMenu"/>
</head>

<div id="search">
<form method="get" action="${ctx}/admin/users" id="searchForm">
<div class="input-append">
    <input type="text" class="span3" name="q" id="query" value="${param.q}"
           placeholder="<fmt:message key='userList.search'/>"/>
    <button type="submit" class="btn"><fmt:message key="button.search"/></button>
 </div>
</form>
</div>

<button type="button" class="btn" style="margin-right: 5px"
    onclick="location.href='<c:url value="/userform?method=Add&from=list"/>'"><fmt:message key="button.add"/></button>


<display:table name="userList" cellspacing="0" cellpadding="0" requestURI="" 
    defaultsort="1" id="users" pagesize="10" class="table">
    <display:column property="username" escapeXml="true" sortable="true" titleKey="user.username" style="width: 20%"
        url="/userform?from=list" paramId="id" paramProperty="id"/>
    <display:column property="fullName" escapeXml="true" sortable="true" titleKey="activeUsers.fullName" style="width: 25"/>
    <display:column property="email" sortable="true" titleKey="user.email" style="width: 25%" autolink="true" media="html"/>
    <display:column property="email" titleKey="user.email" media="csv xml excel pdf"/>
    <display:column sortProperty="enabled" sortable="true" titleKey="user.enabled" style="padding-left: 15px" media="html">
        <input type="checkbox" disabled="disabled" <c:if test="${users.enabled}">checked="checked"</c:if>/>
    </display:column>
    <display:column property="role" sortable="true" titleKey="role" />
</display:table>
