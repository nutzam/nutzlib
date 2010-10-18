<#if obj??>
<div>
<p>${obj.content?html}</p>
<ul>
<#list obj.comments as comment>
	<li><a href="#">#${comment.id}  ${comment.content?html} ${comment.createTime}</a></li>
</#list>
</ul>
</div>
<p>回复</p>
<div id="newCommentDiv">
	<form id="newCommentForm" onsubmit="return false;">
		<fieldset>
			<input type="text" name="newComment" id="newComment" class="text ui-widget-content ui-corner-all" />
		</fieldset>
	</form>
	<script type="text/javascript">
		$("#newCommentForm").keydown(function(event){
  			if(event.keyCode == 13) {
  				Z.forum.addComment($('#newComment').val());
  			}
		});
	</script>
</div>
</#if>