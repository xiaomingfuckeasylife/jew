package com.jew.plugin.activeRecord;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.jew.kit.LogKit;
import com.jew.kit.StrKit;
import com.jew.plugin.activeRecord.cache.EhCache;
import com.jew.plugin.activeRecord.cache.ICache;
import com.jew.plugin.activeRecord.dialect.Dialect;
import com.jew.plugin.activeRecord.dialect.MysqlDialect;

/**
 * 
 */
public class Config {
	/**
	 * every thread should has their own connection  in case of thread safe issue 
	 * transaction problems
	 */
	private static ThreadLocal<Connection> threadLocalConn = new ThreadLocal<Connection>();
		
	private String name;
	private DataSource dataSource;
	
	private Dialect dialect;
	private Boolean showSql;
	private Boolean devMod;
	private Integer transactionLever;
	
	private IContainerFactory containerFactory;
	private ICache cache;
	
	public Config(String name, DataSource dataSource, Dialect dialect, Boolean showSql, Boolean devMod,
			Integer transactionLever, IContainerFactory containerFactory, ICache cache) {
		
		if(StrKit.isBlank(name)){
			throw new IllegalArgumentException("config name can not be blank");
		}
		this.name = name;
		if(dataSource == null ){
			throw new IllegalArgumentException("dataSource can not be null ");
		}
		this.dataSource = dataSource;
		if(dialect == null ){
			throw new IllegalArgumentException("dialect can not be null");
		}
		this.dialect = dialect;
		if(containerFactory == null ){
			throw new IllegalArgumentException("containerFactory can not be null ");
		}
		this.containerFactory = containerFactory;
		if(cache == null){
			throw new IllegalArgumentException("cache can not be null");
		}
		this.cache = cache;
		
		this.showSql = showSql;
		this.devMod = devMod;
		this.transactionLever = transactionLever;
		
	}
	
	public Config(String name,DataSource dataSource){
		this(name,dataSource,new MysqlDialect());
	}
	
	public Config(String name,DataSource dataSource,Dialect dialect){
		this(name,dataSource,dialect,false,false,DbKit.DEFAULT_TRANSACTION_LEVER,IContainerFactory.defaultContainerFactory,new EhCache());
	}
	
	private Config(){}
	
	static Config createBrokenConfig()
	{
		Config config = new Config();
//		config.name = "main";
//		config.dataSource = new MysqlDataSource();
		config.dialect = new MysqlDialect();
		config.transactionLever = DbKit.DEFAULT_TRANSACTION_LEVER;
		config.devMod =false;
		config.showSql =false;
		config.cache = new EhCache();
		config.containerFactory = IContainerFactory.defaultContainerFactory;
		
		return config;
	}
	
	public String getConfigName(){
		return name;
	}
	
	public Dialect getDialect(){
		return dialect;
	}
	
	public DataSource getDataSource(){
		return dataSource;
	}
	public boolean getShowSql(){
		return showSql;
	}
	public boolean getDevMod(){
		return devMod;
	}
	
	public Integer getTransactionLever(){
		return transactionLever;
	}
	
	public IContainerFactory getContainerFactory(){
		return containerFactory;
	}
	
	public ICache getCache(){
		return cache;
	}
	
	// threadLocal  
	public void setThreadLocalConnection(Connection conn){
		threadLocalConn.set(conn);
	}
	
	public void removeThreadLocalConnection(Connection conn){
		threadLocalConn.remove();
	}
	
	public Connection getConnection() throws SQLException{
		Connection conn = threadLocalConn.get();
		if(conn != null){
			return conn;
		}
		return showSql ? new SqlReporter(dataSource.getConnection()).getConnection(): dataSource.getConnection();
	}
	
	/**
	 * check if the current Thread is on the transaction now.
	 * @return
	 */
	public boolean isInTransaction(){
		return threadLocalConn.get() != null;
	}
	
	/**
	 * when there is conn in the threadLocal which means the current connection is 
	 * in a transaction . then we can not close the transaction . until the transaction 
	 * is over . which is the <code>ThreadLoal<code> not hold any Connection 
	 * @param conn
	 */
	public void close(Connection conn){
		if(threadLocalConn.get() == null){ // not in transaction 
			if(conn != null ){
				try {
					conn.close();
				} catch (SQLException e) {
					throw new ActiveRecordException(e);
				}
			}
		}
	}
	
	public void close(Connection conn,ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				LogKit.error(e.getMessage(),e);
			}
		}
		close(conn);
	}
	
	public void close(Connection conn , ResultSet rs , Statement statement){
		
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				LogKit.error(e.getMessage(),e);
			}
		}
		close(conn, rs);
	}
	
}
