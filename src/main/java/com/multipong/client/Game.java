package com.multipong.client;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.PaddleMessage;
import com.multipong.shared.Network.PropMessage;

/**
 * The infrastructure of the game, holds the game loop of the game.
 * 
 * @author patriknygren
 *
 */
class Game implements Runnable {
	
	private ClientFacade clientFacade; // Server communication

	// Graphics
	private BufferStrategy bs;
	private Graphics g;

	private Thread thread;     // game loop
	private Display display;   // game view

	private World world;

	private boolean running;   
	
	private static final int FPS = 35; // frames per second
	private static final double FRAME_RATE = 1000_000_000/FPS;

	private static final int MPS = 1;  // messages per second
	private static final double SEND_RATE = 1000_000_000/MPS;
	
	/**
	 * Initializes all resources needed for the game (Client, Display, Threads etc)
	 */
	public Game() {
		MessageTracker<BallMessage, Ball> ballTracker = new BallTracker();
		MessageTracker<PaddleMessage, OtherPaddle> otherPaddleTracker = new PaddleTracker();
		MessageSender<PaddleMessage, MyPaddle> myPaddleSender = new PaddleSender();

		clientFacade = new ClientFacade(new MessageHandler<PropMessage>() {
			@Override
			public void handle(PropMessage props) {
				display = Display.createDisplay(props.width, props.height);
				display.addKeyListener(KeyManager.getKeyManager());

				Ball ball = ballTracker.init(props.width, props.height, props.ball);
				OtherPaddle other = otherPaddleTracker.init(props.width, props.height, props.otherPaddle);
				MyPaddle myPaddle = MyPaddle.getPaddle(props.width, props.height, props.yourPaddle, ball);
				myPaddleSender.setSender(myPaddle);
				world = new World(props.width, props.height, ball, other, myPaddle);
			}
		});
		clientFacade.connect(ballTracker, otherPaddleTracker, myPaddleSender);
		thread = new Thread(this);
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		long frameTimer = 0; // counter for next tick.
		long sendTimer = 0; // counter for next send.
		
		// Game loop
		while(running) {
			long now = System.nanoTime();
			frameTimer += now - lastTime;
			sendTimer += now - lastTime;
			lastTime = System.nanoTime();
			if(frameTimer >= FRAME_RATE) {
				tick();
				render();
				frameTimer = 0;
			}
			if(sendTimer >= SEND_RATE) {
				clientFacade.send();
				sendTimer = 0;
			}
		}
	}
	
	/**
	 * Update game state
	 */
	private void tick() {
		if(world != null)
			world.tick();
	}

	/**
	 * Render game state on canvas
	 */
	private void render() {
		if(display != null) {
			bs = display.getCanvas().getBufferStrategy();
			if(bs == null) {
				display.getCanvas().createBufferStrategy(3);
				return; // exit to avoid errors
			}
			g = bs.getDrawGraphics();

			// Clear screen
			g.clearRect(0, 0, display.getWidth(), display.getHeight());

			// Double buffering
			world.render(g);
			bs.show();
			
			// Clean up
			g.dispose();
		}
	}

	/**
	 * Start the game
	 */
	public void start() {
		if(!running) {
			running = true;
			thread.start();
		}
	}
	
	/**
	 * Stop the game
	 */
	public void stop() {
		running = false;
	}
	
}
