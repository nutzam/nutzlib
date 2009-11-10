package org.nutz.ioc.aop;

import java.util.Map;

import org.nutz.aop.MethodListener;

/**
 * 描述了一个切片，这个类将是一个从 Ioc 容器取得的标准 POJO
 * 
 * @author zozoh(zozohtnt@gmail.com)
 * 
 * @see ort.nutz.ioc.aop.AopSetting
 */
public class AopItem {

	/*
	 * 如果为 Null 将采用 DefaultHookingFactory
	 */
	private Class<? extends ObjectHookingFactory> factoryType;
	/*
	 * 这个切片，由哪个监听器来负责
	 */
	private Class<? extends MethodListener> type;
	/*
	 * 监听器的构造函数是什么
	 */
	private Object[] args;
	/*
	 * 监听器的工厂类 (ObjectHookingFactory) 可能需要的初始化参数。对于
	 * DefaultHookingFactory，将用不到这个参数。你自己实现的 ObjectHookingFactory 可能会用到这个参数
	 */
	private Map<String, Object> init;
	/*
	 * 监听器将会监听的方法
	 */
	private AopHook[] hooks;

	public Class<? extends MethodListener> getType() {
		return type;
	}

	public Object[] getArgs() {
		return args;
	}

	public Class<? extends ObjectHookingFactory> getFactoryType() {
		return factoryType;
	}

	public Map<String, Object> getInit() {
		return init;
	}

	public AopHook[] getHooks() {
		return hooks;
	}

}
