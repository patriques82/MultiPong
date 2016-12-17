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
		this.paddle = paddle;
		this.other = other;
		this.ball = ball;
	}

	public void tick() {
		paddle.tick();
		other.tick();
		ball.tick();
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		paddle.render(g);
		other.render(g);
		ball.render(g);
	}

}
