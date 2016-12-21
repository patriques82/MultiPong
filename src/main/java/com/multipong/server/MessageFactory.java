package com.multipong.server;

import com.multipong.shared.Network.*;

public class MessageFactory {

	public WaitForOthersResponse waitResponse() {
		WaitForOthersResponse wait = new WaitForOthersResponse();
		wait.currentPlayers = 2;
		return wait;
	}

	public WorldProperties worldProperties(String pos) {
		WorldProperties prop = new WorldProperties();
		// World
		prop.width = Options.getWorldWidth();
		prop.height = Options.getWorldHeight();
		// Ball
		prop.ball = new BallMessage();
		prop.ball.x = prop.width/2;
		prop.ball.y = prop.height/2;
		prop.ball.d = Options.getBallDiameter();
		prop.ball.vx = Options.getBallVx();
		prop.ball.vy = Options.getBallVy();
		// other
		prop.other = new PaddleMessage();
		prop.other.position = "bottom";
		prop.other.height = Conf.PADDLE_THICKNESS;
		prop.other.width = Conf.PADDLE_LENGTH;
		prop.other.x = Conf.PADDLE_UP_UPPER_LEFT_X;
		prop.other.y = Conf.PADDLE_UP_UPPER_LEFT_Y;
		// your
		prop.your = new PaddleMessage();
		prop.your.position = "up";
		prop.your.height = Conf.PADDLE_THICKNESS;
		prop.your.width = Conf.PADDLE_LENGTH;
		prop.your.x = Conf.PADDLE_BOTTOM_UPPER_LEFT_X;
		prop.your.y = Conf.PADDLE_BOTTOM_UPPER_LEFT_Y;
		return prop;
	}
	
}
