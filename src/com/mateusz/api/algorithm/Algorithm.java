package com.mateusz.api.algorithm;

import com.mateusz.api.Message;

public interface Algorithm {
	
	void perform(Message message);
	
	void perform(String message);
}
