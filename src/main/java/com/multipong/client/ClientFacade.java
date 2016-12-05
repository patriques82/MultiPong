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

	final Client client;
	final BallTracker ballTracker;
	final PaddleTracker otherPaddleTracker;
	final PaddleSender myPaddleSender;
	MessageHandler<PropMessage> propsHandler;


	ClientFacade(MessageHandler<PropMessage> propsHandler) {
		client = NetworkFactory.getClient();
		ballTracker = new BallTracker();
		otherPaddleTracker = new PaddleTracker();
		myPaddleSender = new PaddleSender();
		this.propsHandler = propsHandler;
	}

	public World initWorld(PropMessage props) {
		ballTracker.init(props.width, props.height, props.ball);
		otherPaddleTracker.init(props.width, props.height, props.other);
		myPaddleSender.init(props.width, props.height, ballTracker.getBall(), props.your);
		return new World(props.width,
						 props.height,
						 ballTracker.getBall(),
						 otherPaddleTracker.getPaddle(),
						 myPaddleSender.getPaddle());
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
					otherPaddleTracker.track(paddleMessage);
				}
			}
			public void disconnected(Connection connection) {
				System.exit(0);
			}
		});
	}
	
	public void send() {
		client.sendTCP(myPaddleSender.toMessage());
	}


}
