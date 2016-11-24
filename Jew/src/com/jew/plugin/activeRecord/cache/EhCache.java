package com.jew.plugin.activeRecord.cache;

import com.jew.plugin.activeRecord.ehCache.CacheKit;


/**
 * Ehcache using CacheKit to deal with the issue 
 */
public class EhCache implements ICache {
	
	@Override
	public void put(String name, String key,Object value) {
		CacheKit.put(name, key, value);
	}
	
	@Override
	public  <T> T get(String name,String key) {
		return CacheKit.getCache(name, key);
	}
	
	@Override
	public void remove(String name,String key) {
		CacheKit.remove(name, key);
	}

	@Override
	public void removeAll(String name) {
		CacheKit.removeAll(name);
	}
}
