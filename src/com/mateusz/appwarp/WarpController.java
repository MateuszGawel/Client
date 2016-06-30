package com.mateusz.appwarp;

import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;

/**
 * Responsible for connecting and handling every asynchronous message from AppWarp
 *
 */
public interface WarpController {

	/**
	 * Method initialize appwarp with secret keys and connect
	 * 
	 * @param username of connecting player
	 * @param abstractMessageHandler 
	 * @throws exception while initialization was not possible
	 */
	void initialize(String username, AppwarpMessageHandler abstractMessageHandler) throws Exception;

	void onConnectDone(boolean status);

	void onDisconnectDone(boolean status);

	void setIsUDPEnabled(boolean status);

	void onJoinRoomDone(RoomEvent e);

	void onCreateRoomDone(String roomId);

	void onSubscribeRoomDone(String id);

	void onGetLiveRoomInfoDone(String[] liveUsers);

	void onUserJoinedRoom(String id, String username);

	void onUpdatePeersReceived(String message);

}
