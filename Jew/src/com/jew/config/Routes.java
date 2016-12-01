package com.jew.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jew.core.Controller;
import com.jew.kit.PathKit;
import com.jew.kit.StrKit;
/**
 * the router of the framework
 */
public abstract class Routes {

	private static String baseViewPath;

	private Map<String, Class<? extends Controller>> map = new HashMap<>();

	private Map<String, String> viewPathMap = new HashMap<>();

	public Routes add(String controllerKey, Class<? extends Controller> controller, String viewPath) {

		if (controllerKey == null) {
			throw new IllegalArgumentException("controllerkey can not be null");
		}
		if (controller == null) {
			throw new IllegalArgumentException("controller class can not be null");
		}
		if (viewPath == null) {
			throw new IllegalArgumentException("viewPath class can not be null");
		}
		if(map.containsKey(controllerKey)){
			throw new IllegalArgumentException("controller key already exists ");
		}
		if (false == viewPath.startsWith("/")) {
			viewPath = "/" + viewPath;
		}
		if (false == viewPath.endsWith("/")) {
			viewPath += "/";
		}
		if (false == controllerKey.startsWith("/")) {
			controllerKey = "/" + controllerKey;
		}
		if (false == controllerKey.endsWith("/")) {
			controllerKey += "/";
		}
		map.put(controllerKey, controller);
		viewPathMap.put(controllerKey, viewPath);

		return this;
	}
	
	/**
	 * if the viewPath is not set then the controllerKey = viewPath
	 * @param controllerKey
	 * @param controller
	 * @return
	 */
	public Routes add(String controllerKey, Class<? extends Controller> controller) {
		return add(controllerKey,controller,controllerKey);
	}
	
	public void setBaseViewPath(String path) {
		if (StrKit.isBlank(path)) {
			throw new IllegalArgumentException("path can not be blank");
		}
		if (path.startsWith("/")) {
			path = "/" + path;
		}
		// remove the last "/" in the path 
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		this.baseViewPath = path;
	}
	
	public void setDefaultBaseViewPath() {
		String path = PathKit.getWebRootPath();
		if (path.endsWith("/") == false) {
			path += "/";
		}
		this.baseViewPath = path;
	}

	public Set<Entry<String,Class<? extends Controller>>> getEntrySet(){
		return map.entrySet();
	}
	
	public String getViewPath(String path){
		return viewPathMap.get(path);
	}
	
	public String getBaseViewPath() {
		return baseViewPath;
	}
	
	public void clear(){
		map.clear();
		viewPathMap.clear();
	}
}
