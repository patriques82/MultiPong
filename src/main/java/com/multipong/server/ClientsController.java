package com.multipong.server;

import java.util.ArrayList;
import java.util.List;

public class ClientsController {

	private List<Client> connections;
	
	ClientsController() {
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

}
