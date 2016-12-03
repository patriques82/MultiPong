package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;

class World {
	
	private Collection<GameObject> paddles;
	private GameObject ball;
	private int width;
	private int height;

	public World(int w, int h, GameObject ball, Collection<GameObject> paddles) {
		this.paddles = paddles;
		this.ball = ball;
		width = w;
		height = h;
	}

	void tick() {
		ball.tick();
		for(GameObject paddle : paddles)
			paddle.tick();
	}

	void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		ball.render(g);
		for(GameObject paddle : paddles)
			paddle.render(g);
	}

}
