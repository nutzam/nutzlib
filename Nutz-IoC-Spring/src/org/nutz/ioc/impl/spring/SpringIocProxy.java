package org.nutz.ioc.impl.spring;

import javax.servlet.ServletConfig;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.IocProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 简单实现Nutz.IoC-Spring桥
 * <p/>Need Spring 2.0
 * @author wendal chen(wendal1985@gmail.com)
 *
 */
public class SpringIocProxy implements Ioc{
	
	private ApplicationContext applicationContext;

	public static class Factory implements IocProvider{

		@Override
		public Ioc create(ServletConfig config, String[] args) {
			SpringIocProxy ioc = new SpringIocProxy();
			if(config == null)
				ioc.applicationContext = new ClassPathXmlApplicationContext(args);
			else
				ioc.applicationContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
			return ioc;
		}
	}

	@Override
	public void depose() {
		applicationContext.publishEvent(new ContextClosedEvent(applicationContext));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> type, String name) {
		return (T) applicationContext.getBean(name, type);
	}

	@Override
	public String[] getName() {
		return applicationContext.getBeanDefinitionNames();
	}

	@Override
	public boolean has(String name) {
		return applicationContext.containsBean(name);
	}

	@Override
	public void reset() {
		applicationContext.publishEvent(new ContextRefreshedEvent(applicationContext));
	}
}
