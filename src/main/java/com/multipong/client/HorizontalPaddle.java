package com.multipong.client;

import com.multipong.shared.Network.PaddleProperties;

class HorizontalPaddle extends MyPaddle {
	
	HorizontalPaddle(ClientFacade facade, Ball ball, PaddleProperties props) {
		super(facade, ball, props);
	}

	@Override
	public void tick() {
		updateSpeed();
		if(ballHit()) {
			ball.bounceY();
			sendBallHit(ball.toMessage());
		}
		move();
	}
	
	private void move() {
		setPosition(getX() + getVx(), getY());
		if(getX() + getWidth() > range)
			setX(range - getWidth());
		if(getX() < 0) 
			setX(0);
	}

}