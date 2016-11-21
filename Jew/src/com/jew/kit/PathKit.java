package com.jew.kit;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * offer all the path relative methods
 */
public class PathKit {
	
	private static String rootClassPath;
	private static String webRootPath;
	/**
	 * class path can be asked from class itself
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static String getPath(Class<?> clazz) throws Exception{
		/**
		 * "" means get the class directory path
		 */
		return new File(clazz.getResource("").toURI()).getAbsolutePath();
	}
	/**
	 * class path can be asked from class itself
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String getPath(Object obj) throws Exception{
		
		return new File(obj.getClass().getResource("").toURI()).getAbsolutePath();
	}
	
	/**
	 * root Class path can be asked from classLoader or from getResource("/");
	 * @return
	 */
	public static String getRootClassPath(){
		if(rootClassPath == null) {
			String path = PathKit.class.getClassLoader().getResource("").getPath();
			rootClassPath = new File(path).getAbsolutePath();
			return rootClassPath;
		}
		return rootClassPath;
	}
	
	public static void setRootClassPath(String rootClassPath){
		PathKit.rootClassPath = rootClassPath;
	}
	
	/**
	 * webRootPath can be get from root class path 
	 * @return
	 * @throws IOException
	 */
	public static String getWebRootPath() throws IOException{
		if(webRootPath == null){
			webRootPath = detectWebRootPath();
		}
		return webRootPath;
	}
	
	public static void setWebRootPath(String webRootPath){
		PathKit.webRootPath = webRootPath;
	}
	
	/**
	 * get root class path as a base path to get webRootPath
	 * @return
	 * @throws IOException
	 */
	private static String detectWebRootPath() throws IOException{
		String path = PathKit.class.getResource("/").getPath();
		return new File(path).getParentFile().getParentFile().getCanonicalPath();
	}
	
	public static boolean isAbsolutePath(String path){
		/**
		 * "/" is unix liked system & ":" is windows system
		 */
		return path.startsWith("/") || path.indexOf(":") == 1;
	}
	
	public static String getPackagePath(Object obj){
		Package p= obj.getClass().getPackage();
		return p != null ? p.getName().replaceAll("\\.", "/") : "";
	}
	
}
