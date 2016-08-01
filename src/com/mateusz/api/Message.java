package com.mateusz.api;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 8587188729074776736L;

	private MessageType type;
	private String content;
	private String senderName;
	private Long time;
	
	private int lastMessageSent;
	private int lastMessageReceived;
	
	
	private Float posX, posY;

	public Message() {

	}

	public Message(MessageBuilder<?> messageBuilder) {
		this.type = messageBuilder.getType();
		this.content = messageBuilder.getContent();
		this.senderName = messageBuilder.getSenderName();
		this.posX = messageBuilder.getPosX();
		this.posY = messageBuilder.getPosY();
		this.time = messageBuilder.getTime();
		this.lastMessageSent = messageBuilder.getLastMessageSent();
		this.lastMessageReceived = messageBuilder.getLastMessageReceived();
	}

	public MessageType getType() {
		return type;
	}

	public String getContent() {
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

	public Long getTime() {
		return time;
	}
	
	public int getLastMessageSent() {
		return lastMessageSent;
	}

	public int getLastMessageReceived() {
		return lastMessageReceived;
	}

}
