package org.nutz.ioc.val;

import org.nutz.ioc.IocMaking;
import org.nutz.ioc.ValueProxy;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

public class ReferValue implements ValueProxy {

	private String name;
	private Class<?> type;

	public ReferValue(String name) {
		int pos = name.indexOf(':');
		if (pos < 0) {
			this.name = Strings.trim(name);
		} else {
			this.name = Strings.trim(name.substring(0, pos));
			try {
				String typeName = Strings.trim(name.substring(pos + 1));
				this.type = Class.forName(typeName);
			} catch (ClassNotFoundException e) {
				throw Lang.wrapThrow(e);
			}
		}
	}

	public Object get(IocMaking ing) {
		return ing.getIoc().get(type, name);
	}

}
