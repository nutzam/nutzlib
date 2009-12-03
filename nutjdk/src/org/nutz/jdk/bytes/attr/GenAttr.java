package org.nutz.jdk.bytes.attr;

public class GenAttr extends Attr {

	// u1 info[attribute_length]
	private short[] attInfos;

	public short[] getAttInfos() {
		return attInfos;
	}

	public void setAttInfos(short[] attInfos) {
		this.attInfos = attInfos;
	}

}
