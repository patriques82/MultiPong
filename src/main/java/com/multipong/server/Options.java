package com.multipong.server;

public class Options {

	private static final int DEFAULT_PLAYERS = 2;
	private static final int DEFAULT_WORLD_WIDTH = 500;
	private static final int DEFUALT_WORLD_HEIGHT = 500;

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

	Options(String[] args) {
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);
		try {
			nrOfPlayers = Integer.parseInt(args[0]);
		} catch(NumberFormatException e) {
			nrOfPlayers = DEFAULT_PLAYERS;
		}
	}
	
	int getNrOfPlayers() {
		return nrOfPlayers;
	}

}
