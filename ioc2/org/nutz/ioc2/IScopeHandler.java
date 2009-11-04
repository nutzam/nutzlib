package org.nutz.ioc2;

public interface IScopeHandler {
	void save(String id, Object obj);
	
	Object get(String id);
}
