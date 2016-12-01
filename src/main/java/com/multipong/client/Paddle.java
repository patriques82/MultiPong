package com.multipong.client;

import java.awt.Point;

/**
 * The paddle of Pong.
 * 
 * @author patriknygren
 */
public abstract class Paddle implements GameObject {
	
	protected static double SIZE_PERCENTAGE_OF_WINDOW = 0.15;
	protected static int THICKNESS = 20;
	protected int width;
	protected int height;
	
	protected static int SPEED = 10;
	protected Point upperLeft;			 // upper left corner (for rendering)

	protected Position position; 		 // screen position of paddle
	enum Position {
		UP, BOTTOM, LEFT, RIGHT
	}
	
	protected GameObject ball;
	
	Paddle(Position pos, GameObject ball) {
		position = pos;
		this.ball = ball;
	}

	/**
	 * Factory method to create paddles.
	 * @param pos the position of the paddle
	 * @param worldWidth the width of the world
	 * @param worldHeight the height of the world
	 * @return paddle
	 */
	static Paddle getPaddle(Position pos, int worldWidth, int worldHeight, GameObject ball) {
		if(pos == Position.BOTTOM || pos == Position.UP) {
			return new HorizontalPaddle(pos, worldWidth, worldHeight, ball);
		} else if(pos == Position.LEFT || pos == Position.RIGHT) {
			return new VerticalPaddle(pos, worldWidth, worldHeight, ball);
		}
		return null;
	}

}
