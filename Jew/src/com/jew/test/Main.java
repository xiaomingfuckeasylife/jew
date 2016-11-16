package com.jew.test;


import com.jew.kit.LogKit;
import com.jew.log.JdkLogFactory;
import com.jew.log.LogManager;

public class Main {
	
	public static void main(String[] args) {
		try {
			LogManager.me().setDefaultLogFactory(new JdkLogFactory());
			LogKit.error("this is a error from jdkLog factory");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
