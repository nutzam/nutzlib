<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>验证结果</title>
</head>
	<body>
	<#if obj??>
		<div>
			UID : ${obj.uid}
		</div><p/>
		<div>
			Identity : ${obj.identity}
		</div><p/>
		<div>
			Email : ${obj.email}
		</div><p/>
	<#else>
		<div>登录失败</div>
	</#if>
	</body>
</html>