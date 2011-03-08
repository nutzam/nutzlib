package org.nutz.mvc.security;

import javax.servlet.http.HttpServletRequest;

public interface AuthProvider {
	
	boolean isOK(HttpServletRequest req, String...roles);
	
}
