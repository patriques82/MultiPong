package com.multipong.client;

public class Provider {

	private Ball ball;
	private Paddle paddle;

	public Provider(Ball b, Paddle p) {
		ball = b;
		paddle = p;
	}

	public void setBall(int x, int y, int vx, int vy) {
		ball.setPosition(x, y);
		ball.setSpeed(vx, vy);
	}

	public void setPaddle(String position, int x, int y) {
		paddle.setPosition(x, y);
	}
	
	
}
