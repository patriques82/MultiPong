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
	
	private World world;
	private Ball ball; // kept only to handle messages
	private OtherPaddle other; // kept only to handle messages
	private MyPaddle paddle;  // kept only to send messages
	private Display display;

	private Object monitor; // monitor for waiting for all objects getting init

	ClientFacade() {
		client = new Client();
		monitor = new Object();
	}

	/**
	 * Initializer for the world, initializes trackers and senders
	 * @param props message with game properties
	 * @return world the World!
	 */
	public World initWorld(WorldProperties props) {

		ball = new Ball(this, props.width, props.height, props.ball.d, props.ball.x, props.ball.y, props.ball.vx, props.ball.vy);
		other = new OtherPaddle(props.other.position, props.other.width, props.other.height, props.other.x, props.other.y);
		paddle = MyPaddle.getPaddle(this, props.width, props.height, ball, props.your);
		if(ball == null || other == null || paddle == null) {
			throw new NullPointerException("unknown ball, otherpaddle or paddle");
		}
		return new World(props.width, props.height, ball, other, paddle);
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
				if (object instanceof WorldProperties) {
					WorldProperties props = (WorldProperties) object;
					display = Display.createDisplay(props.width, props.height);
					display.addKeyListener(KeyManager.getKeyManager());
					world = initWorld(props);
					synchronized (monitor) {
						monitor.notifyAll(); // now we can stop waiting
					}
				}
				if (object instanceof BallMessage) {
					BallMessage ballMessage = (BallMessage) object;
					ball.track(ballMessage);
				}
				if (object instanceof PaddleMessage) { // other paddles
					PaddleMessage paddleMessage = (PaddleMessage) object;
					other.track(paddleMessage);
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
	 * Send paddle message, called by Game at scheduled times
	 */
	public void send() {
		client.sendTCP(paddle.toMessage());
	}
	
	/**
	 * Send event message, called by some Sender 
	 * @param message
	 */
	public void emitEvent(Message message) {
		client.sendTCP(message);
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
