package org.nutzx.robot.site;

import java.io.File;
import java.util.Map;

import org.nutzx.robot.site.meta.Page;

public interface PageRender {
	
	void render(Map<String, String> params, File dest, Page page) throws Exception;

}
