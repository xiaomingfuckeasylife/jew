package com.jew.plugin.activeRecord;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public interface IContainerFactory {
	
	/**
	 * stole attributes info  
	 * @return
	 */
	Map getAttrsMap();
	/**
	 * stole columns info 
	 * @return
	 */
	Map getColumnsMap();
	/**
	 * stole items that need to be modified info
	 * @return
	 */
	Set getModifyFlagSet();
	
	public static IContainerFactory defaultContainerFactory = new IContainerFactory() {
		
		@Override
		public Set getModifyFlagSet() {
			return new HashSet<String>();
		}
		
		@Override
		public Map getColumnsMap() {
			return new HashMap<String,Object>();
		}
		
		@Override
		public Map getAttrsMap() {
			return new HashMap<String,Object>();
		}
	};
}
