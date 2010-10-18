<#if obj??>
	<div><b>${obj.email } </b><a id="login" href="#"onclick="Z.logout()">登出<a></div>
<#else>
	<div><a id="logout" href="#" onclick="Z.login()">登录<a></div>
</#if>
<script type="text/javascript">
$('#logout').button();
$('#login').button();
</script>