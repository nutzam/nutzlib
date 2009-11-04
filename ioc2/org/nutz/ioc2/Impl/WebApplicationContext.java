package org.nutz.ioc2.Impl;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc2.IContext;

public class WebApplicationContext implements IContext {

	public WebApplicationContext() {
		
	}
	
	private static final ThreadLocal<ILocation> sessionLocation = new ThreadLocal<ILocation>() {
		@Override protected ILocation initialValue() {
			return null;
		}
	};
	
	
	@Override
	public Object get(String id, String level) {
		
		if ("session".equals(level))
			return sessionLocation.get().get(id);
		
		//if ("request".equals(level))....
		
		return null;
	}

	@Override
	public void save(String id, Object obj, String level) {
		if ("session".equals(level)) {
			sessionLocation.get().get(id);
			return;
		}

		//if ("request".equals(level))...
		
		//throw new Exception("level " + level + " is unknown.");
	}

	public void beginServe(HttpServletRequest request) {
		sessionLocation.set(new SessionLocation(request.getSession()));
	}
	
	public void afterServe() {
		sessionLocation.remove();
	}
}
