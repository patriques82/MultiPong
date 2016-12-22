package com.multipong.server;

import java.util.ArrayList;
import java.util.List;

public class ClientsController {

	private int index;
	private String[] positions = {"right", "left", "bottom", "up"};
	private List<Client> connections;
	
	ClientsController() {
		index = 0;
		connections = new ArrayList<Client>(Options.getNrOfPlayers());
	}

	void add(Client client) {
		if(!isFull()) {
			client.setPosition(positions[index]);
			connections.add(client);
			index++;
		}
	}

	boolean isFull() {
		return index == Options.getNrOfPlayers() - 1;
	}

}
