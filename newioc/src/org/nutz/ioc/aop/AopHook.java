package org.nutz.ioc.aop;

import java.util.Map;

public class AopHook {

	public static enum MODE {
		OBJECT_NAME, OBJECT_TYPE
	}

	/**
	 * 监听什么样的对象，一个正则表达式
	 */
	private String regex;

	/**
	 * 正则表达式，是匹配对象的名称，还是对象的类型
	 */
	private MODE mode;

	/**
	 * 监听什么样的方法
	 */
	private AopHookMethod[] methods;

	/**
	 * 这部分是监听器针对这次监听，特殊的配置信息
	 */
	private Map<String, Object> config;

	public String getRegex() {
		return regex;
	}

	public MODE getMode() {
		return mode;
	}

	public AopHookMethod[] getMethods() {
		return methods;
	}

	public Map<String, Object> getConfig() {
		return config;
	}

}
