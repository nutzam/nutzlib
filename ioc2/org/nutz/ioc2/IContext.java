package org.nutz.ioc2;

public interface IContext {
	void save(String id, Object obj, String level);
	
	Object get(String id, String level);
}
