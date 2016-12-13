package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;

class World {
	
	private GameObject ball, other, paddle;
	private int width;
	private int height;

	public World(int w, int h, GameObject ball, GameObject other, GameObject paddle) {
		width = w;
		height = h;
		this.ball = ball;
		this.other = other;
		this.paddle = paddle;
	}

	public void tick() {
		ball.tick();
		paddle.tick();
		other.tick();
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		ball.render(g);
		paddle.render(g);
		other.render(g);
	}

}
