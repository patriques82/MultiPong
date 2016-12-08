package com.multipong.client;

import com.multipong.shared.Network.PaddleMessage;

public class PaddleSender implements MessageSender {
	
	MyPaddle paddle;

	public void init(ClientFacade clientFacade, int worldWidth, int worldHeight, Ball ball, PaddleMessage m) {
		String pos = m.position;
		if(pos.equals("bottom") || pos.equals("up")) {
			paddle = new HorizontalPaddle(clientFacade, pos, worldWidth, ball, m.height, m.width, m.x, m.y);
		} else if(pos.equals("left") || pos.equals("right")) {
			paddle = new VerticalPaddle(clientFacade, pos, worldHeight, ball, m.height, m.width, m.x, m.y);
		} else {
			throw new RuntimeException("Not known position of your paddle");
		}
	}
	
	public Paddle getPaddle() {
		return paddle;
	}
	
	@Override
	public PaddleMessage toMessage() {
		PaddleMessage message = new PaddleMessage();
		message.position = paddle.getPosition();
		message.x = paddle.getPoint().x;
		message.y = paddle.getPoint().y;
		message.vx = paddle.getVx();
		message.vy = paddle.getVy();
		return message;
	}
	
}
