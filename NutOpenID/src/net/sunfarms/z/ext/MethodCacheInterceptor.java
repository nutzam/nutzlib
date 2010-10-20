package net.sunfarms.z.ext;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;

/**
 * 返回值缓存拦截器
 * @author wendal
 *
 */
public class MethodCacheInterceptor implements MethodInterceptor {
	
	private Cache cache;

	@Override
	public void filter(InterceptorChain chain) throws Throwable {
		Object obj = cache.get(createKey(chain));
		if (obj != null) {
			chain.setReturnValue(obj);
			return;
		}
		chain.doChain();
	}

	public void setCacheManager(CacheManager cacheManager) {
		cacheManager.addCache(MethodCacheInterceptor.class.getName());
		cache = cacheManager.getCache(MethodCacheInterceptor.class.getName());
		cache.getCacheConfiguration().setDiskPersistent(false);
	}
	
	private static final String createKey(InterceptorChain chain) {
		StringBuilder sb = new StringBuilder();
		sb.append(chain.getCallingObj().getClass()).append("_").append(chain.getCallingMethod());
		return sb.toString();
	}
}
