package org.nutz.ioc;

public interface Ioc2 extends Ioc {

	/**
	 * 这是更高级的 Ioc 获取对象的方法，它传给 Ioc 容器一个上下文环境。 <br>
	 * 容器以此作为参考，决定如何构建对象，或者将对象缓存在何处
	 * 
	 * @param type
	 *            对象的类型
	 * @param name
	 *            对象的名称
	 * @param context
	 *            对象的上下文环境
	 * @return 对象本身
	 * 
	 * @see org.nutz.ioc.Ioc
	 */
	<T> T get(Class<T> type, String name, IocContext context);

}
