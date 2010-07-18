package org.nutz.lang.reflect;

import java.util.ArrayList;
import java.util.HashSet;

import junit.framework.TestCase;

import org.junit.Test;

public class FastClassFactoryTest extends TestCase{

	@SuppressWarnings("unchecked")
	@Test
	public void test_MethodName() throws Throwable {
		Object object = new Object();
		assertEquals(object.hashCode(), getFastClass(Object.class).invoke(object, "hashCode"));
		ArrayList<?> list = (ArrayList<?>) getFastClass(ArrayList.class).born(new HashSet<Object>());
		list = (ArrayList<?>) getFastClass(ArrayList.class).born(1);
		assertTrue(list != null);
		getFastClass(ArrayList.class).invoke(list, "add", new Object());
		assertTrue(list.size() == 1);
		getFastClass(ArrayList.class).invoke(list, "remove", 0);
		assertTrue(list.size() == 0);
		getFastClass(ArrayList.class).born(new HashSet());
	}
	
	public static FastClass getFastClass(Class<?> classZ){
		try{
			return (FastClass) new FastClassFactory().create(classZ).newInstance();
		}catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
}
