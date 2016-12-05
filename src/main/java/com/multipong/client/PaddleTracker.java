package com.multipong.client;

import com.multipong.shared.Network.PaddleMessage;

public class PaddleTracker implements MessageTracker<PaddleMessage> {

	Paddle paddle;

	@Override
	public void init(int worldWidth, int worldHeight, PaddleMessage m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void track(PaddleMessage m) {

	}
	
	public Paddle getPaddle() {
		return paddle;
	}


}
