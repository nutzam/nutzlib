package org.nutz.lang;

import java.lang.reflect.Method;

public class AbstractInvoker<T> {
	public void invoke_return_void(T obj,Method method,Object...args) throws Throwable{}
	public Object invoke_return_Object(T obj,Method method,Object...args) throws Throwable{return null;}
	
	protected void setMethods(Method[] methods) {
	}
}