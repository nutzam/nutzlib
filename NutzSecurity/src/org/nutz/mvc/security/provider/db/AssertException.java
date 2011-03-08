package org.nutz.mvc.security.provider.db;

@SuppressWarnings("serial")
public class AssertException extends RuntimeException {

	public AssertException(String message) {
		super(message);
	}
	
	public static final AssertException make(String message) {
		return new AssertException(message);
	}
}