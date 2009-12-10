package org.nutz.java.tool;

import static java.lang.System.*;

public class CodeAttributeBrowser implements AttributeBrowser {

	public void load(int[] bytes) {
		out.printf("-'Code'- %dbytes\n", bytes.length);
	}

}
