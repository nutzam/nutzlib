package net.sunfarms.z.forum.view;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

@IocBean
public class ForumViewMaker implements ViewMaker {

	@Override
	public View make(Ioc ioc, String type, String value) {
		if ("ftl".equalsIgnoreCase(type))
			return new FreemarkerView(value);
		return null;
	}

}
