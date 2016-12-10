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
		message.x = rect.x;
		message.y = rect.y;
		message.vx = vx;
		message.vy = vy;
		return message;
	}

	/**
	 * Factory method for getting your paddle
	 * @param client client facade, used for sending events
	 * @param worldWidth
	 * @param worldHeight
	 * @param ball
	 * @param your message with configuration for your paddle
	 * @return paddle your paddle configured according to server sent world properties
	 */
	public static MyPaddle getPaddle(ClientFacade client, int worldWidth, int worldHeight, Ball ball, PaddleMessage your) {
		String pos = your.position;
		if(pos.equals("bottom") || pos.equals("up")) {
			return new HorizontalPaddle(client, pos, worldWidth, ball, your.x, your.y, your.width, your.height);
		} else if(pos.equals("left") || pos.equals("right")) {
			return new VerticalPaddle(client, pos, worldHeight, ball, your.x, your.y, your.width, your.height);
		} else {
			return null;
		}
	}

}
