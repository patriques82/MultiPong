package com.multipong.client;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 */
abstract class GameObject {
	
	private Rectangle rect;
	protected int vx, vy;
	
	GameObject(int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
	}

	/**
	 * Update the object position.
	 */
	abstract void tick();

	/**
	 * Render the object on Graphics.
	 * @param g
	 */
	abstract void render(Graphics g);
	
	void setPosition(int x, int y) {
		rect.setLocation(x, y);
	}

	void setX(int x) {
		rect.x = x;
	}
	
	int getX() {
		return rect.x;
	}

	void setY(int y) {
		rect.y = y;
	}

	int getY() {
		return rect.y;
	}
	
	void setSpeed(int vx, int vy) {
		setVx(vx);
		setVy(vy);
	}

	void setVx(int vx) {
		this.vx = vx;
	}

	int getVx() {
		return vx;
	}

	void setVy(int vy) {
		this.vy = vy;
	}

	int getVy() {
		return vy;
	}

	int getWidth() {
		return rect.width;
	}
	
	int getHeight() {
		return rect.height;
	}

	boolean collidesWith(GameObject other) {
		return rect.intersects(other.rect);
	}
}
