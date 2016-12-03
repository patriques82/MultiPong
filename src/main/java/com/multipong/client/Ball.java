package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class Ball implements GameObject {
	
	private Point upperLeft; // upper left corner
	private int width, height;
	private int vx, vy; 	 // velocity (x, y)
	private int diameter;
	
	public Ball() {
	}

	public Ball(int worldWidth, int worldHeight, int diameter) {
		width = worldWidth;
		height = worldHeight;
		upperLeft = getUpperLeft(worldWidth, worldHeight);
		vx = ThreadLocalRandom.current().nextInt(-5, 6);
		vy = ThreadLocalRandom.current().nextInt(-5, 6);
		this.diameter = diameter;
		Logger.getLogger("game").info("init ball");
	}

	@Override
	public void tick() {
		upperLeft.x += vx;
		upperLeft.y += vy;
		if(horisontalWallCollision()) {
			bounceY();
		}
		if(verticalWallCollision()) {
			bounceX();
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillOval(upperLeft.x, upperLeft.y, diameter, diameter);
	}

	@Override
	public Point getUpperLeft(int worldWidth, int worldHeight) {
		return new Point(worldWidth/2, worldHeight/2);
	}

	private boolean horisontalWallCollision() {
		return upperLeft.y <= 0 || (upperLeft.y + diameter) >= height; // upper or lower wall
	}
	
	private boolean verticalWallCollision() {
		return upperLeft.x <= 0 || (upperLeft.x + diameter) >= width; // left or right wall
	}
	
	private void bounceX() {
		vx *= -1;
	}

	private void bounceY() {
		vy *= -1;
	}


}
