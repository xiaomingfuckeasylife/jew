package com.jew.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class Log4jLog extends Log{
	/**
	 * compose log4j
	 */
	private org.apache.log4j.Logger logger;
	
	private static final String callerFQCN = Log4jLog.class.getName();
	
	public Log4jLog(Class<?> clazz){
		this.logger = Logger.getLogger(clazz);
	}
	
	public Log4jLog(String clazzName){
		this.logger = Logger.getLogger(clazzName);
	}

	@Override
	public void debug(String message) {
		logger.log(callerFQCN, Level.DEBUG, message,null);
	}

	@Override
	public void debug(String message, Throwable e) {
		logger.log(callerFQCN, Level.DEBUG, message,e);
	}

	@Override
	public void info(String message) {
		logger.log(callerFQCN, Level.INFO, message,null);
	}

	@Override
	public void info(String message, Throwable e) {
		logger.log(callerFQCN, Level.INFO, message,e);
	}

	@Override
	public void warn(String message) {
		logger.log(callerFQCN, Level.WARN, message,null);
	}

	@Override
	public void warn(String message, Throwable e) {
		logger.log(callerFQCN, Level.WARN, message,e);
	}

	@Override
	public void error(String message) {
		logger.log(callerFQCN, Level.ERROR, message,null);
	}

	@Override
	public void error(String message, Throwable e) {
		logger.log(callerFQCN, Level.ERROR, message,e);
	}

	@Override
	public void fatal(String message) {
		logger.log(callerFQCN, Level.FATAL, message,null);
	}

	@Override
	public void fatal(String message, Throwable e) {
		logger.log(callerFQCN, Level.FATAL, message,e);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isEnabledFor(Priority.WARN);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isEnabledFor(Priority.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return logger.isEnabledFor(Priority.FATAL);
	}
	
}
