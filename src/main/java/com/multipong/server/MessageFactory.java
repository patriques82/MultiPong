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
		prop.width = Options.getWorldWidth();
		prop.height = Options.getWorldHeight();
		prop.ball = ballMessage(prop.width, prop.height);
		prop.other = paddleMessage(opposite(pos));
		prop.your = paddleMessage(pos);
		return prop;
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

	private static PaddleMessage paddleMessage(String pos) {
		PaddleMessage other = new PaddleMessage();
		other.position = pos;
		other.width = paddleWidth(pos);
		other.height = paddleHeight(pos);
		other.x = paddleXPos(pos); 
		other.y = paddleYPos(pos);
		return other;
	}

	private static int paddleWidth(String pos) {
		if(pos.equals("up") || pos.equals("bottom"))
			return Options.getPaddleLength();
		else if(pos.equals("left") || pos.equals("right"))
			return Options.getPaddleThickness();
		else
			throw new IllegalArgumentException("Unknown position");
	}

	private static int paddleHeight(String pos) {
		if(pos.equals("up") || pos.equals("bottom"))
			return Options.getPaddleThickness();
		else if(pos.equals("left") || pos.equals("right"))
			return Options.getPaddleLength();
		else
			throw new IllegalArgumentException("Unknown position");
	}

	private static int paddleXPos(String pos) {
		if(pos.equals("up") || pos.equals("bottom"))
			return Options.getWorldWidth()/2 - Options.getPaddleLength()/2;
		else if(pos.equals("left"))
			return 0;
		else if(pos.equals("right"))
			return Options.getWorldWidth() - Options.getPaddleThickness();
		else
			throw new IllegalArgumentException("Unknown position");
	}

	private static int paddleYPos(String pos) {
		if(pos.equals("up"))
			return 0;
		else if(pos.equals("bottom"))
			return Options.getWorldHeight() - Options.getPaddleThickness();
		else if(pos.equals("left") || pos.equals("right"))
			return Options.getWorldHeight()/2 - Options.getPaddleLength()/2;
		else
			throw new IllegalArgumentException("Unknown position");
	}
	
}
