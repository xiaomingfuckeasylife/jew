package com.jew.plugin.activeRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DbKit {
	/**
	 * config Object of the Model
	 */
	private static Config config;
	
	public static Config brokenConfig = Config.createBrokenConfig();
	
	/**
	 * model => config
	 */
	private static final Map<Class<? extends Model>,Config> modelToConfig = new HashMap<Class<? extends Model>,Config>();
	/**
	 * configName => config
	 */
	private static final Map<String,Config> configNameToConfig = new HashMap<String,Config>();
	
	public static final String MAIN_CONFIG_NAME = "main";
	
	public static final Integer DEFAULT_TRANSACTION_LEVER = 4;
	
	private DbKit(){}
	
	public static void addConfig(Config config){
		if(config == null){
			throw new IllegalArgumentException("config can not be null");
		}
		String configName = config.getConfigName();
		if(configName == null){
			throw new IllegalArgumentException("configName can not be null");
		}
		if(configNameToConfig.get(configName) != null){
			throw new IllegalArgumentException("config" +config.getConfigName() + " already exists");
		}
		/**
		 * DbKit hold config should be "main" config 
		 */
		if(configName.equals(MAIN_CONFIG_NAME)){
			DbKit.config = config;
		}
		/**
		 * if the first config is not "main" then the first config is the hold config of DbKit
		 */
		if(DbKit.config == null){
			DbKit.config = config;
		}
	}
	
	public static void addModelToConfigMap(Class<? extends Model> clazz, Config config){
		modelToConfig.put(clazz, config);
	}
	
	public static Config getConfig(){
		return config;
	}
	
	public static Config getConfig(String name){
		return configNameToConfig.get(name);
	}
	
	public static Config getConfig(Class<? extends Model> modClazz){
		return modelToConfig.get(modClazz);
	}
	
	public static void close(ResultSet rs, Statement state){
		try {
			if(state != null){
					state.close();
			}
			if(rs != null){
					rs.close();
			}
		} catch (SQLException e) {
			throw new ActiveRecordException(e);
		}
	}
	
	public static void close(PreparedStatement state){
		if(state != null){
			try {
				state.close();
			} catch (SQLException e) {
				throw new ActiveRecordException(e);
			}
		}
	}
	
	public static void removeConfig(Config config){
		if(config != null && config.getConfigName().equals(DbKit.config.getConfigName())){
			throw new IllegalArgumentException("config can not be removed ");
		}
		DbPro.removeConfigFromDbPro(config.getConfigName());
		configNameToConfig.remove(config.getConfigName());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Class<? extends Model> getUsefulClass(Class<? extends Model> clazz){
		return (Class<? extends Model>)((clazz.getName().indexOf("EnhancerByCGLIB") != -1) ? clazz.getClass() : clazz.getSuperclass());
	}
	
}
