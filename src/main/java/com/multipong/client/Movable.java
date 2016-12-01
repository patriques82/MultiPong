package com.multipong.client;

import java.awt.Graphics;
import java.awt.Point;

interface Movable {

	/**
	 * Get the upper left corner used for rendering
	 * @param worldWidth width of world
	 * @param worldHeight height of world
	 * @return point
	 */
	public Point getUpperLeft(int worldWidth, int worldHeight);

	/**
	 * Update the object position.
	 */
	public void tick();

	/**
	 * Render the object on Graphics.
	 * @param g
	 */
	public void render(Graphics g);
}
