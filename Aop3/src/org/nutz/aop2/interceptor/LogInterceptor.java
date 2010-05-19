package org.nutz.aop2.interceptor;

import org.nutz.aop2.InterceptorChain;
import org.nutz.aop2.MethodInterceptor;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class LogInterceptor implements MethodInterceptor {
	
	private static final Log LOG = Logs.getLog(LogInterceptor.class);

	@Override
	public void filter(InterceptorChain chain) {
		try {
			LOG.info("Before Invoke");
			chain.doChain();
			LOG.info("After Invoke");
		}catch (Exception e) {
			LOG.info("Exception cathced",e);
			throw Lang.wrapThrow(e);
		} catch (Throwable e) {
			LOG.info("Throwable cathced",e);
			throw Lang.wrapThrow(e);
		}

	}

}
