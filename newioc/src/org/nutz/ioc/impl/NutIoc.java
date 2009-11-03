package org.nutz.ioc.impl;

import org.nutz.ioc.Ioc2;
import org.nutz.ioc.IocContext;

public class NutIoc implements Ioc2 {

	public <T> T get(Class<T> type, String name, IocContext context) {
		return null;
	}

	public <T> T get(Class<T> type, String name) {
		return null;
	}

	public boolean has(String name) {
		return false;
	}

	public void depose() {}

	public void reset() {}

	public String[] getName() {
		return null;
	}

}
