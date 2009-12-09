package org.nutz.aop.asm;

import junit.framework.TestCase;

import org.junit.Test;
import org.nutz.aop.AbstractMethodInterceptor;
import org.nutz.aop.ClassAgent;
import org.nutz.aop.RegexMethodMatcher;
import org.nutz.aop.asm.test.Aop1;
import org.nutz.lang.Mirror;

public class ClassXTest extends TestCase{
	
	ClassAgent classAgent;
	
	public void setUp(){
		classAgent = new AsmClassAgent();
		classAgent.addListener(new RegexMethodMatcher(".*")	, new AbstractMethodInterceptor(){});
	}
	
	@Test
	public void testCreat(){
		classAgent.define(Object.class);
		classAgent.define(getClass());
	}
	
	@Test
	public void testInterface(){
		try{
			classAgent.define(Runnable.class);
		    fail();
		}catch (Throwable e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testDupAop(){
		Class<Aop1> klass = Aop1.class;
		for (int i = 0; i < 10000; i++) {
			klass = classAgent.define(klass);
		}
		System.out.println(klass);
		assertFalse(Aop1.class == klass);
	}
	
	@Test
	public void testBorn(){
		Class<Aop1> klass =  classAgent.define(Aop1.class);
		Aop1 a1 = Mirror.me(klass).born("Nut");
		a1.returnObjectArray();
	}

	@Test
	public void testCreate2() throws Throwable{
		Class<?> obj = classAgent.define(Aop1.class);
		Class<?> obj2 = classAgent.define(Aop1.class);
		assertEquals(obj, obj2);
	}
	
	@Test
	public void testConstructors(){
		getNewInstance(Aop1.class);
	}
	
	@Test
	public void testConstructor2(){
		Class<Aop1> newClass = classAgent.define(Aop1.class);
		assertTrue(newClass.getDeclaredConstructors().length > 0);
	}

	@Test
	public void testReturnPrimitive(){
		Aop1 a1 = getNewInstance(Aop1.class);
		a1.returnLong();
		a1.returnBoolean();
		a1.returnByte();
		a1.returnChar();
		a1.returnFloat();
		a1.returnShort();
		a1.returnDouble();
	}

	@Test
	public void testReturnPrimitiveArray(){
		Aop1 a1 = getNewInstance(Aop1.class);
		a1.returnLongArray();
		a1.returnBooleanArray();
		a1.returnByteArray();
		a1.returnCharArray();
		a1.returnFloatArray();
		a1.returnShortArray();
		a1.returnDoubleArray();
	}

	@Test
	public void testReturnObject() throws Throwable{
		Aop1 a1 = getNewInstance(Aop1.class);
		a1.returnString();
		a1.returnObjectArray();
		a1.getRunnable();
		a1.getEnum();
	}
	
	@Test
	public void testThrowError() throws Throwable{
		Aop1 a1 = getNewInstance(Aop1.class);
		try{
			a1.throwError();
		    fail();
		}catch (Throwable e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testArgs() throws Throwable{
		Aop1 a1 = getNewInstance(Aop1.class);
		a1.nonArgsVoid();
		a1.argsVoid("Wendal is the best!");
		a1.mixObjectsVoid("Arg1", new Object(), 1, null);
		a1.mixArgsVoid("XX", "WendalXXX", 0, 'c', 1L, 9090L);
		a1.mixArgsVoid2("Aop1", Boolean.TRUE, 8888, 'p', 34L, false, 'b', "Gp", null, null, 23L,
				90L, 78L);
		String result = String.valueOf(a1.mixArgsVoid4("WendalXXX"));
		assertEquals("WendalXXX", result);
	}

	private <T> T getNewInstance(Class<T> klass){
		Class<T> newClass = classAgent.define(klass);
		Mirror<T> mirror = Mirror.me(newClass);
		return  mirror.born("Nutz");
	}

}
