package org.nutz.lang;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("unused")
public class FastClassTest {

	@Test
	public void testInvoke_instant_void() throws Throwable{
		FastClass fastClass = FastClass.create(AClass.class);
		fastClass.invoke_return_void(new AClass(), AClass.class.getMethod("pp"));
		fastClass.invoke_return_void(new AClass(), AClass.class.getMethod("xxx"));
		fastClass.invoke_return_void(new AClass(), AClass.class.getMethod("yy",Object.class),"Wendal");
		fastClass.invoke_return_void(new AClass(), AClass.class.getMethod("yy",Object.class),new Object());
		fastClass.invoke_return_void(new AClass(), AClass.class.getMethod("xxxxyyy"));
		fastClass.invoke_return_void(new AClass(), AClass.class.getMethod("toString"));
		fastClass.invoke_return_void(new AClass(), AClass.class.getMethod("hashCode"));
		fastClass.invoke_return_void(new AClass(), AClass.class.getMethod("equals",Object.class),new AClass());
		Object.class.equals(null);
	}

}
