package com.jew.plugin.activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * TableBuilder
 */
public class TableBuilder {
	private JavaType javaType = new JavaType();

	public void build(List<Table> tabList, Config config) {
		if (tabList.size() == 0) {
			return;
		}

		Connection conn = null;
		try {
			conn = config.getConnection();
			for (Table tab : tabList) {
				doBuild(tab, conn, config);
				/**
				 * connected TableMapping modelClass and table
				 */
				TableMapping.me.putTable(tab);
				/**
				 * connected the model class and config 
				 */
				DbKit.addModelToConfigMap(tab.getModelClass(), config);
			}
		} catch (SQLException e) {
			throw new ActiveRecordException(e);
		} finally {
			config.close(conn);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void doBuild(Table tab, Connection conn, Config config) throws SQLException {
		tab.setColumnTypeMap(IContainerFactory.defaultContainerFactory.getColumnsMap());
		String sql = config.dialect.forTableBuilderDoBuild(tab.getName());
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for (int i = 1; i <= count; i++) {
			String columnName = rsmd.getColumnName(i);
			String columnClassName = rsmd.getColumnClassName(i);
			Class<?> classType = javaType.getType(columnClassName);
			if (classType == null) {
				int type = rsmd.getColumnType(i);
				if (type == Types.BINARY || type == Types.LONGVARBINARY || type == Types.VARBINARY) {
					classType = byte[].class;
				} else {
					classType = String.class;
				}
			}
			tab.getColumnTypeMap().put(columnName, classType);
		}
		ps.close();
		rs.close();
	}
	
}
