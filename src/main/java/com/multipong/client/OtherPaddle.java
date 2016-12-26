package com.multipong.client;

import com.multipong.shared.Network.PaddleMessage;
import com.multipong.shared.Network.PaddleProperties;

class OtherPaddle extends Paddle implements MessageHandler<PaddleMessage> {

	OtherPaddle(PaddleProperties paddleProps) {
		super(paddleProps);
	}

	@Override
	public void tick() {
		// Does nothing since it is tracked
	}

	@Override
	public void trackMessage(PaddleMessage m) {
		
	}

	@Override
	public PaddleMessage toMessage() {
		return null;
	}

}
