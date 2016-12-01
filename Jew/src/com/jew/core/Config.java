package com.jew.core;

import com.jew.config.Constants;

public class Config {
	private static final Constants constants = new Constants();
	
	public static Constants getConstants(){
		return constants;
	}
}
