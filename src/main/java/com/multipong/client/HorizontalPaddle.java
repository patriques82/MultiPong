package com.multipong.client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

public class HorizontalPaddle extends MyPaddle {
	
	private int worldWidth;

	public HorizontalPaddle(String pos, int worldWidth, Ball ball, int height, int width, int x, int y) {
		super(pos, ball);
		this.worldWidth = worldWidth;
		this.height = height;  
		this.width = width;
		this.upperLeft.x = x;
		this.upperLeft.y = y;
		rect = new Rectangle(upperLeft, new Dimension(width, height));
	}

	@Override
	public void tick() {
		if(KeyManager.getKeyManager().isLeftPressed()) {
			setSpeed(-SPEED, 0);
		} else if(KeyManager.getKeyManager().isRightPressed()) {
			setSpeed(SPEED, 0);
		} else {
			setSpeed(0, 0);
		}
		move();
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
	}

	private void move() {
		upperLeft.x += vx;
		if(upperLeft.x + width > worldWidth)
			upperLeft.x = worldWidth - width;
		if(upperLeft.x < 0) 
			upperLeft.x = 0;
	}


}
