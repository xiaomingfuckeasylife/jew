package com.jew.plugin.activeRecord.generator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import com.jew.kit.StrKit;
import com.jew.plugin.activeRecord.dialect.Dialect;
import com.jew.plugin.activeRecord.dialect.MysqlDialect;

/**
 * MetaBuilder stole all the basic dataSource stuff
 */
public class MetaBuilder {
	/**
	 * we only need one dataSource did we !
	 */
	private DataSource dataSource = null;

	/**
	 * stole table that need not to create file
	 */
	private Set<String> excludedTable = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
	/**
	 * so is connection
	 */
	private Connection conn = null;
	/**
	 * so is DatabaseMetaData
	 */
	private DatabaseMetaData dbMeta = null;
	/**
	 * default dialect
	 */
	private Dialect dialect = new MysqlDialect();

	/**
	 * setting you own specific dialect
	 * 
	 * @param dialect
	 */
	public void setDialect(Dialect dialect) {
		if (dialect != null) {
			this.dialect = dialect;
		}
	}

	/**
	 * setting stuff
	 */
	private String[] removedTablePrefix = null;
	/**
	 * setting stuff
	 */
	private TypeMapping typeMapping = new TypeMapping();

	public MetaBuilder(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void addExcludedTable(String... tables) {
		if (tables != null) {
			for (int i = 0; i < tables.length; i++) {
				this.excludedTable.add(tables[i].toUpperCase());
			}
		}
	}

	public void setRemovedTablePrefix(String... prefixs) {
		this.removedTablePrefix = prefixs;
	}

	/**
	 * set your own typeMapping
	 * 
	 * @param typeMapping
	 */
	public void setTypeMapping(TypeMapping typeMapping) {
		if (typeMapping != null) {
			this.typeMapping = typeMapping;
		}
	}

	public List<TableMeta> build() {
		List<TableMeta> tabMetalists = new ArrayList<TableMeta>();
		try {
			this.conn = dataSource.getConnection();
			this.dbMeta = conn.getMetaData();

			buildTableName(tabMetalists);
			for (TableMeta tm : tabMetalists) {
				buildPrimarykeys(tm);
				buildColumnMeta(tm);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return tabMetalists;
	}

	public void buildPrimarykeys(TableMeta tm) throws SQLException {
		StringBuilder sb = new StringBuilder();
		ResultSet rs = dbMeta.getPrimaryKeys(conn.getCatalog(), null, tm.name);
		while (rs.next()) {
			sb.append(rs.getString("COLUMN_NAME"));
			if (rs.first() == false && rs.last() == false) {
				sb.append(",");
			}
			;
		}
		tm.primaryKeys = sb.toString();
		rs.close();
	};

	/**
	 * building columnMeta
	 * 
	 * @param tm
	 * @throws SQLException
	 */
	private void buildColumnMeta(TableMeta tm) throws SQLException {
		String sql = dialect.forTableBuilderDoBuild(tm.name);
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData rsMd = rs.getMetaData();
		for (int i = 1; i <= rsMd.getColumnCount(); i++) {
			ColumnMeta cm = new ColumnMeta();
			cm.name = rsMd.getColumnName(i);
			String colClassName = rsMd.getColumnClassName(i);
			String type = typeMapping.getType(colClassName);
			if (type != null) {
				cm.javaType = type;
			} else {
				int typeInt = rsMd.getColumnType(i);
				if (typeInt == Types.BINARY || typeInt == Types.VARBINARY || typeInt == Types.LONGVARBINARY
						|| typeInt == Types.BLOB) {
					cm.javaType = "byte[]";
				} else {
					cm.javaType = "java.lang.String";
				}
			}

			cm.attrName = buildAttrName(cm.name);

			tm.columnMetas.add(cm);
		}
		;
		rs.close();
		ps.close();
	}

	/**
	 * attribute name of column should be camel kind name
	 * 
	 * @param name
	 * @return
	 */
	public String buildAttrName(String name) {
		return StrKit.toCamelCase(name);
	}

	/**
	 * protected method . leave for other to rewrite the method . to customize
	 * more flexible exclude table
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean isSkipTable(String... tableName) {
		return false;
	};

	/**
	 * build table name of TableMeta
	 * 
	 * @param tabMetaLists
	 * @throws SQLException
	 */
	private void buildTableName(List<TableMeta> tabMetaLists) throws SQLException {
		ResultSet rs = getTableResultSet();
		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");
			if (excludedTable.contains(tableName)) {
				System.out.println("skip table " + tableName);
				continue;
			}
			if (isSkipTable(tableName)) {
				System.out.println("skip table " + tableName);
				continue;
			}
			TableMeta tm = new TableMeta();
			tm.name = tableName;
			tm.baseModelName = buildBaseModelName(tableName);
			tm.modelName = buildModelName(tableName);
			tm.remarks = rs.getString("REMARKS");
			tabMetaLists.add(tm);
		}
		// never forget this
		rs.close();
	}

	/**
	 * fetch connect tables resultSet
	 * 
	 * @return
	 * @throws SQLException
	 */
	private ResultSet getTableResultSet() throws SQLException {
		return this.dbMeta.getTables(conn.getCatalog(), null, null, new String[] { "TABLE", "VIEW" });
	}

	/**
	 * baseModelName
	 * 
	 * @param tableName
	 * @return
	 */
	private String buildBaseModelName(String tableName) {
		if (removedTablePrefix != null) {
			for (int i = 0; i < removedTablePrefix.length; i++) {
				if (tableName.startsWith(removedTablePrefix[i])) {
					tableName = tableName.replaceFirst(removedTablePrefix[i], "");
					break;
				}
			}
		}
		return "Base" + StrKit.upperCaseFirstLetter(StrKit.toCamelCase(tableName));
	}
	
	private String buildModelName(String tableName){
		return StrKit.upperCaseFirstLetter(buildBaseModelName(tableName).replace("Base", ""));
	}
	
}
