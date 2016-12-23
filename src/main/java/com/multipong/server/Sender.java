package com.multipong.server;

import com.multipong.shared.Network.Message;

public interface Sender {
	public void send(Message message);
}
