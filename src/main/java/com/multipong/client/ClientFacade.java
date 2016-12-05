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
	
	private World world;
	private Display display;

	ClientFacade() {
		client = NetworkFactory.getClient();
		ballTracker = new BallTracker();
		otherPaddleTracker = new PaddleTracker();
		myPaddleSender = new PaddleSender();
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

	public void connectAndWait() {
		Object monitor = new Object(); // monitor for waiting for all objects getting init
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
					display = Display.createDisplay(props.width, props.height);
					display.addKeyListener(KeyManager.getKeyManager());
					world = initWorld(props);
					monitor.notifyAll(); // now we can stop waiting
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
		
		while(world == null) {
			try {
				monitor.wait();
			} catch (InterruptedException e) {
				Logger.getLogger("client").info("Was interrupted during wait");
			}
		}
	}
	
	public void send() {
		client.sendTCP(myPaddleSender.toMessage());
	}
	
	public World getWorld() {
		return world;
	}
	
	public Display getDisplay() {
		return display;
	}


}
