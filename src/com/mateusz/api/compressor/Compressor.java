package com.mateusz.api.compressor;

import java.util.Map;

public interface Compressor<T> {

	String compress();
	Map<String, T> decompress(String content);
	void clear();
}
