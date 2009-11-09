package org.nutz.log.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.nutz.log.Log;
import org.nutz.log.LogAdapter;

public class Log4jAdapter implements Log, LogAdapter {

	public static final String LOG4J_CLASS_NAME = "org.apache.log4j.Logger";

	Object log4jImpl = null;
	
	boolean isFatalEnabled = true;
	
	Method fatalMessageMethod = null;
	
	Method fatalMessageThrowableMethod = null;
	
	boolean isErrorEnabled = true;
	
	Method errorObjectMethod = null;
	
	Method errorObjectThrowableMethod = null;
	
	boolean isWarningEnabled = true;
	
	Method warningObjectMethod= null;
	
	Method warningObjectThrowableMethod = null;
	
	boolean isInfoEnabled = false;
	
	Method infoObjectMethod = null;
	
	Method infoObjectThrowableMethod = null;
	
	boolean isDebugEnabled = false;
	
	boolean isTraceEnabled = false;
	
	public Log4jAdapter() {
		
	}
	
	private Log4jAdapter(String className) {
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		try {
			Class logClass = Class.forName(LOG4J_CLASS_NAME, true, classLoader);
			
			Method getLoggerMethod = logClass.getMethod("getLogger", new Class[]{String.class});
			
			log4jImpl = getLoggerMethod.invoke(null, new Object[]{className});
			
			isInfoEnabled = ((Boolean)logClass.getMethod("isInfoEnabled", new Class[]{})
					.invoke(log4jImpl, null)).booleanValue();
			
			if (isInfoEnabled) {
				infoObjectMethod = logClass.getMethod("info", new Class[]{Object.class});
				
				infoObjectThrowableMethod = logClass.getMethod("info", 
						new Class[]{Object.class, Throwable.class});
			}
			
		} catch (ClassNotFoundException e) {
			//function should be called before calling this function, this is not going to happen...
			e.printStackTrace();
			return;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.nutz.log.LogAdapter#canWork()
	 */
	public boolean canWork() {
		try {
			Class.forName(LOG4J_CLASS_NAME, true, Thread.currentThread().getContextClassLoader());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
	@Override
	public void debug(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatal(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatal(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(Object message) {
		if (isInfoEnabled) {
			try {
				infoObjectMethod.invoke(log4jImpl, new Object[]{message});
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void info(Object message, Throwable t) {
		if (isInfoEnabled) {
			try {
				infoObjectThrowableMethod.invoke(log4jImpl, new Object[]{message, t});
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isDebugEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isErrorEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFatalEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInfoEnabled() {
		return isInfoEnabled;
	}

	@Override
	public boolean isTraceEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWarningEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void trace(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warning(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warning(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Log getLogger(String className) {
		return new Log4jAdapter(className);
	}

}
