package org.nutz.jdk.bytes.attr;

public class ExcAttr extends Attr {

	private int excCount;
	private int excIndexTable[];

	public int getExcCount() {
		return excCount;
	}

	public void setExcCount(int excCount) {
		this.excCount = excCount;
	}

	public int[] getExcIndexTable() {
		return excIndexTable;
	}

	public void setExcIndexTable(int[] excIndexTable) {
		this.excIndexTable = excIndexTable;
	}

}
