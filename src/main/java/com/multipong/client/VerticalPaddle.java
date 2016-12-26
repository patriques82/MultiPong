package com.multipong.client;

class VerticalPaddle extends MyPaddle {

	private int worldHeight;

	VerticalPaddle(ClientFacade facade, String pos, int worldHeight, Ball ball, int x, int y, int w, int h) {
		super(x, y, w, h, pos, ball);
		this.clientFacade = facade;
		this.worldHeight = worldHeight;
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
		if(getY() + getHeight() > worldHeight)
			setY(worldHeight - getHeight());
		if(getY() < 0) 
			setY(0);
	}

}
