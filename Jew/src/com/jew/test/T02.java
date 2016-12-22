package com.jew.test;

public class T02 extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode ;
	private Entity entity;
	
	public T02(String errorCode ,Entity entity){
		this.errorCode = errorCode ;
		this.entity = entity;
	}
	
	public static class Entity{
		public  int id;
		
		public Entity id(int id){
			this.id = id;
			return this;
		}
	}
}
