package com.multipong.client;

import java.awt.Graphics;

public class VerticalPaddle extends MyPaddle {

	VerticalPaddle(String pos, int width, int height, int worldHeight, Ball ball) {
		super(pos, ball);
		this.width = width;
		this.height = height;
//		this.worldHeight = worldHeight;
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
