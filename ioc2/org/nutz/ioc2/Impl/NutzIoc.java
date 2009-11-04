package org.nutz.ioc2.Impl;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.IocLoader;
import org.nutz.ioc.ObjectMaker;
import org.nutz.ioc2.IContext;
import org.nutz.ioc2.IIoc;
import org.nutz.ioc2.IScopeHandler;

public class NutzIoc implements IIoc {

	private IContext context;
	
	Map<String, IScopeHandler> scopeTypeHandlers = new HashMap<String, IScopeHandler>();
	
	public NutzIoc(IocLoader loader, IContext context, ObjectMaker maker) {
		this.context = context;
		
		context.registerSupportedScopeTypes(this);
	}
	
	@Override
	public Object get(String id) {
		
		//我们从ioc配置中获得对象的level。
		String level = null;
		
		IScopeHandler handler = scopeTypeHandlers.get(level);
		
		Object obj = handler.get(id);
		
		if (obj != null)
			return obj;
		
		//创建对象实例
		handler.save(id, obj);
		
		return obj;
	}

	@Override
	public void registerScopeType(String scopeTypeName, IScopeHandler handler) {
		scopeTypeHandlers.put(scopeTypeName, handler);
	}

}
