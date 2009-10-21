package org.nutzx.robot.site;

import java.io.File;

import org.nutzx.robot.site.impl.DefaultSiteLoader;
import org.nutzx.robot.site.meta.RenderInfo;
import org.nutzx.robot.site.meta.Site;

public class SiteRobot {

	public void transform(File src, File dest) throws Exception {
		Site site = new DefaultSiteLoader().load(src);
		for (RenderInfo info : site.getRenders())
			info.getRender().render(null, dest, info.getPage());
	}

}
