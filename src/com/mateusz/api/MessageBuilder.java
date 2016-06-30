package com.mateusz.api;

public class MessageBuilder<T extends MessageBuilder<?>> {
	private MessageType type;
	private Object content;
	private String senderName;

	public MessageBuilder(MessageType type, String senderName) {
		this.type = type;
		this.senderName = senderName;
	}

	@SuppressWarnings("unchecked")
	public T content(Object content) {
		this.content = content;
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

}
