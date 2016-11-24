package com.jew.plugin.activeRecord.ehCache;

import java.io.InputStream;
import java.net.URL;

import com.jew.plugin.IPlugin;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
/**
 * EhCachePlugin
 */
public class EhCachePlugin implements IPlugin{
	
	private CacheManager cacheManager;
	private Configuration configuration;
	private String configureFileName;
	private URL url;
	private InputStream is;
	
	public EhCachePlugin(){
		
	}
	
	public EhCachePlugin(Configuration config) {
		this.configuration = config;
	}
	public EhCachePlugin(String configureFileName) {
		this.configureFileName = configureFileName;
	}
	public EhCachePlugin(URL url){
		this.url = url;
	}
	public EhCachePlugin(InputStream is){
		this.is = is;
	}
	
	@SuppressWarnings("static-access")
	private void create(){
		/**
		 * when we create something . we should always check if it is already exists 
		 */
		if(cacheManager != null){
			return ;
		}
		
		if(configuration != null){
			cacheManager.create(configuration);
			return ;
		}
		if(configureFileName != null){
			cacheManager.create(configureFileName);
			return ;
		}
		if(url != null){
			cacheManager.create(url);
			return ;
		}
		if(is != null){
			cacheManager.create(is);
			return ;
		}
		cacheManager.create();
	}
	
	@Override
	public boolean start() {
		create();
		CacheKit.initCache(cacheManager);
		return true;
	}

	@Override
	public boolean stop() {
		cacheManager.shutdown();
		return true;
	}
	
}
