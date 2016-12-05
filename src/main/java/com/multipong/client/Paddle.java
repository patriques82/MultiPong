package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * The paddle of Pong.
 * 
 * @author patriknygren
 */
public abstract class Paddle implements GameObject {
	
	protected static int SPEED = 5;
	protected int width;
	protected int height;
	protected Point upperLeft; // upper left corner (for rendering)
	protected String position; // screen position of paddle
	protected int vx, vy;
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.GREEN);
		g2d.fillRect(upperLeft.x, upperLeft.y, width, height);
	}

}
