package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class HorizontalPaddle extends Paddle {
	
	private int screenWidth;

	HorizontalPaddle(Position pos, int worldWidth, int worldHeight, GameObject ball) {
		super(pos, ball);
		width = (int) (worldWidth * SIZE_PERCENTAGE_OF_WINDOW);
		height = THICKNESS;
		screenWidth = worldWidth;
		upperLeft = getUpperLeft(worldWidth, worldHeight);
	}

	@Override
	public void tick() {
		if(KeyManager.getKeyManager().isLeftPressed()) {
			moveLeft();
		}
		if(KeyManager.getKeyManager().isRightPressed()) {
			moveRight();
		}
	}
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.GREEN);
		g2d.fillRect(upperLeft.x, upperLeft.y, width, height);
	}

	@Override
	public Point getUpperLeft(int worldWidth, int worldHeight) {
		if(position == Position.UP) {
			return new Point((worldWidth/2) - (width/2), 0);
		} else if(position == Position.BOTTOM) {
			return new Point((worldWidth/2) - (width/2), (worldHeight - height));
		} else {
			throw new IllegalArgumentException("Horisontal paddle can only take up or down");
		}
	}
	
	private void moveLeft() {
		upperLeft.x -= SPEED;
		if(upperLeft.x < 0) 
			upperLeft.x = 0;
	}

	private void moveRight() {
		upperLeft.x += SPEED;
		if(upperLeft.x + width > screenWidth)
			upperLeft.x = screenWidth - width;
	}


}
