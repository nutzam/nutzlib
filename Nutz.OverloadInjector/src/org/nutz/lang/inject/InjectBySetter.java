package org.nutz.lang.inject;

import java.lang.reflect.Method;

public class InjectBySetter implements Injecting {
	private Method setter;

	public InjectBySetter(Method setter) {
		this.setter = setter;
	}
	public void inject(Object obj, Object value) {
		try {
			setter.invoke(obj, value);
		}
		catch (Exception e) {
			throw new SecurityException();
		}
	}
}