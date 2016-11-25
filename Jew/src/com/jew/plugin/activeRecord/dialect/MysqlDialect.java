package com.jew.plugin.activeRecord.dialect;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jew.kit.StrKit;
import com.jew.plugin.activeRecord.Table;

public class MysqlDialect extends Dialect {

	@Override
	public String forTableBuilderDoBuild(String tableName) {
		return "select * from `" + tableName + "` where 1 = 2";
	}

	@Override
	public void forModelSave(Table table, StringBuilder sql, Map<String, Object> attrMap, List<Object> param) {
		sql.append("insert into " + table.getName() +"(");
		StringBuilder tempSb = new StringBuilder(")values(");
		Set<Entry<String,Object>> entrySet = attrMap.entrySet();
		for(Entry<String,Object> entry : entrySet){
			String column = entry.getKey();
			if(table.hasColumnLable(column)){
				if(param.size() > 0){
					sql.append(",");
					tempSb.append(",");
				}
				sql.append(entry.getKey());
				tempSb.append("?");
				param.add(entry.getValue());
			}
		}
		sql.append(tempSb.append(")"));
	}
	
	@Override
	public String forModelDelete(Table table) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from " + table.getName() + " where ");
		String[] keys = table.getPrimaryKeys();
		for(int i=0;i<keys.length;i++){
			if( i != 0 ){
				sb.append(" and ");
			}
			sb.append(keys[i] +" = ? ");
		}
		return sb.toString();
	}
}

