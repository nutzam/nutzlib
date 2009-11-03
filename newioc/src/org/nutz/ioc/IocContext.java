package org.nutz.ioc;

/**
 * 进行一次对象装配的上下文环境。 随着 Ioc.get 被调用而产生，随着函数返回而消亡
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public interface IocContext {
	
	/**
	 * 连接至另外一个上下文对象。本上下文对象发生 save，则会同时调用自己所链接的 context，
	 * 并将对象计数减 1，对象的初始计数参见  org.nutz.ioc.meta.IocObject.level
	 * 
	 * @param context
	 */
	void linkTo(IocContext context);

	void save(String key, Object obj);

	Object fetch(String key);

}
