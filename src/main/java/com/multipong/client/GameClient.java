package com.multipong.client;

import java.io.IOException;
import java.util.Scanner;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.multipong.shared.Network;
import com.multipong.shared.Network.SomeRequest;
import com.multipong.shared.Network.SomeResponse;

public class GameClient extends Client {
	
	private Client client;
	
	public GameClient() throws IOException {
		client = new Client();
		client.start();
		client.connect(Network.CONNECT_TIMEOUT_MS,
					   Network.HOST,
					   Network.TCP_PORT,
					   Network.UDP_PORT);
		Network.register(client);
		
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof SomeResponse) {
					SomeResponse response = (SomeResponse) object;
					System.out.println(response.text);
				}
			}
			public void disconnected (Connection connection) {
				System.exit(0);
			}
		});

		try(Scanner sc = new Scanner(System.in)) {
			String input;
			while(true) {
				input = sc.nextLine();
				SomeRequest req = new SomeRequest();
				req.text = input;
				client.sendTCP(req);
			}
		}
	}

	public static void main(String[] args) {
		new Game(null);
//		Game game;
//		try {
//			game = new Game(new GameClient());
//			game.start();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
