package com.mateusz.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageHandler {
	private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());
	private static final int SERVER_PORT = 6066;
	private static final String SERVER_ADDRESS = "localhost";
	private static final int MESSAGE_SEND_INTERVAL = 1000;

	private float stateTime;
	private boolean connected;
	
	private Socket playerSocket;
	private DataInputStream in;
	private DataOutputStream out;

	public void connect(String name) {
		try {
			playerSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
			in = new DataInputStream(playerSocket.getInputStream());
			out = new DataOutputStream(playerSocket.getOutputStream());
			connected = true;
			System.out.println("JESTEM: " + name);
			sendMessage(new Message(MessageType.SUBSCRIBE, null, name));
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Couldn't connect. Server is down.");
		}
	}

	public void send(float delta) {
		stateTime += delta;
		if (connected && stateTime > MESSAGE_SEND_INTERVAL) {
			stateTime = 0;
			//tutaj wysylam zakolejkowany message
		}
	}

	public void listen() {
		if (connected) {
			try {
				if(in.available() > 0) {
					String inputMessage = in.readUTF();
					Message message = JSONConverter.JSONtoObject(inputMessage, Message.class);
					System.out.println("GOT MESSAGE: " + message.toString());
					handleMessages(message);
				}
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Connection lost");
				connected = false;
			}
		}
		else
			LOGGER.log(Level.INFO, "Player is not connected");
	}

	public void handleMessages(Message message){
		//tutaj musze leciec po enumie i wszystkich mozliwych messagach i je obslugiwac
		//wazne ze message przychodzic beda od wielu playerow
	}
	
	private void sendMessage(Message message) {
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
}
