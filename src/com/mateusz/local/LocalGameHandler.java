package com.mateusz.local;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.api.AbstractGameHandler;
import com.mateusz.api.Message;
import com.mateusz.api.MessageBuilder;
import com.mateusz.local.configuration.LocalConfiguration;
import com.mateusz.utils.JSONConverter;

public class LocalGameHandler extends AbstractGameHandler {
	private static final Logger LOGGER = Logger.getLogger(LocalGameHandler.class.getName());
	
	private Socket playerSocket;
	private DataInputStream in;
	private DataOutputStream out;
	
	public LocalGameHandler(String playerName) {
		super(playerName);
	}
	
	public LocalGameHandler(String playerName, MessageBuilder<?> messageBuilder) {
		super(messageBuilder);
	}
	
	@Override
	public void connect() {
		try {
			playerSocket = new Socket(LocalConfiguration.SERVER_ADDRESS, LocalConfiguration.SERVER_PORT);
			in = new DataInputStream(playerSocket.getInputStream());
			out = new DataOutputStream(playerSocket.getOutputStream());
			
			connected = true;
			
			onSubscribedToRoom();
			
			//start game automatically
//			getGameCallback().startGame();

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
					handleMessage(inputMessage);
				}
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Connection lost");
				connected = false;
			}
		} else
			LOGGER.log(Level.INFO, "Player is not connected");
	}
	
	@Override
	public void sendMessage(Message message) {
		try {
			out.writeUTF(JSONConverter.ObjectToJSON(message));
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Couldn't send message");
			connected = false;
		}
	}
}
