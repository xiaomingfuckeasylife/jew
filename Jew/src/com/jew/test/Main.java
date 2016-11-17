package com.jew.test;

import java.io.IOException;
import java.util.Properties;

import com.jew.plugin.c3p0.C3pOPlugin;

public class Main {
	
	public static void main(String[] args) {
//		File file = new File(Thread.currentThread().getContextClassLoader().getResource("a_little_config.txt").getPath());
//		System.out.println(file.isFile());
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("a_little_config.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		C3pOPlugin c3p0 = new C3pOPlugin(prop);
		c3p0.start();
		System.out.println(c3p0);
	}
	
}
