package com.jew.plugin.activeRecord.ehCache;

import com.jew.log.Log;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 *  CacheKit
 */
public class CacheKit {
	
	private static CacheManager cacheManager;
	private volatile static Object lock = new Object();
	private static Log log = Log.getLog(CacheKit.class);
	
	static void initCache(CacheManager cacheManager){
		CacheKit.cacheManager = cacheManager;
	}
	
	private static  Cache getOrAddIfAbsence(String name){
		if(name != null)
		{
			Cache cache = cacheManager.getCache(name);
			if(cache != null){
				return cache;
			}else{
				/**
				 * in case of the concurrent issue 
				 */
				synchronized (lock) {
					cache = cacheManager.getCache(name);
					if(cache == null){
						cacheManager.addCache(name);
						log.debug("Cache " + name +" starting to Working now");
						return cacheManager.getCache(name);
					}else{
						return cache;
					}
				}
			}
		}
		throw new IllegalArgumentException("cache name can not be null");
	}
	
	public static Cache getCache(String name){
		
		return getOrAddIfAbsence(name);
	}
	
	public static <T> T getCache(String name,String key){
		return (T) getOrAddIfAbsence(name).get(key).getObjectValue();
	}
	
	public static void remove(String name ,String key){
		Cache cache = cacheManager.getCache(name);
		if(cache != null){
			cache.remove(key);
		}
	}
	
	public static void removeAll(String name){
		if(name != null){
			cacheManager.removeCache(name);
		}
	}
	
	public static void put(String name,String key ,Object value){
		getOrAddIfAbsence(name).put(new Element(key,value));;
	}
	
}
