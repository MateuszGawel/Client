package com.mateusz.appwarp.listener;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.appwarp.WarpController;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;

public class NotificationListener implements NotifyListener {
	private static final Logger LOGGER = Logger.getLogger(NotificationListener.class.getName());
	private WarpController callBack;

	public NotificationListener(WarpController callBack) {
		this.callBack = callBack;
	}

	@Override
	public void onChatReceived(ChatEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameStarted(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameStopped(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMoveCompleted(MoveEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNextTurnRequest(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPrivateChatReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPrivateUpdateReceived(String arg0, byte[] arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoomCreated(RoomData arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoomDestroyed(RoomData arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdatePeersReceived(UpdateEvent event) {
		callBack.onUpdatePeersReceived(new String(event.getUpdate()));
	}

	@Override
	public void onUserChangeRoomProperty(RoomData arg0, String arg1, HashMap<String, Object> arg2, HashMap<String, String> arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUserJoinedLobby(LobbyData arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUserJoinedRoom(RoomData roomData, String username) {
		LOGGER.log(Level.INFO, "onUserJoinedRoom()");
		callBack.onUserJoinedRoom(roomData.getId(), username);
	}

	@Override
	public void onUserLeftLobby(LobbyData arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUserLeftRoom(RoomData arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUserPaused(String arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUserResumed(String arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub

	}

}
