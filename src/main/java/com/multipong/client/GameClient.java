package com.multipong.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.multipong.shared.*;
import com.multipong.shared.Network.SomeRequest;
import com.multipong.shared.Network.SomeResponse;

public class GameClient extends Client {
	
	Client client;
	
	public GameClient() {
		client = new Client();
		Network.register(client);
		
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof SomeResponse) {
					SomeResponse response = (SomeResponse) object;
					System.out.println(response.text);
				}
			}
		});
		client.start();
		try {
			client.connect(Network.CONNECT_TIMEOUT_MS,
						   Network.HOST,
						   Network.TCP_PORT,
						   Network.UDP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			String input = "DEFAULT";
			try {
				input = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			SomeRequest req = new SomeRequest();
			req.text = input;
			client.sendTCP(req);
		}
	}

	public static void main(String[] args) {
		new GameClient();
	}

}
