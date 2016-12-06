package com.multipong.client;

import com.multipong.shared.Network.PaddleMessage;

public class PaddleTracker implements MessageTracker<PaddleMessage> {

	Paddle paddle;

	public void init(int worldWidth, int worldHeight, PaddleMessage m) {
		paddle = new OtherPaddle(m.position, m.width, m.height, m.x, m.y);
	}

	@Override
	public void track(PaddleMessage m) {
		
	}
	
	public Paddle getPaddle() {
		return paddle;
	}


}
