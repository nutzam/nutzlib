package org.nutz.mvc.security.provider.db;

import org.nutz.lang.Strings;

public class Assert {

	public static void isNotEmpty(String value, String message) {
		if(Strings.isBlank(value))
			throw new AssertException(message);
	}

	public static void isNotNull(Object obj, String message) {
		if(null == obj)
			throw new AssertException(message);
	}
	
	public static void isNotEqual(Object obj, Object obj2, String message) {
		if(obj == obj2)
			throw new AssertException(message);
		if(obj != null && obj.equals(obj2))
			throw new AssertException(message);
	}
}