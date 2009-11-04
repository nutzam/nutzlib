package org.nutz.ioc2.Impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc2.IContext;

public class WebApplicationContext implements IContext {

	public WebApplicationContext() {
		
	}
	
	private static final ThreadLocal<HttpSession> sessionLocation = new ThreadLocal<HttpSession>() {
		@Override protected HttpSession initialValue() {
			return null;
		}
	};
	
	private static final ThreadLocal<HttpServletRequest> requestLocation = new ThreadLocal<HttpServletRequest>() {
		@Override protected HttpServletRequest initialValue() {
			return null;
		}
	};
	
	@Override
	public Object get(String id, String level) {
		
		if ("session".equals(level))
			return sessionLocation.get().getAttribute(id);
		
		//if ("request".equals(level))....
		
		return null;
	}

	@Override
	public void save(String id, Object obj, String level) {
		if ("session".equals(level)) {
			sessionLocation.get().getAttribute(id);
			return;
		}

		//if ("request".equals(level))...
		
		//throw new Exception("level " + level + " is unknown.");
	}

	public void beginServe(HttpServletRequest request) {
		sessionLocation.set(request.getSession());
		
		requestLocation.set(request);
	}
	
	public void afterServe() {
		sessionLocation.remove();
	}
}
