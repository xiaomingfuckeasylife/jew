package com.jew.test;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jew.plugin.activeRecord.IDataSourceProvider;
import com.jew.plugin.activeRecord.SqlReporter;
import com.jew.plugin.c3p0.C3pOPlugin;


public class Main {
	
	public static void main(String[] args){
		C3pOPlugin c3pOPlugin = new C3pOPlugin(new File(Thread.currentThread().getContextClassLoader().getResource("a_little_config.txt").getPath()));
		c3pOPlugin.start();
		IDataSourceProvider provider = c3pOPlugin;
		try {
			
			SqlReporter sr= new SqlReporter(provider.getDataSource().getConnection());
			Connection conn = sr.getConnection();
			System.out.println(" ----  1 " + conn);
			PreparedStatement ps = conn.prepareStatement("select * from blog where id = ?");
			System.out.println(" ----  2 " );
			ps.setInt(1, 23);
			System.out.println(" ----  3");
			ps.executeQuery();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
