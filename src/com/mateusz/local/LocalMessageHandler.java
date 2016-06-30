package com.mateusz.local;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.api.AbstractMessageHandler;
import com.mateusz.api.GameCallback;
import com.mateusz.api.Message;
import com.mateusz.api.MessageBuilder;
import com.mateusz.utils.JSONConverter;

public abstract class LocalMessageHandler extends AbstractMessageHandler {
	private static final Logger LOGGER = Logger.getLogger(LocalMessageHandler.class.getName());
	
	private static final int SERVER_PORT = 6066;
	private static final String SERVER_ADDRESS = "localhost";
	
	private Socket playerSocket;
	private DataInputStream in;
	private DataOutputStream out;
	
	public LocalMessageHandler(MessageBuilder<?> messageBuilder, GameCallback gameCallback) {
		super(messageBuilder, gameCallback);
	}

	@Override
	public void connect(String name) {
		try {
			playerSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
			in = new DataInputStream(playerSocket.getInputStream());
			out = new DataOutputStream(playerSocket.getOutputStream());
			
			playerName = name;
			connected = true;
			
			onSubscribedToRoom();
			
			//start game automatically
			getGameCallback().startGame();

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Couldn't connect. Server is down.", e);
		}
	}

	@Override
	public void listen() {
		if (connected) {
			try {
				if (in.available() > 0) {
					String inputMessage = in.readUTF();
					LOGGER.log(Level.INFO, "Message received: " + inputMessage);
					handleMessages(inputMessage);
				}
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Connection lost");
				connected = false;
			}
		} else
			LOGGER.log(Level.INFO, "Player is not connected");
	}
	
	@Override
	protected void sendMessage(Message message) {
		try {
			out.writeUTF(JSONConverter.ObjectToJSON(message));
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Couldn't send message");
			connected = false;
		}
	}
}
