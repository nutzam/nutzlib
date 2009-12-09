package org.nutz.aop.asm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import org.nutz.aop.AbstractClassAgent;
import org.nutz.aop.MethodInterceptor;

public class AsmClassAgent extends AbstractClassAgent {

	@SuppressWarnings("unchecked")
	protected <T> Class<T> generate(Pair2 [] pair2s,String newName,Class<T> klass,Constructor<T> [] constructors) {
		Method[] methodArray = new Method[pair2s.length];
		List<MethodInterceptor>[] methodInterceptorList = new List[pair2s.length];
		for (int i = 0; i < pair2s.length; i++) {
			Pair2 pair2 = pair2s[i];
			methodArray[i] = pair2.method;
			methodInterceptorList[i] = pair2.listeners;
		}
		Class<T> newClass = (Class<T>)generatorClassLoader.defineClassFromClassFile(newName, ClassX.enhandClass(klass, newName, methodArray,constructors));
		AopToolkit.injectFieldValue(newClass, methodArray, methodInterceptorList);
		return newClass ;
	}

}