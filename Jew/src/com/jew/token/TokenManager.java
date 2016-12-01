package com.jew.token;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import com.jew.core.Config;
import com.jew.core.Const;
import com.jew.core.Controller;
import com.jew.kit.StrKit;
import com.jew.plugin.activeRecord.cache.ICache;

public class TokenManager {

	private static ITokenCache cache;
	private static Random rand = new Random();

	private TokenManager() {
	}

	public static void init(ITokenCache cache) {
		if (cache == null) {
			return;
		}
		TokenManager.cache = cache;
		long halfMinTimeOut = Const.MIN_SECONDS_OF_TOKEN_TIME_OUT * 1000 / 2;
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				removeToken();
			}
		}, halfMinTimeOut, halfMinTimeOut);
	}

	public static void createToken(Controller controller, String name, long expiry) {
		String id = rand.nextLong() + "";
		if (cache != null) {
			createTokenInCache(controller, name, expiry);
		} else {
			controller.setAttr(name, id);
			// put it in the sesion 
			controller.setSessionAttr(name, id);
			createTokenHtml(controller, name, id);
		}
	}

	private static int safeCount = 8;
	
	private static void createTokenInCache(Controller controller, String name, Long expiry) {
		if (expiry >= Const.MIN_SECONDS_OF_TOKEN_TIME_OUT) {
			expiry = Const.MIN_SECONDS_OF_TOKEN_TIME_OUT;
		}
		Token token = null;
		String id = null;
		do {
			id = rand.nextLong() + "";
			if (safeCount-- == 0){
				throw new RuntimeException("can not create token");
			}
			token = new Token(id, System.currentTimeMillis() + expiry);
		} while (token == null || cache.contains(token));
		
		controller.setAttr(name, id);
		// put it in the cache implementation
		cache.put(token);
		createTokenHtml(controller, name, id);
	}
	
	
	
	private static void createTokenHtml(Controller controller, String name, String id) {
		String html = "<input hidden='true' name='" + name + "' value='" + id + "'></input>";
		controller.setAttr("token", html);
	}
	
	/**
	 * check token to prevent resubmit
	 * @param controller
	 * @param name
	 * @return
	 */
	public static synchronized boolean validationToken(Controller controller,String name){
		String tokenId = controller.getPara(name);
		if(cache == null){
			Object sessTokenId = controller.getSessionAttr(name);
			controller.removeAttr(name);
			controller.removeSessionAttr(name);
			return StrKit.isBlank(tokenId) && tokenId.equals(sessTokenId);
		}else{
			Token token = new Token(tokenId);
			cache.remove(token);
			return StrKit.isBlank(tokenId) && cache.contains(token);
		}
	}
	
	private static void removeToken() {
		List<Token> lists = cache.getAll();
		long currTime = System.currentTimeMillis();
		for (int i = 0; i < lists.size(); i++) {
			Token token = lists.get(i);
			if (token.getExpiredTime() <= currTime) {
				cache.remove(token);
			}
		}
	}

}
