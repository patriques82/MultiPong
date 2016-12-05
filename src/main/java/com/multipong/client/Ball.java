package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import com.multipong.shared.Network.BallMessage;


public class Ball implements GameObject<BallMessage> {
	
	private Point upperLeft; // upper left corner
	private int width, height;
	private int vx, vy; 	 // velocity (x, y)
	private int diameter;
	
	Ball(int worldWidth, int worldHeight, int diameter) {
		width = worldWidth;
		height = worldHeight;
		this.diameter = diameter;
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
	public void setPosition(int x, int y) {
		upperLeft.x = x;
		upperLeft.y = y;
	}

	@Override
	public void setSpeed(int vx, int vy) {
		this.vx = vx;
		this.vy = vy;
	}

	private boolean horisontalWallCollision() {
		return upperLeft.y <= 0 || (upperLeft.y + diameter) >= height; // upper or lower wall
	}
	
	private boolean verticalWallCollision() {
		return upperLeft.x <= 0 || (upperLeft.x + diameter) >= width; // left or right wall
	}
	
	private void bounceX() {
		//TODO: send bounce
		vx *= -1;
	}

	private void bounceY() {
		//TODO: send bounce
		vy *= -1;
	}

}
