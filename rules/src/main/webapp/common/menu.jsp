<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="cssHorizontalMenu.vm" permissions="rolesAdapter">
<ul id="myTab" class="nav nav-tabs tabs-right" style="padding-left:200px;">

    <c:if test="${empty pageContext.request.remoteUser}"><li class="active"><a href="<c:url value="/login"/>"><fmt:message key="login.title"/></a></li></c:if>
    <menu:displayMenu name="IntelAuditAndExplain"/>
    <menu:displayMenu name="RulesManage"/>
    <menu:displayMenu name="QualityManage"/> 
    <menu:displayMenu name="AdminMenu"/>
    <menu:displayMenu name="UserMenu"/>
</ul>
</menu:useMenuDisplayer>
