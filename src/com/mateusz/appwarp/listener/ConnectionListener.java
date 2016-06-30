package com.mateusz.appwarp.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mateusz.appwarp.WarpController;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

public class ConnectionListener implements ConnectionRequestListener {

	private static final Logger LOGGER = Logger.getLogger(ConnectionListener.class.getName());
	private WarpController callBack;

	public ConnectionListener(WarpController callBack) {
		this.callBack = callBack;
	}

	@Override
	public void onConnectDone(ConnectEvent e) {
		if (e.getResult() == WarpResponseResultCode.SUCCESS) {
			LOGGER.log(Level.INFO, "onConnectDone: SUCCESS");
			callBack.onConnectDone(true);
		} else {
			LOGGER.log(Level.INFO, "onConnectDone: FAIL");
			callBack.onConnectDone(false);
		}
	}

	@Override
	public void onDisconnectDone(ConnectEvent e) {
		if (e.getResult() == WarpResponseResultCode.SUCCESS) {
			LOGGER.log(Level.INFO, "onDisconnectDone: SUCCESS");
			callBack.onDisconnectDone(true);
		} else {
			LOGGER.log(Level.INFO, "onDisconnectDone: FAIL");
			callBack.onDisconnectDone(false);
		}
	}

	@Override
	public void onInitUDPDone(byte result) {
		if (result == WarpResponseResultCode.SUCCESS) {
			LOGGER.log(Level.INFO, "onInitUDPDone: SUCCESS");
			callBack.setIsUDPEnabled(true);
		}
	}

}
