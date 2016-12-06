package com.multipong.client;

import java.awt.Point;

public abstract class MyPaddle extends Paddle {

	protected Ball ball;

	protected MyPaddle(String pos, Ball ball) {
		this.position = pos;
		this.ball = ball;
		this.upperLeft = new Point();
	}


	public String getPosition() {
		return position;
	}

	public Point getPoint() {
		return upperLeft;
	}

	public int getVx() {
		return vx;
	}

	public int getVy() {
		return vy;
	}

}
