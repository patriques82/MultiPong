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
	static enum Position {
		UP, BOTTOM, LEFT, RIGHT
	}
	
	protected GameObject ball;
	
	Paddle(Position pos, GameObject ball) {
		this.position = pos;
		this.ball = ball;
	}

	/**
	 * Factory method to create paddles.
	 * @param position2 
	 * @param pos the position of the paddle
	 * @param worldWidth the width of the world
	 * @param worldHeight the height of the world
	 * @return paddle
	 */
	static Paddle getPaddle(String position, int worldWidth, int worldHeight, GameObject ball) {
		if(position.equals("bottom")) {
			return new HorizontalPaddle(Position.BOTTOM, worldWidth, worldHeight, ball);
		}
		else if(position.equals("up")) {
			return new HorizontalPaddle(Position.UP, worldWidth, worldHeight, ball);
		}
		else if(position.equals("right")) {
			return new VerticalPaddle(Position.RIGHT, worldWidth, worldHeight, ball);
		}
		else if(position.equals("left")) {
			return new VerticalPaddle(Position.LEFT, worldWidth, worldHeight, ball);
		}
		return null;
	}

}
