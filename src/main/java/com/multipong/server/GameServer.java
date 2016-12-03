package com.multipong.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import com.multipong.shared.NetworkFactory;
import com.multipong.shared.Network;
import com.multipong.shared.Network.PropMessage;
import com.multipong.shared.Network.Register;

public class GameServer extends Server {
	
	private Server server;
	
	public GameServer() throws IOException {
		server = NetworkFactory.getServer();
		server.start();
		Network.register(server);
		server.bind(Network.TCP_PORT, Network.UDP_PORT);
		
		server.addListener(new Listener() {
			public void received (Connection conn, Object object) {
				if (object instanceof Register) {
					PropMessage prop = new PropMessage();
					prop.diameter = 4;
					prop.height = 500;
					prop.width = 500;
					prop.position = "bottom";
		            conn.sendTCP(prop);
				}

			}
		});
	}
	
	public static void main (String[] args) throws IOException {
		GameServer server = new GameServer();
		server.start();
	}

}
