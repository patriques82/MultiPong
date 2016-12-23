package com.multipong.server;

import java.util.ArrayList;
import java.util.List;

import com.multipong.shared.Network.Message;

class ClientsManager {

	private List<Client> clients;
	
	ClientsManager() {
		clients = new ArrayList<>(Options.getNrOfPlayers());
	}
	
	void add(Client client) {
		if(!isFull()) {
			clients.add(client);
		}
	}

	boolean isFull() {
		return clients.size() == Options.getNrOfPlayers();
	}

	void initGame() {
		for(Client c : clients) {
			c.send(WorldFactory.properties(c.getPosition()));
		}
	}

	void sendToAll(Message message) {
		for(Client c : clients) {
			c.send(message);
		}
	}

}
