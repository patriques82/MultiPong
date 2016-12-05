package com.multipong.client;

import java.io.IOException;
import java.util.logging.Logger;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.multipong.shared.Network;
import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.PaddleMessage;
import com.multipong.shared.Network.PropMessage;
import com.multipong.shared.Network.Register;
import com.multipong.shared.NetworkFactory;

public class ClientFacade {

	Client client;
	MessageHandler<PropMessage> propsHandler;
	MessageSender<PaddleMessage> sender;

	ClientFacade(MessageHandler<PropMessage> propsHandler) {
		this.client = NetworkFactory.getClient();
		this.propsHandler = propsHandler;
	}

	public void connect(MessageTracker<BallMessage> ballTracker,
						MessageTracker<PaddleMessage> paddleTracker,
						MessageSender<PaddleMessage> paddleSender) {
		sender = paddleSender;
		client.start();
		Network.register(client);
		try {
			client.connect(Network.CONNECT_TIMEOUT_MS,
						   Network.HOST,
						   Network.TCP_PORT,
						   Network.UDP_PORT);
			client.sendTCP(new Register());
		} catch (IOException e) {
			Logger.getLogger("client").severe("Could not connect with server");
		}
		
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof PropMessage) {
					PropMessage props = (PropMessage) object;
					propsHandler.handle(props);
					Logger.getLogger("client").info("Initialized game with server info");
				}
				if (object instanceof BallMessage) {
					BallMessage ballMessage = (BallMessage) object;
					ballTracker.track(ballMessage);
				}
				if (object instanceof PaddleMessage) { // other paddles
					PaddleMessage paddleMessage = (PaddleMessage) object;
					paddleTracker.track(paddleMessage);
				}
			}
			public void disconnected(Connection connection) {
				System.exit(0);
			}
		});
	}
	
	public void send() {
		client.sendTCP(sender.toMessage());
	}

}
