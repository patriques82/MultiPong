package com.multipong.server;

import com.multipong.shared.Network.Message;

class ClientsManager {

	private final static String[] positions = {"right", "left", "up", "bottom"};
	private static int posIndex = 0;

	private Client[] clients;
	private int nrOfPlayers;
	
	ClientsManager(int players) {
		nrOfPlayers = players;
		clients = new Client[nrOfPlayers];
	}
	
	void add(Client client) {
		if(!isFull()) {
			clients[posIndex] = client;
			posIndex++;
		}
	}

	boolean isFull() {
		return posIndex == nrOfPlayers-1 ;
	}

	void initGame() {
		for(int i = 0; i < clients.length; i++) {
			clients[i].send(MessageFactory.worldProperties(positions[i]));
		}
	}

	void sendToAllExcept(Message message, String position) {
		for(int i = 0; i < clients.length; i++) {
			if(!positions[i].equals(position)) {
				clients[i].send(message);
			}
		}
	}

}
