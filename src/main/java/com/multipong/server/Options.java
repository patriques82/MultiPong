package com.multipong.server;

import java.util.Random;

public class Options {
	private static final int DEFAULT_PLAYERS = 2;
	private static final int DEFAULT_WORLD_WIDTH = 500;
	private static final int DEFAULT_WORLD_HEIGHT = 500;

	private static final int BALL_SPEED = 10;
	private static final int BALL_DIAMETER = 10;
//	private static final int BALL_START_X = WIDTH/2;
//	private static final int BALL_START_Y = HEIGHT/2;
	
	private static final int PADDLE_THICKNESS = 10;
	private static final int PADDLE_LENGTH = 50;
	
//	private static final int PADDLE_UP_UPPER_LEFT_X = WIDTH/2 - PADDLE_THICKNESS/2;
//	private static final int PADDLE_UP_UPPER_LEFT_Y = 0;
//
//	private static final int PADDLE_BOTTOM_UPPER_LEFT_X = WIDTH/2 - PADDLE_THICKNESS/2;
//	private static final int PADDLE_BOTTOM_UPPER_LEFT_Y = HEIGHT - PADDLE_THICKNESS;
	
	private int nrOfPlayers;
	private int worldWidth, worldHeight;
	private int ballVx, ballVy;

	Options(String[] args) {
		setDefaultValues();
		try {
			if(args.length > 0 && args.length < 3) {
				nrOfPlayers = Integer.parseInt(args[0]);
			} else if(args.length == 3) {
				nrOfPlayers = Integer.parseInt(args[0]);
				worldWidth = Integer.parseInt(args[1]);
				worldHeight = Integer.parseInt(args[2]);
			}
		} catch(NumberFormatException e) {
			setDefaultValues();
		}
		setRandomBallSpeed();
	}
	
	private void setRandomBallSpeed() {
		ballVx = new Random().nextInt((BALL_SPEED) + 1);
		ballVy = BALL_SPEED - ballVx;
	}

	private void setDefaultValues() {
		nrOfPlayers = DEFAULT_PLAYERS;
		worldWidth = DEFAULT_WORLD_WIDTH;
		worldHeight = DEFAULT_WORLD_HEIGHT;
	}

	int getNrOfPlayers() {
		return nrOfPlayers;
	}

	int getWorldWidth() {
		return worldWidth;
	}

	int getWorldHeight() {
		return worldHeight;
	}

}
