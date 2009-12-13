package org.nutzx.siter;

import java.io.File;
import java.io.IOException;

public interface SiteRender {

	void render(File src, File dest) throws IOException;

}
