package com.multipong.client;

public class OtherPaddle extends Paddle implements Trackable {

	OtherPaddle(String pos) {
		this.position = pos;
	}

	@Override
	public void tick() {
	}

	@Override
	public void setPosition(int x, int y) {
		upperLeft.x = x;
		upperLeft.y = y;
	}

	@Override
	public void setSpeed(int vx, int vy) {
		this.vx = vx;
		this.vy = vy;
	}

}
