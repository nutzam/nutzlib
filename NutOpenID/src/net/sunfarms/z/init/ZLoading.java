package net.sunfarms.z.init;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.init.DefaultLoading;
import org.nutz.mvc.init.NutConfig;

public class ZLoading extends DefaultLoading {

	@Override
	public void load(NutConfig config, Class<?> mainModule) {
		super.load(config, mainModule);
		
		/*提前初始化全部bean*/
		Ioc ioc = config.getIoc();
		for (String beanName : ioc.getNames())
			ioc.get(null, beanName);
	}
}
