package com.jew.kit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jew.core.Const;
/**
 * Prop 
 */
public class Prop {
	
	private Properties prop = null;
	
	/**
	 * load file with default charset 
	 * @param fileName
	 */
	public Prop(String fileName){
		this(fileName,Const.DEFAULT_CHARSET);
	}
	
	/**
	 * Prop constructor
	 * <example>
	 * 	Prop p = new Prop("a_litter_cofig.properties");
	 *  p.get("key");
	 *  Prop p = new Prop("com/jew/config/a_litter_cofig.properties");
	 *  p.get("key");
	 * </example>
	 * load properties 
	 * @param fileName file to be loaded 
	 * @param charset  specific charset to read the file 
	 * @throws Exception if the file is not been found there will be a exception threw
	 */
	public Prop(String fileName,String charset){
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		try{
			if(is == null ){
				throw new Exception("file "+ fileName + " can not be found ");
			}
			prop = new Properties();
			prop.load(new InputStreamReader(is,charset));
		}catch(Exception ex){
			throw new RuntimeException(ex.getMessage());
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					LogKit.error(e.getMessage(),e);
				}
			}
		}
	}
	
	public Prop(File file){
		this(file,Const.DEFAULT_CHARSET);
	}
	
	/**
	 * load file with specific charset
	 * @param fileName fileName of file 
	 * @param charset the encoding of the file
	 */
	public Prop(File fileName,String charset){
		// it is really important to check the source before process
		if(fileName == null)
			throw new IllegalArgumentException("file can not be null");
		if(false == fileName.isFile())
			throw new IllegalArgumentException("file is not found " + fileName.getName());
		
		InputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(new InputStreamReader(fis,charset));
		} catch (Exception e) {
			throw new RuntimeException("file " + fileName + " is not been found");
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					LogKit.error(e.getMessage());
				}
			}
		}
	}
	
	public String getProperty(String key){
		return prop.getProperty(key);
	}
	
	public String getProperty(String key,String defaultVal){
		String value = prop.getProperty(key);
		if(value != null)
			return value;
		return defaultVal;
	}
	
	public Integer getInt(String key){
		return getInt(key,null);
	}
	
	public Integer getInt(String key,Integer defaultVal){
		
		String propVal = getProperty(key);
		if(propVal != null)
			return Integer.valueOf(propVal);
		return defaultVal;
	}
	
	public Long getLong(String key){
		return getLong(key, null);
	}
	
	public Long getLong(String key,Long defaultVal){
		String strVal = getProperty(key);
		if(strVal != null)
			return Long.valueOf(key);
		return defaultVal;
	}
	
	public Boolean getBoolean(String key){
		return getBoolean(key,null);
	}
	
	public Boolean getBoolean(String key,Boolean defaultVal){
		
		String strVal = getProperty(key);
		
		if("true".equals(strVal.trim())){
			return true;
		}
		
		if("false".equals(strVal.trim())){
			return false;
		}	
		if(defaultVal != null){
			return defaultVal;
		}
		throw new RuntimeException(strVal + " can not be cust to boolean");
	}
	
	public boolean containsKey(String key){
		return prop.containsKey(key);
	}
	
	public Properties getProperties(){
		return prop;
	}
}
