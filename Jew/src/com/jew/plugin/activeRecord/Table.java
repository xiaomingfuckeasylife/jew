package com.jew.plugin.activeRecord;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
/**
 * Table . 
 */
public class Table {
	
	private String name;
	private String[] primaryKeys = null;
	private Map<String,Class<?>> columnTypeMap; // config.brokenConfig().getColumnsAttr();
	
	private Class<? extends Model<?>> modelClass;
	
	public Table(String name,Class<? extends Model<?>> modelClass){
		if(name == null){
			throw new IllegalArgumentException(" table name can not be null");
		}
		if(modelClass == null){
			throw new IllegalArgumentException(" modelClass can not be null");
		}
		this.name = name;
		this.modelClass = modelClass;
	}
	
	public Table(String name,Class<? extends Model<?>> modelClass,String primaryKeys){
		if(name == null){
			throw new IllegalArgumentException(" table name can not be null");
		}
		if(modelClass == null){
			throw new IllegalArgumentException(" modelClass can not be null");
		}
		if(primaryKeys == null){
			throw new IllegalArgumentException("primaryKeys can not be null");
		}
		this.name = name;
		this.modelClass = modelClass;
		this.primaryKeys = setPrimaryKeys(primaryKeys);
	}
	
	String[] setPrimaryKeys(String primaryKeys){
		return primaryKeys.split(",");
	}
	
	void setColumnTypeMap(Map<String,Class<?>> columnTypeMap) {
		this.columnTypeMap = columnTypeMap;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setColumnType(String column,Class<?> classType){
		columnTypeMap.put(column, classType);
	}
	
	public Class<?> getColumnType(String column){
		return columnTypeMap.get(column);
	}
	
	public boolean hasColumnLable(String column){
		return columnTypeMap.containsKey(column);
	}
	
	public String[] getPrimaryKeys(){
		return primaryKeys;
	}

	public Class<? extends Model<?>> getModelClass(){
		return modelClass;
	}
	
	/**
	 * once you get the columnTypeMap you can not change it .
	 * @return
	 */
	public Map<String,Class<?>> getColumnTypeMap(){
		return Collections.unmodifiableMap(columnTypeMap);
	}
	
	public Set<Entry<String,Class<?>>> getColumnTypeMapEntrySet(){
		return Collections.unmodifiableSet(columnTypeMap.entrySet());
	}
}
