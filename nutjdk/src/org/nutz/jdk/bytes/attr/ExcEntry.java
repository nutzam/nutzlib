package org.nutz.jdk.bytes.attr;

public class ExcEntry {
	private int starPc;
	private int endPc;
	private int handlerPc;
	private int catchType;

	public int getStarPc() {
		return starPc;
	}

	public void setStarPc(int starPc) {
		this.starPc = starPc;
	}

	public int getEndPc() {
		return endPc;
	}

	public void setEndPc(int endPc) {
		this.endPc = endPc;
	}

	public int getHandlerPc() {
		return handlerPc;
	}

	public void setHandlerPc(int handlerPc) {
		this.handlerPc = handlerPc;
	}

	public int getCatchType() {
		return catchType;
	}

	public void setCatchType(int catchType) {
		this.catchType = catchType;
	}

}
