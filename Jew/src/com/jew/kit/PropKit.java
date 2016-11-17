package com.jew.kit;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import com.jew.core.Const;

/**
 * PropKit : load file from CLASSPATH or file Object
 */
public class PropKit {
	/**
	 * stole the file that could use <code>PropKit.getInt("key")</code>
	 */
	private static Prop prop;
	/**
	 * stole all the loaded Prop file
	 */
	private static ConcurrentHashMap<String, Prop> propMap = new ConcurrentHashMap<>();

	/**
	 * load file to use if file is not loaded before 
	 * 
	 * @param fileName
	 */
	public static Prop use(String fileName) {
		return use(fileName, Const.DEFAULT_CHARSET);
	}
	/**
	 * load file to use if file is not loaded before
	 * <example>
	 * 		PropKit.use("a_litter_config.properties");
	 * or 	PropKit.use("com/jew/config/a_litter_config.properties");
	 * </example>
	 * @param fileName
	 * @param charset
	 * @return
	 */
	public static Prop use(String fileName, String charset) {
		Prop result = propMap.get(fileName);
		if (result == null) {
			result = new Prop(fileName, charset);
			propMap.put(fileName, result);
			if (prop == null) {
				prop = result;
			}
		}
		return result;
	}
	
	public static Prop use(File file) {
		return use(file, Const.DEFAULT_CHARSET);
	}

	public static Prop use(File file, String charset) {
		Prop result = propMap.get(file.getName());
		if (result == null) {
			result = new Prop(file, charset);
			propMap.put(file.getName(), result);
			if (prop == null) {
				prop = result;
			}
		}
		return result;
	}

	/**
	 * remove the useless file from hashMap
	 * and the next loaded file will be the current Prop file 
	 * @param fileName
	 */
	public static void useless(String fileName) {
		Prop deletedProp = propMap.remove(fileName);
		if (prop == deletedProp) {
			prop = null;
		}
	}
	
	public static String getProperties(String key) {
		return prop.getProperty(key);
	}

	public static String getProperties(String key, String defaultVal) {
		return prop.getProperty(key, defaultVal);
	}

	public static Integer getInt(String key) {
		return prop.getInt(key);
	}

	public static Integer getInt(String key, int defaultVal) {
		return prop.getInt(key, defaultVal);
	}

	public static Long getLong(String key) {
		return prop.getLong(key);
	}

	public static Long getLong(String key, long defaultVal) {
		return prop.getLong(key, defaultVal);
	}

	public static Boolean getBoolean(String key) {
		return prop.getBoolean(key);
	}

	public static Boolean getBoolean(String key, boolean defaultVal) {
		return prop.getBoolean(key, defaultVal);
	}

	public static Boolean containsKey(String key) {
		return prop.containsKey(key);
	}

	public static Prop getProp() {
		return prop;
	}

	public static Prop getProp(String key) {
		return propMap.get(key);
	}

	public static void clear() {
		propMap.clear();
	}
}
