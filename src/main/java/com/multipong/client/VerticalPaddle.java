package com.multipong.client;

import com.multipong.shared.Network.PaddleProperties;

class VerticalPaddle extends MyPaddle {

	VerticalPaddle(ClientFacade facade, Ball ball, PaddleProperties props) {
		super(facade, ball, props);
	}

	@Override
	public void tick() {
		updateSpeed();
		if(ballHit()) {
			ball.bounceX();
			sendBallHit(ball.toMessage());
		}
		move();
	}

	private void move() {
		setPosition(getX(), getY() + getVy());
		if(getY() + getHeight() > range)
			setY(range - getHeight());
		if(getY() < 0) 
			setY(0);
	}

}
