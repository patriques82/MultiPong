package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;

class World {
	
	private Paddle paddle;
	private Ball ball;
	private int width;
	private int height;

	public World(int w, int h, Ball ball, Paddle paddle) {
		this.ball = ball;
		this.paddle = paddle;
		width = w;
		height = h;

	}

	void tick() {
		ball.tick();
		paddle.tick();
//		for(Paddle p : paddles)
//			p.tick();
	}

	void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		paddle.render(g);
		ball.render(g);
//		for(Paddle paddle : paddles)
//			paddle.render(g);
	}

}
