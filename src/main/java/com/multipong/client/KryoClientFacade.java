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
public class KryoClientFacade implements ClientFacade {

	private final Client client;
	private World world;
	private Display display;
	private Object monitor; // monitor for waiting for all objects getting initialized

	private MessageHandler<BallMessage> ballHandler;
	private MessageHandler<PaddleMessage> myPaddleHandler, otherPaddleHandler;

	KryoClientFacade() {
		client = new Client();
		monitor = new Object();
	}

	@Override
	public void connect() {
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
					ballHandler.trackMessage(ballMessage);
				}
				if (object instanceof PaddleMessage) { // other paddles
					PaddleMessage paddleMessage = (PaddleMessage) object;
					otherPaddleHandler.trackMessage(paddleMessage);
				}
			}
			public void disconnected(Connection connection) {
				System.exit(0);
			}
		});
		waitForInitialization();
	}

	private void initWorld(WorldProperties props) {
		if(!validProperties(props)) {
			throw new IllegalArgumentException("unknown ball, otherpaddle or paddle");
		}
		Ball ball = new Ball(this, props.width, props.height, props.ball);
		OtherPaddle other = new OtherPaddle(props.other);
		MyPaddle paddle = MyPaddle.getPaddle(this, props.width, props.height, ball, props.your);
		setMessageHandlers(ball, other, paddle);
		world = new World(props.width, props.height, ball, other, paddle);
	}

	private void setMessageHandlers(Ball ball, OtherPaddle other, MyPaddle paddle) {
		otherPaddleHandler = other;
		ballHandler = ball;
		myPaddleHandler = paddle;
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
		System.out.println("worldDimension: " + worldDimension);
		System.out.println("ballSpeed: " + ballSpeed);
		System.out.println("ballPosition: " + ballPosition);
		System.out.println("ballProps: " + ballProps);
		System.out.println("myPaddleDimension: " + myPaddleDimension);
		System.out.println("otherPaddleDimension: " + otherPaddleDimension);
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

	@Override
	public void send() {
		client.sendUDP(myPaddleHandler.toMessage());
	}
	
	@Override
	public void emitEvent(Message message) {
		client.sendTCP(message);
	}
	
	@Override
	public World getWorld() {
		return world;
	}
	
	@Override
	public Display getDisplay() {
		return display;
	}

}
