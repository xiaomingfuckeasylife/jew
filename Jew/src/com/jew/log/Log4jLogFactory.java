package com.jew.log;

public class Log4jLogFactory implements ILogFactory{

	@Override
	public Log getLogger(Class<?> clazz) {
		return new Log4jLog(clazz);
	}

	@Override
	public Log getLogger(String clazzName) {
		return new Log4jLog(clazzName);
	}

}
