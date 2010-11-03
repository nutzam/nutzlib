var ioc = {
	/*定义事务模板拦截器,共5种*/
	txNONE : {
		type : 'org.nutz.aop.interceptor.TransactionInterceptor',
		args : [ 0 ]
	},
	txREAD_UNCOMMITTED : {
		type : 'org.nutz.aop.interceptor.TransactionInterceptor',
		args : [ 1 ]
	},
	txREAD_COMMITTED : {
		type : 'org.nutz.aop.interceptor.TransactionInterceptor',
		args : [ 2 ]
	},
	txREPEATABLE_READ : {
		type : 'org.nutz.aop.interceptor.TransactionInterceptor',
		args : [ 4 ]
	},
	txSERIALIZABLE : {
		type : 'org.nutz.aop.interceptor.TransactionInterceptor',
		args : [ 8 ]
	},
	log : {
		type : "org.nutz.aop.interceptor.LoggingMethodInterceptor"
	},
	exeTime : {
		type : 'net.sunfarms.z.ext.ExecutionTimeInterceptor'
	},
	/*缓存管理器,这里使用的是Ehcache*/
	cacheManager : {
		type : 'net.sf.ehcache.CacheManager',
		events : {
        	depose : 'shutdown'
    	}
	},
	/*方法返回值缓冲器*/
	methodCacheInterceptor : {
		type : 'net.sunfarms.z.ext.MethodCacheInterceptor',
		fields : {
			cacheManager : {refer : "cacheManager"}
		}
	}
}