package com.multipong.server;

import java.io.IOException;
import java.util.Random;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import com.multipong.shared.Network;
import com.multipong.shared.Network.*;

public class GameServer extends Server {
	
	private Server server;

	public static void main (String[] args) throws IOException {
		GameServer server = new GameServer();
		server.start();
	}
	
	public GameServer() throws IOException {
		MessageBus messageBus = new MessageBus();
		server = new Server();
		server.start();
		Network.register(server);
		server.bind(Network.TCP_PORT, Network.UDP_PORT);
		
		server.addListener(new Listener() {
			public void received (Connection conn, Object object) {
				// TODO: handle multiple connections

				// If client wants to register send game properties
				if (object instanceof RegisterRequest) {
					messageBus.registerClient(conn);
				}

				// If client sends its Paddle position forward to others
				
				// If client sends message that it hit ball forward to others

			}
		});
	}
	
	

}
