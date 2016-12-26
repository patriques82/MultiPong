package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.multipong.shared.Network.PaddleProperties;

abstract class Paddle extends GameObject {
	
	protected static int SPEED = 5;
	protected String position; // screen position of paddle
	
	Paddle(PaddleProperties props) {
		super(props.x, props.y, props.width, props.height);
		this.position = props.position;
	}
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.GREEN);
		g2d.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	String getPosition() {
		return position;
	}

}
