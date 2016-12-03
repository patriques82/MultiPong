package com.multipong.client;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.multipong.shared.GameInitializer;
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

	private Ball ball;
	private Paddle paddle;
	private World world;

	private boolean running;   
	
	private static final int FPS = 35;
	private static final double FRAME_RATE = 1000_000_000/FPS;
	
	/**
	 * Initializes all resources needed for the game (Client, Display, Threads etc)
	 */
	public Game() {
		clientFacade = new ClientFacade(new GameInitializer() {
			@Override
			public void initGame(PropMessage props) {
				display = Display.createDisplay(props.width, props.height);
				display.addKeyListener(KeyManager.getKeyManager());
				ball = new Ball(props.width, props.height, props.diameter);
				paddle = Paddle.getPaddle(props.position, props.width, props.height, ball);
				world = new World(props.width, props.height, ball, paddle);
			}
		});
		clientFacade.connect(ball, paddle);
		thread = new Thread(this);
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = 0; // counter for next tick.
		
		// Game loop
		while(running) {
			long now = System.nanoTime();
			timer += now - lastTime;
			lastTime = System.nanoTime();
			if(timer >= FRAME_RATE) {
				tick();
				render();
				timer = 0;
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
