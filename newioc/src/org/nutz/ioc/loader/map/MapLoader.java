package org.nutz.ioc.loader.map;

import java.util.Map;

import org.nutz.ioc.IocLoader;
import org.nutz.ioc.meta.IocObject;

/**
 * 从一个 Map 对象中读取配置信息，支持 Parent
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class MapLoader implements IocLoader {

	public MapLoader(Map<String, Map<String, Object>> map) {
		super();
	}

	public String[] getName() {
		return null;
	}

	public boolean has(String name) {
		return false;
	}

	public IocObject load(String name) {
		return null;
	}

}
