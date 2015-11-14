package com.mateusz.client;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 8587188729074776736L;

	private MessageType type;
	private Object content;
	private String senderName;

	public Message() {

	}

	public Message(MessageBuilder<?> messageBuilder) {
		this.type = messageBuilder.getType();
		this.content = messageBuilder.getContent();
		this.senderName = messageBuilder.getSenderName();
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	@Override
	public String toString() {
		return "Message [type=" + type + ", content=" + content + ", senderName=" + senderName + "]";
	}
}
