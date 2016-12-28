package com.multipong.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.multipong.shared.Network.PaddleMessage;
import com.multipong.shared.Network.PaddleProperties;
import com.multipong.shared.Network.Position;

abstract class Paddle extends GameObject implements MessageHandler<PaddleMessage> {
	
	protected static int SPEED = 5;
	protected Position position; // screen position of paddle

	private PaddleMessage message;
	private Color color;
	
	Paddle(PaddleProperties props, Color col) {
		super(props.x, props.y, props.width, props.height);
		position = props.position;
		message = new PaddleMessage();
		color = col;
	}

	@Override
	public void tick() { }

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.fillRect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public PaddleMessage toMessage() {
		message.position = position;
		message.x = getX();
		message.y = getY();
		message.vx = getVx();
		message.vy = getVy();
		return message;
	}
	
	@Override
	public void trackMessage(PaddleMessage message) {
		if(position == message.position) {
			setPosition(message.x, message.y);
			setSpeed(message.vx, message.vy);
		}
	}

	Position getPosition() {
		return position;
	}

}
