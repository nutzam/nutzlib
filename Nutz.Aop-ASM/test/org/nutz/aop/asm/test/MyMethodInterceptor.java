package org.nutz.aop.asm.test;

import java.lang.reflect.Method;

import org.nutz.aop.MethodInterceptor;

public class MyMethodInterceptor implements MethodInterceptor {

	@Override
	public Object afterInvoke(Object arg0, Object arg1, Method arg2,
			Object... arg3) {
		System.out.println("After...");
		return arg1;
	}

	@Override
	public boolean beforeInvoke(Object arg0, Method arg1, Object... arg2) {
		System.out.println("Before....");
		return true;
	}

	@Override
	public void whenError(Throwable arg0, Object arg1, Method arg2,
			Object... arg3) {
	}

	@Override
	public void whenException(Exception arg0, Object arg1, Method arg2,
			Object... arg3) {
	}

}
