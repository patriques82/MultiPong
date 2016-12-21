package com.multipong.client;

import java.io.IOException;
import java.util.logging.Logger;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import com.multipong.shared.Network;
import com.multipong.shared.Network.*;

/**
 * Facade for Kryonet client
 */
public class ClientFacade {

	private final Client client;
	private World world;
	private Display display;
	private Object monitor; // monitor for waiting for all objects getting initialized

	private Ball ball; 			// message handler
	private OtherPaddle other; 	// message handler
	private MyPaddle paddle;  	// message sender

	ClientFacade() {
		client = new Client();
		monitor = new Object();
	}

	void initWorld(WorldProperties props) {
		if(!validProperties(props)) {
			throw new IllegalArgumentException("unknown ball, otherpaddle or paddle");
		}
		ball = new Ball(this, props.width, props.height, props.ball);
		other = new OtherPaddle(props.other);
		paddle = MyPaddle.getPaddle(this, props.width, props.height, ball, props.your);
		world = new World(props.width, props.height, ball, other, paddle);
	}

	void connectAndWait() {
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
					try {
						initWorld(props);
					} catch(IllegalArgumentException e) {
						Logger.getLogger("client").severe(e.getMessage());
					}
					stopWaiting();
				}
				if (object instanceof BallMessage) {
					BallMessage ballMessage = (BallMessage) object;
					ball.trackMessage(ballMessage);
				}
				if (object instanceof PaddleMessage) { // other paddles
					PaddleMessage paddleMessage = (PaddleMessage) object;
					other.trackMessage(paddleMessage);
				}
			}
			public void disconnected(Connection connection) {
				System.exit(0);
			}
		});
		waitForInitialization();
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
	
	World getWorld() {
		return world;
	}
	
	Display getDisplay() {
		return display;
	}

	private boolean validProperties(WorldProperties props) {
		boolean worldDimension = props.height > 0 && props.width > 0; 
		boolean ballSpeed = props.ball.vx > 0 && props.ball.vy > 0;
		boolean ballPosition = props.ball.x > 0 && props.ball.x < props.width &&
							   props.ball.y > 0 && props.ball.y < props.height;
		boolean ballProps = props.ball.d > 0 && ballSpeed && ballPosition;
		boolean myPaddleDimension = props.your.height > 0 && props.your.height < props.height &&
									props.your.width > 0 && props.your.width < props.width;
		boolean otherPaddleDimension = props.other.height > 0 && props.other.height < props.height &&
									   props.other.width > 0 && props.other.width < props.width;
		return worldDimension && ballProps && myPaddleDimension && otherPaddleDimension;
	}


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
	
	private void stopWaiting() {
		synchronized (monitor) {
			monitor.notifyAll(); // now we can stop waiting
		}
	}

}
