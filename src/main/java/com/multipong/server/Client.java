package com.multipong.server;

import com.esotericsoftware.kryonet.Connection;

public class Client {

	private Connection connection;
	private String position;

	public Client (Connection conn) {
		connection = conn;
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