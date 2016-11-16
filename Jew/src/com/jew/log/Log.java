package com.jew.log;
/**
 * 
 * @author clark
 * 
 * Nov 16, 2016
 * 
 * The five logging levers used by Log are (in order)
 * 	1 debug (the least serious) 
 *  2 info 
 *  3 warn
 *  4 error
 *  5 fatal (the most serious)
 */
public abstract class Log {
	
	private static ILogFactory defaultFactory = null;
	
	static {
		init();
	}
	
	static void init(){
		if(defaultFactory == null){
			try {
				/**
				 * check if log4j class exist
				 */
				Class.forName("org.apache.log4j.Logger");
				Class<?> log4jLogFactoryClass = Class.forName("com.jew.log.Log4jLogFactory");
				defaultFactory = (ILogFactory) log4jLogFactoryClass.newInstance();
			} catch (Exception e) {
				/**
				 * if there is no log4j package using the jdk logger
				 */
				defaultFactory = new JdkLogFactory();
			}
		}
	}
	
	public static void setDefaultLogFactory(ILogFactory defaultLogFactory) throws Exception{
		/**
		 * check the value 
		 */
		if(defaultLogFactory == null){
			throw new Exception("defaultLogFacotry can not be null");
		}
		defaultFactory = defaultLogFactory;
	}
	
	public static Log getLog(Class<?> clazz){
		return defaultFactory.getLogger(clazz);
	}
	
	public static Log getLog(String clazzName){
		return defaultFactory.getLogger(clazzName);
	}
	
	public abstract void debug(String message);
	
	public abstract void debug(String message,Throwable e);
	
	public abstract void info(String message);
	
	public abstract void info(String message,Throwable e);
	
	public abstract void warn(String message);
	
	public abstract void warn(String message,Throwable e);
	
	public abstract void error(String message);
	
	public abstract void error(String message,Throwable e);

	public abstract void fatal(String message);
	
	public abstract void fatal(String message,Throwable e);
	
	public abstract boolean isDebugEnabled();
	public abstract boolean isInfoEnabled();
	public abstract boolean isWarnEnabled();
	public abstract boolean isErrorEnabled();
	public abstract boolean isFatalEnabled();
}