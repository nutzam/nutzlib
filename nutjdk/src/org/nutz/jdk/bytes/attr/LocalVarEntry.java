package org.nutz.jdk.bytes.attr;

public class LocalVarEntry {
	private int startPc;
	private int length;
	private int nameNndex;
	private int descriptorIndex;
	private int index;

	public int getStartPc() {
		return startPc;
	}

	public void setStartPc(int startPc) {
		this.startPc = startPc;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getNameNndex() {
		return nameNndex;
	}

	public void setNameNndex(int nameNndex) {
		this.nameNndex = nameNndex;
	}

	public int getDescriptorIndex() {
		return descriptorIndex;
	}

	public void setDescriptorIndex(int descriptorIndex) {
		this.descriptorIndex = descriptorIndex;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
