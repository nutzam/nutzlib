package org.nutz.ioc2;

public interface IIoc {
	Object get(String id);
	
	public void registerScopeType(String scopeTypeName, IScopeHandler handler);
}
