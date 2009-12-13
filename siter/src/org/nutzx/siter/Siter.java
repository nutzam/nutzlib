package org.nutzx.siter;

import java.io.File;
import java.io.IOException;

import org.nutz.lang.Files;
import org.nutzx.siter.impl.SimpleSiteRender;

public class Siter {

	public static void main(String[] args) throws IOException {
		File src = new File(args[0]);
		File dest = new File(args[1]);

		if (!src.exists()) {
			System.err.printf("Directory '%s' doesn't exists!", src);
			System.exit(0);
		}

		if (!Files.clearDir(dest))
			Files.makeDir(dest);

		System.out.printf("Render Site '%s' \n  from: %s\n    to: %s\n", src.getName(), src, dest);
		new SimpleSiteRender().render(src, dest);
		System.out.println("Done!");
	}

}
