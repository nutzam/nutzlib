package org.nutz.aop.asm.test;

import java.lang.reflect.Method;

import org.nutz.aop.MethodInterceptor;
import org.nutz.castor.Castors;

public class MyMethodInterceptor implements MethodInterceptor {

	@Override
	public Object afterInvoke(Object arg0, Object arg1, Method arg2,
			Object... objs) {
		System.out.println("After...");
		System.out.println("参数 "+Castors.me().castToString(objs));
		return arg1;
	}

	@Override
	public boolean beforeInvoke(Object arg0, Method arg1, Object... objs) {
		System.out.println("-----------------------------------------------------");
		System.out.println("Before....");
		System.out.println("参数 "+Castors.me().castToString(objs));
		return true;
	}

	@Override
	public void whenError(Throwable e, Object arg1, Method arg2,
			Object... objs) {
		System.out.println("抛出了Throwable "+e);
		System.out.println("参数 "+Castors.me().castToString(objs));
	}

	@Override
	public void whenException(Exception e, Object arg1, Method arg2,
			Object... objs) {
		System.out.println("抛出了Exception "+e);
		System.out.println("参数 "+Castors.me().castToString(objs));
	}
	
	void printArgs(Object...objs){
		System.out.println("参数 "+Castors.me().castToString(objs));
	}

}
