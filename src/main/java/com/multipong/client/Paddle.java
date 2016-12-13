package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * The paddle of Pong.
 * 
 * @author patriknygren
 */
public abstract class Paddle implements GameObject {
	
	protected static int SPEED = 5;
	protected String position; // screen position of paddle
	protected int vx, vy;
	protected Rectangle rect;
	
	public Paddle(int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
	}
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.GREEN);
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	public void setPosition(int x, int y) {
		rect.setLocation(x, y);
	}

	@Override
	public void setSpeed(int vx, int vy) {
		this.vx = vx;
		this.vy = vy;
	}

}
