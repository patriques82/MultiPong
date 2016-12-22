package com.multipong.server;

import com.esotericsoftware.kryonet.Connection;

public class Client {

	private static int posIndex = 0;
	private static String[] positions = {"right", "left", "up", "bottom"};

	private Connection connection;
	private String position;

	public Client (Connection conn) {
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
}