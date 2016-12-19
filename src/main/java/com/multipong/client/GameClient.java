package com.multipong.client;

public class GameClient {

	public static void main(String[] args) {
		Game game = new Game(new ClientFacade());
		game.start();
	}

}
