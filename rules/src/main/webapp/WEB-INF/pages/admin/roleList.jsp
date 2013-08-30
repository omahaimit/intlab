<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="roleList.title"/></title>
    <meta name="heading" content="<fmt:message key='roleList.heading'/>"/>
    <meta name="menu" content="AdminMenu"/>
    <style type="text/css">
    .img_icon {
    	width : 32px;
    	height : 32px;
    	margin-top : -8px;
    	margin-bottom : -8px;
    	margin-left : 0px;
    }
    .a_icon:hover {
    	cursor: pointer;
    }
    .table {
    	width:800px;
    }
    #roles {
    	font-size:14px;
    }
    </style>
    <script type="text/javascript">
	$(function(){
		
		$("#roles th:first").css('display','none');
		
    	$("#roles tbody tr").each(function(index,self) {
    		for (var i=2;i<=6;i++) {
    			var status = $(self).find("td:eq("+i+")");
				var id = $(self).find("td:eq(0)").html();
				id = id + "," + (i-1);
        		if (status.html() == "true") {
        			status.html("<a class='a_icon'><div style='display:none'>"+id+"</div><div style='display:none'>1</div><img src='../images/status.finished.png' class='img_icon'/></a>");
        		} else {
        			status.html("<a class='a_icon'><div style='display:none'>"+id+"</div><div style='display:none'>0</div><img src='../images/status.cancel.png' class='img_icon'/></a>");
        		}
    		}
    	});
    	
    	$(".a_icon").bind("click", function(){
    		var id = $(this).children(":first").html();
    		var status = $(this).children(":eq(1)");
    		var img = $(this).find("img:eq(0)");
        	$.get("../admin/roles/ajax/edit",{id : id}, function(data) {
        		if (data == true) {
	        		if (status.html() == 1) {
	        			img.attr('src','../images/status.cancel.png');
	        			status.html(0);
	        		} else {
	        			img.attr('src','../images/status.finished.png');
	        			status.html(1);
	        		}
        		} else {
        			alert("Change failed!");
        		}
        	});
    	});
    });
    </script>
</head>

<display:table name="roleList" cellspacing="0" cellpadding="0" requestURI="" id="roles" pagesize="20" class="table">
	<display:column property="id" style="display:none;" />
    <display:column property="item"  titleKey="role.operate" />
    <display:column property="bAdmin" titleKey="role.admin" style="width:14%"/>
    <display:column property="bDoctor" titleKey="role.doctor" style="width:14%"/>
    <display:column property="bOperator" titleKey="role.operator" style="width:14%"/>
    <display:column property="bPatient" titleKey="role.patient" style="width:14%"/>
    <display:column property="bUser" titleKey="role.user" style="width:14%"/>
</display:table>