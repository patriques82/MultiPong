package com.multipong.server;

import com.multipong.shared.Network.Message;

import com.esotericsoftware.kryonet.Connection;

public class Client {

	private static int posIndex = 0;
	private static String[] positions = {"right", "left", "up", "bottom"};

	private Connection connection;
	private String position;

	public Client (Connection conn) {
		if(posIndex == positions.length)
			throw new IndexOutOfBoundsException("position index is out of bounds");
		connection = conn;
		position = positions[posIndex++];
	}

	public Connection getConnection() {
		return connection;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String pos) {
		position = pos;
	}

	public void send(Message message) {
		connection.sendTCP(message);
	}
}