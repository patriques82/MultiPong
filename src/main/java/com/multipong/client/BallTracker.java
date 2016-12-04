package com.multipong.client;

import com.multipong.shared.Network.BallMessage;

public class BallTracker implements MessageTracker<BallMessage, Ball> {
	
	private Ball ball;

	@Override
	public Ball init(int width, int height, BallMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void track(BallMessage m) {
		// TODO Auto-generated method stub

	}

}
