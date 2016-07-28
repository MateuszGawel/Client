package com.mateusz.api;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class MessageBuilder<T extends MessageBuilder<?>> {
	private static final Logger LOGGER = Logger.getLogger(MessageBuilder.class.getName());

	private MessageType type;
	private Object content;
	private String senderName;
	private Float posX, posY;

	private Long time;
	private int lastMessageSent;
	private int lastMessageReceived;

	public MessageBuilder(MessageType type) {
		this.type = type;
		this.senderName = AbstractGameHandler.playerName;
	}

	@SuppressWarnings("unchecked")
	public T content(Object content) {
		this.content = content;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T position(Float posX, Float posY) {
		this.posX = posX;
		this.posY = posY;
		return (T) this;
	}

	public Message build() {	
		this.lastMessageSent = AbstractGameHandler.lastMessageSent;
		this.lastMessageReceived = AbstractGameHandler.lastMessageReceived;
		
		Message message = new Message(this);
		AbstractGameHandler.lastMessageSent++;
		
		this.content = null;
		
		return message;
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
