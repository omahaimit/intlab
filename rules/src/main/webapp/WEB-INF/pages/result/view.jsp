<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="viewResult.title"/></title>
    <meta name="heading" content="<fmt:message key='viewResult.heading'/>"/>
    <meta name="menu" content="RulesManage"/>
    <script type="text/javascript">
    function deleteConfirm() {
		if (confirm('<fmt:message key="confirm.delete" />')) {
			location.href='../result/delete?id=<c:out value="${result.id}" />';
		}	
	}
    </script>
</head>
<style type="text/css">
table {
	font-size:14px;
}
td {
	padding: 10px;
	line-height:20px;
}
th {
	width:90px;
	padding: 10px;
}
</style>
<div>
<div style="width:430px;float:left;">
<table>
	<tr>
		<th style="padding-top:13px;" valign="top">
		<span style="word-spacing:24px;"><fmt:message key='result.content'/></span> :</th>
		<td><b><c:out value="${result.content}"/></b></td>
	</tr>
	<%-- <tr>
		<th><fmt:message key='result.level'/>:</th>
		<td><c:out value="${result.level}"/></td>
	</tr> --%>
	<tr>
		<th><span style="word-spacing:24px;"><fmt:message key='result.category'/></span> :</th>
		<td><c:out value="${result.category}"/></td>
	</tr>
	<%-- <tr>
		<th><fmt:message key='result.reject'/>:</th>
		<td><c:out value="${result.reject}"/></td>
	</tr> --%>
	<tr>
		<th><span style="word-spacing:3px;"><fmt:message key='result.percent'/></span> :</th>
		<td><c:out value="${result.percent}"/></td>
	</tr>
	<tr>
		<th><span style="word-spacing:3px;"><fmt:message key='result.createBy'/></span> :</th>
		<td><c:out value="${result.createUser.username}"/></td>
	</tr>
	<tr>
		<th><fmt:message key='result.createTime'/> :</th>
		<td><c:out value="${result.createTime}"/></td>
	</tr>
	<tr>
		<th><span style="word-spacing:3px;"><fmt:message key='result.modifyBy'/></span> :</th>
		<td><c:out value="${result.modifyUser.username}"/></td>
	</tr>
	<tr>
		<th><fmt:message key='result.modifyTime'/> :</th>
		<td><c:out value="${result.modifyTime}"/></td>
	</tr>
	<tr>
	<th></th>
	<td>
	<c:if test="${canEdit}">
		<button id="editResultnav" class="btn"
		onclick="location.href='<c:url value="/result/edit?id=${result.id}"/>'">
		<fmt:message key="button.edit"/></button>
		
		<button id="deleteResultnav" class="btn"
		onclick="javascipt:deleteConfirm()">
		<fmt:message key="button.delete"/></button>
	</c:if>
		<button id="returnBtn" class="btn"
		onclick="javascript:history.go(-1)">
		<fmt:message key="button.return"/></button>
	</td>
	</tr>
</table>
</div>

<div style="width: 180px;margin-right:70px; float: right;background-color: white;border: 1px solid #E1E1E1;padding: 5px 0px 10px 0px;" class="border-radius-6 border-shadow"">
	<h4 style="padding-left:14px;font-family:Microsoft YaHei;">
		<fmt:message key="rule.relate" />
	</h4>
	<div style="border-top: 1px solid #E1E1E1;min-height:180px; ">
		<c:forEach var="rule" items="${rulesList}">
			<div class="tag-section">
				<a class="selbtn" style="width:180px;" href="../rule/view?id=<c:out value="${rule.id}"/>"> 
				 	<span class="tag-name"><c:out value="${rule.name}" /></span>
			</a></div>
		</c:forEach>
	</div>
</div>
</div>
