package com.multipong.server;

import com.multipong.shared.Network.*;

/**
 * Used by clients controller to create messages
 *
 */
public class MessageFactory {

	public static WaitForOthersResponse waitResponse() {
		WaitForOthersResponse wait = new WaitForOthersResponse();
		return wait;
	}
	
	public static WorldProperties worldProperties(String pos) {
		WorldProperties prop = new WorldProperties();
		// World
		prop.width = Options.getWorldWidth();
		prop.height = Options.getWorldHeight();
		// Ball
		prop.ball = ballMessage(prop.width, prop.height);
		// other
		prop.other = paddleMessage(opposite(pos));
		// your
		prop.your = new PaddleMessage();
		prop.your.position = pos;
		prop.your.height = Conf.PADDLE_THICKNESS;
		prop.your.width = Conf.PADDLE_LENGTH;
		prop.your.x = Conf.PADDLE_BOTTOM_UPPER_LEFT_X;
		prop.your.y = Conf.PADDLE_BOTTOM_UPPER_LEFT_Y;
		return prop;
	}

	private static PaddleMessage paddleMessage(String opposite) {
		PaddleMessage other = new PaddleMessage();
		other.position = opposite;
		other.width = paddleWidth(opposite);
		other.height = paddleHeight(opposite);
		other.x = paddleXPos(opposite); 
		other.y = paddleYPos(opposite);
		return other;
	}

	private static int paddleYPos(String opposite) {
		return 0;
	}

	private static int paddleXPos(String opposite) {
		return 0;
	}

	private static int paddleHeight(String opposite) {
		return 0;
	}

	private static int paddleWidth(String opposite) {
		return 0;
	}

	private static String opposite(String pos) {
		if(pos.equals("up"))
			return "bottom";
		else if(pos.equals("bottom"))
			return "up";
		else if(pos.equals("left"))
			return "right";
		else if(pos.equals("right"))
			return "left";
		else
			throw new IllegalArgumentException("Unknown position");
	}

	private static BallMessage ballMessage(int width, int height) {
		BallMessage ballMessage = new BallMessage();
		ballMessage.x = width/2;
		ballMessage.y = height/2;
		ballMessage.d = Options.getBallDiameter();
		ballMessage.vx = Options.getBallVx();
		ballMessage.vy = Options.getBallVy();
		return ballMessage;
	}
	
}
