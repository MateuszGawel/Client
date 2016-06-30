package com.mateusz.appwarp.listener;


import com.mateusz.appwarp.WarpController;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.LobbyRequestListener;

public class LobbyListener implements LobbyRequestListener
{

	private WarpController callBack;
	
	public LobbyListener(WarpController callBack) 
	{
		this.callBack = callBack;
	}
	
	@Override
	public void onGetLiveLobbyInfoDone(LiveRoomInfoEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJoinLobbyDone(LobbyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveLobbyDone(LobbyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribeLobbyDone(LobbyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnSubscribeLobbyDone(LobbyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
