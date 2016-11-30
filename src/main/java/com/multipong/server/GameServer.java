package com.multipong.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.multipong.shared.Network;
import com.multipong.shared.Network.SomeRequest;
import com.multipong.shared.Network.SomeResponse;

public class GameServer extends Server {
	
	private Server server;
	
	public GameServer() throws IOException {
		server = new Server() {
			protected Connection newConnection () {
				return new ClientConnection();
			}
		};
		server.start();
		server.bind(Network.TCP_PORT, Network.UDP_PORT);
		Network.register(server);
		
		server.addListener(new Listener() {
			public void received (Connection c, Object object) {
				ClientConnection clientConn = (ClientConnection) c;
				if (object instanceof SomeRequest) {
		             SomeRequest request = (SomeRequest) object;
		             System.out.println("request: " + request.text);
		             SomeResponse response = new SomeResponse();
		             response.text = "Thanks";
		             clientConn.sendTCP(response);
				}
			}
			public void disconnected (Connection c) {
				ClientConnection clientConn = (ClientConnection) c;
				if (clientConn.name != null) {
					System.out.println(clientConn.name + " Disconnected");
					// Announce to everyone that someone (with a registered name) has left.
//					ClientMessage chatMessage = new ChatMessage();
//					chatMessage.text = connection.name + " disconnected.";
//					server.sendToAllTCP(chatMessage);
//					updateNames();
				}
			}
		});
	}
	
	// This holds per connection state.
	static class ClientConnection extends Connection {
		public String name;
	}
	
	public static void main (String[] args) throws IOException {
		new GameServer();
	}

}
