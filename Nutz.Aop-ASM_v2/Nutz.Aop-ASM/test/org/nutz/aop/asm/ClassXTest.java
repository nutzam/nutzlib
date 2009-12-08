package org.nutz.aop.asm;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.nutz.aop.AbstractMethodInterceptor;
import org.nutz.aop.ClassAgent;
import org.nutz.aop.RegexMethodMatcher;
import org.nutz.aop.asm.test.Aop1;
import org.nutz.lang.Mirror;

public class ClassXTest extends TestCase{
	
	ClassAgent classAgent;
	
	@Before
	public void init(){
		classAgent = new NutClassGenerator();
		classAgent.addListener(new RegexMethodMatcher(".*")	, new AbstractMethodInterceptor(){});
	}
	
	@Test
	public void testCreat(){
		classAgent.define(Object.class);
		classAgent.define(getClass());
	}
	
	@Test public void testInterface(){
		Class<?> aopClass = classAgent.define(Runnable.class);
		assertTrue(aopClass == Runnable.class);
	}
	
	@Test
	public void testDupAop(){
		Class<Aop1> klass = Aop1.class;
		for (int i = 0; i < 100; i++) {
			klass = classAgent.define(klass);
		}
		assertFalse(Aop1.class == klass);
	}
	
	@Test
	public void testBorn(){
		Class<Aop1> klass =  classAgent.define(Aop1.class);
		Aop1 a1 = Mirror.me(klass).born("Nut");
		a1.returnObjectArray();
	}

}
