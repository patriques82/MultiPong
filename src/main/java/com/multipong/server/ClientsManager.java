package com.multipong.server;

import java.util.ArrayList;
import java.util.List;

import com.multipong.shared.Network.Message;
import com.multipong.shared.Network.Response;
import com.multipong.shared.Network.WaitForOthersResponse;

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

	public void initGame() {
		for(Client c : connections) {
			c.sendResponse(MessageFactory.worldProperties(c.getPosition()));
		}
	}

	public void sendToAll(Message message) {
		for(Client c : connections) {
			c.sendMessage(message);
		}
	}

	public void sendToAll(Response response) {
		for(Client c : connections) {
			c.sendResponse(response);
		}
	}


}
