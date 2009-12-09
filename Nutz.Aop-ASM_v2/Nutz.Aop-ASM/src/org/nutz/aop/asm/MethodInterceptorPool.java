package org.nutz.aop.asm;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.nutz.aop.MethodInterceptor;

@SuppressWarnings("unchecked")
public class MethodInterceptorPool {

	private static HashMap<Class<?>, Method []> methodArrayMap = new HashMap<Class<?>, Method[]>();
	
	private static HashMap<Class<?>, List<MethodInterceptor> []> methodInterceptorListMap = new HashMap<Class<?>, List<MethodInterceptor> []>();

	public static boolean _Nut_before(int flag_int,Object obj, Object... args) {
		Object [] objs = getInfo(obj);
		Method method = ((Method[])objs[0])[flag_int];
		List<MethodInterceptor> miList = ((List<MethodInterceptor>[])objs[1])[flag_int];
		boolean flag = true;
		for (MethodInterceptor methodInterceptor : miList)
			flag &= methodInterceptor.beforeInvoke(obj, method, args);
		return flag;
	}

	public static Object _Nut_after(int flag_int,Object obj, Object src_return, Object... args) {
		Object [] objs = getInfo(obj);
		Method method = ((Method[])objs[0])[flag_int];
		List<MethodInterceptor> miList = ((List<MethodInterceptor>[])objs[1])[flag_int];
		for (MethodInterceptor methodInterceptor : miList)
			src_return = methodInterceptor.afterInvoke(obj, src_return, method, args);
		return src_return;
	}

	public static boolean _Nut_Exception(int flag_int,Object obj, Exception e, Object... args) {
		Object [] objs = getInfo(obj);
		Method method = ((Method[])objs[0])[flag_int];
		List<MethodInterceptor> miList = ((List<MethodInterceptor>[])objs[1])[flag_int];
		boolean flag = true;
		for (MethodInterceptor methodInterceptor : miList)
			flag &= methodInterceptor.whenException(e, obj, method, args);
		return flag;
	}

	public static boolean _Nut_Error(int flag_int,Object obj, Throwable e, Object... args) {
		Object [] objs = getInfo(obj);
		Method method = ((Method[])objs[0])[flag_int];
		List<MethodInterceptor> miList = ((List<MethodInterceptor>[])objs[1])[flag_int];
		boolean flag = true;
		for (MethodInterceptor methodInterceptor : miList)
			flag &= methodInterceptor.whenError(e, obj, method, args);
		return flag;
	}
	
	public static void setMethodArray(Class<?> newClass,Method[] methodArray,List<MethodInterceptor>[] methodInterceptorList){
		methodArrayMap.put(newClass, methodArray);
		methodInterceptorListMap.put(newClass, methodInterceptorList);
	}
	
	private static Object [] getInfo(Object obj){
		Object [] objs = new Object[2];
		objs[0] = methodArrayMap.get(obj.getClass());
		objs[1] = methodInterceptorListMap.get(obj.getClass());
		return objs;
	}
	
}
