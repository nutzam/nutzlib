package org.nutz.jdk.bytes.attr;

public class LineNumAttr extends Attr {

	private int length;
	private LineNumEntry[] lnEntrys;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public LineNumEntry[] getLnEntrys() {
		return lnEntrys;
	}

	public void setLnEntrys(LineNumEntry[] lnEntrys) {
		this.lnEntrys = lnEntrys;
	}

}
