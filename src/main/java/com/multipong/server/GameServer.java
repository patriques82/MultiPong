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
		Network.register(server);
		
		server.addListener(new Listener() {
			public void received (Connection c, Object object) {
				// We know all connections for this server are actually ChatConnections.
				ClientConnection connection = (ClientConnection) c;
				if (object instanceof SomeRequest) {
		             SomeRequest request = (SomeRequest) object;
		             System.out.println(request.text);

		             SomeResponse response = new SomeResponse();
		             response.text = "Thanks";
		             connection.sendTCP(response);
				}
			}
			
			public void disconnected (Connection c) {
				ClientConnection connection = (ClientConnection) c;
				if (connection.name != null) {
					System.out.println(connection.name + " Disconnected");
					// Announce to everyone that someone (with a registered name) has left.
//					ClientMessage chatMessage = new ChatMessage();
//					chatMessage.text = connection.name + " disconnected.";
//					server.sendToAllTCP(chatMessage);
//					updateNames();
				}
			}
		});
		server.start();
		server.bind(Network.TCP_PORT, Network.UDP_PORT);
	}
	
	// This holds per connection state.
	static class ClientConnection extends Connection {
		public String name;
	}

}
