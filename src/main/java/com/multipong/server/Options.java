package com.multipong.server;

import java.util.Random;

class Options {
	private static final int DEFAULT_PLAYERS = 2;
	private static final int DEFAULT_WORLD_WIDTH = 500;
	private static final int DEFAULT_WORLD_HEIGHT = 500;

	private static final int BALL_SPEED = 10;
	private static final int BALL_DIAMETER = 10;

	private static final int PADDLE_THICKNESS = 10;
	private static final int PADDLE_LENGTH = 50;
	
	private static int nrOfPlayers;
	private static int worldWidth, worldHeight;
	private static int ballVx, ballVy;

	static void init(String[] args) {
		setRandomBallSpeed();
		setDefaultValues();
		if(args.length > 0) {
			try {
				if(args.length < 3) {
					nrOfPlayers = Integer.parseInt(args[0]);
				} else if(args.length >= 3) {
					nrOfPlayers = Integer.parseInt(args[0]);
					worldHeight = Integer.parseInt(args[2]);
					worldWidth = Integer.parseInt(args[1]);
				}
			} catch(NumberFormatException e) {
				setDefaultValues();
			}
		}
	}
	
	private static void setRandomBallSpeed() {
		ballVx = new Random().nextInt(BALL_SPEED + 1);
		ballVy = BALL_SPEED - ballVx;
	}

	private static void setDefaultValues() {
		nrOfPlayers = DEFAULT_PLAYERS;
		worldHeight = DEFAULT_WORLD_HEIGHT;
		worldWidth = DEFAULT_WORLD_WIDTH;
	}

	static int getNrOfPlayers() {
		return nrOfPlayers;
	}

	static int getWorldWidth() {
		return worldWidth;
	}

	static int getWorldHeight() {
		return worldHeight;
	}

	static int getBallVx() {
		return ballVx;
	}

	static int getBallVy() {
		return ballVy;
	}

	static int getBallDiameter() {
		return BALL_DIAMETER;
	}

	static int getPaddleThickness() {
		return PADDLE_THICKNESS;
	}

	static int getPaddleLength() {
		return PADDLE_LENGTH;
	}

}
