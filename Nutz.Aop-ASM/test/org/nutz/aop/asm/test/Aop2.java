package org.nutz.aop.asm.test;

import java.lang.reflect.Method;
import java.util.List;

import org.nutz.aop.MethodInterceptor;
import org.nutz.log.Log;

/**
 * 演示Aop1进行Aop改造后的行为....
 * <p/>
 * 1. 相同数量的构造函数(除私有和静态构造函数)
 * <p/>
 * 2. 包含两个静态类变量,其值会在Aop后期通过反射进行赋值
 * <p/>
 * 3. 包含四个静态方法, 用于被Aop拦截的方法进行调用.
 * 
 * @author zcchen
 * 
 */
public class Aop2 extends Aop1 {

	public Aop2(String name) {
		super(name);
	}

	private static Method[] _$$Nut_methodArray;

	private static List<MethodInterceptor>[] _$$Nut_methodInterceptorList;

	private boolean _Nut_before(int flag_int, Object... args) {
		Method method = _$$Nut_methodArray[flag_int];
		List<MethodInterceptor> miList = _$$Nut_methodInterceptorList[flag_int];
		boolean flag = true;
		for (MethodInterceptor methodInterceptor : miList)
			flag &= methodInterceptor.beforeInvoke(this, method, args);
		return flag;
	}

	private Object _Nut_after(int flag_int, Object src_return, Object... args) {
		Method method = _$$Nut_methodArray[flag_int];
		List<MethodInterceptor> miList = _$$Nut_methodInterceptorList[flag_int];
		for (MethodInterceptor methodInterceptor : miList)
			src_return = methodInterceptor.afterInvoke(this, src_return, method, args);
		return src_return;
	}

	private void _Nut_Exception(int flag_int, Exception e, Object... args) {
		Method method = _$$Nut_methodArray[flag_int];
		List<MethodInterceptor> miList = _$$Nut_methodInterceptorList[flag_int];
		for (MethodInterceptor methodInterceptor : miList)
			methodInterceptor.whenException(e, this, method, args);
	}

	private void _Nut_Error(int flag_int, Throwable e, Object... args) {
		Method method = _$$Nut_methodArray[flag_int];
		List<MethodInterceptor> miList = _$$Nut_methodInterceptorList[flag_int];
		for (MethodInterceptor methodInterceptor : miList)
			methodInterceptor.whenError(e, this, method, args);

	}

	@Override
	public void nonArgsVoid() {
		try {
			if (_Nut_before(188)) {
				super.nonArgsVoid();
			}
			_Nut_after(188, null);
		} catch (Exception e) {
			_Nut_Exception(188, e);
		} catch (Throwable e) {
			_Nut_Error(188, e);
		}
	}
	
//	@Override
//	public void argsVoid(String x) {
//		if (_Nut_before(188, x)) {
//			super.argsVoid(x);
//			_Nut_after(188, null, x);
//		}
//	}
//
//	@Override
//	public void mixArgsVoid(String x, Object obj, int yy, char xp, long... z) {
//		if (_Nut_before(188, x, obj, yy, xp, z)) {
//			super.mixArgsVoid(x, obj, yy, xp, z);
//			_Nut_after(188, null, x, obj, yy, xp, z);
//		}
//	}


	public void mixArgsVoid2(String x, Object obj, int yy, char xp, long bb, boolean ser, char xzzz, String ppp, StringBuffer sb, Log log, long... z) {
		try {
			if (_Nut_before(188, x, obj, yy, xp, bb, ser, xzzz, ppp, sb, log, z)) {
				super.mixArgsVoid2(x, obj, yy, xp, bb, ser, xzzz, ppp, sb, log, z);
			}
			_Nut_after(188, x, obj, yy, xp, bb, ser, xzzz, ppp, sb, log, z);
		} catch (Exception e) {
			_Nut_Exception(188, e, x, obj, yy, xp, bb, ser, xzzz, ppp, sb, log, z);
		} catch (Throwable e) {
			_Nut_Error(188, e, x, obj, yy, xp, bb, ser, xzzz, ppp, sb, log, z);
		}
	}

	public Object mixArgsVoid3(String x) {
		try {
			Object _result = null;
			if (_Nut_before(188, x)) {
				_result = mixArgsVoid4(x);
			}
			return _Nut_after(188, _result, x);
		} catch (Exception e) {
			_Nut_Exception(188, e, x);
		} catch (Throwable e) {
			_Nut_Error(188, e, x);
		}
		return null;
	}

	public Object mixArgsVoid4(String x) {
		try {
			Object _result = null;
			if (_Nut_before(188, x)) {
				 _result = super.mixArgsVoid4(x);
			}
			return _Nut_after(188, _result, x);
		} catch (Exception e) {
			_Nut_Exception(188, e, x);
		} catch (Throwable e) {
			_Nut_Error(188, e, x);
		}
			return null;
	}

	@Override
	public String returnString() {
		try {
			Object _result = null;
			if (_Nut_before(188)) {
				_result = super.returnString();
			}
			return (String) _Nut_after(188, _result);
		} catch (Exception e) {
			_Nut_Exception(188, e);
		} catch (Throwable e) {
			_Nut_Error(188, e);
		}
		return null;
	}
	

	@Override
	public long returnLong() {
		try {
			Object _result = null;
			if (_Nut_before(188)) {
				_result = super.returnLong();
			}
			return (Long) _Nut_after(188, _result);
		} catch (Exception e) {
			_Nut_Exception(188, e);
		} catch (Throwable e) {
			_Nut_Error(188, e);
		}
		return 0L;
	}
	
	@Override
	public Object[] returnObjectArray() {
		try {
			Object _result = null;
			if (_Nut_before(188)) {
				_result = super.returnObjectArray();
			}
			return (Object[]) _Nut_after(188, _result);
		} catch (Exception e) {
			_Nut_Exception(188, e);
		} catch (Throwable e) {
			_Nut_Error(188, e);
		}
		return null;
	}
	
}
