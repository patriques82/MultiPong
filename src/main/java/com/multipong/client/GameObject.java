package com.multipong.client;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author patriknygren
 *
 */
public interface GameObject {

	/**
	 * Update the object position.
	 */
	public void tick();

	/**
	 * Render the object on Graphics.
	 * @param g
	 */
	public void render(Graphics g);
	
	public Rectangle getBoundingRect();

	public void setPosition(int x, int y);

	public void setSpeed(int vx, int vy);
}
