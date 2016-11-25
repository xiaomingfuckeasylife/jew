package com.jew.plugin.activeRecord.ehCache;

import net.sf.ehcache.Cache;

/**
 * load data from application 
 */
public interface IDataLoader {
	
	Cache load();
	
	<T> T load(String key);
}
