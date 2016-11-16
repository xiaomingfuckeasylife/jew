package com.jew.kit;

import com.jew.log.Log;

/**
 * Logkit
 */
public class LogKit {
	/**
	 * Holding log instance 
	 */
	private static class Holder{
		private static Log log = Log.getLog(LogKit.class);
	}
	/**
	 * when Log.setDefaultLogFactory(...) is invoked 
	 * we should reset the log object instance 
	 */
	public static void synchronizeLog(){
		Holder.log = Log.getLog(LogKit.class);
	}
	
	public static void debug(String message){
		Holder.log.debug(message);
	}
	
	public static  void debug(String message,Throwable e){
		Holder.log.debug(message,e);
	}
	
	public static void info(String message){
		Holder.log.info(message);
	};
	
	public static void info(String message,Throwable e){
		Holder.log.info(message,e);
	}
	
	public static void warn(String message){
		Holder.log.warn(message);
	}
	
	public static void warn(String message,Throwable e){
		Holder.log.warn(message,e);
	}
	
	public static  void error(String message){
		Holder.log.warn(message);
	}
	
	public static void error(String message,Throwable e){
		Holder.log.error(message,e);
	}

	public static void fatal(String message){
		Holder.log.fatal(message);
	}
	
	public static void fatal(String message,Throwable e){
		Holder.log.fatal(message,e);
	}
	
	public static boolean isDebugEnabled(){
		return Holder.log.isDebugEnabled();
	}
	public static boolean isInfoEnabled(){
		return Holder.log.isInfoEnabled();
	}
	public static boolean isWarnEnabled(){
		return Holder.log.isWarnEnabled();
	}
	public static boolean isErrorEnabled(){
		return Holder.log.isErrorEnabled();
	}
	public static boolean isFatalEnabled(){
		return Holder.log.isFatalEnabled();
	}
	
}
