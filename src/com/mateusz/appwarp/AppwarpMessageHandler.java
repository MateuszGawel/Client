package com.mateusz.appwarp;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.api.AbstractMessageHandler;
import com.mateusz.api.GameCallback;
import com.mateusz.api.Message;
import com.mateusz.api.MessageBuilder;
import com.mateusz.utils.JSONConverter;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

public abstract class AppwarpMessageHandler extends AbstractMessageHandler {
	private static final Logger LOGGER = Logger.getLogger(AppwarpMessageHandler.class.getName());

	private Queue<String> messageQueue;

	public AppwarpMessageHandler(MessageBuilder<?> messageBuilder, GameCallback gameCallback) {
		super(messageBuilder, gameCallback);
	}

	@Override
	public void connect(String name) {
		try {
			WarpControllerImpl.getInstance().initialize(name, this);
			messageQueue = new LinkedList<String>();
			playerName = name;
			connected = true;

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Couldn't connect to appwarp", e);
		}
	}

	@Override
	public void listen() {
		if (connected) {
			if (!messageQueue.isEmpty()) {
				String message = messageQueue.poll();
				handleMessages(message);
			}
		} else
			LOGGER.log(Level.INFO, "Player is not connected");
	}

	@Override
	protected void sendMessage(Message message) {
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
