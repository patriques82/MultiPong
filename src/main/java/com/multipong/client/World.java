package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;

class World implements GameObject {
	
	private Paddle other, paddle;
	private Ball ball;
	private int width;
	private int height;

	public World(int w, int h, Ball ball, Paddle other, Paddle paddle) {
		this.ball = ball;
		this.other = other;
		this.paddle = paddle;
		width = w;
		height = h;
	}

	public void tick() {
		ball.tick();
		other.tick();
		paddle.tick();
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		ball.render(g);
		other.render(g);
		paddle.render(g);
	}

}
