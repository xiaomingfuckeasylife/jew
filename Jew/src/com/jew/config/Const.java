package com.jew.config;

public interface Const {
	boolean DEFAULT_DEV_MOD = false;
	String DEFAULT_UPLOAD_PATH = "upload";
	String DEFAULT_DOWNLOAD_PATH = "download";
	String DEFAULT_ENCODING = "utf-8";
	String DEFAULT_URL_PARAM_SEPERATOR = "-";
	ViewType DEFAULT_VIEWTYPE = ViewType.JSP;
	String JSP_VIEW_EXTENTION = ".jsp";
	String HTML_VIEW_EXTENTION = ".html";
	int MAX_UPLOAD_SIZE = 1024 * 1024 * 10 ;  // 10M
	
}
