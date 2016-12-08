package com.multipong.client;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.multipong.shared.Network.BallMessage;

public class BallTracker implements GameObject, MessageTracker<BallMessage> {
	
	Ball ball;

	public void init(ClientFacade facade, int worldWidth, int worldHeight, BallMessage message) {
		ball = new Ball(facade, worldWidth, worldHeight, message.d, message.x, message.y);
		ball.setSpeed(message.vx, message.vy);
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

	@Override
	public Rectangle getBoundingRect() {
		// TODO Auto-generated method stub
		return null;
	}

}
