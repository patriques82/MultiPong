package com.multipong.client;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 */
public abstract class GameObject {
	
	private Rectangle rect;
	protected int vx, vy;
	
	public GameObject(int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
	}

	/**
	 * Update the object position.
	 */
	abstract public void tick();

	/**
	 * Render the object on Graphics.
	 * @param g
	 */
	abstract public void render(Graphics g);
	
	public void setPosition(int x, int y) {
		rect.setLocation(x, y);
	}

	public void setX(int x) {
		rect.x = x;
	}
	
	public int getX() {
		return rect.x;
	}

	public void setY(int y) {
		rect.y = y;
	}

	public int getY() {
		return rect.y;
	}
	
	public void setSpeed(int vx, int vy) {
		setVx(vx);
		setVy(vy);
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVx() {
		return vx;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	public int getVy() {
		return vy;
	}

	public int getWidth() {
		return rect.width;
	}
	
	public int getHeight() {
		return rect.height;
	}

	public boolean collidesWith(GameObject other) {
		return rect.intersects(other.rect);
	}
}
