package com.jew.plugin.activeRecord;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
/**
 * using dynamic proxy to wrap sql log in the command line
 */
public class SqlReporter implements InvocationHandler {

	private Connection conn ;
	
	public SqlReporter(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * the second class must be a interface . for InvocationHandler only handle interface 
	 * @return
	 */
	public Connection getConnection(){
		return (Connection) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Connection.class}, this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		if(method.getName().indexOf("prepareStatement") != -1){
			
			System.out.println("sql : " + args[0]);
			
		};
		/**
		 * this place must using conn not proxy . for the actual deal the problem is by conn . 
		 * the proxy object is not really helping in someway it is just a face to the outside world
		 */
		return method.invoke(conn, args);
	}

}
