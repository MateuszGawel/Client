package com.mateusz.api;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractMessageHandler {
	private static final Logger LOGGER = Logger.getLogger(AbstractMessageHandler.class.getName());
	private static final float MESSAGE_SEND_INTERVAL = 0.5f;

	private float stateTime;
	
	//state
	protected boolean connected;
	protected boolean subscribed;
	protected String playerName;
	
	private MessageBuilder<?> synchronousMessageBuilder;

	private GameCallback gameCallback;

	public AbstractMessageHandler(MessageBuilder<?> messageBuilder, GameCallback gameCallback) {
		this.synchronousMessageBuilder = messageBuilder;
		this.gameCallback = gameCallback;
	}

	public abstract void connect(String name);

	public abstract void listen();

	public abstract void handleMessages(String message);
	
	@SuppressWarnings("rawtypes")
	public void onSubscribedToRoom() {
		LOGGER.log(Level.INFO, "Introducing myself: " + playerName);
		sendMessage(new MessageBuilder(MessageType.SUBSCRIBE, playerName).content("subscribe").build());
	}

	public void sendBufferedMessage(float delta) {
		stateTime += delta;
		if (connected && stateTime > MESSAGE_SEND_INTERVAL) {
			stateTime = 0;
			sendMessage(synchronousMessageBuilder.build());
		}
	}

	protected abstract void sendMessage(Message message);

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public String getPlayerName() {
		return playerName;
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public GameCallback getGameCallback() {
		return gameCallback;
	}

}
