package com.multipong.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.multipong.shared.Network;
import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.PaddleMessage;
import com.multipong.shared.Network.RegisterRequest;
import com.multipong.shared.Network.GameIsFullResponse;
import com.multipong.shared.Network.WaitForOthersResponse;
import com.multipong.shared.Network.WallHitMessage;

public class GameServer {

	private Server server;
	private ClientsManager clientsManager;

	public static void main (String[] args) {
		Options.init(args);
		GameServer server = new GameServer(new ClientsManager());
		server.start();
	}

	GameServer(ClientsManager mngr) {
		clientsManager = mngr;
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

				if (object instanceof RegisterRequest) {
					if(clientsManager.isFull()) {
						conn.sendTCP(new GameIsFullResponse());
					} else {
						clientsManager.add(new Client(conn));
						if(clientsManager.isFull())
							clientsManager.initGame();
						else
							clientsManager.sendToAll(new WaitForOthersResponse());
					}
				}

				if (object instanceof PaddleMessage) {
					PaddleMessage mess = (PaddleMessage) object;
					clientsManager.sendToAll(mess);
				}
				
				if (object instanceof BallMessage) {
					BallMessage mess = (BallMessage) object;
					System.out.println("Ball Hit at: " + mess.x + ", " + mess.y);
					clientsManager.sendToAll(mess);
				}
				
				if (object instanceof WallHitMessage) {
					WallHitMessage mess = (WallHitMessage) object;
					System.out.println("Wall hit at: " + mess.x + ", " + mess.y);
					clientsManager.sendToAll(mess);
				}

			}
		});
	}

}
