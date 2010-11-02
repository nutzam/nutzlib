package net.sunfarms.z.wiki.view;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

public class WikiViewMarker implements ViewMaker {

	@Override
	public View make(Ioc ioc, String type, String value) {
		if ("zdoc".equals(type)){
			return new ZDocView();
		}
		return null;
	}

}
