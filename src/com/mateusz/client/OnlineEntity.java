package com.mateusz.client;

public interface OnlineEntity {
	
	boolean isControlledByPlayer();
	
	String getEntityName();
	float getEntityX();
	float getEntityY();
	void init();
	
}
