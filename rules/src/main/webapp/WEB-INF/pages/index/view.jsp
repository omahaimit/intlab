<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="viewIndex.title" /></title>
<meta name="heading" content="<fmt:message key='viewIndex.heading'/>" />
<meta name="menu" content="RulesManage" />
<style type="text/css">
td {
	padding: 7px;	
}
th {
	width:85px;
	padding: 7px;
}
</style>
<script type="text/javascript">
$(function() {
	$("#hospId").html($("#hospSelect").eq(0).val());
});

function deleteConfirm() {
	if (confirm('<fmt:message key="confirm.delete" />')) {
		location.href='../index/delete?id=<c:out value="${index.id}" />';
	}	
}

function HispitalSelected(obj) {
	for (var i=0;i<obj.length;i++) {
		if (obj.options[i].selected) {
			$("#hospId").html(obj.options[i].value);
		}
	}
}
</script>
</head>

<div>
	<div style="width:410px;margin-left:10px;float: left; ">
		<table>
			<tr>
				<th class="left"><fmt:message key="index.indexId" /> :</th>
				<td><c:out value="${index.indexId}" /></td>
			</tr>
			<tr>
				<th class="left"><span style="word-spacing:24px;"><fmt:message key="index.hospital" /></span> :</th>
				<td><select id="hospSelect" style="width:100px;" onchange="return HispitalSelected(this);">
					<c:forEach var="map" items="${index.idMap}">
						<option value="<c:out value="${map.hospitalID}"/>">
							<c:out value="${map.hospital}"/>
						</option>
					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.hospitalId" /> :</th>
				<td id="hospId"></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.name" /> :</th>
				<td><c:out value="${index.name}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.sampleFrom" /> :</th>
				<td><c:out value="${index.sampleFrom}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.unit" /> :</th>
				<td><c:out value="${index.unit}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.type" /> :</th>
				<td><c:out value="${index.type}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="index.algorithm" /> :</th>
				<td><c:out value="${index.algorithm}" /></td>
			</tr>
			<tr>
				<th class="left"  valign="top"><fmt:message key="index.description" /> :</th>
				<td><p style="line-height:150%;"><c:out value="${index.description}" /></p></td>
			</tr>
			<tr>
				<th class="left"><span style="letter-spacing:6px;"><fmt:message key="rule.createBy" /></span>:</th>
				<td><c:out value="${index.createUser.username}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="rule.createTime" /> :</th>
				<td><c:out value="${index.createTime}" /></td>
			</tr>
			<tr>
				<th class="left" ><span style="letter-spacing:6px;"><fmt:message key="rule.modifyBy" /></span>:</th>
				<td><c:out value="${index.modifyUser.username}" /></td>
			</tr>
			<tr>
				<th class="left"><fmt:message key="rule.modifyTime" /> :</th>
				<td><c:out value="${index.modifyTime}" /></td>
			</tr>
			<tr>
				<th></th>
				<td>
				<c:if test="${canEdit}">
					<button id="editBtn" class="btn"
					onclick="location.href='../index/edit?id=<c:out value="${index.id}" />'">
					<fmt:message key="button.edit" /></button>
					
					<button id="deleteBtn" class="btn" 
					onclick="javascipt:deleteConfirm()">
					<fmt:message key="button.delete" /></button>
				</c:if>
					<button id="returnBtn" class="btn" 
					onclick="javascript:history.go(-1)">
					<fmt:message key="button.return" /></button>
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
					<a class="selbtn" style="width:180px;" href="../rule/view?id=<c:out value="${rule.key}"/>"> 
					 	<span class="tag-name"><c:out value="${rule.value}" /></span>
				</a></div>
			</c:forEach>
		</div>
	</div>
</div>

