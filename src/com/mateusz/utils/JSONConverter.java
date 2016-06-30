package com.mateusz.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConverter {
	private static final Logger LOGGER = Logger.getLogger(JSONConverter.class.getName());
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	public static <T> T JSONtoObject(String json, Class<T> cls){
		try {
			return OBJECT_MAPPER.readValue(json, cls);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Couldn't convert JSON to object.", e);
		}
		return null;
	}
	
	public static <T> String ObjectToJSON(T object){
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			LOGGER.log(Level.SEVERE, "Couldn't convert object to JSON.", e);
		}
		return null;
	}
}
