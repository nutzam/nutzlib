package org.nutz.aop2;

import org.nutz.aop.ClassDefiner;
import org.nutz.aop.MethodMatcher;

public interface ClassAgent2 {

	<T> Class<T> define(ClassDefiner cd, Class<T> klass);

	
	ClassAgent2 addInterceptor(MethodMatcher matcher, MethodInterceptor inte);

	String CLASSNAME_SUFFIX = "$$NUTZAOP3";
}
