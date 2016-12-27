package com.multipong.server;

import com.multipong.shared.Network.Message;

class ClientsManager {

	private final static String[] positions = {"right", "left", "up", "bottom"};
	private Client[] clients;
	private int index;

	private int totalPlayers;
	
	ClientsManager(int players) throws IllegalArgumentException {
		if(players > positions.length)
			throw new IllegalArgumentException(String.format("Max %d players", positions.length));
		totalPlayers = players;
		clients = new Client[totalPlayers];
		index = 0;
	}
	
	void add(Client client) {
		if(!isFull()) {
			clients[index] = client;
			index++;
		}
	}

	boolean isFull() {
		return index == totalPlayers;
	}

	void initGame() {
		for(int i = 0; i < totalPlayers; i++) {
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
	
	int getNrOfPlayers() {
		return index;
	}
	
	int getTotalPlayers() {
		return totalPlayers;
	}

}
