package com.multipong.server;

/**
 * Configuration class for game properties.
 *
 */
public class Conf {
	// Global
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;

	// Ball
	public static final int BALL_SPEED = 10;
	public static final int BALL_DIAMETER = 10;
	public static final int BALL_START_X = WIDTH/2;
	public static final int BALL_START_Y = HEIGHT/2;
	
	// Paddle
	public static final int PADDLE_THICKNESS = 10;
	public static final int PADDLE_LENGTH = 50;
	
	// Paddle up
	public static final int PADDLE_UP_UPPER_LEFT_X = WIDTH/2 - PADDLE_THICKNESS/2;
	public static final int PADDLE_UP_UPPER_LEFT_Y = 0;

	// Paddle bottom
	public static final int PADDLE_BOTTOM_UPPER_LEFT_X = WIDTH/2 - PADDLE_THICKNESS/2;
	public static final int PADDLE_BOTTOM_UPPER_LEFT_Y = HEIGHT - PADDLE_THICKNESS;
}
