package com.jew.log;

public class JdkLogFactory implements ILogFactory{

	@Override
	public Log getLogger(Class<?> clazz) {
		
		return new JdkLog(clazz);
	}

	@Override
	public Log getLogger(String clazzName) {
		
		return new JdkLog(clazzName);
	}

}
