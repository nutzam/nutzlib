package org.nutz.aop.asm;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.nutz.aop.ClassAgent;
import org.nutz.aop.MethodInterceptor;
import org.nutz.aop.MethodMatcher;
import org.nutz.lang.Mirror;

public class NutClassGenerator implements ClassAgent {

	private static GeneratorClassLoader classLoader = new GeneratorClassLoader();

	@SuppressWarnings("unchecked")
	public <T> Class<T> generate(Class<T> kclass, String newName,
			Method[] methodArray) throws ClassFormatError,
			InstantiationException, IllegalAccessException, IOException {
		return (Class<T>) classLoader.defineClassFromClassFile(newName, 
					ClassX.enhandClass(kclass, newName, methodArray));
	}

	static class GeneratorClassLoader extends ClassLoader {
		public Class<?> defineClassFromClassFile(String className,
				byte[] classFile) throws ClassFormatError {
			return defineClass(className, classFile, 0, classFile.length);
		}
	}

	private ArrayList<Pair> pairs = new ArrayList<Pair>();

	public ClassAgent addListener(MethodMatcher matcher,
			MethodInterceptor listener) {
		if (null != listener)
			pairs.add(new Pair(matcher, listener));
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> Class<T> define(Class<T> klass) {
		Pair2[] pair2s = findMatchedMethod(klass);
		if (pair2s.length == 0)
			return klass;
		String newName = klass.getName() + "$$Nut";
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
		try {
			Class<T> newClass = generate(klass, newName, methodArray);
			AopToolkit.injectFieldValue(newClass, methodArray,
					methodInterceptorList);
			return newClass;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
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
		System.out.println("共有"+p2.size()+"个方法需要拦截");
		return p2.toArray(new Pair2[p2.size()] );
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