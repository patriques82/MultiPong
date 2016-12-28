package com.multipong.server;

import java.util.Random;

import com.multipong.shared.Network.*;

/**
 * Used by clients controller to create messages
 */
class MessageFactory {

	static final int WORLD_WIDTH = 500;
	static final int WORLD_HEIGHT = 500;

	static final int BALL_SPEED = 10;
	static final int BALL_DIAMETER = 10;

	static final int PADDLE_THICKNESS = 10;
	static final int PADDLE_LENGTH = 50;
	
	private static int ballVx, ballVy;
	
	static void init() {
		setRandomBallSpeed();
	}

	private static void setRandomBallSpeed() {
		ballVx = new Random().nextInt(BALL_SPEED + 1);
		ballVy = BALL_SPEED - ballVx;
	}

	static Message gameIsFull(int players) {
		GameIsFull isFull = new GameIsFull();
		isFull.players = players;
		return isFull;
	}
	
	static Message waitForOthers(int total, int waiting) {
		WaitForOthers wait = new WaitForOthers();
		wait.total = total;
		wait.waiting = waiting;
		return wait;
	}
	
	static WorldProperties worldProperties(Position pos) throws IllegalArgumentException {
		WorldProperties prop = new WorldProperties();
		prop.width = WORLD_WIDTH;
		prop.height = WORLD_HEIGHT;
		prop.ballProps = ballProperties();
		prop.other = paddleProperties(opposite(pos));
		prop.your = paddleProperties(pos);
		return prop;
	}

	private static BallProperties ballProperties() {
		BallProperties ballProps = new BallProperties();
		ballProps.diameter = BALL_DIAMETER;
		ballProps.vx = ballVx;
		ballProps.vy = ballVy;
		return ballProps;
	}

	private static Position opposite(Position pos) {
		if(pos == Position.UP)
			return Position.BOTTOM;
		else if(pos == Position.BOTTOM)
			return Position.UP;
		else if(pos == Position.LEFT)
			return Position.RIGHT;
		else
			return Position.LEFT;
	}

	private static PaddleProperties paddleProperties(Position pos) {
		PaddleProperties paddle = new PaddleProperties();
		paddle.position = pos;
		paddle.range = paddleRange(pos);
		paddle.width = paddleWidth(pos);
		paddle.height = paddleHeight(pos);
		paddle.x = paddleXPos(pos);
		paddle.y = paddleYPos(pos);
		return paddle;
	}

	private static int paddleRange(Position pos) {
		if(pos == Position.UP || pos == Position.BOTTOM)
			return WORLD_WIDTH;
		else
			return WORLD_HEIGHT;
	}

	private static int paddleWidth(Position pos) {
		if(pos == Position.UP || pos == Position.BOTTOM)
			return PADDLE_LENGTH;
		else
			return PADDLE_THICKNESS;
	}

	private static int paddleHeight(Position pos) {
		if(pos == Position.UP || pos == Position.BOTTOM)
			return PADDLE_THICKNESS;
		else 
			return PADDLE_LENGTH;
	}

	private static int paddleXPos(Position pos) {
		if(pos == Position.LEFT)
			return 0;
		else if(pos == Position.RIGHT)
			return WORLD_WIDTH - PADDLE_THICKNESS;
		else
			return WORLD_WIDTH/2 - PADDLE_LENGTH/2;
	}

	private static int paddleYPos(Position pos) {
		if(pos == Position.UP)
			return 0;
		else if(pos == Position.BOTTOM)
			return WORLD_HEIGHT - PADDLE_THICKNESS;
		else 
			return WORLD_HEIGHT/2 - PADDLE_LENGTH/2;
	}

}