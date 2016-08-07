package com.mateusz.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.api.compressor.Compressor;
import com.mateusz.api.compressor.CompressorException;

public class MessageBuilder<T extends MessageBuilder<?>> {
	private static final Logger LOGGER = Logger.getLogger(MessageBuilder.class.getName());
	
	private MessageType type;
	private String content;
	private String senderName;
	private Float posX, posY;

	private Long time;
	private int lastMessageSent;
	private int lastMessageReceived;
	
	private boolean useCompressor;
	private Compressor<?> compressor;

	public MessageBuilder(MessageType type) {
		this.type = type;
		this.senderName = AbstractGameHandler.playerName;
	}

	@SuppressWarnings("unchecked")
	public T content(String content) {
		if(useCompressor){
			throw new CompressorException("Can't set content when useCompressor is true. Use compressor feature");
		}
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
		
		if(useCompressor){
			if(compressor != null){
				this.content = compressor.compress();
				this.compressor.clear();
			}
			else{
				LOGGER.log(Level.SEVERE, "Compressor is not set!");
			}
		}
		
		Message message = new Message(this);
		AbstractGameHandler.lastMessageSent++;
		
		this.content = null;
		
		return message;
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

	public void setCompressor(Compressor<?> compressor) {
		this.compressor = compressor;
	}

	@SuppressWarnings("rawtypes")
	public Compressor getCompressor() {
		return compressor;
	}

	public boolean isUseCompressor() {
		return useCompressor;
	}

	public void setUseCompressor(boolean useCompressor) {
		this.useCompressor = useCompressor;
	}

}
