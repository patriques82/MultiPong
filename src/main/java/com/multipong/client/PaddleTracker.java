package com.multipong.client;

import com.multipong.shared.Network.PaddleMessage;

public class PaddleTracker implements MessageTracker<PaddleMessage, OtherPaddle> {

	@Override
	public OtherPaddle init(int width, int height, PaddleMessage message) {
		return null;
	}

	@Override
	public void track(PaddleMessage m) {

	}

}
