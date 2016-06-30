package com.mateusz.appwarp.listener;


import java.util.ArrayList;

import com.mateusz.appwarp.WarpController;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ChatRequestListener;

public class ChatListener implements ChatRequestListener {

	private WarpController callBack;

	public ChatListener(WarpController callBack) {
		this.callBack = callBack;
	}

	@Override
	public void onSendChatDone(byte arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSendPrivateChatDone(byte arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetChatHistoryDone(byte arg0, ArrayList<ChatEvent> arg1) {
		// TODO Auto-generated method stub
		
	}

}
