package com.multipong.client;

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
		return rect.intersects(ball.getBoundingRect());
	}

	private void move() {
		rect.setLocation(rect.x + vx, rect.y);
		if(rect.x + rect.width > worldWidth)
			rect.x = worldWidth - rect.width;
		if(rect.x < 0) 
			rect.x = 0;
	}


}
