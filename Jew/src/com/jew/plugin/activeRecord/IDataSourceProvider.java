package com.jew.plugin.activeRecord;

import javax.sql.DataSource;

public interface IDataSourceProvider {
	DataSource getDataSource();
}
