package com.multipong.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.multipong.shared.Network;
import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.RegisterRequest;
import com.multipong.shared.Network.WallHitMessage;

public class ServerFacade {
	
	private MessageBus bus;
	private Server server;

	ServerFacade(MessageBus bus) {
		this.bus = bus;
		server = new Server();
	}
	
	void start() {
		server.start();
		server = new Server();
		server.start();
		Network.register(server);
		try {
			server.bind(Network.TCP_PORT, Network.UDP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.addListener(new Listener() {
			public void received (Connection conn, Object object) {
				// TODO: handle multiple connections

				// If client wants to register send game properties
				if (object instanceof RegisterRequest) {
					bus.registerClient(conn);
				}

				// If client sends its Paddle position forward to others
				
				// If client sends message that it hit ball forward to others
				if (object instanceof BallMessage) {
					BallMessage mess = (BallMessage) object;
					System.out.println("Ball Hit at: " + mess.x + ", " + mess.y);
					bus.forwardBallHit(mess.x, mess.y, mess.vx, mess.vy);
				}
				
				if (object instanceof WallHitMessage) {
					WallHitMessage mess = (WallHitMessage) object;
					System.out.println("Wall hit at: " + mess.x + ", " + mess.y);
					bus.forwardWallHit(mess.position, mess.x, mess.y, mess.vx, mess.vy);
				}

			}
		});
	}

}
