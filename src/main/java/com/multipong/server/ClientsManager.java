package com.multipong.server;

import com.multipong.shared.Network.Message;
import com.multipong.shared.Network.Position;

class ClientsManager {

	private Client[] clients;
	private int totalPlayers;
	private int index;
	
	ClientsManager(int players) throws IllegalArgumentException {
		if(players > Position.values().length)
			throw new IllegalArgumentException(String.format("Max %d players", Position.values().length));
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
			clients[i].send(MessageFactory.worldProperties(Position.values()[i]));
		}
	}

	void sendToAllExcept(Message message, Position pos) {
		for(int i = 0; i < clients.length; i++) {
			if(!(Position.values()[i] == pos)) {
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
