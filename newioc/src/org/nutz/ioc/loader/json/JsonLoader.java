package org.nutz.ioc.loader.json;

import org.nutz.ioc.IocLoader;
import org.nutz.ioc.meta.IocObject;

/**
 * 从 Json 文件中读取配置信息。 支持 Merge with parent ，利用 MapLoader
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class JsonLoader implements IocLoader {

	public JsonLoader(String... files) {
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
