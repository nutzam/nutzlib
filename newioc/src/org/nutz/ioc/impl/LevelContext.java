package org.nutz.ioc.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.nutz.ioc.IocContext;
import org.nutz.ioc.ObjectProxy;

/**
 * 自定义级别上下文对象
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class LevelContext implements IocContext {

	private String level;
	private Map<String, ObjectProxy> objs;

	public LevelContext(String level) {
		this.level = level;
		objs = new HashMap<String, ObjectProxy>();
	}

	public ObjectProxy fetch(String name) {
		return objs.get(name);
	}

	public boolean save(String level, String name, ObjectProxy obj) {
		if (this.level.equals(level)) {
			synchronized (this) {
				if (!objs.containsKey(name)) {
					objs.put(name, obj);
				}
			}
		}
		return false;
	}

	public void clear() {
		objs.clear();
	}

	public void depose() {
		for (Entry<String, ObjectProxy> en : objs.entrySet()) {
			en.getValue().depose();
		}
		objs.clear();
	}

}
