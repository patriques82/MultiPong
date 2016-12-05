package com.multipong.client;

import java.awt.Graphics;

public class HorizontalPaddle extends MyPaddle {
	
	private int worldWidth;

	public HorizontalPaddle(String pos, int worldWidth, Ball ball) {
		super(pos, ball);
		this.worldWidth = worldWidth;
	}


	@Override
	public void tick() {
		if(KeyManager.getKeyManager().isLeftPressed()) {
			vx = -SPEED;
		}
		if(KeyManager.getKeyManager().isRightPressed()) {
			vx = SPEED;
		}
		move();
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
	}

	private void move() {
		upperLeft.x += SPEED;
		if(upperLeft.x + width > worldWidth)
			upperLeft.x = worldWidth - width;
		if(upperLeft.x < 0) 
			upperLeft.x = 0;
	}

	@Override
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setSpeed(int vx, int vy) {
		// TODO Auto-generated method stub
	}


}
