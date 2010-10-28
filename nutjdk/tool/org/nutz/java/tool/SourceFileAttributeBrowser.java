package org.nutz.java.tool;

import org.nutz.java.bytecode.cp.CP;

public class SourceFileAttributeBrowser implements AttributeBrowser {
	
	ByteCodeSupport bc;
	
	CP cp;

	@Override
	public void load(int[] bytes, CP cp) {
		this.cp = cp;
		bc = new ByteCodeSupport(bytes);

		bc.next(2);
		bc.dump("sourcefile_index [%s]",cp.getInfoText(bc.getInt2()));
		
	}

}
