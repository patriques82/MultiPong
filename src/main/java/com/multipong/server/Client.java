package com.multipong.server;

import com.multipong.shared.Network.Message;

public interface Client {
	public void send(Message message);
}
