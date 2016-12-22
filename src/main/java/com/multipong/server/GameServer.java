package com.multipong.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.multipong.shared.Network;
import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.PaddleMessage;
import com.multipong.shared.Network.RegisterRequest;
import com.multipong.shared.Network.WallHitMessage;

public class GameServer {

	private Server server;
	private ClientsController clientsController;
	private MessageFactory messageFactory;

	public static void main (String[] args) {
		Options.init(args);
		GameServer server = new GameServer(new MessageFactory());
		server.start();
	}

	GameServer(MessageFactory factory) {
		clientsController = new ClientsController();
		messageFactory = factory;
		server = new Server();
	}
	
	public void start() {
		server.start();
		Network.register(server);
		try {
			server.bind(Network.TCP_PORT, Network.UDP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.addListener(new Listener() {
			public void received (Connection conn, Object object) {

				// If client wants to register send game properties
				if (object instanceof RegisterRequest) {
					clientsController.add(new Client(conn));
					if(clientsController.isFull()) {
						// send worldProperties to all waiting
					} else {
						// send wait
					}
					conn.sendTCP(messageFactory.worldProperties("up"));
				}

				// If client sends its Paddle position forward to others
				if (object instanceof PaddleMessage) {
					PaddleMessage mess = (PaddleMessage) object;
					// sendToAll(mess)
				}
				
				// If client sends message that it hit ball forward to others
				if (object instanceof BallMessage) {
					BallMessage mess = (BallMessage) object;
					System.out.println("Ball Hit at: " + mess.x + ", " + mess.y);
					// sendToAll(ballMessage)
				}
				
				if (object instanceof WallHitMessage) {
					WallHitMessage mess = (WallHitMessage) object;
					System.out.println("Wall hit at: " + mess.x + ", " + mess.y);
					// sendToAll(WallHitMessage)
				}

			}
		});
	}

}
