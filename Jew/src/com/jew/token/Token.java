package com.jew.token;

public class Token {
	private String id;
	private Long expiredTime ; 
	public Token(String id,Long expiredTime){
		if(id == null){
			throw new IllegalArgumentException("token id can not be null");
		}
		this.id = id ; 
		this.expiredTime = expiredTime;
	}
	public Token(String id){
		if(id == null){
			throw new IllegalArgumentException("token id can not be null");
		}
		this.id = id ; 
	}
	
	public String getTokenId(){
		return id;
	}
	
	public Long getExpiredTime(){
		return expiredTime;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(hashCode() == obj.hashCode()){
			return true;
		}
		return false;
	}
}
