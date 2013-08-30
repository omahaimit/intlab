<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="userCustom.heading"/></title>
    <meta name="heading" content="<fmt:message key='userCustom.heading'/>"/>
    <meta name="menu" content="UserMenu"/>
    
    <script type="text/javascript">
    	$(function() {
    		$.ajax({
    			type: "get",
    			url: "<c:url value='/usercustom/data'/>",
    			success: function(data){
    				var knowledge = $("input[name='knowledgeCheckBox']");
    				if(data[0]==""){
    					for(var i=0; i<knowledge.length; i++){
        					knowledge[i].checked=true;
        				}
    				}
    				for(var i=0; i<knowledge.length; i++){
    					for(var j=0; j<data[0].length; j++){
    						if(knowledge[i].value==data[0][j]){
    							knowledge[i].checked=true;
    						}
    					}
    				}
    				
    				var result = $("input[name='resultCheckBox']");
    				if(data[1]==""){
    					for(var i=0; i<result.length; i++){
    						result[i].checked=true;
        				}
    				}
    				for(var i=0; i<data[1].length; i++){
    					for(var j=0; j<result.length; j++){
    						if(result[j].value==data[1][i]){
    							result[j].checked=true;
    						}
    					}
    				}
    			}
    		});
    		
    		$( "#searchBag" ).autocomplete({
    	        source: function( request, response ) {
    	            $.ajax({
    	            	url: "<c:url value='/bag/ajax/searchBag'/>",
    	                dataType: "json",
    	                data: {
    	                    name : request.term
    	                },
    	                success: function( data ) {
    	  					
    	                	response( $.map( data, function( bag ) {
    	                        return {
    	                            label: bag.name,
    	                            value: bag.name,
    	                            id : bag.id
    	                        }
    	                    }));
    	                }
    	            });
    	        },
    	        minLength: 1,
    	        select : function(event, ui) {
    	        	$("#bag_id").val(ui.item.id);
    	        }
    		});
    		
    		$( "#bagPanel" ).dialog({
    			autoOpen: false,
    			resizable: false,
    			width: 300,
    			height: 200
    		});
    		
    		$("#addBag").click(function() {
    			$("#bag_id").val("");
    			$("#searchBag").val("");
    			$( "#bagPanel" ).dialog("open");
    		});
    		
    		$("#addBagBtn").click(function() {
    	    	if($("#bag_id").val() != "") {
    	    	 $("#bag_select").append("<option value='" + $("#bag_id").val() +"'>" + $("#searchBag").val() + "</option>");
    	    	} else {
    	    		alert("<fmt:message key="warning.bag.select.first"/>");
    	    	}
    	    });
    	    
    	    $("#removeBag").click(function() {
    	    	$("#bag_select option:selected").remove();
    	    });
    	    
    	    $( "#searchIndex" ).autocomplete({
    	        source: function( request, response ) {
    	            $.ajax({
    	            	url: "<c:url value='/index/ajax/searchIndex'/>",
    	                dataType: "json",
    	                data: {
    	                    name : request.term
    	                },
    	                success: function( data ) {
    	  					
    	                	response( $.map( data, function( item ) {
    	                        return {
    	                        	label: (item.category=="I")?(item.indexId+" "+item.name+" ("+item.sample+(item.unit?(","+item.unit):"")+")"):item.name,
									value: item.name,
									id : item.id,
									indexId : item.indexId,
									sample : item.sample,
									type : item.type,
									category : item.category,
									unit : item.unit?item.unit:"",
									data : item.data?item.data:""
    	                        }
    	                    }));
    	                }
    	            });
    	        },
    	        minLength: 1,
    	        select : function(event, ui) {
    	        	$("#index_id").val(ui.item.indexId);
    	        }
    		});
    	    
    	    $( "#indexPanel" ).dialog({
    			autoOpen: false,
    			resizable: false,
    			width: 300,
    			height: 200
    		});
    	    
    	    $("#addIndex").click(function() {
    			$("#index_id").val("");
    			$("#searchIndex").val("");
    			$( "#indexPanel" ).dialog("open");
    		});
    	    
    	    $("#addIndexBtn").click(function() {
    	    	if($("#index_id").val() != "") {
    	    	 $("#index_select").append("<option value='" + $("#index_id").val() +"'>" + $("#searchIndex").val() + "</option>");
    	    	} else {
    	    		alert("<fmt:message key="warning.index.select.first"/>");
    	    	}
    	    });
    	    
    	    $("#removeIndex").click(function() {
    	    	$("#index_select option:selected").remove();
    	    });
    	});
    	
    	function submitCustom(){
    		$.ajax({
                type : "post",
                url : "<c:url value='/usercustom/submit'/>",
                data : {"knowledge":getKnowledge(),"result":getResult(),"bag":getBag(),"history":getIndex()},
                dataType: "text",
                success : function(msg) {
                	if(msg=="success"){
                		window.location = "javascript:history.go(-1)";
                	}
                }
        	});
    	}
    	
    	function getBag(){
    		$("#bagId").val("");
    		$("#bag_select option").each(function(){
    			if ($("#bagId").val() != "") {
    				$("#bagId").val($("#bagId").val() + ",");
    			}
    			$("#bagId").val($("#bagId").val() + $(this).val()); 
    		});
    		return $("#bagId").val();
    	}
    	
    	function getKnowledge(){
    		var obj = $("input[name='knowledgeCheckBox']");
    		var data = "";
    		var flag=true;
    		for(var i=0; i<obj.length; i++){
    			if(obj[i].checked){
    				if(flag){
    					data=obj[i].value;
    					flag=false;
    				}else{
    					data=data+","+obj[i].value;
    				}
    			}
    		}
    		return data;
    	}
    	
    	function getIndex(){
    		$("#indexId").val("");
    		$("#index_select option").each(function(){
    			if ($("#indexId").val() != "") {
    				$("#indexId").val($("#indexId").val() + ",");
    			}
    			$("#indexId").val($("#indexId").val() + $(this).val()); 
    		});
    		return $("#indexId").val();
    	}
    	
    	function getResult(){
    		var obj = $("input[name='resultCheckBox']");
    		var data = "";
    		var flag=true;
    		for(var i=0; i<obj.length; i++){
    			if(obj[i].checked){
    				if(flag){
    					data=obj[i].value;
    					flag=false;
    				}else{
    					data=data+","+obj[i].value;
    				}
    			}
    		}
    		return data;
    	}
	</script>

    
