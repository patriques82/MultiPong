package com.multipong.server;

import com.multipong.shared.Network.Message;

class Client {

	private final static String[] positions = {"right", "left", "up", "bottom"};
	private static int posIndex = 0;

	private final String position;
	private final Sender sender;

	Client (Sender sender) {
		if(posIndex == positions.length)
			throw new IndexOutOfBoundsException("position index is out of bounds");
		this.sender = sender;
		position = positions[posIndex++];
	}

	String getPosition() {
		return position;
	}

	void send(Message message) {
		sender.send(message);
	}
}