package org.nutz.jdk.bytes.attr;

public class CstAttr extends Attr {

	// u2 constantvalue_index;
	private int cvIndex;

	public int getCvIndex() {
		return cvIndex;
	}

	public void setCvIndex(int cvIndex) {
		this.cvIndex = cvIndex;
	}

}
