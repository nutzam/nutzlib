package org.nutz.aop2.interceptor;

import java.lang.reflect.Method;

import org.nutz.aop2.InterceptorChain;
import org.nutz.aop2.MethodInterceptor;
import org.nutz.lang.Lang;

public class AbstractMethodInterceptor implements MethodInterceptor {

	public void filter(InterceptorChain chain) {
		try {
			if (beforeInvoke(chain.getCallingObj(), chain.getCallingMethod(), chain.getArgs()))
				chain.doChain();
		}
		catch (Exception e) {
			if (whenException(e, chain.getCallingObj(), chain.getCallingMethod(), chain.getArgs()))
				throw Lang.wrapThrow(e);
		}
		catch (Throwable e) {
			if (whenError(e, chain.getCallingObj(), chain.getCallingMethod(), chain.getArgs()))
				throw Lang.wrapThrow(e);
		}
		finally {
			Object obj = afterInvoke(chain.getCallingObj(), chain.getReturn(), chain.getCallingMethod(), chain.getArgs());
			chain.setReturnValue(obj);
		}

	}

	public Object afterInvoke(Object obj, Object returnObj, Method method, Object... args) {
		return returnObj;
	}

	public boolean beforeInvoke(Object obj, Method method, Object... args) {
		return true;
	}

	public boolean whenError(Throwable e, Object obj, Method method, Object... args) {
		return true;
	}

	public boolean whenException(Exception e, Object obj, Method method, Object... args) {
		return true;
	}
}
