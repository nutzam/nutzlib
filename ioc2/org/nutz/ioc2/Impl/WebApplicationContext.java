package org.nutz.ioc2.Impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc2.IContext;
import org.nutz.ioc2.IIoc;
import org.nutz.ioc2.IScopeHandler;

public class WebApplicationContext implements IContext {

	/**
	 * 至少，应该是
	 * 
	 * <pre>
	 * ThreadLocal<Map<String,Map<String, IContext>>> locations;
	 * </pre>
	 * 
	 * 然后，
	 * 
	 * <pre>
	 * Object get(String id, String level) {
	 * 	   locations.get().get(level).get(id) ???
	 * }
	 * ....... 呃 好像完全不行。
	 * </pre>
	 * 
	 * 你的思路，主要是：
	 *  1. 当开始一个 Request 的时候，通过 Servlet ， 通知 WebApplicationContext 开始，然后传入 Request 对象
	 *  2. 当 Request 结束时，通过 Servlet 告诉  WebApplicationContext 要注销某一个 Request 对象。
	 * 
	 * 我不明白的是：
	 *  1. 为什么一定要由  WebApplicationContext 来创建 Request 对象？
	 *  2. 调用者来控制  Request 的创建和销毁时间，而 WebApplicationContext 负责具体创建和销毁，这样做有什么好处？
	 *     调用者还是参与了一个上下文环境的创建工作了啊 -- 它决定了时间。
	 *     调用者一个不慎，还是会造成容器的内存泄漏
	 *  
	 * 代码设计上的缺点：
	 *  1. 在 WebApplicationContext 只要出现 "request"，"session" 字样，它就代表着“僵硬”
	 *        虽然我们认为，僵硬一点也没什么。但是起码它很丑陋，因为你把“scope”硬编码了
	 *  2. WebApplicationContext 必须得有一个策略来索引所有自己创建的 Request，否则它就不能销毁某一个 Request 增加了复杂度
	 *     无论是调用者，还是 WebApplicationContext，复杂度都提高了。
	 * 
	 * 
	 */

	public static final String SCOPENAME_SESSION = "session";
	
	public static final String SCOPENAME_REQUEST = "request";
	
	private static final ThreadLocal<HttpSession> sessionLocation = new ThreadLocal<HttpSession>() {
		@Override
		protected HttpSession initialValue() {
			return null;
		}
	};

	private static final ThreadLocal<HttpServletRequest> requestLocation = new ThreadLocal<HttpServletRequest>() {
		@Override
		protected HttpServletRequest initialValue() {
			return null;
		}
	};

	public WebApplicationContext() {

	}

	public void registerSupportedScopeTypes(IIoc ioc) {
		ioc.registerScopeType(SCOPENAME_SESSION, new IScopeHandler(){
			
			@Override
			public void save(String id, Object obj) {
				requestLocation.get().setAttribute(id,	obj);
			}

			@Override
			public Object get(String id) {
				return requestLocation.get().getAttribute(id);
			}
		});
		
		ioc.registerScopeType(SCOPENAME_REQUEST, new IScopeHandler(){

			@Override
			public Object get(String id) {
				return sessionLocation.get().getAttribute(id);
			}

			@Override
			public void save(String id, Object obj) {
				sessionLocation.get().setAttribute(id, obj);
			}
			
		});
	}
	
	public void beginServe(HttpServletRequest request) {
		sessionLocation.set(request.getSession());

		requestLocation.set(request);
	}

	public void afterServe() {
		sessionLocation.remove();
	}
}