</head>

<form style="font-size:14px;">
<table border="0" cellspacing="0" cellpadding="0" width="95%" align="center">
  <tbody>
  <tr>
    <td bgcolor="#dddddd" height="1" colspan="4"></td></tr>
  <tr>
    <td bgcolor="#6ccbf7" height="5" width="200"></td>
    <td height="5" colspan="3"></td></tr>
  <tr>
    <td valign="top">

      <div align="right"><b class="p1"><fmt:message key='knowledge.title'/></b></div></td>

    <td height="19" colspan="3">
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tbody>
        <tr valign="top">
          <td width="28%">
            <ul>
              <li><input type='checkbox' name='knowledgeCheckBox' value="<fmt:message key='knowledge.check1'/>"><fmt:message key='knowledge.check1'/></li>
              <li><input type='checkbox' name='knowledgeCheckBox' value="<fmt:message key='knowledge.check5'/>"><fmt:message key='knowledge.check5'/></li>
              <li><input type='checkbox' name='knowledgeCheckBox' value="<fmt:message key='knowledge.check9'/>"><fmt:message key='knowledge.check9'/></li>
            </ul>
          </td>
          <td width="24%">
            <ul>
              <li><input type='checkbox' name='knowledgeCheckBox' value="<fmt:message key='knowledge.check2'/>"><fmt:message key='knowledge.check2'/></li>
              <li><input type='checkbox' name='knowledgeCheckBox' value="<fmt:message key='knowledge.check6'/>"><fmt:message key='knowledge.check6'/></li>
              <li><input type='checkbox' name='knowledgeCheckBox' value="<fmt:message key='knowledge.check10'/>"><fmt:message key='knowledge.check10'/></li>
            </ul>
          </td>
          <td width="24%">
            <ul>
              <li><input type='checkbox' name='knowledgeCheckBox' value="<fmt:message key='knowledge.check3'/>"><fmt:message key='knowledge.check3'/></li>
              <li><input type='checkbox' name='knowledgeCheckBox' value="<fmt:message key='knowledge.check7'/>"><fmt:message key='knowledge.check7'/></li>
           	</ul>
          </td>
          <td width="24%">
            <ul>
              <li><input type='checkbox' name='knowledgeCheckBox' value="<fmt:message key='knowledge.check4'/>"><fmt:message key='knowledge.check4'/></li>
              <li><input type='checkbox' name='knowledgeCheckBox' value="<fmt:message key='knowledge.check8'/>"><fmt:message key='knowledge.check8'/></li>
            </ul>
          </td></tr></tbody></table></td>
</tr></tbody>
</table>

