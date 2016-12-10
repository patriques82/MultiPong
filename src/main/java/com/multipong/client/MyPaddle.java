package com.multipong.client;

import java.awt.Point;

import com.multipong.shared.Network.PaddleMessage;

public abstract class MyPaddle extends Paddle implements MessageSender<PaddleMessage> {

	protected Ball ball;
	private PaddleMessage message;

	protected MyPaddle(String pos, Ball ball) {
		this.position = pos;
		this.ball = ball;
		this.upperLeft = new Point();
		message = new PaddleMessage();
	}

	@Override
	public PaddleMessage toMessage() {
		message.position = position;
		message.x = upperLeft.x;
		message.y = upperLeft.y;
		message.vx = vx;
		message.vy = vy;
		return message;
	}

	public static MyPaddle getPaddle(ClientFacade client, int width, int height, Ball ball, PaddleMessage your) {
		String pos = your.position;
		if(pos.equals("bottom") || pos.equals("up")) {
			return new HorizontalPaddle(client, pos, width, ball, your.width, your.height, your.x, your.y);
		} else if(pos.equals("left") || pos.equals("right")) {
			return new VerticalPaddle(client, pos, height, ball, your.width, your.height, your.x, your.y);
		} else {
			return null;
		}
	}

}
