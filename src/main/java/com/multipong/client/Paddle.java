package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The paddle of Pong.
 * 
 * @author patriknygren
 */
public abstract class Paddle extends GameObject {
	
	protected static int SPEED = 5;
	protected String position; // screen position of paddle
	
	public Paddle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.GREEN);
		g2d.fillRect(getX(), getY(), getWidth(), getHeight());
	}

}
