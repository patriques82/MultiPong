package com.multipong.server;

import com.multipong.shared.Network.Message;

class ClientsManager {

	private final static String[] positions = {"right", "left", "up", "bottom"};
	private Client[] clients;
	private int index;

	private int nrOfPlayers;
	
	ClientsManager(int players) throws IllegalArgumentException {
		if(players > positions.length)
			throw new IllegalArgumentException(String.format("Max %d players", positions.length));
		nrOfPlayers = players;
		clients = new Client[nrOfPlayers];
		index = 0;
	}
	
	void add(Client client) {
		if(!isFull()) {
			clients[index] = client;
			index++;
		}
	}

	boolean isFull() {
		return index == nrOfPlayers;
	}

	void initGame() {
		for(int i = 0; i < nrOfPlayers; i++) {
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
