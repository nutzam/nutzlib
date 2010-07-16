package org.nutz.lang.reflect;

import junit.framework.TestCase;

import org.junit.Test;
import org.nutz.aop.interceptor.AbstractMethodInterceptor;
import org.nutz.lang.reflect.FastClass;
import org.nutz.lang.reflect.FastClassFactory;

public class FastClassFactoryTest extends TestCase{

	@Test
	public void test_MethodName() throws Throwable {
		getFastClass(FastClassFactoryTest.class).invoke(new FastClassFactoryTest(), "xxx");
		getFastClass(FastClassFactoryTest.class).invoke(new FastClassFactoryTest(), "getFastClass", Object.class);
		getFastClass(FastClassFactoryTest.class).invoke(null, "yyy");
		getFastClass(AbstractMethodInterceptor.class).invoke(new AbstractMethodInterceptor(){}, "toString");
		getFastClass(Runnable.class).invoke(new Thread(), "run");
		Object object = new Object();
		assertEquals(object.hashCode(), getFastClass(Object.class).invoke(object, "hashCode"));
		for (int i = 0; i < 1000; i++) {
			getFastClass(FastClassFactoryTest.class).invoke(null, "yyy");
		}
	}
	
	public void xxx(){
		System.out.println("XXXXXXXXXXX");
	}
	
	public static void yyy(){
//		System.out.println("YYYYYYYYYYY");
	}
	
	public FastClass getFastClass(Class<?> classZ){
		try{
			return (FastClass) new FastClassFactory().create(classZ).newInstance();
		}catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
}
