package com.jew.config;

import java.util.HashMap;
import java.util.Map;

import com.jew.log.ILogFactory;
import com.jew.log.Log;
import com.jew.log.LogManager;
import com.jew.token.ITokenCache;

/**
 * Global Constant configure
 */
final public class Constants {
	private boolean devMod = Const.DEFAULT_DEV_MOD;
	private String baseUploadPath = Const.DEFAULT_UPLOAD_PATH;
	private String baseDownloadPath = Const.DEFAULT_DOWNLOAD_PATH;
	private String encoding = Const.DEFAULT_ENCODING;
	private String urlParamSeperator = Const.DEFAULT_URL_PARAM_SEPERATOR;
	private ViewType viewType = Const.DEFAULT_VIEWTYPE;
	private String jspViewExtention = Const.JSP_VIEW_EXTENTION;
	private String htmlViewExtention = Const.HTML_VIEW_EXTENTION;
	private int maxUploadSize = Const.MAX_UPLOAD_SIZE;
	private ITokenCache cache ; 
	
	public ITokenCache getCache() {
		return cache;
	}

	public void setCache(ITokenCache cache) {
		this.cache = cache;
	}

	public boolean isDevMod() {
		return devMod;
	}

	public void setDevMod(boolean devMod) {
		this.devMod = devMod;
	}

	public String getBaseUploadPath() {
		return baseUploadPath;
	}

	public void setBaseUploadPath(String baseUploadPath) {
		this.baseUploadPath = baseUploadPath;
	}

	public String getBaseDownloadPath() {
		return baseDownloadPath;
	}

	public void setBaseDownloadPath(String baseDownloadPath) {
		this.baseDownloadPath = baseDownloadPath;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getUrlParamSeperator() {
		return urlParamSeperator;
	}

	public void setUrlParamSeperator(String urlParamSeperator) {
		this.urlParamSeperator = urlParamSeperator;
	}

	public ViewType getViewType() {
		return viewType;
	}

	public void setViewType(ViewType viewType) {
		this.viewType = viewType;
	}

	public String getJspViewExtention() {
		return jspViewExtention;
	}

	public void setJspViewExtention(String jspViewExtention) {
		this.jspViewExtention = jspViewExtention;
	}

	public String getHtmlViewExtention() {
		return htmlViewExtention;
	}

	public void setHtmlViewExtention(String htmlViewExtention) {
		this.htmlViewExtention = htmlViewExtention;
	}

	public int getMaxUploadSize() {
		return maxUploadSize;
	}

	public void setMaxUploadSize(int maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}
	
	private Map<Integer,String> errorMappingView = new HashMap<>();
	
	public void set403View(String viewName){
		errorMappingView.put(403, viewName);
	}
	public void set404View(String viewName){
		errorMappingView.put(404, viewName);
	}
	public void set502View(String viewName){
		errorMappingView.put(502, viewName);
	}
	public void setErrorView(int code ,String viewName){
		errorMappingView.put(code, viewName);
	}
	
	public void setLogFactory(ILogFactory logFactory){
		LogManager.me().setDefaultLogFactory(logFactory);
	}
	
	
}
