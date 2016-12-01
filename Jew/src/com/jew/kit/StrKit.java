package com.jew.kit;

public class StrKit {
	
	/**
	 * lower first letter of the case 
	 * @param str
	 * @return
	 */
	public static String lowercaseFirstLetter(String str){
		return str.substring(0,1).toLowerCase() + str.substring(1);
	}
	
	/**
	 * str_yes ==> strYes
	 * @param name
	 * @return
	 */
	public static String toCamelCase(String name){
		if(name.indexOf("_") == -1){
			return name;
		}
		StringBuilder sb = new StringBuilder();
		char[] chars = name.toLowerCase().toCharArray();
		for(int i=0;i<chars.length;i++){
			char c = chars[i];
			if(c == '_'){
				if( i+1 < chars.length ){
					i++;
					c = (char)(chars[i] - 32);
				}
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**
	 * uppercase the first letter 
	 * @param str
	 * @return
	 */
	public static String upperCaseFirstLetter(String str){
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str){
		
		return str == null || str.trim().equals("");
	}
	
	public static String removeLastChar(String str){
		return str.substring(0, str.length()-1);
	}
	
	public static void main(String[] args) {
		System.out.println(removeLastChar("asdfafd"));
	}
}
