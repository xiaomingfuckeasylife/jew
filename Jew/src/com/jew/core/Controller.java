package com.jew.core;

import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jew.kit.StrKit;
import com.jew.plugin.activeRecord.Model;
import com.jew.token.TokenManager;

/**
 * the controller of the whole frame
 */
public abstract class Controller {

	private HttpServletRequest request;
	private HttpServletResponse response;

	private String urlPara;
	private String urlParaArray[];

	private String URL_PARA_SEPERATOR = Config.getConstants().getUrlParamSeperator();
	private String[] NULL_PARAM_ARRAY = new String[0];

	void init(HttpServletRequest request, HttpServletResponse response, String urlPara) {
		this.request = request;
		this.response = response;
		this.urlPara = urlPara;
	}

	public void setHttpServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setHttpServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setUrlPara(String urlPara) {
		if (StrKit.isBlank(urlPara)) {
			throw new IllegalArgumentException("urlParam can not be blank");
		}
		this.urlPara = urlPara;
	}

	public void setAttr(String name, Object value) {
		request.setAttribute(name, value);
	}

	public void removeAttr(String name) {
		request.removeAttribute(name);
	}

	public void setAttrs(Map<String, Object> attrs) {
		for (Entry<String, Object> e : attrs.entrySet()) {
			request.setAttribute(e.getKey(), e.getValue());
		}
	}

	public Object getAttr(String name) {
		return request.getAttribute(name);
	}

	public Enumeration<String> getAttrNames() {

		return request.getAttributeNames();
	}

	public String getPara(String name) {

		return request.getParameter(name);
	}

	public String getPara(String name, String defaultVal) {

		String result = null;

		return (result = request.getParameter(name)) == null ? defaultVal : result;
	}

	public Map<String, String[]> getParaMap() {
		return request.getParameterMap();
	}

	public String[] getParaValues(String name) {
		return request.getParameterValues(name);
	}

	public Enumeration<String> getParaNames() {
		return request.getParameterNames();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return request.getSession();
	}

	public HttpSession getSession(boolean create) {
		return request.getSession(create);
	}

	public Object getSessionAttr(String name) {
		HttpSession session = getSession(false);
		return session != null ? session.getAttribute(name) : null;
	}

	public Controller setSessionAttr(String name, Object obj) {
		getSession(true).setAttribute(name, obj);
		return this;
	}

	public void removeSessionAttr(String name) {
		getSession(false).removeAttribute(name);
	}

	public Cookie getCookie(String name) {
		Cookie[] cookie = request.getCookies();
		for (int i = 0; i < cookie.length; i++) {
			if (cookie[i].getName().equals(name.trim())) {
				return cookie[i];
			}
		}
		return null;
	}

	public String getCookie(String name, String defaultVal) {
		String result = null;
		return (result = getCookie(name).getValue()) == null ? defaultVal : result;
	}

	public void setCookies(String name, String value, int expiry) {
		doSetCookies(name, value, null, expiry, false, null);
	}

	public void setCookie(Cookie cookie) {
		response.addCookie(cookie);
	}

	public void setCookie(String name, String value, int expiry, Boolean httpOnly) {
		doSetCookies(name, value, null, expiry, httpOnly, null);
	}

	public void setCookie(String name, String value, int expiry, Boolean httpOnly, String uri) {
		doSetCookies(name, value, null, expiry, httpOnly, uri);
	}

	public void setCookie(String name, String value, int expiry, Boolean httpOnly, String uri, String pattern) {
		doSetCookies(name, value, pattern, expiry, httpOnly, uri);
	}

	public void removeCookie(String name) {
		doSetCookies(name, null, null, 0, null, null);
	}

	private void doSetCookies(String name, String value, String pattern, Integer expiry, Boolean httpOnly, String uri) {
		Cookie cookie = getCookie(name);

		if (cookie == null) {
			cookie = new Cookie(name, value);
		}

		cookie.setMaxAge(expiry);

		if (pattern != null) {
			cookie.setDomain(pattern);
		}
		if (httpOnly != null) {
			cookie.setHttpOnly(httpOnly);
		}
		// default path is "/"
		if (uri == null) {
			uri = "/";
		}
		cookie.setPath(uri);

		response.addCookie(cookie);
	}

	/**
	 * get url param
	 * 
	 * @return
	 */
	public String getPara() {
		if (StrKit.isBlank(urlPara)) {
			return null;
		}
		return urlPara;
	}

	public String getPara(int index) {
		if (urlParaArray == null) {
			urlParaArray = urlPara.split(URL_PARA_SEPERATOR);
		}
		if (index > (urlParaArray.length - 1)) {
			return null;
		}
		return urlParaArray[index];
	}

	public String getPara(int index, String defaultVal) {
		String result = null;
		return (result = getPara(index)) == null ? defaultVal : result;
	}

	/**
	 * 
	 * @param clazz
	 * @param className
	 * @param acceptError
	 * @return
	 */
	public <T> T getModel(Class<? extends Model<?>> clazz, String className, boolean faultTolerant) {

		return Injecter.injectModel(clazz, className, faultTolerant, request);
	}

	public <T> T getModel(Class<? extends Model<?>> clazz) {

		return Injecter.injectModel(clazz, null, false, request);

	}

	public <T> T getModel(Class<? extends Model<?>> clazz, String className) {

		return Injecter.injectModel(clazz, className, false, request);

	}

	public <T> T getBean(Class<?> clazz, String className, boolean faultTolerant) {
		return Injecter.injectBean(clazz, className, faultTolerant, request);
	}

	public <T> T getBean(Class<?> clazz, String className) {
		return Injecter.injectBean(clazz, className, false, request);
	}

	public <T> T getBean(Class<?> clazz) {
		return Injecter.injectBean(clazz, null, false, request);
	}
	/**
	 * keep the para from the client side to the server side . maybe resend to clientSide.
	 * @return
	 */
	public Controller keepPara() {
		Map<String, String[]> map = request.getParameterMap();
		for(Entry<String,String[]> entry : map.entrySet()){
			request.setAttribute(entry.getKey(),entry.getValue());
		}
		return this;
	}
	
	public void createToken(String name,int expiry){
		TokenManager.createToken(this, name, expiry);
	}
	
	public void createToken(String name){
		TokenManager.createToken(this, name, Const.MIN_SECONDS_OF_TOKEN_TIME_OUT);
	}
	
	public void validateToken(String name){
		TokenManager.validationToken(this, name);
	}
}
