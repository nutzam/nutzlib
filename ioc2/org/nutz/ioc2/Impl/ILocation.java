package org.nutz.ioc2.Impl;

public interface ILocation {
	void save(String id, Object obj);
	
	Object get(String id);
}
