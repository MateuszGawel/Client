package com.mateusz.appwarp.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.appwarp.WarpController;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

public class RoomListener implements RoomRequestListener {
	private static final Logger LOGGER = Logger.getLogger(RoomListener.class.getName());
	private WarpController callBack;

	public RoomListener(WarpController callBack) {
		this.callBack = callBack;
	}

	@Override
	public void onGetLiveRoomInfoDone(LiveRoomInfoEvent e) {
		if (e.getResult() == WarpResponseResultCode.SUCCESS) {
			LOGGER.log(Level.INFO, "onGetLiveRoomInfoDone(): SUCCESS");
			callBack.onGetLiveRoomInfoDone(e.getJoinedUsers());
		} else {
			LOGGER.log(Level.INFO, "onGetLiveRoomInfoDone(): FAIL");
			callBack.onGetLiveRoomInfoDone(null);
		}
	}

	@Override
	public void onJoinRoomDone(RoomEvent e) {
		LOGGER.log(Level.INFO, "onJoinRoomDone()");
		callBack.onJoinRoomDone(e);
	}

	@Override
	public void onLeaveRoomDone(RoomEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLockPropertiesDone(byte e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSetCustomRoomDataDone(LiveRoomInfoEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSubscribeRoomDone(RoomEvent e) {
		if (e.getResult() == WarpResponseResultCode.SUCCESS) {
			LOGGER.log(Level.INFO, "onUnSubscribeRoomDone(): SUCCESS");
			callBack.onSubscribeRoomDone(e.getData().getId());
		} else {
			LOGGER.log(Level.INFO, "onUnSubscribeRoomDone(): FAIL");
			callBack.onSubscribeRoomDone(null);
		}
	}

	@Override
	public void onUnSubscribeRoomDone(RoomEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnlockPropertiesDone(byte e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdatePropertyDone(LiveRoomInfoEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onJoinAndSubscribeRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeaveAndUnsubscribeRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub

	}

}