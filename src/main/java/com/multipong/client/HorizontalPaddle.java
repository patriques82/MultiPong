package com.multipong.client;

class HorizontalPaddle extends MyPaddle {
	
	private int worldWidth;

	HorizontalPaddle(ClientFacade facade, String pos, int worldWidth, Ball ball, int x, int y, int w, int h) {
		super(x, y, w, h, pos, ball);
		this.clientFacade = facade;
		this.worldWidth = worldWidth;
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
		if(getX() + getWidth() > worldWidth)
			setX(worldWidth - getWidth());
		if(getX() < 0) 
			setX(0);
	}


}
