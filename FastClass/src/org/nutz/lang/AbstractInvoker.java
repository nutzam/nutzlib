package org.nutz.lang;

import java.lang.reflect.Method;

public class AbstractInvoker {
	public void invoke_return_void(Object obj,Method method,Object...args) throws Throwable{}
	public Object invoke_return_Object(Object obj,Method method,Object...args) throws Throwable{return null;}
}