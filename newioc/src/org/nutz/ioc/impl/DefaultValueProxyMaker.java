package org.nutz.ioc.impl;

import java.util.Collection;
import java.util.Map;

import org.nutz.ioc.ValueProxy;
import org.nutz.ioc.ValueProxyMaker;
import org.nutz.ioc.meta.IocValue;
import org.nutz.ioc.val.StaticValue;

public class DefaultValueProxyMaker implements ValueProxyMaker {

	public ValueProxy make(IocValue iv) {
		Object value = iv.getValue();
		String type = iv.getType();
		// Null
		if ("null".equals(type) || null == value) {
			return new StaticValue(null);
		}
		// String, Number, .....
		else if ("normal".equals(type) || null == type) {
			// Array
			if (value.getClass().isArray()) {

			}
			// Map
			else if (value instanceof Map<?, ?>) {

			}
			// Collection
			else if (value instanceof Collection<?>) {

			}
			return new StaticValue(value);
		}
		// Refer
		else if ("refer".equals(type)) {

		}
		// Java
		else if ("java".equals(type)) {

		}
		// File
		else if ("file".equals(type)) {

		}
		// Env
		else if ("env".equals(type)) {

		}
		// Inner
		else if ("inner".equals(type)) {

		}
		return null;
	}

}
