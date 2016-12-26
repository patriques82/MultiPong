package com.multipong.client;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * The infrastructure of the game, holds the game loop of the game.
 * 
 * @author patriknygren
 *
 */
class Game implements Runnable {
	
	private ClientFacade client; // Server communication

	// Graphics
	private BufferStrategy bs;
	private Graphics g;

	private Thread thread;     // game loop
	private Display display;   // game view

	private World world; 	   // game logic

	private boolean running;   
	
	private static final int FPS = 45; // frames per second
	private static final double FRAME_RATE = 1000_000_000/FPS;

	private static final int MPS = 1;  // messages per second (to send)
	private static final double SEND_RATE = 1000_000_000/MPS;
	
	/**
	 * Initializes all resources needed for the game (Client, Display, Thread)
	 */
	public Game(ClientFacade clientFacade) {
		client = clientFacade;
		client.connect();
		display = client.getDisplay();
		world = client.getWorld();
		thread = new Thread(this);
		running = false;
	}

	/**
	 * The Gameloop
	 */
	public void run() {
		long lastTime = System.nanoTime();
		long frameTimer = 0; // counter for next tick.
		long sendTimer = 0; // counter for next send.
		
		// Game loop
		while(running) {
			long diff = System.nanoTime() - lastTime;
			frameTimer += diff;
			sendTimer += diff;
			lastTime = System.nanoTime();
			if(frameTimer >= FRAME_RATE) {
				tick();
				render();
				frameTimer = 0;
			}
			if(sendTimer >= SEND_RATE) {
				send();
				sendTimer = 0;
			}
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

			// clear screen
			g.clearRect(0, 0, display.getWidth(), display.getHeight());

			// Double buffering
			world.render(g);
			bs.show();
			
			// Clean up
			g.dispose();
		}
	}
	
	private void send() {
		client.scheduledSend();
	}
	
}
