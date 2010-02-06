package org.nutz.lang;

import java.lang.reflect.Method;

public class AbstractInvoker {
	public void invoke_static_void(Method method,Object...args) throws Throwable{}
	public Object invoke_static_Object(Method method,Object...args) throws Throwable{return null;}
	public void invoke_instant_void(Object obj,Method method,Object...args) throws Throwable{}
	public Object invoke_instant_Object(Object obj,Method method,Object...args) throws Throwable{return null;}
}