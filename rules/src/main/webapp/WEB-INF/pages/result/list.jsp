<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="resultList.title"/></title>
    <meta name="heading" content="<fmt:message key='resultList.heading'/>"/>
    <meta name="menu" content="RulesManage"/>
    <script>
       	$(function() {
       		$("#addbutton").button();
		});
	</script>
	<style type="text/css">
	table td {
		height:20px;
	}
	</style>
</head>

<div style="margin-bottom: 50px;display:none;">
	<div style="width:400px;float:left">
		<%-- <fmt:message key="resultList.categorySelect"/>
		<select class="first" id="first" style="width:100px;">
		</select> --%>
	</div>
	<div style="width:120px;float:right">
		<button id="addbutton" style="width:120px;height:30px;" onclick="location.href='<c:url value="/result/add"/>'">
			<fmt:message key="result.add"/>
		</button>
	</div>
</div>

<display:table name="resultList" cellspacing="0" cellpadding="0" requestURI="/result/list" 
    id="results" defaultsort="4" defaultorder="ascending" class="table" partialList="true" sort="external"  
    size="${resultList.fullListSize}" pagesize="${resultList.objectsPerPage}">
    <display:column property="content" maxLength="25" sortable="true" titleKey="result.content" style="width: 50%"
    	url="/result/view" paramId="id" paramProperty="id"/>
    <display:column property="category" sortable="true" titleKey="result.category" />
    <display:column property="createUser.username" sortable="true" titleKey="result.createBy" />
    <display:column property="modifyTime" format="{0,date,yyyy-MM-dd hh:mm:ss}" sortable="true" titleKey="result.modifyTime" style="width: 23%;" />
</display:table>

<script type="text/javascript">
    highlightTableRows("results");
</script>