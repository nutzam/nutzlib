package org.nutz.ioc.weaver;

import org.nutz.ioc.IocContext;
import org.nutz.ioc.IocEventTrigger;
import org.nutz.ioc.ObjectWeaver;

public class StaticWeaver implements ObjectWeaver {

	private IocEventTrigger depose;
	private Object obj;

	StaticWeaver(Object obj, IocEventTrigger depose) {
		this.depose = depose;
		this.obj = obj;
	}

	public void deose() {
		if (null != depose)
			depose.trigger(obj);
	}

	public Object weave(IocContext context) {
		return obj;
	}

}
