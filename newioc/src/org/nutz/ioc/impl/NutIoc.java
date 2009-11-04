package org.nutz.ioc.impl;

import java.util.ArrayList;
import java.util.List;

import org.nutz.ioc.Ioc2;
import org.nutz.ioc.IocContext;
import org.nutz.ioc.IocLoader;
import org.nutz.ioc.IocMaking;
import org.nutz.ioc.ObjectMaker;
import org.nutz.ioc.ObjectProxy;
import org.nutz.ioc.ValueProxyMaker;
import org.nutz.ioc.meta.IocObject;
import org.nutz.lang.Lang;

public class NutIoc implements Ioc2 {

	private IocLoader loader;
	private IocContext context;
	private ObjectMaker maker;
	private List<ValueProxyMaker> vpms;

	public NutIoc(IocLoader loader, IocContext context, ObjectMaker maker) {
		this.loader = loader;
		this.context = context;
		this.maker = maker;
		vpms = new ArrayList<ValueProxyMaker>(5); // 预留五个位置，足够了吧
	}

	public <T> T get(Class<T> type, String name, IocContext context) {
		// 连接上下文
		IocContext cntx;
		if (null == context)
			cntx = this.context;
		else
			cntx = new ComboContext(this, context, this.context);

		// 从上下文缓存中获取对象代理
		ObjectProxy re = cntx.fetch(name);

		// 如果未发现对象
		if (null == re) {
			// 线程同步
			synchronized (this) {
				// 再次读取
				re = cntx.fetch(name);

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

					// 根据对象定义，创建对象，maker 会自动的缓存对象到 context 中
					re = maker.make(new IocMaking(this, cntx, name), iObj);
				}
			}
		}
		return re.get(cntx, type);
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

	public void addValueProxyMaker(ValueProxyMaker vpm) {
		vpms.add(vpm);
	}

}
