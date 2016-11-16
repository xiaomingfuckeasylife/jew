package com.jew.log;

import java.util.logging.Level;

public class JdkLog extends Log{
	
	/**
	 * compose the jdk logger
	 */
	private java.util.logging.Logger logger;
	
	private String clazzName;
	
	public JdkLog(Class<?> clazz){
		this.logger = java.util.logging.Logger.getLogger(clazz.getName());
		this.clazzName = clazz.getName();
	}
	
	public JdkLog(String clazzName){
		this.logger = java.util.logging.Logger.getLogger(clazzName);
		this.clazzName = clazzName;
	}

	@Override
	public void debug(String message) {
		logger.logp(Level.FINEST,clazzName,Thread.currentThread().getStackTrace()[1].getMethodName(), message);
	}
	
	@Override
	public void debug(String message, Throwable e) {
		logger.logp(Level.FINEST,clazzName,Thread.currentThread().getStackTrace()[1].getMethodName(), message, e);
	}

	@Override
	public void info(String message) {
		logger.logp(Level.INFO,clazzName,Thread.currentThread().getStackTrace()[1].getMethodName(), message);
	}

	@Override
	public void info(String message, Throwable e) {
		logger.logp(Level.INFO,clazzName,Thread.currentThread().getStackTrace()[1].getMethodName(), message,e);
	}

	@Override
	public void warn(String message) {
		logger.logp(Level.WARNING,clazzName,Thread.currentThread().getStackTrace()[1].getMethodName(), message);
	}

	@Override
	public void warn(String message, Throwable e) {
		logger.logp(Level.WARNING,clazzName,Thread.currentThread().getStackTrace()[1].getMethodName(), message,e);		
	}

	@Override
	public void error(String message) {
		logger.logp(Level.SEVERE,clazzName,Thread.currentThread().getStackTrace()[1].getMethodName(), message);		
	}

	@Override
	public void error(String message, Throwable e) {
		logger.logp(Level.SEVERE,clazzName,Thread.currentThread().getStackTrace()[1].getMethodName(), message,e);
	}

	@Override
	public void fatal(String message) {
		logger.logp(Level.SEVERE,clazzName,Thread.currentThread().getStackTrace()[1].getMethodName(), message);
	}

	@Override
	public void fatal(String message, Throwable e) {
		logger.log(Level.SEVERE, message,e);	
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isLoggable(Level.FINEST);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isLoggable(Level.INFO);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	@Override
	public boolean isFatalEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}
}
