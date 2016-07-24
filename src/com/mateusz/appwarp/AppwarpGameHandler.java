package com.mateusz.appwarp;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.api.AbstractGameHandler;
import com.mateusz.api.Message;
import com.mateusz.api.MessageBuilder;
import com.mateusz.utils.JSONConverter;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

public class AppwarpGameHandler extends AbstractGameHandler {
	private static final Logger LOGGER = Logger.getLogger(AppwarpGameHandler.class.getName());

	private Queue<String> messageQueue = new LinkedList<String>();	
	
	public AppwarpGameHandler(String playerName) {
		super(playerName);
	}
	
	public AppwarpGameHandler(String playerName, MessageBuilder<?> messageBuilder) {
		super(messageBuilder);
	}

	@Override
	public void connect() {
		try {
			WarpControllerImpl.getInstance().initialize(playerName, this);
			connected = true;

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Couldn't connect to appwarp", e);
		}
	}

	@Override
	public void listen() {
		if (connected) {
			if (!messageQueue.isEmpty()) {
				String messageJson = messageQueue.poll();
				handleMessage(messageJson);
			}
		} else
			LOGGER.log(Level.INFO, "Player is not connected");
	}

	@Override
	public void sendMessage(Message message) {
		try {
			WarpClient.getInstance().sendUpdatePeers(JSONConverter.ObjectToJSON(message).getBytes());
		} catch (Exception e1) {
			LOGGER.log(Level.INFO, "Couldn't send message");
			connected = false;
		}
	}

	public Queue<String> getMessageQueue() {
		return messageQueue;
	}

}
