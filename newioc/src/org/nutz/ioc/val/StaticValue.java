package org.nutz.ioc.val;

import org.nutz.ioc.IocContext;
import org.nutz.ioc.ValueProxy;

public class StaticValue implements ValueProxy {

	private Object obj;

	public StaticValue(Object obj) {
		this.obj = obj;
	}

	public Object get(IocContext context) {
		return obj;
	}

}
