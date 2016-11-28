package com.jew.plugin.activeRecord;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class JavaType {
	@SuppressWarnings("serial")
	private Map<String,Class<?>> mapping = new HashMap<String,Class<?>>(){
		{
			put("java.lang.Integer", Integer.class);
			put("java.lang.Double", Double.class);
			put("java.lang.Long", Long.class);
			put("java.lang.String", String.class);
			put("java.sql.Date",java.sql.Date.class);
			put("java.sql.Time",java.sql.Time.class);
			put("[B",byte[].class);
			put("java.lang.float",float.class);
			put("java.math.BigDecimal",BigDecimal.class);
			put("java.math.BigInteger",BigInteger.class);
			put("java.lang.Boolean",Boolean.class);
			put("java.sql.Timestamp",Timestamp.class);
		}
	};
	
	public Class<?> getType(String type){
		return mapping.get(type);
	}
}
