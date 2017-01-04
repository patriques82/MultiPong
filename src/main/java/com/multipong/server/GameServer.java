package com.multipong.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import com.multipong.shared.Network;
import com.multipong.shared.Network.*;

class GameServer {

	private static final int PLAYERS = 2;

	private Server server; // Kryonet server
	private ClientsManager clientsManager;

	public static void main (String[] args) {
		MessageFactory.init();
		try {
			GameServer server = new GameServer(new ClientsManager(PLAYERS));
			server.start();
		} catch(IllegalArgumentException e) {
			System.out.print(e.getMessage());
		}
	}

	GameServer(ClientsManager mngr) {
		clientsManager = mngr;
		server = new Server();
	}
	
	void start() {
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
					Client client = createClient(conn);
					if(clientsManager.isFull()) {
						sendIsFullToClient(client);
					} else {
						clientsManager.add(client);
						if(clientsManager.isFull())
							clientsManager.initGame();
						else {
							sendWaitToClient(client);
						}
					}
				}

				if (object instanceof PaddleMessage) {
					PaddleMessage mess = (PaddleMessage) object;
					clientsManager.sendToAllExcept(mess, mess.position);
				}
				
				if (object instanceof BallHitMessage) {
					BallHitMessage mess = (BallHitMessage) object;
					System.out.println("Ball Hit at: (" + mess.x + ", " + mess.y + ") by " + mess.position + "-paddle.");
					clientsManager.sendToAllExcept(mess, mess.position);
				}
				
				// TODO: handle score
//				if (object instanceof WallHitMessage) {
//					WallHitMessage mess = (WallHitMessage) object;
//				}

			}

			private Client createClient(final Connection conn) {
				return new Client() {
					@Override
					public void send(Message message) {
						conn.sendTCP(message);
					}
				};
			}

			private void sendWaitToClient(Client client) {
				int total = clientsManager.getTotalPlayers();
				int waiting = clientsManager.getNrOfPlayers();
				Message waitMessage = MessageFactory.waitForOthers(total, waiting);
				client.send(waitMessage);
			}

			private void sendIsFullToClient(Client client) {
				Message isFullMessage = MessageFactory.gameIsFull(clientsManager.getNrOfPlayers());
				client.send(isFullMessage);
			}

		});
	}

}
