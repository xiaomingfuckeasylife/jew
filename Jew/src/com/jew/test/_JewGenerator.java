package com.jew.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.jew.kit.PathKit;
import com.jew.plugin.activeRecord.generator.Generator;
import com.jew.plugin.c3p0.C3pOPlugin;

/**
 * generator verbose code according to the database table .
 */

public class _JewGenerator {
	
	public static DataSource getDataSource(){
		C3pOPlugin cp = new C3pOPlugin(new File(Thread.currentThread().getContextClassLoader().getResource("a_little_config.txt").getPath()));
		cp.start();
		return cp.getDataSource();
	}
	
	public static void main(String[] args) throws IOException {
		String baseModelPackageDir = "com.jew.test.model.base";
		String baseModelOutputDir = PathKit.getWebRootPath() + "/src/com/jew/test/model/base"; 
		String modelPackageDir = "com.jew.test.model";
		String modelOutputDir = PathKit.getWebRootPath()  + "/src/com/jew/test/model/";
		Generator generator = new Generator(getDataSource(), baseModelPackageDir, baseModelOutputDir,modelPackageDir,modelOutputDir);
//		generator.setExcluedTables("Blog");
		generator.generator();
		
	}
}

