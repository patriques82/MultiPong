package com.multipong.server;

import com.multipong.shared.Network.Message;

import java.util.Random;

import com.multipong.shared.Network.BallProperties;
import com.multipong.shared.Network.PaddleProperties;
import com.multipong.shared.Network.WorldProperties;
import com.multipong.shared.Network.GameIsFull;
import com.multipong.shared.Network.WaitForOthers;

/**
 * Used by clients controller to create messages
 */
class MessageFactory {

	private static final int BALL_SPEED = 10;
	private static final int BALL_DIAMETER = 10;

	private static final int PADDLE_THICKNESS = 10;
	private static final int PADDLE_LENGTH = 50;
	
	private static int worldWidth, worldHeight;
	private static int ballVx, ballVy;
	
	static void init(int w, int h) {
		setRandomBallSpeed();
		worldWidth = w;
		worldHeight = h;
	}

	private static void setRandomBallSpeed() {
		ballVx = new Random().nextInt(BALL_SPEED + 1);
		ballVy = BALL_SPEED - ballVx;
	}

	static Message gameIsFull() {
		return new GameIsFull();
	}
	
	static Message waitForOthers() {
		return new WaitForOthers();
	}
	
	static Message worldProperties(String pos) throws IllegalArgumentException {
		if(!validPosition(pos)) {
			throw new IllegalArgumentException("Unknown position");
		} else {
			WorldProperties prop = new WorldProperties();
			prop.width = worldWidth;
			prop.height = worldHeight;
			prop.ballProps = ballProperties();
			prop.other = paddleProperties(opposite(pos));
			prop.your = paddleProperties(pos);
			return prop;
		}
	}

	private static boolean validPosition(String pos) {
		return pos.equals("up") || pos.equals("bottom") || pos.equals("left") || pos.equals("right");
	}

	private static BallProperties ballProperties() {
		BallProperties ballProps = new BallProperties();
		ballProps.diameter = BALL_DIAMETER;
		ballProps.vx = ballVx;
		ballProps.vy = ballVy;
		return ballProps;
	}

	private static String opposite(String pos) {
		if(pos.equals("up"))
			return "bottom";
		else if(pos.equals("bottom"))
			return "up";
		else if(pos.equals("left"))
			return "right";
		else
			return "left";
	}

	private static PaddleProperties paddleProperties(String pos) {
		PaddleProperties paddle = new PaddleProperties();
		paddle.position = pos;
		paddle.range = paddleRange(pos);
		paddle.width = paddleWidth(pos);
		paddle.height = paddleHeight(pos);
		paddle.x = paddleXPos(pos);
		paddle.y = paddleYPos(pos);
		return paddle;
	}

	private static int paddleRange(String pos) {
		if(pos.equals("up") || pos.equals("bottom"))
			return worldWidth;
		else
			return worldHeight;
	}

	private static int paddleWidth(String pos) {
		if(pos.equals("up") || pos.equals("bottom"))
			return PADDLE_LENGTH;
		else
			return PADDLE_THICKNESS;
	}

	private static int paddleHeight(String pos) {
		if(pos.equals("up") || pos.equals("bottom"))
			return PADDLE_THICKNESS;
		else 
			return PADDLE_LENGTH;
	}

	private static int paddleXPos(String pos) {
		if(pos.equals("left"))
			return 0;
		else if(pos.equals("right"))
			return worldWidth - PADDLE_THICKNESS;
		else
			return worldWidth/2 - PADDLE_LENGTH/2;
	}

	private static int paddleYPos(String pos) {
		if(pos.equals("up"))
			return 0;
		else if(pos.equals("bottom"))
			return worldHeight - PADDLE_THICKNESS;
		else 
			return worldHeight/2 - PADDLE_LENGTH/2;
	}

}
