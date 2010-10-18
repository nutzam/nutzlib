
<a href="#" onclick="$('#newTipDiv').toggle()">添加新的帖子</a>
<div id="newTipDiv" style="display:none">
	<form id="newTipForm" onsubmit="return false;">
		<fieldset>
			<label for="name">主题</label>
			<input type="text" name="newTipContent" id="newTipContent" class="text ui-widget-content ui-corner-all" />
		</fieldset>
	</form>
	<script type="text/javascript">
		$("#newTipForm").keydown(function(event){
  			if(event.keyCode == 13) {
  				Z.forum.newTip($('#newTipContent').val());
  			}
		});
	</script>
</div>

<#if obj??>
<ul>
<#list obj as tip>
	<li><a href="#" onclick="Z.forum.viewTip(${tip.id})">#${tip.id}  ${tip.content?html} ${tip.createTime}</a></li>
</#list>
</ul>
</#if>