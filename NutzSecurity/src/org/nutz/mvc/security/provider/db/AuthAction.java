package org.nutz.mvc.security.provider.db;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@InjectName
@IocBean
@At("/auth")
public class AuthAction {
	
	@Inject
	private Dao dao;
	
	@Ok("json")
	@Fail(">>:/index.html")
	@At
	public boolean login(String name, String password, HttpServletRequest req){
		Assert.isNotEmpty(name, "用户名不能为空");
		Assert.isNotEmpty(password, "密码不能为空");
		User user = dao.fetch(User.class, Cnd.where("name", "like", name).and("password","like",password));
		if(user == null)
			return false;
		user = dao.fetchLinks(user, null);
		req.getSession().setAttribute("auth.user", user);
		return true;
	}
	
	@Ok(">>:/index.html")
	@Fail(">>:/index.html")
	@At
	public void logout(HttpServletRequest req){
		HttpSession session = req.getSession(false);
		if(session != null)
			session.invalidate();
	}

	@At("/userOpt/*")
	public void userOpt(UserOpt method, 
			@Param("name")String name, @Param("password")String password,
			@Param("role")String role){
		Assert.isNotEmpty(name, "用户名不能为空");
		switch (method) {
		case user_add:
			Assert.isNotEmpty(password, "密码不能为空");
			dao.insert(User.class, Chain.make("name", name).add("password", password));
			break;
		case user_del:
			dao.delete(User.class, name);
			break;
		case user_update:
			Assert.isNotEmpty(password, "密码不能为空");
			dao.update(User.class, Chain.make("password", password), Cnd.where("name", "LIKE", name));
			break;
		case role_assign:
			Assert.isNotEmpty(role, "权限不能为空");
			
			break;
		case role_cacel:
			Assert.isNotEmpty(role, "权限不能为空");
			
			break;

		default:
			break;
		}
	}
}
