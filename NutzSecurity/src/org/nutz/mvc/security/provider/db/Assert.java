package org.nutz.mvc.security.provider.db;

import org.nutz.lang.Strings;

public class Assert {

	public static void isNotEmpty(String value, String message) {
		if(Strings.isBlank(value))
			throw new AssertException(message);
	}
}

@SuppressWarnings("serial")
class AssertException extends RuntimeException {

	public AssertException(String message) {
		super(message);
	}
	
}