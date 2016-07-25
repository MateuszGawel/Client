package com.mateusz.api;

public interface MessageResolver {

	void resolve(String message);
	
	void resolve(Message message);
	
}
