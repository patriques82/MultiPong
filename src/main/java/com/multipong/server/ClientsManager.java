package com.multipong.server;

import java.util.ArrayList;
import java.util.List;

import com.multipong.shared.Network.Message;

public class ClientsManager {

	private List<Client> connections;
	
	ClientsManager() {
		connections = new ArrayList<>(Options.getNrOfPlayers());
	}
	
	void add(Client client) {
		if(!isFull()) {
			connections.add(client);
		}
	}

	boolean isFull() {
		return connections.size() == Options.getNrOfPlayers();
	}

	void initGame() {
		for(Client c : connections) {
			c.send(WorldFactory.properties(c.getPosition()));
		}
	}

	void sendToAll(Message message) {
		for(Client c : connections) {
			c.send(message);
		}
	}

}
