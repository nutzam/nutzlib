package org.nutz.ioc;

/**
 * 进行一次对象装配的上下文环境。 随着 Ioc.get 被调用而产生，随着函数返回而消亡
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public interface IocContext {
	
	/**
	 * 连接至另外一个上下文对象。
	 * 
	 * @param context
	 */
	void linkTo(IocContext context);

	void save(String key, Object obj);

	Object fetch(String key);

}
