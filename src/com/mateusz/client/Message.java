package com.mateusz.client;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 8587188729074776736L;

	private MessageType type;
	private Object content;
	private String senderName;

	public static class MessageBuilder<T extends MessageBuilder<?>> {
		private MessageType type;
		private Object content;
		private String senderName;

		public MessageBuilder() {

		}

		public MessageBuilder(MessageType type, Object content, String senderName) {
			this.type = type;
			this.content = content;
			this.senderName = senderName;
		}

		@SuppressWarnings("unchecked")
		public T type(MessageType type) {
			this.type = type;
			return (T) this;
		}

		@SuppressWarnings("unchecked")
		public T content(Object content) {
			this.content = content;
			return (T) this;
		}

		@SuppressWarnings("unchecked")
		public T senderName(String senderName) {
			this.senderName = senderName;
			return (T) this;
		}

		public Message build() {
			return new Message(this);
		}

	}

	protected Message(MessageBuilder<?> messageBuilder) {
		this.type = messageBuilder.type;
		this.content = messageBuilder.content;
		this.senderName = messageBuilder.senderName;
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
