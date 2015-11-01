package com.mateusz.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player {
	private static final Logger LOGGER = Logger.getLogger(Player.class.getName());
	private static final int SERVER_PORT = 6066;
	private static final String SERVER_ADDRESS = "localhost";

	private String name;

	private Socket playerSocket;
	private Scanner scan = new Scanner(System.in); // for test purposes
	private DataInputStream in;
	private DataOutputStream out;

	public Player(String name) {
		this.name = name;
	}

	public void connect() {
		try {
			playerSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
			in = new DataInputStream(playerSocket.getInputStream());
			out = new DataOutputStream(playerSocket.getOutputStream());

			new MessageListener().start();

			while (true) {
				System.out.print("> ");
				String msg = scan.nextLine();
				if (msg.equalsIgnoreCase("SUBSCRIBE")) {
					subscribe();
				} else {
					sendMessage("SENDER:" + name + ":MSG:" + msg);
				}
			}
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Couldn't connect. Server is down.");
		}
	}

	public void subscribe() {
		System.out.println("DODAJE PLAYERA: " + name);
		sendMessage("SENDER:" + name + ":MSG:SUBSCRIBE");
	}

	private void sendMessage(String msg) {
		try {
			out.writeUTF(msg);
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Couldn't send message");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private class MessageListener extends Thread {

		public void run() {
			while (true) {
				try {
					String inputMessage = in.readUTF();
					System.out.println(inputMessage);
				} catch (IOException e) {
					LOGGER.log(Level.INFO, "Connection lost");
					break;
				}
			}
		}
	}
}
