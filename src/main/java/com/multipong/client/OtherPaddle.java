package com.multipong.client;

import java.awt.Point;

public class OtherPaddle extends Paddle {

	OtherPaddle(String pos, int width, int height, int x, int y) {
		this.position = pos;
		this.width = width;
		this.height = height;
		this.upperLeft = new Point(x, y);
	}

	@Override
	public void tick() {
		// Does nothing since it is tracked
	}

}
