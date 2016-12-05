package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;

import com.multipong.shared.Network.BallMessage;
import com.multipong.shared.Network.PaddleMessage;

class World {
	
	private GameObject<BallMessage> ball;
	private GameObject<PaddleMessage> other, paddle;
	private int width;
	private int height;

	public World(int w, int h,
				 GameObject<BallMessage> ball,
				 GameObject<PaddleMessage> other,
				 GameObject<PaddleMessage> paddle) {
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
