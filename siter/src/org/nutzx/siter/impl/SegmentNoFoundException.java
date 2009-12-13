package org.nutzx.siter.impl;

public class SegmentNoFoundException extends Exception {

	private static final long serialVersionUID = -554080212848299196L;

	public SegmentNoFoundException(String name) {
		super(String.format("Fail to find ${%s}", name));
	}

}
