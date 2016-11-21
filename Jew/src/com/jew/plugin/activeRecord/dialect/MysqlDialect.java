package com.jew.plugin.activeRecord.dialect;

public class MysqlDialect extends Dialect {

	@Override
	public String forTableBuilderDoBuild(String tableName) {
		return "select * from `" + tableName + "` where 1 = 2";
	}

}
