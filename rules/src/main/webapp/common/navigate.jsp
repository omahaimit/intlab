<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function navSearchBtnClick() {
	if ($("#nav-search-panel").css('display') == "none") {
		$("#nav-search-panel").css('display','block');
	} else {
		$("#nav-search-panel").css('display','none');
	}
}
function navRuleBtnClick() {
	if ($("#nav-rule-panel").css('display') == "none") {
		$("#nav-rule-panel").css('display','block');
	} else {
		$("#nav-rule-panel").css('display','none');
	}
}
</script>

<div class="border-radius-6 border-shadow" id="navContent" style="padding-bottom:30px;">
	<div class="tag-section">
		<a class="selbtn" href="javascript:navSearchBtnClick();"><div class="tag-item">
		<b class="tag-name" style="letter-spacing:5px; "><fmt:message key="search" /></b>
		</div></a>
	</div>
	<div id="nav-search-panel" class="tag-panel" style="padding:10px 10px 0 10px;display:block;">
		<p style="color:#777777;"><fmt:message key="nav.search" />
		<input id="globalsearchbox" placeholder="<fmt:message key="rule" />,<fmt:message key="index" />,<fmt:message key="result" />" style="width: 180px;margin-top:10px;"/>
	</div>
	<div class="tag-section">
		<a class="selbtn" href="javascript:navRuleBtnClick();"><div class="tag-item">
			<b class="tag-name" style="letter-spacing:5px; "><fmt:message key="rule" /></b>
			</div>
		</a>
	</div>
	<div id="nav-rule-panel" class="tag-panel" style="display:block;">
		<div style="margin:5px 0px 5px 5px;">
			
       		<div>
				<ul id="tree" class="ztree"></ul>
			</div>
       	</div>
	</div>
	<div class="tag-section">
	<a class="selbtn" href="<c:url value="/index/list"/>">
		<div class="tag-item">
			<b class="tag-name" style="letter-spacing:5px; "><fmt:message key="index" /></b>
		</div>
	</a>
	</div>
	<div class="tag-section">
	<a class="selbtn" href="<c:url value="/result/list"/>">
		<div class="tag-item">
			<b class="tag-name" style="letter-spacing:5px; "><fmt:message key="result" /></b>
		</div>
	</a>
	</div>
</div>