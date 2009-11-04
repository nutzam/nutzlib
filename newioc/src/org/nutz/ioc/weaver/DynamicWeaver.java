package org.nutz.ioc.weaver;

import org.nutz.ioc.IocContext;
import org.nutz.ioc.IocEventTrigger;
import org.nutz.ioc.ObjectWeaver;
import org.nutz.ioc.ValueProxy;
import org.nutz.lang.born.Borning;

public class DynamicWeaver implements ObjectWeaver {

	private IocEventTrigger create;
	private IocEventTrigger depose;
	private Borning<?> borning;
	private ValueProxy[] args;
	private FieldInjector[] fields;

	public void setCreate(IocEventTrigger create) {
		this.create = create;
	}

	public void setDepose(IocEventTrigger depose) {
		this.depose = depose;
	}

	public void setBorning(Borning<?> borning) {
		this.borning = borning;
	}

	public void setArgs(ValueProxy[] args) {
		this.args = args;
	}

	public void setFields(FieldInjector[] fields) {
		this.fields = fields;
	}

	public void deose() {}

	public Object weave(IocContext context) {
		// 准备构造函数
		Object[] args = new Object[this.args.length];
		for (int i = 0; i < args.length; i++)
			args[i] = this.args[i].get(context);

		// 创建实例
		Object obj = borning.born(args);

		// 设置字段的值
		for (FieldInjector fi : fields)
			fi.inject(context, obj);

		// 触发创建事件
		if (null != create)
			create.trigger(obj);

		// 返回
		return obj;
	}

	public StaticWeaver toStatic(IocContext context) {
		Object obj = weave(context);
		return new StaticWeaver(obj, depose);
	}
}
