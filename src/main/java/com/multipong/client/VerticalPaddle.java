package com.multipong.client;

import java.awt.Graphics;
import java.awt.Point;

public class VerticalPaddle extends Paddle implements GameObject {

	public VerticalPaddle(Position pos, int worldWidth, int worldHeight, GameObject ball) {
		super(pos, ball);
	}

	@Override
	public Point getUpperLeft(int w, int h) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
