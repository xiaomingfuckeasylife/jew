package com.jew.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.jew.kit.StrKit;
import com.jew.plugin.activeRecord.Model;
import com.jew.plugin.activeRecord.Table;
import com.jew.plugin.activeRecord.TableMapping;

public class Injecter {

	private static Object createInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static <T> T injectBean(Class<?> clazz, String className,boolean faultTolerant,
			HttpServletRequest request) {
		Object bean = createInstance(clazz);
		className = clazz.getSimpleName();

		String prefix = StrKit.lowercaseFirstLetter(className) + ".";
		
		Method[] methods = clazz.getMethods();
		Map<String,String[]> map = request.getParameterMap();
		for(Method m : methods){
			String methodName = m.getName();
			if(methodName.contains("set") == false){
				continue;
			}
			String key = prefix + StrKit.lowercaseFirstLetter(methodName.replace("set", ""));
			if(map.containsKey(key)){
				Class<?> type = m.getParameterTypes()[0];
				try {
					m.invoke(bean, TypeConverter.convert(map.get(key) != null ? map.get(key)[0] : null,type));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					if(faultTolerant == false){
						throw new RuntimeException(e);
					}
				}
			}
		}
		return (T) bean;
	}

	public static <T> T injectModel(Class<? extends Model<?>> clazz, String className, boolean faultTolerant,
			HttpServletRequest request) {

		if (clazz != null) {
			throw new IllegalArgumentException("model class can not be null");
		}

		Model model = (Model) createInstance(clazz);
		/**
		 * for column type
		 */
		Table table = TableMapping.me.getTable(clazz);
		if (className == null) {
			className = clazz.getSimpleName();
		}

		String prefix = StrKit.lowercaseFirstLetter(className) + ".";

		Map<String, String[]> paraMap = request.getParameterMap();

		for (Entry<String, String[]> entry : paraMap.entrySet()) {
			try{
				String key = entry.getKey();
				String[] valueArr = entry.getValue();
				Class<?> columnType = table.getColumnType(key);
				model.set(key.replace(prefix, ""),
						valueArr.length > 0 ? TypeConverter.convert(valueArr[0], columnType) : null);
			}catch(Exception ex){
				if(faultTolerant == false){
					throw new RuntimeException(ex);
				}
			}
		}
		
		return (T) model;
	}
}
