package org.nutz.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.nutz.testing.LogTestClass;

public class BasicTest {

	@Test
	public void testMethod1() {
		LogTestClass testee = new LogTestClass();
		
		testee.method1();
	}

}
