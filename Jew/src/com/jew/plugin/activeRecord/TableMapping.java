package com.jew.plugin.activeRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * table Mapping
 */
public final class TableMapping {
	
	public static TableMapping me = new TableMapping();
	
	private Map<Class<? extends Model<?>>,Table> modelToTableMap = new HashMap<>();
	
	private TableMapping(){}
	
	public Table getTable(Class<? extends Model<?>> clazz){
		return modelToTableMap.get(clazz);
	}
	
	public void addModelToTable(Class<? extends Model<?>> clazz,Table table){
		modelToTableMap.put(clazz, table);
	}
	
}
