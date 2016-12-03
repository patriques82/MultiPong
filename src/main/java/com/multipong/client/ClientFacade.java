package com.multipong.client;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.multipong.shared.GameInitializer;
import com.multipong.shared.Network;
import com.multipong.shared.Network.PropMessage;
import com.multipong.shared.Network.Register;
import com.multipong.shared.NetworkFactory;

public class ClientFacade {

	Client client;
	GameInitializer initializer;

	ClientFacade(GameInitializer initializer) {
		this.client = NetworkFactory.getClient();
		this.initializer = initializer;
	}
	
	public void connect() {
		client.start();
		Network.register(client);
		try {
			client.connect(Network.CONNECT_TIMEOUT_MS,
						   Network.HOST,
						   Network.TCP_PORT,
						   Network.UDP_PORT);
			client.sendTCP(new Register());
		} catch (IOException e) {
			System.out.println("Connection error");
			e.printStackTrace();
		}
		
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof PropMessage) {
					PropMessage props = (PropMessage) object;
					initializer.initGame(props);
					System.out.println("Got PropMessage: " + props.height + ", " + props.width);
				}
			}
			public void disconnected(Connection connection) {
				System.exit(0);
			}
		});
		
	}

}
