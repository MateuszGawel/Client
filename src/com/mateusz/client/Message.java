package com.mateusz.client;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 8587188729074776736L;
	
	private MessageType type;
	private String content;
	private OnlineEntity sender;
	
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public OnlineEntity getSender() {
		return sender;
	}

	public void setSender(OnlineEntity sender) {
		this.sender = sender;
	}
}
