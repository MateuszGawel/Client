package com.mateusz.api;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.utils.JSONConverter;

public abstract class AbstractGameHandler {
	private static final Logger LOGGER = Logger.getLogger(AbstractGameHandler.class.getName());

	//-------------------- CONFIG --------------------//
	private static final float MESSAGE_SEND_INTERVAL = 0.5f;

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

		Message message = JSONConverter.JSONtoObject(messageJson, Message.class);

		//if null - couldn't parse message - it means that it should be handled by specific game
		if (message == null) {
			messageResolver.resolve(messageJson);
			return;
		}

		//do nothing if i am sender
		if (playerName.equals(message.getSenderName())) {
			return;
		}

		String senderName = message.getSenderName();
		MessageType messageType = message.getType();

		//config and subscribe are handled by API
		if (MessageType.SUBSCRIBE.equals(messageType)) {
			if (!playerConfigs.containsKey(senderName)) {
				//initialize with empty config
				playerConfigs.put(senderName, "");

				//send to others
				if (message.getContent() != null && !message.getContent().equals("introduce")) {
					sendMessage(new MessageBuilder(MessageType.SUBSCRIBE).content("introduce").build());
				}
			}
		} else if (MessageType.CONFIG_DATA.equals(messageType)) {
			if (playerConfigs.containsKey(senderName)) {
				playerConfigs.put(senderName, message.getContent().toString());
			} else {
				//if this happen, something went really wrong
				LOGGER.log(Level.INFO, "Player " + message.getSenderName() + " doesn't exist");
			}
		} else {
			//custom handling by specific game
			messageResolver.resolve(messageJson);
		}

	}

	//------------------- PUBLIC METHODS -------------------//

	@SuppressWarnings("rawtypes")
	public void onSubscribedToRoom() {
		LOGGER.log(Level.INFO, "Introducing myself: " + playerName);
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
	
}
