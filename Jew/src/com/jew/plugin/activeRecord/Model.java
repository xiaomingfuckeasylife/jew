package com.jew.plugin.activeRecord;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Model<M extends Model<M>> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,Object> attrMap = getAttrsMap();
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getAttrsMap(){
		Config config = getConfig();
		if(config == null){
			return DbKit.brokenConfig.containerFactory.getAttrsMap();
		}
		return config.containerFactory.getAttrsMap();
	}
	
	/**
	 * update column indicator set
	 */
	private Set<String> modifyFlagSet ;
	
	@SuppressWarnings("unchecked")
	private Set<String> getModifyFlag(){
		Config config = getConfig();
		if(config == null){
			return DbKit.brokenConfig.containerFactory.getModifyFlagSet();
		}
		return config.containerFactory.getModifyFlagSet();
	}
	
	
	private String configName = null;
	
	/**
	 * change the hole 
	 * @param configName
	 * @return
	 */
	public <M> M use(String configName){
		this.configName = configName;
		return (M) this;
	}
	
	private Config getConfig(){
		if(configName == null){
			return DbKit.getConfig(DbKit.getUsefulClass(this.getClass()));
		}
		return DbKit.getConfig(configName);
	}
	
	private Table getTable(){
		return TableMapping.me.getTable((Class<? extends Model<?>>) DbKit.getUsefulClass(this.getClass()));
	}
	
	/**
	 * set attribute to the model  
	 * @param column
	 * @param value
	 * @return
	 */
	public M set(String column ,Object value){
		Table table = getTable();
		if(table == null){   // not start activeRecord plugin 
			attrMap.put(column, value);
			modifyFlagSet.add(column);
			return (M)this;
		}
			
		if(table.hasColumnLable(column)){
				attrMap.put(column, value);
				modifyFlagSet.add(column);
				return (M)this;
		}
		
		throw new IllegalArgumentException("attribute " + column + " is not exist " );
	}
	
	/**
	 * put key value pair into map without check the attribute exists
	 * @param key
	 * @param value
	 * @return
	 */
	public M put(String key,String value){
		attrMap.put(key, value);
		return (M)this;
	}
	
	public M put(Model model){
		attrMap.putAll(model.getAttrs());
		return (M)this;
	}
	
	public M put(Map map){
		attrMap.putAll(map);
		return (M)this;
	}
	
	public Map<String,Object> getAttrs(){
		return attrMap;
	}
	
	//   The Record Method TO_DO 
	
	/**
	 * @param attr
	 * @return
	 */
	public <T> T get(String attr){
		return (T) attrMap.get(attr);
	}
	
	public <T> T get(String attr,String defaultVal){
		T t = null;
		return (t = (T) attrMap.get(attr)) == null ? (T) defaultVal  : t;
	}
	
	public String getStr(String attr){
		return String.valueOf(attrMap.get(attr));
	}
	
	public Integer getInt(String attr){
		return Integer.valueOf(attrMap.get(attr)+"");
	}
	
	public Double getDouble(String attr){
		return Double.valueOf(attr);
	}
	
	@SuppressWarnings("static-access")
	public boolean save(){
		
		Config config = getConfig();
		Table table = getTable();
		StringBuilder sql = new StringBuilder();
		List<Object> param = new ArrayList<>();
		config.dialect.forModelSave(table,sql,attrMap,param);
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = config.getConnection();
			/**
			 * the second parameter is important for retrieval the generated id .
			 */
			ps = conn.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			config.dialect.fillStatement(ps, param);
			int result = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			modifyFlagSet.clear();
			return result >= 1;
		}catch(Exception ex){
			throw new ActiveRecordException(ex);
		}finally{
			DbKit.close(ps);
			config.close(conn);
		}
	}
	
	
	private void getGeneratedKey(PreparedStatement ps , Table table) throws SQLException{
		ResultSet rs = ps.getGeneratedKeys();
		String[] primaryKeys = table.getPrimaryKeys();
		for(String str : primaryKeys){
			if(get(str) == null && rs.next()){
				Class<?> clazz = table.getColumnType(str);
				if(clazz == Integer.class || clazz == int.class){
					set(str,rs.getInt(1));
				}
				else if(clazz == Long.class || clazz == long.class){
					set(str,rs.getLong(1));
				}else {
					set(str,rs.getObject(1));
				}
			};
		}
		rs.close();
	}
	
	public boolean delete(){
		Table table = getTable();
		String[] keys = table.getPrimaryKeys();
		String[] idValues = new String[keys.length];
		for(int i=0;i<keys.length;i++){
			idValues[i] = get(keys[i]);
			if(null == idValues[i]){
				throw new IllegalArgumentException("primary key can not be null");
			}
		}
		return deleteById(idValues);
	}
	
	private boolean deleteById(Object ...idValues){
		if(idValues == null){
			throw new IllegalArgumentException("id can not be null");
		}
		Connection conn = null;
		Table table = getTable();
		Config config = getConfig();
		try{
			conn = config.getConnection();
			String sql = config.dialect.forModelDelete(table);
			return Db.update(config, conn, sql, idValues) >= 1;
		}catch(Exception ex){
			throw new ActiveRecordException(ex);
		}finally{
			config.close(conn);
		}
	}
	
}
