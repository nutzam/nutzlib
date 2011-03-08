package org.nutz.mvc.security.provider.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	public boolean login(@Param("name")String name, 
			             @Param("password")String password,
			             @Param("rememberMe") boolean rememberMe,
			             HttpServletRequest req,
			             HttpServletResponse resp){
		Assert.isNotEmpty(name, "用户名不能为空");
		//Check auto login
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("auth.remember.code".equalsIgnoreCase(cookie.getName())) {
					String rc = cookie.getValue();
					String ip = req.getRemoteHost();
					String code = MD5.encode(name,rc,ip);
					AutoLoginInfo info = dao.fetch(AutoLoginInfo.class, 
							Cnd.where("name", "=", name).
							    and("code", "=", code).
							    and("expTime", ">", System.currentTimeMillis()));
					if (info != null) {
						User user = dao.fetch(User.class, Cnd.where("name", "=", name));
						if(user == null)
							return false;
						user = dao.fetchLinks(user, null);
						req.getSession().setAttribute("auth.user", user);
						return true;
					}
				}
			}
		}
		//---------------------------------------------------
		Assert.isNotEmpty(password, "密码不能为空");
		User user = dao.fetch(User.class, Cnd.where("name", "=", name).and("password","=",password));
		if(user == null)
			return false;
		user = dao.fetchLinks(user, null);
		req.getSession().setAttribute("auth.user", user);
		if(rememberMe) {
			long expTime = System.currentTimeMillis() + 86400 * 30;
			String rc = UUID.randomUUID().toString().replaceAll("-", "");
			String ip = req.getRemoteHost();
			String code = MD5.encode(name,rc,ip);
			AutoLoginInfo info = new AutoLoginInfo();
			info.setCode(code);
			info.setExpTime(new Timestamp(expTime * 1000));
			info.setName(name);
			dao.clear(AutoLoginInfo.class, Cnd.where("name", "=", name));
			dao.insert(info);
			Cookie cookie = new Cookie("auth.remember.code", code);
			cookie.setMaxAge((int) expTime);
			resp.addCookie(cookie);
		}
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
			dao.update(User.class, Chain.make("password", password), Cnd.where("name", "=", name));
			break;
		case role_assign: {
			Assert.isNotEmpty(role, "权限不能为空");
			User user = dao.fetch(User.class, name);
			Assert.isNotNull(user, "没有这个用户:"+name);
			user = dao.fetchLinks(user, null);
			if (user.getRoles() == null)
				user.setRoles(new ArrayList<Role>());
			else {
				if (user.getRoles().contains(role))
					throw AssertException.make("该用户已经拥有这种权限:"+role);
			}
			Role x_role = dao.fetch(Role.class,role);
			Assert.isNotNull(x_role, "没有这种权限:"+role);
			user.getRoles().add(x_role);
			dao.updateLinks(dao, null);
			break;
		}
		case role_cacel:
			Assert.isNotEmpty(role, "权限不能为空");
			User user = dao.fetch(User.class, name);
			Assert.isNotNull(user, "没有这个用户:"+name);
			Role x_role = dao.fetch(Role.class,role);
			Assert.isNotNull(x_role, "没有这种权限:"+role);
			user = dao.fetchLinks(user, null);
			if (user.getRoles() == null || (!user.getRoles().contains(role)))
					throw AssertException.make("该用户没有拥有这种权限:"+role);
			user.getRoles().remove(x_role);
			dao.updateLinks(dao, null);
			break;

		default://不可能!
			break;
		}
	}
	
	protected boolean checkAutoLogin(String name, HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("auth.remember.code".equalsIgnoreCase(cookie.getName())) {
					String rc = cookie.getValue();
					String ip = req.getRemoteHost();
					String code = MD5.encode(name,rc,ip);
					AutoLoginInfo info = dao.fetch(AutoLoginInfo.class, 
							Cnd.where("name", "=", name).
							    and("code", "=", code).
							    and("expTime", ">", System.currentTimeMillis()));
					return info != null;
				}
			}
		}
		return false;
	}
	
}
