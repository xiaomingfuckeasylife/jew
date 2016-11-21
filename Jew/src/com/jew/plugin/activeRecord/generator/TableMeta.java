package com.jew.plugin.activeRecord.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * Table Meta Entity
 */
public class TableMeta {
	/**
	 * table name 
	 */
	public String name;
	/**
	 * primary keys & seperated by ,
	 */
	public String primaryKeys;
	/**
	 * remark of the table 
	 */
	public String remarks;
	/**
	 * table Columns 
	 */
	public List<ColumnMeta> columnMetas = new ArrayList<ColumnMeta>();
	
	// ----
	public String baseModelName;
	public String baseModelContent;
	public String modelName;
	public String modelContent;
	
}
