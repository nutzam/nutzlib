package org.nutz.ioc.impl.spring;

import javax.servlet.ServletConfig;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.IocProvider;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author wendal chen(wendal1985@gmail.com)
 *
 */
public class SpringIocProvider implements IocProvider{

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
