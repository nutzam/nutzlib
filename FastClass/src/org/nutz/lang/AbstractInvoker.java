package org.nutz.lang;

import java.lang.reflect.Method;

public class AbstractInvoker<T> {
	protected void invoke_return_void(T obj,Method method,Object...args) throws Throwable{}
	protected Object invoke_return_Object(T obj,Method method,Object...args) throws Throwable{return null;}
	
	protected void setMethods(Method[] methods) {
	}
}