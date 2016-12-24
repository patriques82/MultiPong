package com.multipong.server;

import java.util.ArrayList;
import java.util.List;

import com.multipong.shared.Network.BallHitMessage;
import com.multipong.shared.Network.Message;

class ClientsManager {

	private List<Client> clients;
	private int nrOfPlayers;
	
	ClientsManager(int players) {
		nrOfPlayers = players;
		clients = new ArrayList<>(nrOfPlayers);
	}
	
	void add(Client client) {
		if(!isFull()) {
			//Tuple<String, String> tup = new Tuple<>();
			clients.add(client);
		}
	}

	boolean isFull() {
		return clients.size() == nrOfPlayers;
	}

	void initGame() {
		for(Client c : clients) {
			c.send(MessageFactory.worldProperties(c.getPosition()));
		}
	}

	public void sendToAllExcept(Message message, String position) {
		for(Client c : clients) {
			if(!c.getPosition().equals(position)) {
				c.send(message);
			}
		}
	}

}
