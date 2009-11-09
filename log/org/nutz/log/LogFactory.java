package org.nutz.log;

import java.util.LinkedList;
import java.util.List;

import org.nutz.log.impl.Log4jAdapter;
import org.nutz.log.impl.NullLog;

public class LogFactory {
	
	static Log nullLog = new NullLog();
	
	static LogAdapter workableAdapter = null;
	
	static List<LogAdapter> adapters = null;
	
	static boolean notFound = false;
	
	static {
		registerLogAdapter(new Log4jAdapter());
	}
	
	public static void registerLogAdapter(LogAdapter adapter) {
		if (adapters == null)
			adapters = new LinkedList<LogAdapter>();
		
		adapters.add(adapter);
	}
	
	public static Log getLog(Class clazz){
		return getLog(clazz.getName());
	}
	
	public static Log getLog(String className) {
		if (workableAdapter != null) {
			return workableAdapter.getLogger(className);
		}
		
		if (notFound || adapters == null) {
			return nullLog;
		}
		
		for (LogAdapter adapter : adapters) {
			if (adapter.canWork()) {
				workableAdapter = adapter;
				
				return workableAdapter.getLogger(className);
			}
		}
		
		//to avoid searching again...
		notFound = true;
		
		return nullLog;
	}
}
