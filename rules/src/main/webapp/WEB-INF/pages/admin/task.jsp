<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="taskManager.title"/></title>
    <meta name="heading" content="<fmt:message key='taskManager.title'/>"/>
    <meta name="menu" content="AdminMenu"/>
    <style type="text/css">
    .status_icon {
    	width : 30px;
    	height : 30px;
    	margin-top : -5px;
    	margin-bottom : -5px;
    	margin-left : -2px;
    }
    #interval {
    	width:50px;
    }
    .ui-progressbar .ui-progressbar-value { background-image: url(<c:url value='/images/pbar-ani.gif'/>); }
    </style>
    <script type="text/javascript">
    function getProValue() {
    	$.get("<c:url value='/task/ajax/progress'/>",{},function(data){

    		var map = {
   				put : function(key,value){this[key] = value},
   				get : function(key){return this[key]},
   				contains : function(key){return this[key] == null?false:true},
   				remove : function(key){delete this[key]}
   			}
    		var array = jQuery.parseJSON(data);
    		if (array.length > $("#taskList tbody tr").length) {
    			location.reload();
    		}
    		var flag = false;
    		for (var i=0 ; i < array.length ; i++) {
    			map.put(array[i].id,i);
    			if (array[i].status==0 || array[i].status==1) {
    				flag = true;
    			}
    		} 
    		$("#taskList tbody tr").each(function(index,self) {
    			var first = $(self).children(":first");
    			var id = first.children(":first").html();
    			if (map.contains(id)) {
    				var progTd = $(self).find("td:eq(4)");
    	    		var index = map.get(id);
    	    		var val = array[index].value;
    	    		var status = array[index].status;
    	    		progTd.children(":first").progressbar({value:parseInt(val)});
    	    		$(self).find("td:eq(5)").html(array[index].finish);
    	    		$(self).find("td:eq(6)").html(array[index].total);
    	    		$(self).find("td:eq(7)").html(array[index].result);
    	    		$(self).find("td:eq(8)").html(array[index].end);
    	    		var img = first.find("img:eq(0)");
    	    		if (val==100 || status==2) {
    	    			img.attr('src','<c:url value="/images/status.finished.png"/>');
    	    		} else if (status==3) {
    	    			img.attr('src','<c:url value="/images/status.cancel.png"/>');
    	    		} else if (((status==2)||(array[index].finish != 0)) && first.find("img:eq(0)").attr('id') == 'wait_icon') {
    	    			first.html("<div style='display:none;'>"+id+"</div><a class='runIcon'><img src='<c:url value='/images/status.run.png' />' class='status_icon'/></a>");
    	    		}
    			}
    		});
    		if (flag) {
    			setTimeout(getProValue,1000);
    		} else {
    			setTimeout(getProValue,10000);
    		}
    	});
    }
    
    $(function(){
    	$(".empty").remove();
    	
    	$("#taskList tbody tr").each(function(index,self) {
    		var first = $(self).children(":first");
    		var res = parseInt(first.html());
    		var status = res % 10;
    		var id = parseInt(res/10);
    		if (status == "1") {
    			first.html("<div style='display:none;'>"+id+"</div><a class='runIcon'><img src='<c:url value='/images/status.run.png'/>' class='status_icon'/></a>");
    		} else if (status =="2") {
    			first.html("<div style='display:none;'>"+id+"</div><img src='<c:url value='/images/status.finished.png'/>' class='status_icon'/>");
    		} else if (status == "3") {
    			first.html("<div style='display:none;'>"+id+"</div><img src='<c:url value='/images/status.cancel.png'/>' class='status_icon'/>");
    		} else if (status == "0") {
    			first.html("<div style='display:none;'>"+id+"</div><img src='<c:url value='/images/status.wait.gif'/>' id='wait_icon' style='width:20px;height:20px;margin-left:5px;' class='status_icon'/>");
    		}
    		
    		var progTd = $(self).find("td:eq(4)");
    		var val = progTd.html();
    		progTd.html("<div style='height:15px;'></div>");
    		progTd.children(":first").progressbar({value:parseFloat(val)*100});
    	});
    	
    	$(".runIcon").bind("click", function(){
    		var id = $(this).parent().children(":first").html();
    		var img = $(this).find("img:eq(0)");
        	$.get("<c:url value='/task/ajax/cancel'/>",{id : id}, function() {
        		img.attr('src',"<c:url value='/images/status.cancel.png'/>");
        	});
    	});
    	getProValue(1000);
    	
    	$("#startBtn").click(function() {
    		var intl = $("#interval").val();
        	$.post("<c:url value='/task/ajax/audit'/>",{interval:intl}, function(data) {
        		if (data) {
            		if ($("#startBtn").val() == '<fmt:message key="audit.start" />') {
            			$("#startBtn").val('<fmt:message key="audit.stop" />');
            		} else {
            			$("#startBtn").val('<fmt:message key="audit.start" />');
            		}
        		}
        	});
        });
    });
    
    </script>
</head>

<div>
	<div class="input-prepend input-append">
		<span class="add-on"><fmt:message key='background.audit.interval' /></span> 
		<input class="span3" id="interval" type="text" value="${backAuditInterval}">
		<span class="add-on"><fmt:message key='minute' /></span>
		<c:if test='${currentStatus == false}'>
		<input id="startBtn" class="btn btn-info" type="button" value='<fmt:message key="audit.start" />' />
		</c:if>
		<c:if test='${currentStatus == true}'>
		<input id="startBtn" class="btn btn-info" type="button" value='<fmt:message key="audit.stop" />' />
		</c:if>
		
	</div>
	
	<input id="currentStatus" type="hidden"/>
</div>
<div>
	<display:table name="taskList" class="table" requestURI="" id="taskList"  pagesize="20">
		<display:column property="idAndStatus" titleKey="task.status" style="width: 25px;" />
		<display:column property="startBy" titleKey="task.startBy" style="width: 50px;"/>
		<display:column property="searchText" titleKey="task.SearchText" style="width: 60px;"/>
	    <display:column property="startTime" format="{0,date,yyyy-MM-dd hh:mm:ss}" titleKey="task.startTime" style="width: 140px;"/>
	    <display:column property="proValue" titleKey="task.progress" style="width: 200px;"/>
	    <display:column property="finishCount" titleKey="task.finishCount" />
	    <display:column property="sampleCount" titleKey="task.sampleCount" />
	    <display:column property="hasResultNum" titleKey="task.hasResultNum" />s
	    <display:column property="endTime" format="{0,date,yyyy-MM-dd hh:mm:ss}" titleKey="task.endTime" style="width: 140px;"/>
	</display:table>
</div>