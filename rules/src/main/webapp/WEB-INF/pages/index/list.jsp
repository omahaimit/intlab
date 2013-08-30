<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="indexList.title"/></title>
    <meta name="heading" content="<fmt:message key='indexList.heading'/>"/>
    <meta name="menu" content="RulesManage"/>
    <script>
       	$(function() {
       		$("#sample-select").change(function() {
       			var v = $("#sample-select").val();
       			location.href="../index/list?sample="+encodeURIComponent(v);
       		});
       		$("#searchButton").click(function() {
       			var search = $("#searchText").val();
       			location.href="../index/list?text="+search;
       		});
       		
       		$("#searchText").bind('keyup', function(event){
       		    if (event.keyCode=="13") {
       		    	var search = $("#searchText").val();
           			location.href="../index/list?text="+search;
       		    }
       		 });
       		
       		$("#sample-select").val('${sample}');
       		
       		$("#addbutton").click(function(){
       			self.location='<c:url value="/index/add"/>';
       		});
		});
	</script>
	<style type="text/css">
	table td {
		height:20px;
	}
	</style>
</head>

<div class="form-inline" style="margin-bottom:50px;">
	<div class="span3" style="margin-left:0px;">
	<label class="">
	<b><fmt:message key="index.option.one"/></b>
	</label>
	<select id="sample-select" style="width:120px;">
		<option value=""><fmt:message key="index.all"/></option>
		<c:forEach var="s" items="${sampleList}">
			<option value='<c:out value="${s.key}" escapeXml="false"/>'><c:out value="${s.value}" /></option>
		</c:forEach>
	</select>
	</div>
	<div class="input-append span4">
		<input id="searchText" class="span3" type="text" value="${searchText}" placeholder="<fmt:message key='search.index.holder'/>"/>
		<button id="searchButton" class="btn" ><fmt:message key="search"/></button>
	</div>
	<div class="span2">
	<button id="addbutton" class="btn span2" ><fmt:message key="index.add"/></button>
	</div>
</div>

<display:table name="indexList" cellspacing="0" cellpadding="0" requestURI="/index/list" 
    id="indexs" class="table" partialList="true" sort="external"  
    size="${indexList.fullListSize}" pagesize="${indexList.objectsPerPage}">
    <display:column property="name" sortable="true" titleKey="index.name" style="width: 35%"
    	url="/index/view" paramId="id" paramProperty="id"/>
    <display:column property="indexId" sortable="true" titleKey="index.indexId" />
    <display:column property="sampleFrom" sortable="true" titleKey="index.sampleFrom" />
    <display:column property="ruleCount" titleKey="index.ruleCount" />
</display:table>
