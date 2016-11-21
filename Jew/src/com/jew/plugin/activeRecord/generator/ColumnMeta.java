package com.jew.plugin.activeRecord.generator;
/**
 * Column Meta Entity
 */
public class ColumnMeta {
	/**
	 * column name 
	 */
	public String name;
	/**
	 * java type name
	 */
	public String javaType;
	/**
	 * column => generated attribute name
	 * <example>T_Blog => blog</example>
	 */
	public String attrName;
	
	
}
