package com.multipong.client;

import java.io.IOException;
import java.util.logging.Logger;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import com.multipong.shared.Network;
import com.multipong.shared.Network.*;

public class ClientFacade {

	private final Client client;
	private final BallTracker ballTracker;
	private final PaddleTracker otherPaddleTracker;
	private final PaddleSender myPaddleSender;
	
	private World world;
	private Display display;

	private Object monitor; // monitor for waiting for all objects getting init

	ClientFacade() {
		client = new Client();
		ballTracker = new BallTracker();
		otherPaddleTracker = new PaddleTracker();
		myPaddleSender = new PaddleSender();
		monitor = new Object();
	}

	/**
	 * Initializer for the world, initializes trackers and senders
	 * @param props message with game properties
	 * @return world the World!
	 */
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

	/**
	 * Connect to server, add Listener for incoming messages from server and wait for connection.
	 */
	public void connectAndWait() {
		client.start();
		Network.register(client);
		try {
			// Connect to server
			client.connect(Network.CONNECT_TIMEOUT_MS,
						   Network.HOST,
						   Network.TCP_PORT,
						   Network.UDP_PORT);
			client.sendTCP(new RegisterRequest());
		} catch (IOException e) {
			Logger.getLogger("client").severe("Could not connect with server");
		}
		
		// Add listener for incoming messages
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof PropMessage) {
					PropMessage props = (PropMessage) object;
					display = Display.createDisplay(props.width, props.height);
					display.addKeyListener(KeyManager.getKeyManager());
					world = initWorld(props);
					synchronized (monitor) {
						monitor.notifyAll(); // now we can stop waiting
					}
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
		waitForInitialization();
	}

	/**
	 * Waits for notification from listener (connectAndWait) for initialization 
	 */
	private void waitForInitialization() {
		synchronized (monitor) {
			try {
				monitor.wait();
			} catch (InterruptedException e) {
				Logger.getLogger("client").info("Was interrupted during wait");
				System.exit(0);
			}
		}
	}
	
	/**
	 * Send paddle position
	 */
	public void send() {
		client.sendTCP(myPaddleSender.toMessage());
	}
	
	/**
	 * Getter for world
	 * @return world the World!
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Getter for display
	 * @return display the Display!
	 */
	public Display getDisplay() {
		return display;
	}


}
