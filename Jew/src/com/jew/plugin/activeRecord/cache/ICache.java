package com.jew.plugin.activeRecord.cache;

public interface ICache {
	
	void put(String name, String key,Object value);
	
	 <T> T  get(String name,String key);
	
	void remove(String name,String key);
	
	void removeAll(String name);
}
