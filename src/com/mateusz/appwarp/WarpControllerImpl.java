package com.mateusz.appwarp;

import java.lang.reflect.GenericArrayType;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.api.GameState;
import com.mateusz.appwarp.listener.ChatListener;
import com.mateusz.appwarp.listener.ConnectionListener;
import com.mateusz.appwarp.listener.LobbyListener;
import com.mateusz.appwarp.listener.NotificationListener;
import com.mateusz.appwarp.listener.RoomListener;
import com.mateusz.appwarp.listener.ZoneListener;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;

public class WarpControllerImpl implements WarpController {
	private static final Logger LOGGER = Logger.getLogger(WarpControllerImpl.class.getName());
	//-- SINGLETON --//
	private static WarpController instance = new WarpControllerImpl();
	
	public static WarpController getInstance() {
		return instance;
	}

	//-- KEYS --//
	private final String apiKey = "96fdf27bb654f2d6e0e5fc558673b728b636deebc01e5725d6275f92148a871c";
	private final String secretKey = "48bffe83864b4b125ed75192784ec2e8579cb73ba1b420e72ae095b2455b80c4";
	private WarpClient warpClient;
	private AppwarpGameHandler messageHandler;

	//-- DATA --//
	private String roomId;
	
	@Override
	public void initialize(String username, AppwarpGameHandler messageHandler) throws Exception {
		LOGGER.log(Level.INFO, "connect(" + username + ", " + messageHandler + ")");
		WarpClient.initialize(apiKey, secretKey);
		warpClient = WarpClient.getInstance();
//		warpClient.setGeo("eu");
		this.messageHandler = messageHandler;
		
		addListeners();

		warpClient.connectWithUserName(username);
	}

	@Override
	public void onConnectDone(boolean status) {
		LOGGER.log(Level.INFO, "onConnectDone(" + status + ")");
		if (status) {
			warpClient.initUDP();
			messageHandler.setConnected(true);

			//TODO automatically join room. should be handled manually
			warpClient.joinRoomInRange(1, 1, false);
		} else {
			messageHandler.setConnected(false);
			handleError();
		}
	}

	@Override
	public void onDisconnectDone(boolean status) {
		LOGGER.log(Level.INFO, "onDisconnectDone(" + status + ")");
	}

	@Override
	public void setIsUDPEnabled(boolean status) {
		LOGGER.log(Level.INFO, "setIsUDPEnabled(" + status + ")");

	}

	@Override
	public void onJoinRoomDone(RoomEvent e) {
		if (e.getResult() == WarpResponseResultCode.SUCCESS) {
			this.roomId = e.getData().getId();
			LOGGER.log(Level.INFO, "Matching room was found, joining in. roomId: " + roomId);
			warpClient.subscribeRoom(roomId);
		} else if (e.getResult() == WarpResponseResultCode.RESOURCE_NOT_FOUND) {
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("result", "");
			LOGGER.log(Level.INFO, "Matching room was NOT found, creating new one");
			//TODO what are those values?
			warpClient.createRoom("superjumper", "shephertz", 2, data);
		} else {
			warpClient.disconnect();
			handleError();
		}
	}

	@Override
	public void onCreateRoomDone(String roomId) {
		LOGGER.log(Level.INFO, "onCreateRoomDone(" + roomId + ")");

		if (roomId != null) {
			this.roomId = roomId;
			warpClient.joinRoom(roomId);
		} else {
			handleError();
		}
	}

	@Override
	public void onSubscribeRoomDone(String id) {
		LOGGER.log(Level.INFO, "onSubscribeRoomDone()");

		if (roomId != null) {
			warpClient.getLiveRoomInfo(roomId);
			messageHandler.setSubscribed(true);
			messageHandler.onSubscribedToRoom();
		} else {
			warpClient.disconnect();
			handleError();
		}
	}

	@Override
	public void onGetLiveRoomInfoDone(String[] liveUsers) {
		LOGGER.log(Level.INFO, "onGetLiveRoomInfoDone()");

		if (liveUsers != null) {
			if (liveUsers.length == 2) {
				LOGGER.log(Level.INFO, "WE CAN START GAME");
				messageHandler.setState(GameState.PLAYING);
			} else {
				messageHandler.setState(GameState.WAITING);
				LOGGER.log(Level.INFO, "NOT ENOUGH PLAYERS. WE SHOULD WAIT");
			}
		} else {
			warpClient.disconnect();
			handleError();
		}
	}

	@Override
	public void onUserJoinedRoom(String id, String username) {
		warpClient.getLiveRoomInfo(roomId);
	}
	

	@Override
	public void onUpdatePeersReceived(String message) {
		messageHandler.getMessageQueue().offer(message);
	}
	//------------- PRIVATE METHODS ----------------//

	private void addListeners() {
		LOGGER.log(Level.INFO, "addListeners()");
		warpClient.addChatRequestListener(new ChatListener(this));
		warpClient.addConnectionRequestListener(new ConnectionListener(this));
		warpClient.addLobbyRequestListener(new LobbyListener(this));
		warpClient.addNotificationListener(new NotificationListener(this));
		warpClient.addRoomRequestListener(new RoomListener(this));
		warpClient.addZoneRequestListener(new ZoneListener(this));
	}

	private void handleError() {
		LOGGER.log(Level.INFO, "handleError()");
		if (roomId != null && roomId.length() > 0) {
			warpClient.deleteRoom(roomId);
		}
		disconnect();
	}

	private void disconnect() {
		LOGGER.log(Level.INFO, "disconnect()");
		warpClient.removeChatRequestListener(new ChatListener(this));
		warpClient.removeConnectionRequestListener(new ConnectionListener(this));
		warpClient.removeLobbyRequestListener(new LobbyListener(this));
		warpClient.removeNotificationListener(new NotificationListener(this));
		warpClient.removeRoomRequestListener(new RoomListener(this));
		warpClient.removeZoneRequestListener(new ZoneListener(this));
		warpClient.disconnect();

		messageHandler.setConnected(false);
		messageHandler.setSubscribed(false);
	}


}
