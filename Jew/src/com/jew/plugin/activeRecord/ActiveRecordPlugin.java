package com.jew.plugin.activeRecord;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jew.plugin.IPlugin;
import com.jew.plugin.activeRecord.cache.ICache;
import com.jew.plugin.activeRecord.dialect.Dialect;
/**
 * ActiveRecordPlugin
 */
public class ActiveRecordPlugin implements IPlugin{
	
	private String configName;
	private DataSource dataSource;
	private IDataSourceProvider dataProvider;
	private Integer transactionLever;
	private Dialect dialect;
	private Boolean showSql;
	private Boolean devMod;
	private List<Table> tabList = new ArrayList<Table>();
	private Boolean isStart = false;
	
	private ICache cache;
	private IContainerFactory containerFactory;
	private Config config;
	public ActiveRecordPlugin(DataSource dataSource) {
		this(DbKit.MAIN_CONFIG_NAME,dataSource);
	}
	public ActiveRecordPlugin(String configName,DataSource dataSource) {
		this(configName,dataSource,DbKit.DEFAULT_TRANSACTION_LEVER);
	}
	
	public ActiveRecordPlugin(String configName,DataSource dataSource,Integer transactionLever) {
		if(configName == null){
			throw new IllegalArgumentException("configName can not be null");
		}
		if(dataSource == null){
			throw new IllegalArgumentException("dataSource can not be null");
		}
		if(transactionLever == null  ){
			throw new IllegalArgumentException("transactionLever can not be null ");
		}
		this.configName = configName;
		this.dataSource = dataSource;
		setTransaction(transactionLever);
	}
	
	public ActiveRecordPlugin(IDataSourceProvider dataSource) {
		this(DbKit.MAIN_CONFIG_NAME,dataSource);
	}
	public ActiveRecordPlugin(String configName,IDataSourceProvider dataSource) {
		this(configName,dataSource,DbKit.DEFAULT_TRANSACTION_LEVER);
	}
	
	public ActiveRecordPlugin(String configName,IDataSourceProvider dataSource,Integer transactionLever) {
		if(configName == null){
			throw new IllegalArgumentException("configName can not be null");
		}
		if(dataSource == null){
			throw new IllegalArgumentException("dataSource can not be null");
		}
		if(transactionLever == null  ){
			throw new IllegalArgumentException("transactionLever can not be null ");
		}
		this.configName = configName;
		this.dataProvider = dataSource;
		setTransaction(transactionLever);
	}
	
	
	
	private void setTransaction(Integer transactionLever){
		if( transactionLever != 0 ||transactionLever != 1 || transactionLever != 2 || transactionLever != 4 || transactionLever != 8 ){
			throw new IllegalArgumentException("transaction lever can only be 0 , 1, 2, 4, 8");
		}
		this.transactionLever = transactionLever;
	}
	
	public ActiveRecordPlugin addMapping(String name,String primaryKeys,Class<? extends Model<?>> clazz) {
		if(name == null ){
			throw new IllegalArgumentException("table name can not be null");
		}
		if(primaryKeys == null){
			throw new IllegalArgumentException("primaryKeys can not be null");
		}
		if(clazz == null){
			throw new IllegalArgumentException("table class can not be null");
		}
		tabList.add(new Table(name,clazz,primaryKeys));
		return this;
	}
	
	public ActiveRecordPlugin setShowSql(Boolean showSql){
		if(showSql == null){
			throw new IllegalArgumentException("show sql can not be null");
		}
		this.showSql = showSql;
		return this;
	}
	
	public ActiveRecordPlugin setDevMod(Boolean devMod){
		if(devMod == null){
			throw new IllegalArgumentException("devMod can not be null");
		}
		this.devMod = devMod;
		return this;
		
	}
	
	public ActiveRecordPlugin setCache(ICache cache){
		if(cache == null){
			throw new IllegalArgumentException("cache can not be null");
		}
		this.cache = cache;
		return this;
		
	}
	
	public ActiveRecordPlugin setContainer(IContainerFactory container){
		if(container == null){
			throw new IllegalArgumentException("container can not be null");
		}
		this.containerFactory = container;
		return this;
		
	}
	/**
	 * for deleteById(...) using primary key ordered exactly like the database so 
	 * in case the order is not right . here is the emphasize the primary key order 
	 * @param tableName
	 * @param primaryKeys
	 */
	public void setPrimaryKeys(String tableName,String primaryKeys){
		for(Table tab : tabList){
			if(tab.getName().equalsIgnoreCase(tableName)){
				tab.setPrimaryKeys(primaryKeys);
			}
		}
	}
	
	public ActiveRecordPlugin setDialect(Dialect dialect){
		if(dialect == null){
			throw new IllegalArgumentException("dialect can not be null");
		}
		this.dialect = dialect;
		return this;
		
	}
	
	@Override
	public boolean start() {
		
		if(isStart){
			return true;
		}
		
		if(configName == null){
			this.configName = DbKit.MAIN_CONFIG_NAME;
		}
		
		if(dataSource == null && dataProvider != null){
			this.dataSource = dataProvider.getDataSource();
		}
		
		if(config == null){
			config = new Config(configName, dataSource);
		}
		
		if(dialect != null){
			config.dialect = dialect;
		}
		
		if(showSql != null){
			config.showSql = showSql;
		}
		
		if(devMod != null){
			config.devMod = devMod;
		}
		if(containerFactory != null){
			config.containerFactory = containerFactory;
		}
		if(cache != null){
			config.cache = cache;
		}
		new TableBuilder().build(tabList,config);
		DbKit.addConfig(config);
		/**
		 * start the Db query module .
		 */
		Db.init();
		isStart = true;
		return true;
	}

	public boolean stop() {
		isStart = false;
		return true;
	}

}
