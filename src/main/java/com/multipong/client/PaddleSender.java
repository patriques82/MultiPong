package com.multipong.client;

import com.multipong.shared.Network.PaddleMessage;

public class PaddleSender implements MessageSender<PaddleMessage, MyPaddle> {
	
	private MyPaddle myPaddle;

	@Override
	public void setSender(MyPaddle sendable) {
		this.myPaddle = sendable;
	}

	@Override
	public PaddleMessage toMessage() {
		PaddleMessage message = new PaddleMessage();
		message.position = myPaddle.getPosition();
		message.x = myPaddle.getPoint().x;
		message.y = myPaddle.getPoint().y;
		message.vx = myPaddle.getVx();
		message.vy = myPaddle.getVy();
		return message;
	}

}
