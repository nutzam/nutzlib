package org.nutz.ioc2.Impl;

import javax.servlet.http.HttpSession;

public class SessionLocation implements ILocation {

	HttpSession session;
	
	public SessionLocation(HttpSession session) {
		this.session = session;
	}
	
	@Override
	public Object get(String id) {
		// TODO Auto-generated method stub
		return session.getAttribute(id);
	}

	@Override
	public void save(String id, Object obj) {
		session.setAttribute(id, obj);
	}

}
