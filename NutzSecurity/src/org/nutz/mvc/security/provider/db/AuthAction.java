package org.nutz.mvc.security.provider.db;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
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
		if (Strings.isBlank(name) || Strings.isBlank(password))
			return false;
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
		switch (method) {
		case user_add:
			dao.insert(User.class, Chain.make("name", name).add("password", password));
			break;
		case user_del:
			dao.delete(User.class, name);
			break;
		case user_update:
			dao.update(User.class, Chain.make("password", password), Cnd.where("name", "LIKE", name));
			break;
		case role_assign:
			
			break;
		case role_cacel:
			
			break;

		default:
			break;
		}
	}
}
