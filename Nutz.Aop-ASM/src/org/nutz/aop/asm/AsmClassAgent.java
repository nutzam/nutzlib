package org.nutz.aop.asm;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.nutz.aop.ClassAgent;
import org.nutz.aop.MethodInterceptor;
import org.nutz.aop.MethodMatcher;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;

public class AsmClassAgent implements ClassAgent {

	private static GeneratorClassLoader generatorClassLoader = new GeneratorClassLoader();

	private ArrayList<Pair> pairs = new ArrayList<Pair>();

	public ClassAgent addListener(MethodMatcher matcher,
			MethodInterceptor listener) {
		if (null != listener)
			pairs.add(new Pair(matcher, listener));
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> define(Class<T> klass) {
		if(klass == null)
			return klass;
		String klass_name = klass.getName();
		if(klass.isInterface() || klass.isArray() 
				|| klass.isEnum() || klass.isPrimitive()
				|| klass.isMemberClass() )
			throw Lang.makeThrow("需要拦截的%s不是一个顶层类!创建失败!",klass_name);
		if(Modifier.isFinal(klass.getModifiers()))
			throw Lang.makeThrow("需要拦截的类:%s是final的!创建失败!",klass_name);
		if(klass.getName().endsWith(CLASSNAME_SUFFIX))
			return klass;
		String newName = klass_name + CLASSNAME_SUFFIX;
		Pair2[] pair2s = findMatchedMethod(klass);
		if (pair2s.length == 0)
			return klass;
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			return (Class<T>) Class.forName(newName, false, classLoader);
		} catch (ClassNotFoundException e2) {
			try {
				return (Class<T>) Class.forName(newName);
			} catch (ClassNotFoundException e1) {
				try {
					return (Class<T>) classLoader.loadClass(newName);
				} catch (ClassNotFoundException e) {
					try {
						return (Class<T>) generatorClassLoader.loadClass(newName);
					} catch (ClassNotFoundException e3) {
					}
				}
			}
		}
		Method[] methodArray = new Method[pair2s.length];
		List<MethodInterceptor>[] methodInterceptorList = new List[pair2s.length];
		for (int i = 0; i < pair2s.length; i++) {
			Pair2 pair2 = pair2s[i];
			methodArray[i] = pair2.method;
			methodInterceptorList[i] = pair2.listeners;
		}
		Class<T> newClass = (Class<T>) generatorClassLoader.defineClassFromClassFile(newName, 
					ClassX.enhandClass(klass, newName, methodArray));
		AopToolkit.injectFieldValue(newClass, methodArray,methodInterceptorList);
		return newClass;
	}

	private <T> Pair2[] findMatchedMethod(Class<T> klass) {
		Method[] all = Mirror.me(klass).getAllDeclaredMethodsWithoutTop();
		List<Pair2> p2 = new ArrayList<Pair2>();
		for (Method m : all) {
			int mod = m.getModifiers();
			if (mod == 0 || Modifier.isStatic(mod) || Modifier.isPrivate(mod))
				continue;
			ArrayList<MethodInterceptor> mls = new ArrayList<MethodInterceptor>();
			for (Pair p : pairs)
				if (p.matcher.match(m))
					mls.add(p.listener);
			if (mls.size() > 0) {
				p2.add(new Pair2(m, mls));
			}
		}
//		System.out.println("共有"+p2.size()+"个方法需要拦截");
		return p2.toArray(new Pair2[p2.size()] );
	}

}

class GeneratorClassLoader extends ClassLoader {
	public Class<?> defineClassFromClassFile(String className,
			byte[] classFile) throws ClassFormatError {
		return defineClass(className, classFile, 0, classFile.length);
	}
}

class Pair {
	Pair(MethodMatcher matcher, MethodInterceptor listener) {
		this.matcher = matcher;
		this.listener = listener;
	}

	MethodMatcher matcher;
	MethodInterceptor listener;
}

class Pair2 {
	Pair2(Method method, ArrayList<MethodInterceptor> listeners) {
		this.method = method;
		this.listeners = listeners;
	}

	Method method;
	ArrayList<MethodInterceptor> listeners;
}