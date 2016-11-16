package com.jew.log;

import com.jew.kit.LogKit;

/**
 * logManager : singleton
 */
public class LogManager {
	
	private static LogManager me = new LogManager();
	
	public static LogManager me(){
		return me;
	}
	
	private LogManager(){}
	
	public void setDefaultLogFactory(ILogFactory defaultLogFactory) throws Exception{
		Log.setDefaultLogFactory(defaultLogFactory);
		LogKit.synchronizeLog();
	}
	
}
