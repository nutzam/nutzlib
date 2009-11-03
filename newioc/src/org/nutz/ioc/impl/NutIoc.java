package org.nutz.ioc.impl;

import org.nutz.ioc.Ioc2;
import org.nutz.ioc.IocContext;
import org.nutz.ioc.IocLoader;
import org.nutz.ioc.ObjectMaker;
import org.nutz.ioc.ObjectProxy;
import org.nutz.ioc.meta.IocObject;
import org.nutz.lang.Lang;

public class NutIoc implements Ioc2 {

	private IocLoader loader;
	private IocContext context;
	private ObjectMaker maker;

	public NutIoc(IocLoader loader, IocContext context, ObjectMaker maker) {
		this.loader = loader;
		this.context = context;
		this.maker = maker;
	}

	public <T> T get(Class<T> type, String name, IocContext context) {
		// 连接上下文
		IocContext cc;
		if (null != context)
			cc = new ComboContext(context, this.context);
		else
			cc = this.context;

		// 从上下文缓存中获取对象代理
		ObjectProxy re = cc.fetch(name);

		// 如果未发现对象
		if (null == re) {
			// 线程同步
			synchronized (this) {
				// 再次读取
				re = cc.fetch(name);

				// 如果未发现对象
				if (null == re) {
					// 读取对象定义
					IocObject iObj = loader.load(name);
					if (null == iObj)
						throw Lang.makeThrow("Undefined object '%s'", name);

					// 修正对象类型
					if (null == iObj.getType())
						if (null == type)
							throw Lang.makeThrow("NULL TYPE object '%s'", name);
						else
							iObj.setType(type);

					// 根据对象定义，创建对象
					re = maker.make(cc, iObj);

					// 保存至上下文环境
					if (iObj.isSingleton())
						cc.save(iObj.getLevel(), name, re);
				}
			}
		}
		return re.get(type);
	}

	public <T> T get(Class<T> type, String name) {
		return this.get(type, name, null);
	}

	public boolean has(String name) {
		return loader.has(name);
	}

	public void depose() {
		context.depose();
	}

	public void reset() {
		context.clear();
	}

	public String[] getName() {
		return loader.getName();
	}

}
