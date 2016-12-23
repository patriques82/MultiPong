package com.multipong.server;

import com.multipong.shared.Network.*;

/**
 * Used by clients controller to create messages
 */
public class WorldFactory {

	public static WorldProperties properties(String pos) throws IllegalArgumentException {
		if(!validPosition(pos))
			throw new IllegalArgumentException("Unknown position");
		else {
			WorldProperties prop = new WorldProperties();
			prop.width = Options.getWorldWidth();
			prop.height = Options.getWorldHeight();
			prop.ball = ballMessage(prop.width, prop.height);
			prop.other = paddleMessage(opposite(pos));
			prop.your = paddleMessage(pos);
			return prop;
		}
	}

	private static boolean validPosition(String pos) {
		return pos.equals("up") || pos.equals("bottom") || pos.equals("left") || pos.equals("right");
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
		else
			return "left";
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
		else
			return Options.getPaddleThickness();
	}

	private static int paddleHeight(String pos) {
		if(pos.equals("up") || pos.equals("bottom"))
			return Options.getPaddleThickness();
		else 
			return Options.getPaddleLength();
	}

	private static int paddleXPos(String pos) {
		if(pos.equals("left"))
			return 0;
		else if(pos.equals("right"))
			return Options.getWorldWidth() - Options.getPaddleThickness();
		else
			return Options.getWorldWidth()/2 - Options.getPaddleLength()/2;
	}

	private static int paddleYPos(String pos) {
		if(pos.equals("up"))
			return 0;
		else if(pos.equals("bottom"))
			return Options.getWorldHeight() - Options.getPaddleThickness();
		else 
			return Options.getWorldHeight()/2 - Options.getPaddleLength()/2;
	}
	
}
