package com.jew.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TypeConverter {

	private static final String datePattern = "yyyy-MM-dd";
	private static final String timeStampPattern = "yyyy-MM-dd hh24:mi:ss";
	private static final int datePatternLen = datePattern.length();
	
	/**
	 * test for all types of mysql
	 * 
	 * 表单提交测试结果:
	 * 1: 表单中的域,就算不输入任何内容,也会传过来 "", 也即永远不可能为 null.
	 * 2: 如果输入空格也会提交上来
	 * 3: 需要考 model中的 string属性,在传过来 "" 时是该转成 null还是不该转换,
	 *    我想, 因为用户没有输入那么肯定是 null, 而不该是 ""
	 * 
	 * 注意: 1:当type参数不为String.class, 且参数s为空串blank的情况,
	 *       此情况下转换结果为 null, 而不应该抛出异常
	 *      2:调用者需要对被转换数据做 null 判断，参见 ModelInjector 的两处调用
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(String value, Class<?> sourceType) {
		
		if("".equals(value.trim())){
			return null;
		}
		// mysql type: varchar, char, enum, set, text, tinytext, mediumtext, longtext
		if(sourceType == String.class){
			return (T) value;
		}
		// mysql type: real, double
		if (sourceType == java.lang.Double.class || sourceType == double.class) {

			return (T) Double.valueOf(value);

		}
		// mysql type: bigint
		if (sourceType == java.lang.Long.class || sourceType == long.class) {

			return (T) Long.valueOf(value);

		}
		// mysql type: float
		if (sourceType == java.lang.Float.class || sourceType == float.class) {

			return (T) Float.valueOf(value);

		}
		// mysql type: int, integer, tinyint(n) n > 1, smallint, mediumint
		if (sourceType == java.lang.Integer.class || sourceType == int.class) {

			return (T) Integer.valueOf(value);

		}
		
		if (sourceType == java.util.Date.class) {
			String format = null;
			if (value.length() == datePatternLen) {
				format = datePattern;
			} else {
				format = timeStampPattern;
			}
			
			try {
				return (T) new SimpleDateFormat(format).parse(value);
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}
		
		// mysql type: date, year
		if(sourceType == java.sql.Date.class){
			String format = null;
			if (value.length() == datePatternLen) {
				format = datePattern;
			} else {
				format = timeStampPattern;
			}
			
			try {
				return (T) new java.sql.Date(new SimpleDateFormat(format).parse(value).getTime());
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}
		
		// mysql type: time
		if(sourceType == java.sql.Time.class){
			return (T) Time.valueOf(value);
		}
		
		// mysql type: timestamp, datetime
		if(sourceType == java.sql.Timestamp.class){
			if (value.length() == datePatternLen) {
				try {
					return (T) new Timestamp(new SimpleDateFormat(datePattern).parse(value).getTime());
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				return (T) Timestamp.valueOf(value);
			}
		}
		
		// mysql type: bit, tinyint(1)
		if(sourceType == Boolean.class || sourceType == boolean.class){
			if("1".equals(value) || "true".equals(value)){
				return (T) new Boolean(true);
			}
			if("0".equals(value) || "false".equals(value)){
				return (T)new Boolean(false);
			}
			throw new IllegalArgumentException(value + " can not be cast to boolean ");
		}
		
		// mysql type: decimal, numeric
		if(sourceType == java.math.BigDecimal.class){
			return (T) new BigDecimal(value);
		}
		
		// mysql type: unsigned bigint
		if(sourceType == java.math.BigInteger.class){
			return (T)new BigInteger(value);
		}
		
		// mysql type: binary, varbinary, tinyblob, blob, mediumblob, longblob. I have not finished the test.
		if(sourceType == byte[].class){
			return (T) value.getBytes();
		}
		
		if(Config.getConstants().isDevMod()){
			throw new RuntimeException("type " + sourceType + " can not be convert please add type to  TypeConvert.class");
		}else{
			throw new RuntimeException("type " + sourceType + " is not support .");
		}
	}

}
