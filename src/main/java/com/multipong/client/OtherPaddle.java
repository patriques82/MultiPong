package com.multipong.client;

import com.multipong.shared.Network.PaddleMessage;

public class OtherPaddle extends Paddle implements MessageTracker<PaddleMessage> {

	OtherPaddle(String pos, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.position = pos;
	}

	@Override
	public void tick() {
		// Does nothing since it is tracked
	}

	@Override
	public void track(PaddleMessage m) {
		
	}

}
