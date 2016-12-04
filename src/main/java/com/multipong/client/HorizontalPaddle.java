package com.multipong.client;

import java.awt.Graphics;

public class HorizontalPaddle extends MyPaddle {
	
	private int worldWidth;

	public HorizontalPaddle(String pos, int width, int height, int worldWidth, Ball ball) {
		super(pos, ball);
		this.width = width;
		this.height = height;
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

//	@Override
//	public Point getUpperLeft(int worldWidth, int worldHeight) {
//		if(position == Position.UP) {
//			return new Point((worldWidth/2) - (width/2), 0);
//		} else if(position == Position.BOTTOM) {
//			return new Point((worldWidth/2) - (width/2), (worldHeight - height));
//		} else {
//			throw new IllegalArgumentException("Horisontal paddle can only take up or down");
//		}
//	}
	
	private void move() {
		upperLeft.x += SPEED;
		if(upperLeft.x + width > worldWidth)
			upperLeft.x = worldWidth - width;
		if(upperLeft.x < 0) 
			upperLeft.x = 0;
	}

}
