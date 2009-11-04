package org.nutz.ioc2.Impl;

import org.nutz.ioc.IocLoader;
import org.nutz.ioc.ObjectMaker;
import org.nutz.ioc2.IContext;
import org.nutz.ioc2.IIoc;

public class NutzIoc implements IIoc {

	private IContext context;
	
	public NutzIoc(IocLoader loader, IContext context, ObjectMaker maker) {
		this.context = context;
	}
	
	@Override
	public Object get(String id) {
		
		//我们从ioc配置中获得对象的level。
		String level = null;
		
		Object obj = context.get(id, level);
		
		if (obj != null)
			return obj;
		
		//创建对象实例
		context.save(id, obj, level);
		
		return obj;
	}

}
