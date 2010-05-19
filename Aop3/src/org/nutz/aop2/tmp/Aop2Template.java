package org.nutz.aop2.tmp;

import java.lang.reflect.Method;
import java.util.List;

import org.nutz.aop2.AopCallback;
import org.nutz.aop2.InterceptorChain;
import org.nutz.aop2.MethodInterceptor;
import org.nutz.lang.Lang;

public class Aop2Template extends Aop2TemplateP implements AopCallback{
	
	private static Method [] _$$Nut_methodArray;
	
	private static List<MethodInterceptor> [] _$$Nut_methodInterceptorList;

	public void mA(String str, int x){
		try{
			new InterceptorChain(20,this, _$$Nut_methodArray[20], _$$Nut_methodInterceptorList[20],new Object[]{str,x}).doChain().getReturn();
		}catch (Throwable e) {
			throw Lang.wrapThrow(e);
		}
	}

	@Override
	public Object _aop_invoke(int methodIndex, Object[] args) {
		if (methodIndex == 1){
			super.mA((String)args[0],(Integer)args[1]);
			return null;
		} else if (methodIndex == 1){
			mA((String)args[0],(Integer)args[1]);
			return null;
		} else if (methodIndex == 1){
			mA((String)args[0],(Integer)args[1]);
			return null;
		} else if (methodIndex == 1){
			mA((String)args[0],(Integer)args[1]);
			return null;
		} else if (methodIndex == 1){
			mA((String)args[0],(Integer)args[1]);
			return null;
		} else if (methodIndex == 1){
			mA((String)args[0],(Integer)args[1]);
			return null;
		} else if (methodIndex == 1){
			mA((String)args[0],(Integer)args[1]);
			return null;
		} else if (methodIndex == 1){
			mA((String)args[0],(Integer)args[1]);
			return null;
		} 
			
		return null;
	}
}
