package com.multipong.client;

import com.multipong.shared.Network.PaddleProperties;
import com.multipong.shared.Network.BallHitMessage;
import com.multipong.shared.Network.PaddleMessage;

/**
 * Abstract class for paddles that the client controls 
 */
abstract class MyPaddle extends Paddle implements MessageHandler<PaddleMessage> {

	protected Ball ball;
	protected ClientFacade clientFacade;
	protected int range;

	private PaddleMessage message;

	protected MyPaddle(ClientFacade facade, Ball ball, PaddleProperties props) {
		super(props);
		this.range = props.range;
		this.clientFacade = facade;
		this.ball = ball;
		message = new PaddleMessage();
	}

	/**
	 * Factory method for getting your paddle
	 */
	static MyPaddle getPaddle(ClientFacade client, Ball ball, PaddleProperties your) {
		String pos = your.position;
		if(pos.equals("bottom") || pos.equals("up")) {
			return new HorizontalPaddle(client, ball, your);
		} else if(pos.equals("left") || pos.equals("right")) {
			return new VerticalPaddle(client, ball, your);
		} else {
			throw new IllegalArgumentException("Unknown paddle position");
		}
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

}