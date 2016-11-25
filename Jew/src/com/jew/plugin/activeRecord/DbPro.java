package com.jew.plugin.activeRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * Professional database search class
 */
public class DbPro {
	private Config config;
	private static Map<String,DbPro> map = new HashMap<String,DbPro>();
	
	public static void removeConfigFromDbPro(String name){
		map.remove(name);
	}
}
