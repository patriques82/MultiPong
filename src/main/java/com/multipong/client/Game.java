package com.multipong.client;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * The infrastructure of the game, holds the game loop of the game.
 * 
 * @author patriknygren
 *
 */
public class Game implements Runnable {

	private GameClient client; // networking

	// Graphics
	private BufferStrategy bs;
	private Graphics g;

	private Thread thread;     // game loop
	private Display display;   // game view
	private GameState state;   // game state

	private boolean running;   
	
	private static final int FPS = 40;
	private static final double FRAME_RATE = 1000_000_000/FPS;

	public Game(GameClient gameClient) {
		client = gameClient;
		thread = new Thread(this);
		display = Display.createDisplay(500, 500);
		display.addKeyListener(KeyManager.getKeyManager());
		state = new GameState();
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
		state.tick();
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
		state.render(g);
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
