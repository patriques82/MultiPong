package com.multipong.client;

import java.awt.Dimension;
import java.awt.Rectangle;

public class HorizontalPaddle extends MyPaddle {
	
	private int worldWidth;
	private ClientFacade clientFacade;

	public HorizontalPaddle(ClientFacade facade, String pos, int worldWidth, Ball ball, int w, int h, int x, int y) {
		super(pos, ball);
		this.clientFacade = facade;
		this.worldWidth = worldWidth;
		this.height = h;
		this.width = w;
		this.upperLeft.x = x;
		this.upperLeft.y = y;
		rect = new Rectangle(upperLeft, new Dimension(width, height));
	}

	@Override
	public void tick() {
		if(ballHit()) {
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
		return rect.intersects(ball.getBoundingRect());
	}

	private void move() {
		upperLeft.x += vx;
		if(upperLeft.x + width > worldWidth)
			upperLeft.x = worldWidth - width;
		if(upperLeft.x < 0) 
			upperLeft.x = 0;
	}


}
