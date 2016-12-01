package com.jew.token;

import java.util.List;

public interface ITokenCache {
	<T> List<Token> getAll();
	void put(Token token);
	void remove(Token token);
	boolean contains(Token token);
}
