package org.nutz.ioc.loader.map;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.IocLoader;
import org.nutz.ioc.loader.Loaders;
import org.nutz.ioc.meta.IocObject;

/**
 * 从一个 Map 对象中读取配置信息，支持 Parent
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class MapLoader implements IocLoader {

	private Map<String, Map<String, Object>> map;

	protected MapLoader() {
		map = new HashMap<String, Map<String, Object>>();
	}

	public MapLoader(Map<String, Map<String, Object>> map) {
		this.map = map;
	}

	public Map<String, Map<String, Object>> getMap() {
		return map;
	}

	public void setMap(Map<String, Map<String, Object>> map) {
		this.map = map;
	}

	public String[] getName() {
		return map.keySet().toArray(new String[map.size()]);
	}

	public boolean has(String name) {
		return map.containsKey(name);
	}

	public IocObject load(String name) {
		return Loaders.map2obj(map.get(name));
	}

}
