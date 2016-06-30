package com.mateusz.appwarp.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.appwarp.WarpController;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

public class ZoneListener implements ZoneRequestListener {
	private static final Logger LOGGER = Logger.getLogger(RoomListener.class.getName());
	private WarpController callBack;

	public ZoneListener(WarpController callBack) {
		this.callBack = callBack;
	}

	@Override
	public void onCreateRoomDone(RoomEvent e) {
		
		if (e.getResult() == WarpResponseResultCode.SUCCESS) {
			LOGGER.log(Level.INFO, "onCreateRoomDone: SUCCESS");
			callBack.onCreateRoomDone(e.getData().getId());
		} else {
			LOGGER.log(Level.INFO, "onCreateRoomDone: FAIL");
			callBack.onCreateRoomDone(null);
		}
	}

	@Override
	public void onDeleteRoomDone(RoomEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetAllRoomsDone(AllRoomsEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetLiveUserInfoDone(LiveUserInfoEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetMatchedRoomsDone(MatchedRoomsEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetOnlineUsersDone(AllUsersEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSetCustomUserDataDone(LiveUserInfoEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetAllRoomsCountDone(AllRoomsEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetOnlineUsersCountDone(AllUsersEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetUserStatusDone(LiveUserInfoEvent e) {
		// TODO Auto-generated method stub

	}
}
