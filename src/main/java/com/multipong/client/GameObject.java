package com.multipong.client;

import java.awt.Graphics;

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
}
