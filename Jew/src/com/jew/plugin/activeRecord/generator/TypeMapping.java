package com.jew.plugin.activeRecord.generator;

import java.util.HashMap;
import java.util.Map;
/**
 * TypeMapping 建立起 ResultSetMetaData.getColumnClassName(i)到 java类型的映射关系
 * 特别注意所有时间型类型全部映射为 java.util.Date，可通过继承扩展该类来调整映射满足特殊需求
 * notice this is not a kit class so this place we are not make the method static . and we make the map protected . so if there are any error match your database 
 * you should be able to rewrite the map 
 */
public class TypeMapping {
	
	public TypeMapping(){
		
	}
	
	@SuppressWarnings("serial")
	protected Map<String,String> map = new HashMap<String,String>(){{
		// java.util.Data can not be returned
		// java.sql.Date, java.sql.Time, java.sql.Timestamp all extends java.util.Data so getDate can return the three types data
		// put("java.util.Date", "java.util.Date");
		
		// date, year
		put("java.sql.Date", "java.util.Date");
		
		// time
		put("java.sql.Time", "java.util.Date");
		
		// timestamp, datetime
		put("java.sql.Timestamp", "java.util.Date");
		
		// binary, varbinary, tinyblob, blob, mediumblob, longblob
		// qjd project: print_info.content varbinary(61800);
		put("[B", "byte[]");
		
		// ---------
		
		// varchar, char, enum, set, text, tinytext, mediumtext, longtext
		put("java.lang.String", "java.lang.String");
		
		// int, integer, tinyint, smallint, mediumint
		put("java.lang.Integer", "java.lang.Integer");
		
		// bigint
		put("java.lang.Long", "java.lang.Long");
		
		// real, double
		put("java.lang.Double", "java.lang.Double");
		
		// float
		put("java.lang.Float", "java.lang.Float");
		
		// bit
		put("java.lang.Boolean", "java.lang.Boolean");
		
		// decimal, numeric
		put("java.math.BigDecimal", "java.math.BigDecimal");
		
		// unsigned bigint
		put("java.math.BigInteger", "java.math.BigInteger");
	}};
	
	public String getType(String type){
		return map.get(type);
	}
	
}
