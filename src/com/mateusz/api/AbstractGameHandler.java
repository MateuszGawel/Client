package com.mateusz.api;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.utils.JSONConverter;

public abstract class AbstractGameHandler {
	private static final Logger LOGGER = Logger.getLogger(AbstractGameHandler.class.getName());

	//-------------------- CONFIG --------------------//
	private static final float MESSAGE_SEND_INTERVAL = 0.1f;

	//-------------------- HELPERS --------------------//
	private float stateTime;

	//-------------------- STATE --------------------//
	/**
	 * key - playerName
	 * value - player config (json)
	 */
	private Map<String, String> playerConfigs = new HashMap<String, String>();
	private GameState gameState = GameState.DEFAULT;

	protected boolean connected;
	protected boolean subscribed;

	public static String playerName;
	public static int lastMessageSent = 0;
	public static int lastMessageReceived = 0;

	//---------------------- HANDLERS ----------------------//
	private MessageResolver messageResolver;
	private MessageBuilder<?> synchronousMessageBuilder;

	//-------------------- CONSTRUCTORS ---------------------//

	/**
	 * Handler will use default synchronous message builder with MessageType.UPDATE_STATE
	 * 
	 * @param playerName
	 */
	@SuppressWarnings("rawtypes")
	public AbstractGameHandler(String playerName) {	
		AbstractGameHandler.playerName = playerName;
		this.synchronousMessageBuilder = new MessageBuilder(MessageType.UPDATE_STATE);
	}

	/**
	 * Custom message builder can be provided. It's necessary if you extend Message
	 * 
	 * @param messageBuilder
	 */
	public AbstractGameHandler(MessageBuilder<?> messageBuilder) {
		this.synchronousMessageBuilder = messageBuilder;
	}

	//------------------ ABSTRACT METHODS ------------------//
	protected abstract void connect();

	protected abstract void listen();

	protected abstract void sendMessage(Message message);

	//----------------- PROTECTED METHODS ------------------//

	@SuppressWarnings("rawtypes")
	protected void handleMessage(String messageJson) {
		//TODO care when overriding message
		Message message = JSONConverter.JSONtoObject(messageJson, Message.class);

		//if null - couldn't parse message - it means that it should be handled by specific game
		if (message == null) {
			LOGGER.log(Level.INFO, "Message received: " + messageJson);
			LOGGER.log(Level.INFO, "Can't parse message by API. It has to be handled by game");
			messageResolver.performAlgorithms(messageJson);
			messageResolver.resolve(messageJson);
			return;
		}

		//do nothing if i am sender
		if (playerName.equals(message.getSenderName())) {
			LOGGER.log(Level.INFO, "Received my own message. Skipping");
			return;
		}

		LOGGER.log(Level.INFO, "Message received: " + messageJson);
		
		String senderName = message.getSenderName();
		MessageType messageType = message.getType();

		//always add player to the map if missing
		if (!playerConfigs.containsKey(senderName)) {
			playerConfigs.put(senderName, "");
		}
		
		//always update lastMessageReceived (value taken from message so sent - means that is was sent by opponent so it's received for us
		if(AbstractGameHandler.lastMessageReceived > message.getLastMessageSent()){
			LOGGER.log(Level.INFO, "Message out of order. Skipping");
			return;
		}
		AbstractGameHandler.lastMessageReceived = message.getLastMessageSent();

		//config and subscribe are handled by API
		if (MessageType.SUBSCRIBE.equals(messageType)) {
			//send to others
			if (message.getContent() != null && message.getContent().equals("subscribe")) {
				//we answer for subscribe but not for introduce to avoid loop
				sendMessage(new MessageBuilder(MessageType.SUBSCRIBE).content("introduce").build());
			}
		} else if (MessageType.CONFIG_DATA.equals(messageType)) {
			playerConfigs.put(senderName, message.getContent().toString());
		} else if(messageResolver != null){
			//custom handling by specific game
			messageResolver.performAlgorithms(message);
			messageResolver.resolve(message);
		}
		else{
			LOGGER.log(Level.INFO, "Message resolver is not set, either you forgot about it or it's not set yet. Skipping message");
		}

	}

	//------------------- PUBLIC METHODS -------------------//

	@SuppressWarnings("rawtypes")
	public void onSubscribedToRoom() {
		LOGGER.log(Level.INFO, "Subscribe myself: " + playerName);
		sendMessage(new MessageBuilder(MessageType.SUBSCRIBE).content("subscribe").build());
	}

	/**
	 * Sends message only if configured interval elapsed
	 * 
	 * @param delta - time from game main loop (since last frame)
	 */
	public void sendBufferedMessage(float delta) {
		stateTime += delta;
		if (connected && stateTime > MESSAGE_SEND_INTERVAL) {
			stateTime = 0;
			sendMessage(synchronousMessageBuilder.build());
		}
	}

	//------------------ SETTERS/GETTERS ------------------//

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

	public void setState(GameState gameState) {
		this.gameState = gameState;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setMessageResolver(MessageResolver messageResolver) {
		this.messageResolver = messageResolver;
	}

	public MessageBuilder<?> getSynchronousMessageBuilder() {
		return synchronousMessageBuilder;
	}

	public Map<String, String> getPlayerConfigs() {
		return playerConfigs;
	}

	public void setSynchronousMessageBuilder(MessageBuilder<?> synchronousMessageBuilder) {
		this.synchronousMessageBuilder = synchronousMessageBuilder;
	}

}
