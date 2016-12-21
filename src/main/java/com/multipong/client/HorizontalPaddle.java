package com.multipong.client;

import com.multipong.shared.Network.PaddleMessage;

public class HorizontalPaddle extends MyPaddle {
	
	private int worldWidth;
	private ClientFacade clientFacade;

	public HorizontalPaddle(ClientFacade facade, String pos, int worldWidth, Ball ball, int x, int y, int w, int h) {
		super(x, y, w, h, pos, ball);
		this.clientFacade = facade;
		this.worldWidth = worldWidth;
	}

	@Override
	public void tick() {
		if(ballHit()) {
			ball.bounceY();
			clientFacade.emitEvent(ball.toMessage()); // You bounced the ball!
		}
		if(KeyManager.getKeyManager().isLeftPressed()) {
			setSpeed(-SPEED, 0);
		} else if(KeyManager.getKeyManager().isRightPressed()) {
			setSpeed(SPEED, 0);
		} else {
			setSpeed(0, 0);
		}
		move();
	}
	
	private boolean ballHit() {
		return collidesWith(ball);
	}

	private void move() {
		setPosition(getX() + getVx(), getY());
		if(getX() + getWidth() > worldWidth)
			setX(worldWidth - getWidth());
		if(getX() < 0) 
			setX(0);
	}


}
