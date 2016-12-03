package com.multipong.shared;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

public class NetworkFactory {
	
	private static Object LOCK = new Object();
	private static Client client;
	private static Server server;

	public static Client getClient() {
		synchronized(LOCK) {
			if(client == null) {
				client = new Client();
			}
		}
		return client;
	}

	public static Server getServer() {
		synchronized(LOCK) {
			if(server == null) {
				return new Server() {
					protected Connection newConnection () {
						return new ClientConnection();
					}
				};
			}
		}
		return null;
	}

	// This holds per connection state.
	static class ClientConnection extends Connection {
		public String name;
	}

}
