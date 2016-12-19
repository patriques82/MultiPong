package com.multipong.server;

public class GameServer {
	
	public static void main (String[] args) {
		ServerFacade server = new ServerFacade(new MessageBus(new Options(args)));
		server.start();
	}

}
