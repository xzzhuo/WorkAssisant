package com.assistant.process;

import java.net.SocketAddress;

import bdx.net.log.NetLog;
import bdx.net.netty.WebSocket;

public class MyWebSocket extends WebSocket {

	public MyWebSocket() {
		super("\\websocket1");
	}

	@Override
	public void onConnect(SocketAddress address) {

		NetLog.error("onConnect", "Address : " + address);
	}

	@Override
	public void onDisconnect(SocketAddress address) {
		NetLog.error("onDisconnect", "Address : " + address);

	}

	@Override
	public void onMessage(SocketAddress address, String message) {
		NetLog.error("onMessage", "Address : " + address);

	}

	@Override
	public void onMessage(SocketAddress address, byte[] data) {
		NetLog.error("onMessage", "Address : " + address);

	}

}
