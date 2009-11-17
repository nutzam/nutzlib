package org.nutz.log.impl;

import org.nutz.log.Log;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * 用来做在Log内部处理时的日志输出。到System.err.
 * @author Young(sunonfire@gmail.com)
 *
 */
public class SystemLog implements Log {

	public void debug(Object message) {
		throw new NotImplementedException();
	}

	public void debug(Object message, Throwable t) {
		throw new NotImplementedException();
	}

	public void error(Object message) {
		throw new NotImplementedException();
	}

	public void error(Object message, Throwable t) {
		throw new NotImplementedException();
	}

	public void fatal(Object message) {
		System.err.println(message);
	}

	public void fatal(Object message, Throwable t) {
		System.err.println(message);
		System.err.println(t.getMessage());
		t.printStackTrace(System.err);
	}

	public void info(Object message) {
		throw new NotImplementedException();
	}

	public void info(Object message, Throwable t) {
		throw new NotImplementedException();
	}

	public boolean isDebugEnabled() {
		throw new NotImplementedException();
	}

	public boolean isErrorEnabled() {
		throw new NotImplementedException();
	}

	public boolean isFatalEnabled() {
		throw new NotImplementedException();
	}

	public boolean isInfoEnabled() {
		throw new NotImplementedException();
	}

	public boolean isTraceEnabled() {
		throw new NotImplementedException();
	}

	public boolean isWarnEnabled() {
		throw new NotImplementedException();
	}

	public void trace(Object message) {
		throw new NotImplementedException();
	}

	public void trace(Object message, Throwable t) {
		throw new NotImplementedException();
	}

	public void warn(Object message) {
		throw new NotImplementedException();
	}

	public void warn(Object message, Throwable t) {
		throw new NotImplementedException();
	}

}
