<%@page import="com.nutz.mvc.auth.AuthUserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>验证结果</title>
</head>
	<body>
		<div>
			UID : <%=((AuthUserInfo)request.getAttribute("obj")).getUid() %>
		</div><p/>
		<div>
			Identity : <%=((AuthUserInfo)request.getAttribute("obj")).getIdentity() %>
		</div><p/>
		<div>
			Email : <%=((AuthUserInfo)request.getAttribute("obj")).getEmail() %>
		</div><p/>
	</body>
</html>