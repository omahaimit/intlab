<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="viewRule.title"/></title>
    <meta name="heading" content="<fmt:message key='viewRule.heading'/>"/>
    <meta name="menu" content="RulesManage"/>
    <script type="text/javascript" src="../scripts/jquery.jstree.js"></script>
    <script>
    	function deleteConfirm() {
    		if (confirm('<fmt:message key="confirm.delete" />')) {
    			location.href='../rule/delete?id=<c:out value="${rule.id}" />';
    		}	
    	}
    
       	$(function() {
       		$.jstree._themes = "../styles/themes/";
       		
			$("#ruleTree").jstree({ 
				"json_data" : {
					
					"ajax" : {
						"url" : '../rule/ajax/getRule?id=<c:out value="${rule.id}"/>'
					}
				},
				"themes":{
		            "theme" : "classic",
		            "dots" : true,
		            "icons" : true
		        },
				"plugins" : [ "themes", "json_data", "ui"]
			}).bind("loaded.jstree", function (e, data) {
                data.inst.open_all(-1); // -1 opens all nodes in the container
            });
			
			$("#editBtn").button();
			$("#deleteBtn").button();
			$("#returnBtn").button();
			$("#ruleTestDialog").dialog({
				autoOpen: false,
			    width: 400,
			    height: 300
			});
			$("#textBtn").click(function() {
				$("#testDataPanel").empty();
				$.get("../rule/ajax/testData",{id:<c:out value="${rule.id}"/>},function(data){
					$("#testDataPanel").append("<table>");
					var content = "<table style='padding:0px;'>";
					var array = jQuery.parseJSON(data);
					for (var i=0 ; i < array.length ; i++) {
						$("#testDataPanel").append("<tr><td>"+array[i].name+"</td><td><input value='"
								+ array[i].value + "'/></td><td>" + array[i].unit + "</td></tr>");
					}
					$("#testDataPanel").append("</table>");
				});
				$("#ruleTestDialog").dialog("open");
			});
			
			var select = <c:out value="${rule.type}" />;
			if(select == 1) {
				$("#algorithm_tr").css('display', 'table-row');
				$("#hospitalmode_tr").css('display', 'table-row');
			}
       	});
	</script>
    <style type="text/css">
    #ruleViewTable table {
    	font-size:14px;
    }
	#ruleViewTable td {
		padding: 10px;	
	}
	#ruleViewTable th {
		float:right;
		width: 80px;
		padding: 10px;
	}
	#ruleViewTable tr {
		line-height:17px;
	}
	#testDataPanel input {
		width:50px;
	}
	</style>
</head>

<div>
	<div style="float:left;">
	<table id="ruleViewTable">
		<tr>
			<th class="left"><fmt:message key="rule.name" /> :</th>
			<td><b><c:out value="${rule.name}" /></b></td>
		</tr>
		<tr>
			<th class="left" valign="top"><fmt:message key="rule.content" /> :</th>
			<td><div id="ruleTree" class="border-radius-6 border-shadow" style="padding:10px;"></div></td>
		</tr>
		<tr>
			<th class="left" style="padding-top:15px;" valign="top"><fmt:message key="rule.result" /> :</th>
			<td><p>
				<c:forEach var="result" items="${rule.results}">
					<a href="../result/view?id=${result.id}"><c:out value="${result.content}" /></a>
					<c:if test="${result.percent != null }"> <c:out value="${result.percent}" /></c:if>
					<br/>
				</c:forEach>
			</p></td>
		</tr>
		<tr>
			<th class="left" style="padding-top:15px;padding-bottom:15px;" valign="top"><fmt:message key="rule.category" /> :</th>
			<td><p>
				<c:forEach var="bag" items="${rule.bags}">
					<a href="../rule/list?bag=${bag.id}"><c:out value="${bag.name}" /></a><br/>
				</c:forEach>
			</p></td>
		</tr>
		<tr>
			<th class="left"><span style="letter-spacing:6px;"><fmt:message key="rule.credibility" /></span>:</th>
			<td><c:out value="${rule.credibility}" /></td>
		</tr>
		<tr>
			<th class="left"><fmt:message key="rule.type" /> :</th>
			<td><c:out value="${rule.typeName}" /></td>
		</tr>
		<tr id="algorithm_tr" style="display:none;">
			<th class="left"><fmt:message key="rule.algorithm" /> :</th>
			<td><c:out value="${rule.algorithmName}" /></td>
		</tr>
		<tr id="hospitalmode_tr" style="display:none;">
			<th class="left"><fmt:message key="rule.hospitalmode" /> :</th>
			<td><c:out value="${rule.mode}" /></td>
		</tr>
		<tr>
			<th class="left"><span style="letter-spacing:6px;"><fmt:message key="rule.createBy" /></span>:</th>
			<td><c:out value="${rule.createUser.username}" /></td>
		</tr>
		<tr>
			<th class="left"><fmt:message key="rule.createTime" /> :</th>
			<td><c:out value="${rule.createTime}" /></td>
		</tr>
		<tr>
			<th class="left"><span style="letter-spacing:6px;"><fmt:message key="rule.modifyBy" /></span>:</th>
			<td><c:out value="${rule.modifyUser.username}" /></td>
		</tr>
		<tr>
			<th class="left"><fmt:message key="rule.modifyTime" /> :</th>
			<td><c:out value="${rule.modifyTime}" /></td>
		</tr>
		<tr>
			<th class="left" valign="top"><fmt:message key="rule.description" /> :</th>
			<td><p><c:out value="${rule.description}" /></p></td>
		</tr>
		<tr>
			<th></th>
			<td>
			<div>
			<c:if test="${canEdit}">
				<button id="editBtn" class="btn" 
				onclick="location.href='../rule/edit?id=<c:out value="${rule.id}" />'">
				<fmt:message key="button.edit" /></button>
				
				<button id="deleteBtn" class="btn"  
				onclick="javascript:deleteConfirm()">
				<fmt:message key="button.delete" /></button>
			</c:if>	
				<button id="returnBtn" class="btn" 
				onclick='javascript:history.go(-${history})'>
				<fmt:message key="button.return" /></button>
			</div>
			</td>
		</tr>
	</table>
	</div>
	<div style="float:left;">
		<div style="margin-top:50px;width:240px;height:300px;background-image:url(../images/people.png);">
			<div style="margin-left:10px;margin-top:50px;width:218px;height:90px;">
				<div style="padding-top:15px;padding-left:10px;font-size:14px;"><span><fmt:message key="rule.want.test" /></span></div>
				<div style="margin:20px;font-size:18px;"><a id="textBtn" style="cursor: pointer;"><fmt:message key="rule.test" /></a></div>
			</div>
		</div>
	</div>	
</div>

<div id="ruleTestDialog" align="left" title="<fmt:message key='rule.test'/>" >
	<div><h1 style="margin-bottom:-10px;font-size:18px;"><fmt:message key="rule.test.data" /></h1></div>
	<div id="testDataPanel">
	
	</div>
</div>