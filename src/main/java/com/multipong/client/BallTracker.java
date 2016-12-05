package com.multipong.client;

import java.awt.Graphics;

import com.multipong.shared.Network.BallMessage;

public class BallTracker implements GameObject<BallMessage>, MessageTracker<BallMessage> {
	
	Ball ball;

	@Override
	public void init(BallMessage m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void track(BallMessage m) {

	}
	
	public Ball getBall() {
		return ball;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpeed(int vx, int vy) {
		// TODO Auto-generated method stub
		
	}

}
