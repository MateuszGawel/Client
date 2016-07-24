package com.mateusz.api;

public class MessageBuilder<T extends MessageBuilder<?>> {

	private MessageType type;
	private Object content;
	private String senderName;
	private Float posX, posY;

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
		return new Message(this);
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
}
