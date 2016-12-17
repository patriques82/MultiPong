package com.multipong.client;

import com.multipong.shared.Network.PaddleMessage;

public class OtherPaddle extends Paddle implements MessageTracker<PaddleMessage> {

	OtherPaddle(PaddleMessage paddleProps) {
		super(paddleProps.x, paddleProps.y, paddleProps.width, paddleProps.height);
		this.position = paddleProps.position;
	}

	@Override
	public void tick() {
		// Does nothing since it is tracked
	}

	@Override
	public void trackMessage(PaddleMessage m) {
		
	}

}
