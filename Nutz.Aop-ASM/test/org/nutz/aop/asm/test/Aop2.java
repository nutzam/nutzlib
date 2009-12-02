package org.nutz.aop.asm.test;

import java.lang.reflect.Method;
import java.util.List;

import org.nutz.aop.MethodInterceptor;

public class Aop2 extends Aop1{
	
	public Aop2(String name) {
		super(name);
	}

	private static Method [] _$$Nut_methodArray;
	
	private static List<MethodInterceptor> [] _$$Nut_methodInterceptorList;
	
	private boolean _Nut_before(int flag_int,Object...args){
		Method method = _$$Nut_methodArray[flag_int];
		List<MethodInterceptor> miList = _$$Nut_methodInterceptorList[flag_int];
		boolean flag = true;
		for (MethodInterceptor methodInterceptor : miList)
			flag &= methodInterceptor.beforeInvoke(this, method, args);
		return flag;
	}
	
	private Object _Nut_after(int flag_int,Object src_return,Object...args){
		Method method = _$$Nut_methodArray[flag_int];
		List<MethodInterceptor> miList = _$$Nut_methodInterceptorList[flag_int];
		for (MethodInterceptor methodInterceptor : miList)
			src_return = methodInterceptor.afterInvoke(this, src_return, method, args);
		return src_return;
	}

	@Override
	public void nonArgsVoid() {
		if(_Nut_before(188)){
			super.nonArgsVoid();
			_Nut_after(188, null);
		}
	}
	
	@Override
	public void argsVoid(String x) {
		if(_Nut_before(188)){
			super.argsVoid(x);
			_Nut_after(188, null);
		}
	}
	
	@Override
	public void mixArgsVoid(String x, Object obj, int yy, char xp, long... z) {
		if(_Nut_before(188)){
			super.mixArgsVoid(x, obj, yy, xp, z);
			_Nut_after(188, null);
		}
	}
}
