package com.multipong.client;

import java.awt.Color;

import com.multipong.shared.Network.BallHitMessage;
import com.multipong.shared.Network.PaddleProperties;
import com.multipong.shared.Network.Position;

/**
 * Abstract class for paddles that the client controls 
 */
abstract class MyPaddle extends Paddle {

	protected Ball ball;
	protected ClientFacade clientFacade;
	protected int range;

	protected MyPaddle(ClientFacade facade, Ball ball, PaddleProperties props) {
		super(props, Color.ORANGE);
		this.range = props.range;
		this.clientFacade = facade;
		this.ball = ball;
	}

	/**
	 * Factory method for getting your paddle
	 */
	static MyPaddle getPaddle(ClientFacade client, Ball ball, PaddleProperties your) {
		Position pos = your.position;
		if(pos == Position.BOTTOM || pos == Position.UP) {
			return new HorizontalPaddle(client, ball, your);
		} else {
			return new VerticalPaddle(client, ball, your);
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