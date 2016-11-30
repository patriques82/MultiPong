package com.multipong.client;

import java.awt.Graphics;
import java.awt.Point;

/**
 * The paddle of Pong.
 * 
 * @author patriknygren
 */
public abstract class Paddle {
	
	protected static double SIZE_PERCENTAGE_OF_WINDOW = 0.15;
	protected static int THICKNESS = 20;
	protected int width;
	protected int height;
	
	protected static int SPEED = 10;
	protected Position position; 		 // Up, right, left or down paddle
	protected Point upperLeft;			 // upper left corner (for rendering)
	
	Paddle(Position pos, int w, int h) {
		position = pos;
	}

	/**
	 * Abstract method which tells the start point of the paddles center
	 * @param pos
	 * @param worldWidth width of world
	 * @param worldHeight height of world
	 * @return point
	 */
	abstract protected Point getUpperLeft(Position pos, int worldWidth, int worldHeight);

	/**
	 * Update the paddle position
	 */
	abstract void tick();

	/**
	 * Factory method to create paddles.
	 * @param pos the position of the paddle
	 * @param worldWidth the width of the world
	 * @param worldHeight the height of the world
	 * @return paddle
	 */
	static Paddle getPaddle(Position pos, int worldWidth, int worldHeight) {
		if(pos == Position.DOWN || pos == Position.UP) {
			return new HorizontalPaddle(pos, worldWidth, worldHeight);
		} else if(pos == Position.LEFT || pos == Position.RIGHT) {
			return new VerticalPaddle(pos, worldWidth, worldHeight);
		}
		return null;
	}

	/**
	 * Render the paddle on Graphics by drawing a simple rectangle
	 * @param g
	 */
	abstract void render(Graphics g);

	
}
