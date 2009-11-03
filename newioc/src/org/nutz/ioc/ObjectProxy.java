package org.nutz.ioc;

public class ObjectProxy {

	/**
	 * 创建一个对象代理，当创建时，会触发 create 事件 <br>
	 * 每次获取对象时会触发 fetch 事件，销毁时触发 depose 事件。
	 * 
	 * @param obj
	 *            对象本身
	 * @param create
	 *            创建时触发器
	 * @param fetch
	 *            获取时触发器
	 * @param depose
	 *            销毁时触发器
	 */
	public ObjectProxy(	Object obj,
						IocEventTrigger create,
						IocEventTrigger fetch,
						IocEventTrigger depose) {
		if (null != create)
			create.trigger(obj);
		this.fetch = fetch;
		this.depose = depose;
	}

	private Object obj;

	private IocEventTrigger fetch;

	private IocEventTrigger depose;

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> classOfT) {
		if (null != fetch)
			fetch.trigger(obj);
		return (T) obj;
	}

	public void depose() {
		if (null != depose)
			depose.trigger(obj);
	}

}
