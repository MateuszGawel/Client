package com.mateusz.api;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 8587188729074776736L;

	private MessageType type;
	private Object content;
	private String senderName;
	
	private Float posX, posY;

	public Message() {

	}

	public Message(MessageBuilder<?> messageBuilder) {
		this.type = messageBuilder.getType();
		this.content = messageBuilder.getContent();
		this.senderName = messageBuilder.getSenderName();
		this.posX = messageBuilder.getPosX();
		this.posY = messageBuilder.getPosY();
	}

	public MessageType getType() {
		return type;
	}

	public Object getContent() {
		return content;
	}

	public String getSenderName() {
		return senderName;
	}

	public Float getPosX() {
		return posX;
	}

	public Float getPosY() {
		return posY;
	}
	
	@Override
	public String toString() {
		return "Message [type=" + type + ", content=" + content + ", senderName=" + senderName + "]";
	}
}
