package com.jew.plugin.c3p0;

import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Properties;

import javax.sql.DataSource;

import com.jew.kit.Prop;
import com.jew.kit.PropKit;
import com.jew.log.Log;
import com.jew.plugin.IPlugin;
import com.jew.plugin.activeRecord.IDataSourceProvider;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * c3pO plugin
 */
public class C3pOPlugin implements IPlugin, IDataSourceProvider {
	/**
	 * defines the username that will be used for the DataSource's default
	 * getConnection() method.
	 */
	private String user;
	/**
	 * defines the password that will be used for the DataSource's default
	 * getConnection() method
	 */
	private String password;
	/**
	 * The JDBC URL of the database from which Connections can and should be
	 * acquired.
	 */
	private String jdbcUrl;
	/**
	 * default driver mysql
	 */
	private String driverClass = "com.mysql.jdbc.Driver";
	/**
	 * Maximum number of Connections a pool will maintain at any given time.
	 */
	private Integer maxPoolSize = 20;
	/**
	 * Minimum number of Connections a pool will maintain at any given time.
	 */
	private Integer minPoolSize = 10;
	/**
	 * Number of Connections a pool will try to acquire upon startup. Should be
	 * between minPoolSize and maxPoolSize.
	 */
	private Integer initialPoolSize = 10;
	/**
	 * Seconds a Connection can remain pooled but unused before being discarded.
	 * Zero means idle connections never expire.
	 */
	private Integer maxIdleTime = 20;
	/**
	 * Determines how many connections at a time c3p0 will try to acquire when
	 * the pool is exhausted
	 */
	private Integer acquireIncrement = 2;
	/**
	 * c3p0 dataSource
	 */
	private ComboPooledDataSource dataSource;

	private boolean isStart = false;

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public void setMinPoolSize(Integer minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public void setInitialPoolSize(Integer initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public void setMaxIdleTime(Integer maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public void setAcquireIncrement(Integer acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public C3pOPlugin(String user, String password, String jdbcUrl) {
		this.user = user;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
	}

	public C3pOPlugin(String user, String password, String jdbcUrl, String driverClass) {
		this.user = user;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
		this.driverClass = driverClass;
	}

	public C3pOPlugin(String user, String password, String jdbcUrl, String driverClass, Integer maxIdleTime,
			Integer maxPoolSize, Integer minPoolSize, Integer initialPoolSize, Integer acquireIncrement) {
		this.user = user;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
		this.driverClass = driverClass;
		this.maxIdleTime = maxIdleTime;
		this.acquireIncrement = acquireIncrement;
		this.minPoolSize = minPoolSize;
		this.maxPoolSize = maxIdleTime;
		this.initialPoolSize = initialPoolSize;
	}

	public C3pOPlugin(File file) {
		Prop prop = PropKit.use(file);
		this.user = prop.getProperty("user");
		this.password = prop.getProperty("password");
		this.jdbcUrl = prop.getProperty("jdbcUrl");
		String driverClass = null;
		this.driverClass = driverClass = prop.getProperty("driverClass") != null ? driverClass : this.driverClass;
		Integer acquireIncrement = null;
		this.acquireIncrement = (acquireIncrement = prop.getInt("acquireIncrement") != null ? acquireIncrement
				: this.acquireIncrement);
		Integer minPoolSize = null;
		this.minPoolSize = (minPoolSize = prop.getInt("minPoolSize") != null ? minPoolSize : this.minPoolSize);
		Integer maxPoolSize = null;
		this.maxPoolSize = (maxPoolSize = prop.getInt("maxPoolSize") != null ? maxPoolSize : this.maxPoolSize);
		Integer initialPoolSize = null;
		this.initialPoolSize = (initialPoolSize = prop.getInt("initialPoolSize") != null ? initialPoolSize
				: this.initialPoolSize);
		Integer maxIdleTime = null;
		this.maxIdleTime = (maxIdleTime = prop.getInt("maxIdleTime") != null ? maxIdleTime : this.maxIdleTime);
	}
	
	public C3pOPlugin(Properties prop) {
		this.user = prop.getProperty("user");
		this.password = prop.getProperty("password");
		this.jdbcUrl = prop.getProperty("jdbcUrl");
		String driverClass = null;
		this.driverClass = driverClass = prop.getProperty("driverClass") != null ? driverClass : this.driverClass;
		Integer acquireIncrement = null;
		this.acquireIncrement = ((acquireIncrement = (Integer) prop.get("acquireIncrement")) != null ? acquireIncrement
				: this.acquireIncrement);
		Integer minPoolSize = null;
		this.minPoolSize = ((minPoolSize = (Integer) prop.get("minPoolSize")) != null ? minPoolSize : this.minPoolSize);
		Integer maxPoolSize = null;
		this.maxPoolSize = ((maxPoolSize = (Integer) prop.get("maxPoolSize")) != null ? maxPoolSize : this.maxPoolSize);
		Integer initialPoolSize = null;
		this.initialPoolSize = ((initialPoolSize = (Integer) prop.get("initialPoolSize")) != null ? initialPoolSize
				: this.initialPoolSize);
		Integer maxIdleTime = null;
		this.maxIdleTime = ((maxIdleTime = (Integer) prop.get("maxIdleTime")) != null ? maxIdleTime : this.maxIdleTime);
	}
	

	/**
	 * return DataSource for more deep manipulation
	 */
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public boolean start() {
		/**
		 * really important !! you do not want two dataSource instance in your
		 * application
		 */
		if (isStart)
			return true;
		
		dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl(jdbcUrl);
		try {
			dataSource.setDriverClass(driverClass);
		} catch (PropertyVetoException e) {
			// a good habit is clean the useless memory by yourself.
			dataSource = null;
			Log.getLog(C3pOPlugin.class).error(e.getMessage());
			throw new RuntimeException("driverClass can not be setted correctlly");
		}
		dataSource.setAcquireIncrement(acquireIncrement);
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setMinPoolSize(minPoolSize);
		dataSource.setInitialPoolSize(initialPoolSize);
		dataSource.setMaxIdleTime(maxIdleTime);
		isStart = true;
		return isStart;
	}

	@Override
	public boolean stop() {
		if (dataSource != null)
			dataSource.close();
		// trigger the GC to collect to useless dataSourcee
		dataSource = null;
		isStart = false;
		return isStart;
	}

	public boolean isStart() {
		return isStart;
	}
}
