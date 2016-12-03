package com.multipong.client;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The infrastructure of the game, holds the game loop of the game.
 * 
 * @author patriknygren
 *
 */
class Game implements Runnable {

	// Graphics
	private BufferStrategy bs;
	private Graphics g;

	private Thread thread;     // game loop
	private Display display;   // game view
	private World world;   	   // game state

	private boolean running;   
	
	private static final int FPS = 40;
	private static final double FRAME_RATE = 1000_000_000/FPS;
	
	private static int WIDTH = 500;
	private static int HEIGHT = 500;
	private static int BALL_DIAMETER = 10;

	public Game(GameClient gameClient) {
		// Game Resources
		thread = new Thread(this);
		display = Display.createDisplay(WIDTH, HEIGHT);
		display.addKeyListener(KeyManager.getKeyManager());
		running = false;

		// Game Logic
		Collection<GameObject> paddles = new ArrayList<>();
		GameObject ball = new Ball(WIDTH, HEIGHT, BALL_DIAMETER);
		GameObject paddle = Paddle.getPaddle(Paddle.Position.UP, WIDTH, HEIGHT, ball);
		paddles.add(paddle);
		world = new World(WIDTH, HEIGHT, ball, paddles);
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
		world.tick();
	}

	/**
	 * Render game state on canvas
	 */
	private void render() {
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
