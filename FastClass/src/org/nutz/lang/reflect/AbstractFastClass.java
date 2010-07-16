package org.nutz.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class AbstractFastClass implements FastClass {
	
	private static final String [] cMethodName = new String[]{"create","newInstance"};
	
	private static final Class<?>[] toClasses(Object...args) {
		Class<?> [] classes = new Class[args.length];
		for (int i = 0; i < classes.length; i++)
			classes[i] = args[i].getClass();
		return classes;
	}

	protected Object _born(int index, Object...args){
		return null;
	}
	
	protected Object _invoke(Object obj,int index,Object...args){
		return null;
	}

	public Object born(Constructor<?> constructor,Object...args){
		if (constructor == null)
			throw new IllegalArgumentException("!!Constructor must not NULL !");
		if (Modifier.isPrivate(constructor.getModifiers()))
			throw new IllegalArgumentException("!!Constructor is private !");
		return _born(getConstructorIndex(constructor), args);
	}
	
	public Object born(Object...args){
		Class<?> [] classes = toClasses(args);
		try {
			Constructor<?> constructor = getSrcClass().getConstructor(classes);
			return born(constructor, args);
		}
		catch (Throwable e) {
			for (int i = 0; i < cMethodName.length; i++) {
				try {
					Method method = getSrcClass().getDeclaredMethod(cMethodName[i], classes);
					if (Modifier.isPrivate(method.getModifiers()))
						continue;
					if (! Modifier.isStatic(method.getModifiers()))
						continue;
					if (! getSrcClass().isAssignableFrom(method.getReturnType()))
						continue;
					return invoke(null, method, args);
				}catch (Throwable e2) {
				}
			}
		}
		throw new IllegalArgumentException("!!Fail to find Constructor for args");
	}
	
	private int getConstructorIndex(Constructor<?> constructor) {
		for (int i = 0; i < getConstructors().length; i++)
			if (getConstructors()[i].equals(constructor))
				return i;
		throw new RuntimeException("!!No such Constructor found!");
	}
	
	private int getMethodIndex(Method method) {
		for (int i = 0; i < getMethods().length; i++)
			if (getMethods()[i].equals(method))
				return i;
		throw new RuntimeException("!!No such Method found!");
	}
	
	public Object invoke(Object obj,Method method,Object...args){
		if (method == null)
			throw new IllegalArgumentException("!!Method must not NULL !");
		if (Modifier.isPrivate(method.getModifiers()))
			throw new IllegalArgumentException("!!Method is private !");
		if (obj == null && (Modifier.isStatic(method.getModifiers())) == false)
			throw new IllegalArgumentException("!!obj is NULL but Method isn't static !");
		return _invoke(obj, getMethodIndex(method), args);
	}
	
	public Object invoke(Object obj,String methodName,Object...args){
		Class<?> [] classes = toClasses(args);
		try {
			Method method = getSrcClass().getMethod(methodName, classes);
			return invoke(obj, method, args);
		}
		catch (SecurityException e) {
			throw new IllegalArgumentException("!!Fail to get method !",e);
		}
		catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("!!Fail to get method !",e);
		}
	}

	protected abstract Method[] getMethods();
	protected abstract Constructor<?>[] getConstructors();
	protected abstract Class<?> getSrcClass();
}
