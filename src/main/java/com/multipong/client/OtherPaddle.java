package com.multipong.client;

import java.awt.Point;

import com.multipong.shared.Network.PaddleMessage;

public class OtherPaddle extends Paddle implements MessageTracker<PaddleMessage> {

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

	@Override
	public void track(PaddleMessage m) {
		
	}

}
