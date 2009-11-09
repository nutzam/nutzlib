package org.nutz.testing;

import org.nutz.log.Log;
import org.nutz.log.LogFactory;


public class LogTestClass {

	private static Log log = LogFactory.getLog(LogTestClass.class);
	
	public void method1() {
		if (log.isInfoEnabled()) {
			log.info("test message");
		}
		
		log.info("test message", new Exception("test exception"));
	}
}
