package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class HorizontalPaddle extends Paddle {

	HorizontalPaddle(Position pos, int w, int h) {
		super(pos, w, h);
		width = (int) (w * SIZE_PERCENTAGE_OF_WINDOW);
		height = THICKNESS;
		upperLeft = getUpperLeft(position, w, h);
	}

	@Override
	protected Point getUpperLeft(Position pos, int w, int h) {
		if(pos == Position.UP) {
			return new Point((w/2) - (width/2), 0);
		} else if(pos == Position.DOWN) {
			return new Point((w/2) - (width/2), (h - height));
		} else {
			throw new IllegalArgumentException("Horisontal paddle can only take up or down");
		}
	}

	@Override
	void tick() {
		if(KeyManager.getKeyManager().isLeftPressed()) {
			moveLeft();
		}
		if(KeyManager.getKeyManager().isRightPressed()) {
			moveRight();
		}
	}
	
	@Override
	void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.GREEN);
		g2d.fillRect(upperLeft.x, upperLeft.y, width, height);
	}
	
	private void moveLeft() {
		this.upperLeft.x -= SPEED;
	}

	private void moveRight() {
		this.upperLeft.x += SPEED;
	}


}
