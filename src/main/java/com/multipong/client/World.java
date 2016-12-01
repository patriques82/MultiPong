package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;

class World {
	
	private Movable paddle;
	private int width;
	private int height;

	World(Movable p, int w, int h) {
		paddle = p;
		width = w;
		height = h;
	}

	void tick() {
		paddle.tick();
	}

	void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		paddle.render(g);
	}

}
