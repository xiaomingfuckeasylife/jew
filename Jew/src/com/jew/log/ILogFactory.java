package com.jew.log;
/**
 * ILogFactory 
 */
public interface ILogFactory {
	
	Log getLogger(Class<?> clazz);
	
	Log getLogger(String clazzName);
	
}
