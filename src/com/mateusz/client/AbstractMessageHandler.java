package com.mateusz.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractMessageHandler {
	private static final Logger LOGGER = Logger.getLogger(AbstractMessageHandler.class.getName());
	private static final int SERVER_PORT = 6066;
	private static final String SERVER_ADDRESS = "localhost";
	private static final float MESSAGE_SEND_INTERVAL = 0.1f;

	private float stateTime;
	private boolean connected;

	private Socket playerSocket;
	private DataInputStream in;
	private DataOutputStream out;

	private String playerName;
	private MessageBuilder<?> synchronousMessageBuilder;

	public AbstractMessageHandler(MessageBuilder<?> messageBuilder) {
		this.synchronousMessageBuilder = messageBuilder;
	}

	@SuppressWarnings("rawtypes")
	public void connect(String name) {
		try {
			playerSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
			in = new DataInputStream(playerSocket.getInputStream());
			out = new DataOutputStream(playerSocket.getOutputStream());
			connected = true;
			playerName = name;
			System.out.println("JESTEM: " + playerName);
			sendMessage(new MessageBuilder(MessageType.SUBSCRIBE, playerName).content("subscribe").build());
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Couldn't connect. Server is down.");
		}
	}

	public void sendBufferedMessage(float delta) {
		stateTime += delta;
		if (connected && stateTime > MESSAGE_SEND_INTERVAL) {
			stateTime = 0;
			sendMessage(synchronousMessageBuilder.build());
		}
	}

	public void listen() {
		if (connected) {
			try {
				if (in.available() > 0) {
					String inputMessage = in.readUTF();
					System.out.println("GOT MESSAGE: " + inputMessage);
					handleMessages(inputMessage);
				}
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Connection lost");
				connected = false;
			}
		} else
			LOGGER.log(Level.INFO, "Player is not connected");
	}

	public abstract void handleMessages(String message);

	protected void sendMessage(Message message) {
		try {
			out.writeUTF(JSONConverter.ObjectToJSON(message));
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Couldn't send message");
			connected = false;
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public String getPlayerName() {
		return playerName;
	}
}
