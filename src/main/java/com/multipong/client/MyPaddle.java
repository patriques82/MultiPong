package com.multipong.client;

import com.multipong.shared.Network.PaddleMessage;

/**
 * Abstract class for paddles that the client controls 
 */
public abstract class MyPaddle extends Paddle implements MessageSender<PaddleMessage> {

	protected Ball ball;
	private PaddleMessage message;

	protected MyPaddle(int x, int y, int w, int h, String pos, Ball ball) {
		super(x, y, w, h);
		this.position = pos;
		this.ball = ball;
		message = new PaddleMessage();
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

	/**
	 * Factory method for getting your paddle
	 */
	public static MyPaddle getPaddle(ClientFacade client, int worldWidth, int worldHeight, Ball ball, PaddleMessage your) {
		String pos = your.position;
		if(pos.equals("bottom") || pos.equals("up")) {
			return new HorizontalPaddle(client, pos, worldWidth, ball, your.x, your.y, your.width, your.height);
		} else if(pos.equals("left") || pos.equals("right")) {
			return new VerticalPaddle(client, pos, worldHeight, ball, your.x, your.y, your.width, your.height);
		} else {
			throw new IllegalArgumentException("Unknown paddle position");
		}
	}

}
