package com.multipong.client;

import com.multipong.shared.Network.BallHitMessage;
import com.multipong.shared.Network.PaddleMessage;

/**
 * Abstract class for paddles that the client controls 
 */
abstract class MyPaddle extends Paddle implements MessageHandler<PaddleMessage> {

	private PaddleMessage message;

	protected Ball ball;
	protected ClientFacade clientFacade;

	protected MyPaddle(int x, int y, int w, int h, String pos, Ball ball) {
		super(x, y, w, h);
		this.position = pos;
		this.ball = ball;
		message = new PaddleMessage();
	}

	/**
	 * Factory method for getting your paddle
	 */
	static MyPaddle getPaddle(ClientFacade client, int worldWidth, int worldHeight, Ball ball, PaddleMessage your) {
		String pos = your.position;
		if(pos.equals("bottom") || pos.equals("up")) {
			return new HorizontalPaddle(client, pos, worldWidth, ball, your.x, your.y, your.width, your.height);
		} else if(pos.equals("left") || pos.equals("right")) {
			return new VerticalPaddle(client, pos, worldHeight, ball, your.x, your.y, your.width, your.height);
		} else {
			throw new IllegalArgumentException("Unknown paddle position");
		}
	}

	protected boolean ballHit() {
		return collidesWith(ball);
	}

	protected void sendBallHit(BallHitMessage message) {
		message.position = position;
		clientFacade.emitEvent(message);
	}
	
	protected void updateSpeed() {
		setSpeed(0,0);
		if(KeyManager.getKeyManager().isLeftPressed())
			setVx(-SPEED);
		if(KeyManager.getKeyManager().isRightPressed())
			setVx(SPEED);
		if(KeyManager.getKeyManager().isUpPressed())
			setVy(-SPEED);
		if(KeyManager.getKeyManager().isDownPressed())
			setVy(SPEED);
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
		if(position.equals(message.position)) {
			setPosition(message.x, message.y);
			setSpeed(message.vx, message.vy);
		}
	}

}
