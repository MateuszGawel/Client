package com.mateusz.api.compressor;

import java.util.HashMap;
import java.util.Map;

public class BooleanCompressor implements Compressor<Boolean> {

	private String[] possibleKeys;
	private boolean[] values;
	
	/**
	 * Always use same compressor instance to compress and decompress message.
	 * 
	 * @param keys for possible values, this is main compressor definition
	 */
	public BooleanCompressor(String...keys) {
		this.possibleKeys = keys;
		this.values = new boolean[possibleKeys.length];
	}
	
	@Override
	public String compress() {
		StringBuffer compressedContent = new StringBuffer();
		for(int i=0; i<possibleKeys.length; i++){
			if(values[i])
				compressedContent.append("1");
			else
				compressedContent.append("0");
		}
		return compressedContent.toString();
	}

	@Override
	public Map<String, Boolean> decompress(String content) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		
		char[] chars = content.toCharArray();
		for(int i=0; i<chars.length; i++){
			if(chars[i] == '0')
				map.put(possibleKeys[i], false);
			else if(chars[i] == '1')
				map.put(possibleKeys[i], true);
			else
				throw new CompressorException("Not allowed sign: " + chars[i]);
		}
		
		return map;
	}
	
	@Override
	public void clear() {
		for(int i=0; i<values.length; i++){
			values[i] = false;
		}
	}
	
	public void set(String key, boolean value){
		boolean set = false;
		
		for(int i=0; i<possibleKeys.length; i++){
			if(possibleKeys[i].equals(key)){
				values[i] = value;
				set = true;
			}
		}
		
		if(!set)
			throw new CompressorException("'" + key + "' is not allowed! Check if you defined it in constructor");
	}
	
	public Boolean get(String key){
		for(int i=0; i<possibleKeys.length; i++){
			if(possibleKeys[i].equals(key)){
				return values[i];
			}
		}
		
		throw new CompressorException("'" + key + "' is not allowed! Check if you defined it in constructor");
	}

}
