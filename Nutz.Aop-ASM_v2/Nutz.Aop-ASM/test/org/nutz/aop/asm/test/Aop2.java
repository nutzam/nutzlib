package org.nutz.aop.asm.test;

import org.nutz.aop.asm.MethodInterceptorPool;
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

	@Override
	public void nonArgsVoid() throws Throwable{
		try {
			if (MethodInterceptorPool._Nut_before(188,this)) {
				super.nonArgsVoid();
			}
			MethodInterceptorPool._Nut_after(188, this,null);
		} catch (Exception e) {
			if(MethodInterceptorPool._Nut_Exception(188, this,e))
				throw e;
		} catch (Throwable e) {
			if(MethodInterceptorPool._Nut_Error(188,this, e))
				throw e;
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


	public void mixArgsVoid2(String x, Object obj, 
			int yy, char xp, long bb, 
			boolean ser, char xzzz, 
			String ppp, StringBuffer sb, Log log, long... z) 
				throws Throwable{
		try {
			if (MethodInterceptorPool._Nut_before(188,this, x, obj, yy, xp, bb, ser, xzzz, ppp, sb, log, z)) {
				super.mixArgsVoid2(x, obj, yy, xp, bb, ser, xzzz, ppp, sb, log, z);
			}
			MethodInterceptorPool._Nut_after(188,this, x, obj, yy, xp, bb, ser, xzzz, ppp, sb, log, z);
		} catch (Exception e) {
			if(MethodInterceptorPool._Nut_Exception(188,this, e, x, obj, yy, xp, bb, ser, xzzz, ppp, sb, log, z))
				throw e;
		} catch (Throwable e) {
			if(MethodInterceptorPool._Nut_Error(188,this, e, x, obj, yy, xp, bb, ser, xzzz, ppp, sb, log, z))
				throw e;
		}
	}

//	public Object mixArgsVoid3(String x) {
//		try {
//			Object _result = null;
//			if (_Nut_before(188, x)) {
//				_result = mixArgsVoid4(x);
//			}
//			return _Nut_after(188, _result, x);
//		} catch (Exception e) {
//			_Nut_Exception(188, e, x);
//		} catch (Throwable e) {
//			_Nut_Error(188, e, x);
//		}
//		return null;
//	}

//	public Object mixArgsVoid4(String x) {
//		try {
//			Object _result = null;
//			if (_Nut_before(188, x)) {
//				 _result = super.mixArgsVoid4(x);
//			}
//			return _Nut_after(188, _result, x);
//		} catch (Exception e) {
//			_Nut_Exception(188, e, x);
//		} catch (Throwable e) {
//			_Nut_Error(188, e, x);
//		}
//			return null;
//	}

	@Override
	public String returnString() throws Throwable{
		try {
			Object _result = null;
			if (MethodInterceptorPool._Nut_before(188,this)) {
				_result = super.returnString();
			}
			return (String) MethodInterceptorPool._Nut_after(188,this, _result);
		} catch (Exception e) {
			if(MethodInterceptorPool._Nut_Exception(188,this, e))
				throw e;
		} catch (Throwable e) {
			if(MethodInterceptorPool._Nut_Error(188,this, e))
				throw e;
		}
		return null;
	}
	

//	@Override
//	public long returnLong() {
//		try {
//			Object _result = null;
//			if (_Nut_before(188)) {
//				_result = super.returnLong();
//			}
//			return (Long) _Nut_after(188, _result);
//		} catch (Exception e) {
//			_Nut_Exception(188, e);
//		} catch (Throwable e) {
//			_Nut_Error(188, e);
//		}
//		return 0L;
//	}
//	
//	@Override
//	public Object[] returnObjectArray() {
//		try {
//			Object _result = null;
//			if (_Nut_before(188)) {
//				_result = super.returnObjectArray();
//			}
//			return (Object[]) _Nut_after(188, _result);
//		} catch (Exception e) {
//			_Nut_Exception(188, e);
//		} catch (Throwable e) {
//			_Nut_Error(188, e);
//		}
//		return null;
//	}
	
}
