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
class KryoClientFacade implements ClientFacade {

	private final Object monitor; // monitor for waiting for all objects getting initialized
	private final Client client;
	private Display display;
	private World world;

	private MessageHandler<BallHitMessage> ballHandler;
	private MessageHandler<PaddleMessage> myPaddleHandler, otherPaddleHandler;

	KryoClientFacade() {
		monitor = new Object();
		client = new Client();
	}

	@Override
	public void connect() {
		client.start();
		Network.register(client);

		// Connect to server
		try {
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

				if (object instanceof WaitForOthers) { // other paddles
					WaitForOthers wait = (WaitForOthers) object;
					System.out.println("Waiting for other players");
				}

				if (object instanceof BallHitMessage) { // other paddles ball hits
					BallHitMessage ballMessage = (BallHitMessage) object;
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
		Ball ball = new Ball(this, props.width, props.height, props.ballProps);
		OtherPaddle other = new OtherPaddle(props.other);
		MyPaddle paddle = MyPaddle.getPaddle(this, ball, props.your);
		setMessageHandlers(ball, other, paddle);
		world = new World(props.width, props.height, ball, other, paddle);
	}

	private void setMessageHandlers(MessageHandler<BallHitMessage> ball,
									MessageHandler<PaddleMessage> other,
									MessageHandler<PaddleMessage> paddle) {
		otherPaddleHandler = other;
		ballHandler = ball;
		myPaddleHandler = paddle;
	}

	private boolean validProperties(WorldProperties props) {
		boolean worldDimension = props.height > 0 && props.width > 0; 
		boolean ballSpeed = props.ballProps.vx > 0 && props.ballProps.vy > 0;
		boolean ballProps = props.ballProps.diameter > 0 && ballSpeed;
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

	@Override
	public void scheduledSend() {
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
