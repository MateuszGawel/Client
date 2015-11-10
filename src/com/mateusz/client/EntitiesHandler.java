package com.mateusz.client;

import java.util.HashSet;
import java.util.Set;


public class EntitiesHandler {
	private static final short NUMBER_OF_ENTITIES = 3;
	
	private static Set<OnlineEntity> entities = new HashSet<OnlineEntity>(NUMBER_OF_ENTITIES);
	
	public void addEntity(OnlineEntity entity){
		entities.add(entity);
	}
}
