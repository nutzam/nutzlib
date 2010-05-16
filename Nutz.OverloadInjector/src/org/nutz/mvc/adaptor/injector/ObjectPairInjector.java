package org.nutz.mvc.adaptor.injector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;
import org.nutz.lang.inject.Injecting;
import org.nutz.mvc.adaptor.ParamInjector;
import org.nutz.mvc.annotation.Param;

public class ObjectPairInjector implements ParamInjector {
	private Map<String, List<Injecting>> injss = new HashMap<String, List<Injecting>>();
	private Mirror<?> mirror;

	public ObjectPairInjector(String prefix, Class<?> type) {
		this.mirror = Mirror.me(type);
		for(Field f : mirror.getFields()){
			injss.put(parseFieldName(prefix, f), mirror.getInjecting(f.getName()));
		}
	}
	/**
	 * 转换属性名
	 * @param prefix
	 * @param f
	 * @return
	 */
	private String parseFieldName(String prefix, Field f){
		prefix = Strings.isBlank(prefix) ? "" : Strings.trim(prefix);
		Param param = f.getAnnotation(Param.class);
		String nm = null == param ? f.getName() : param.value();
		return prefix + nm;
	}
	
	public Object get(HttpServletRequest req, HttpServletResponse resp, Object refer) {
		Object obj = mirror.born();
		for(String key : injss.keySet()){
			if(req.getParameterMap().containsKey(key)){
				run(injss.get(key), obj, req.getParameter(key));
			}
		}
		return obj;
	}
	/**
	 * 执行注入
	 * @param ijs
	 * @param obj
	 * @param value
	 */
	private void run(List<Injecting> ijs, Object obj, Object value){
		for(Injecting in : ijs){
			try{
				in.inject(obj, value);
				return;
			} catch(SecurityException e){
				continue;
			}
		}
		throw Lang.makeThrow(	"Fail to set '%s' by setter  because: ", value);
	}
}
