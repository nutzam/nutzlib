package org.nutz.mvc.security.provider.db;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.mvc.security.AuthProvider;

public class DatabaseAuthProvider implements AuthProvider {

	@Override
	public boolean isOK(HttpServletRequest req, String... roles) {
		if (roles == null || roles.length == 0 || "ANYONE".equalsIgnoreCase(roles[0]))
			return true;
		HttpSession session = req.getSession(false);
		if (session == null)
			return false;
		User user = (User) session.getAttribute("auth.user");
		if (user == null)
			return false;
		for (String role : roles) {
			for (Role myRole : user.getRoles()) {
				if (myRole.getName().equalsIgnoreCase(role))
					return true;
			}
		}
		return false;
	}

}
