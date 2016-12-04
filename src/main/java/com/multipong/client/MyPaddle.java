package com.multipong.client;

import java.awt.Point;
import java.util.logging.Logger;

import com.multipong.shared.Network.PaddleMessage;

public abstract class MyPaddle extends Paddle implements Sendable {

	protected Ball ball;

	protected MyPaddle(String pos, Ball ball) {
		this.position = pos;
		this.ball = ball;
	}

	/**
	 * Factory method to create paddles.
	 * @param position position of the paddle
	 * @param width width of paddle
	 * @param height height of paddle  
	 * @param worldWidth the width of the world
	 * @param worldHeight the height of the world
	 * @param ball the ball
	 * @return paddle
	 */
	static MyPaddle getPaddle(int worldWidth, int worldHeight, PaddleMessage paddleMsg, Ball ball) {
		Logger.getLogger("game").info("init bottom paddle");
		String position = paddleMsg.position;
		if(position.equals("bottom") || position.equals("up")) {
			return new HorizontalPaddle(position, paddleMsg.width, paddleMsg.height, worldWidth, ball);
		}
		else if(position.equals("right") || position.equals("left")) {
			return new VerticalPaddle(position, paddleMsg.width, paddleMsg.height, worldHeight, ball);
		}
		else {
			Logger.getLogger("game").severe("could not init bottom paddle");
		}
		return null;
	}


	@Override
	public String getPosition() {
		return position;
	}

	@Override
	public Point getPoint() {
		return upperLeft;
	}

	@Override
	public int getVx() {
		return vx;
	}

	@Override
	public int getVy() {
		return vy;
	}

}
