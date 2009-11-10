package org.nutz.ioc.json.pojo;

import java.lang.reflect.Method;

import org.nutz.aop.MethodListener;

public class AnimalListener implements MethodListener {

	public Object afterInvoke(Object obj, Object returnObj, Method method, Object... args) {
		return null;
	}

	public boolean beforeInvoke(Object obj, Method method, Object... args) {
		return true;
	}

	public void whenError(Throwable e, Object obj, Method method, Object... args) {}

	public void whenException(Exception e, Object obj, Method method, Object... args) {}

}
