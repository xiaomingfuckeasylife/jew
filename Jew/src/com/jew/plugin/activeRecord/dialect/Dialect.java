package com.jew.plugin.activeRecord.dialect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jew.kit.LogKit;
import com.jew.plugin.activeRecord.Table;

/**
 * dialect
 */
public abstract class Dialect {
	
	// common dialect
	public abstract String forTableBuilderDoBuild(String tableName);
	
	
	// model dialect
	public abstract void forModelSave(Table table,StringBuilder sql,Map<String,Object> attrMap,List<Object> param);
	public abstract String forModelDelete(Table table);
	
	public static void fillStatement(PreparedStatement ps,List<Object> param){
		for(int i=0;i<param.size();i++){
			try {
				ps.setObject(i, param.get(i));
			} catch (SQLException e) {
				LogKit.error(e.getMessage(), e);
			}
		}
	}
	
	
}