<%-- <table border="0" cellspacing="0" cellpadding="0" width="95%" align="center">
  <tbody>
  <tr>
    <td bgcolor="#dddddd" height="1" colspan="4"></td></tr>
  <tr>
    <td bgcolor="#ccff64" height="5" width="200"></td>
    <td height="5" colspan="3"></td></tr>
  <tr>
    <td valign="top">
    	<div align="right"><b class="p1"><fmt:message key='reasoningresult.title'/></b></div></td>
    <td height="19" colspan="3">
    	<div style="margin-left:100px;margin-top:10px;width:480px;">
			<div style="float:left;">
				<select size=2 id="bag_select" style="width: 300px;height:55px;">
					<c:forEach var="bag" items="${bagIdList}">
			            <option value="<c:out value="${bag.id}"/>">
			                <c:out value="${bag.name}"/>
			            </option>
			        </c:forEach>
				</select>
			</div>
			<div style="width:30px;float:right;">
				<input type="button" value="+" id="addBag"  style="width:30px;"/>
				<input type="button" value="-" id="removeBag" style="margin-top:8px;width:30px;"/>
			</div>
		</div>
</td></tr></tbody></table> --%>
    

<table border="0" cellspacing="0" cellpadding="0" width="95%" align="center">
  <tbody>
  <tr>
    <td bgcolor="#dddddd" height="1" colspan="4"></td></tr>
  <tr>
    <td bgcolor="#a1a0ff" height="5" width="200"></td>
    <td height="5" colspan="3"></td></tr>
  <tr>
    <td valign="top">
      <div align="right"><b class="p1"><fmt:message key='rulebase.title'/></b></div></td>
    <td height="19" colspan="3">
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tbody>
        <tr valign="top">
        <td width="28%">
            <ul><li><input type='checkbox' name='resultCheckBox' value="<fmt:message key='result.check1'/>"><fmt:message key='result.check1'/></li>
            <li><input type='checkbox' name='resultCheckBox' value="<fmt:message key='result.check5'/>"><fmt:message key='result.check5'/></li></ul>
        </td>
        <td width="24%">
            <ul><li><input type='checkbox' name='resultCheckBox' value="<fmt:message key='result.check2'/>"><fmt:message key='result.check2'/></li>
            <li><input type='checkbox' name='resultCheckBox' value="<fmt:message key='result.check6'/>"><fmt:message key='result.check6'/></li></ul>
        </td>
        <td width="24%">
            <ul><li><input type='checkbox' name='resultCheckBox' value="<fmt:message key='result.check3'/>"><fmt:message key='result.check3'/></li>
            <li><input type='checkbox' name='resultCheckBox' value="<fmt:message key='result.check7'/>"><fmt:message key='result.check7'/></li></ul>
        </td>
        <td width="24%">
            <ul>
            <li><input type='checkbox' name='resultCheckBox' value="<fmt:message key='result.check4'/>"><fmt:message key='result.check4'/></li>
            </ul>
        </td>
</tr></tbody></table></td></tr></tbody></table>

<table border="0" cellspacing="0" cellpadding="0" width="95%" align="center">
  <tbody>
  <tr>
    <td bgcolor="#dddddd" height="1" colspan="4"></td></tr>
  <tr>
    <td bgcolor="#ff9ac5" height="5" width="200"></td>
    <td height="5" colspan="3"></td></tr>
  <tr>
    <td valign="top">
    	<div align="right"><b class="p1"><fmt:message key='historylist.title'/></b></div></td>
    <td height="19" colspan="3">
    	<div style="margin-left:100px;margin-top:10px;width:480px;">
			<div style="float:left;">
				<select size=2 id="index_select" style="width: 300px;height:55px;">
					<c:forEach var="index" items="${indexIdList}">
			            <option value="<c:out value="${index.indexId}"/>">
			                <c:out value="${index.name}"/>
			            </option>
			        </c:forEach>
				</select>
			</div>
			<div style="width:30px;float:right;">
				<input type="button" value="+" id="addIndex"  style="width:30px;"/>
				<input type="button" value="-" id="removeIndex" style="margin-top:8px;width:30px;"/>
			</div>
		</div>
</td></tr></tbody></table>

<div>
	<input type="button" value="<fmt:message key='button.save'/>" onclick="submitCustom()" />  
	<input type="button" value="<fmt:message key='button.return'/>" onclick='javascript:history.go(-1)'/>
</div>

<div id="bagPanel" title="<fmt:message key="bag.add.dialog"/>" style="text-align:left;">
	<div><label><fmt:message key="input.search.name"/></label></div>
	<div><input id="searchBag" style="width:200px"/>
	<input type="hidden" id="bag_id"/></div>
	<div><input type="button" id="addBagBtn" value="<fmt:message key="bag.add"/>"/></div>
</div>

<div id="indexPanel" title="<fmt:message key="historylist.index"/>" style="text-align:left;">
	<div><label><fmt:message key="input.search.name"/></label></div>
	<div><input id="searchIndex" style="width:200px"/>
	<input type="hidden" id="index_id"/></div>
	<div><input type="button" id="addIndexBtn" value="<fmt:message key="historylist.add"/>"/></div>
</div>

<input id="bagId" name="bagId" type="hidden"/>
<input id="indexId" name="indexId" type="hidden"/>


</form>
