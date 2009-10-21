package org.nutzx.robot.site;

import java.io.File;

import org.nutzx.robot.site.meta.Site;

public interface SiteLoader {

	Site load(File dir) throws Exception;
	
}
